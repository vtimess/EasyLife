package com.heo.service;

import com.heo.domain.Help;
import com.heo.domain.Result;
import com.heo.domain.User;
import com.heo.domain.Wallet;
import com.heo.enums.ResultEnum;
import com.heo.exception.MyException;
import com.heo.mina.UserSession;
import com.heo.repository.HelpRepository;
import com.heo.repository.UserRepository;
import com.heo.utils.ResultUtil;
import com.heo.utils.UploadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class HelpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HelpRepository helpRepository;


    @Autowired
    private WalletService walletService;

    /**
     * 发布help
     * @return
     */
    @Transactional
    public Result releaseHelp(Help help,MultipartFile image){
        help.setStatus(0);
        User user = userRepository.getOne(help.getUserId());
        if(user==null)
            throw new MyException(ResultEnum.REQUEST_EXCEPTION);

        help.setCredit(user.getCredit());
        help.setReleaseDate(new Date());
        help.setImagesUrl(uploadHelpImages(image));

        return ResultUtil.success("发布成功",helpRepository.save(help));
    }

    /**
     * 发布者
     * 取消自己发布的help
     */
    @Transactional
    public Result cancelHelp(Integer helpId,Integer userId){
        if(userRepository.getOne(userId) == null)
            throw new MyException(ResultEnum.REQUEST_EXCEPTION);
        Help help = helpRepository.findOne(helpId);
        if(help == null)
            throw new MyException(ResultEnum.REQUEST_EXCEPTION);
        //判断订单状态
        if (help.getStatus() == 0) {
            //发布中
            helpRepository.delete(helpId);
            return ResultUtil.success();
        }
        else{
            return ResultUtil.error(10086,"你发布的内容已经被接受。");
        }

    }

    /**
     * 发布者
     * 强制取消自己发布的信息
     * @return
     */
    @Transactional
    public Result coercionDelSelfReleaseHelp(Integer helpId,Integer userId) {
        User user = userRepository.getOne(userId);
        if(userRepository.getOne(userId) == null)
            throw new MyException(ResultEnum.REQUEST_EXCEPTION);
        Help help = helpRepository.findOne(helpId);
        if(help == null)
            throw new MyException(ResultEnum.REQUEST_EXCEPTION);

        //删除
        help.setStatus(-1);
        helpRepository.save(help);

        //判断订单状态
        if (help.getStatus() == 0) {
            return ResultUtil.success();
        }
        else{
            float credit = user.getCredit();
            Integer canceNumber = user.getCanceNumber();
            canceNumber++;
            credit -=  help.getMoney() * canceNumber * 7;
            if (credit<0)
                credit = 0f;

            user.setCanceNumber(canceNumber);
            user.setCredit(credit);
            userRepository.save(user);

            //去通知被取消人
            Integer acceptUserId = help.getAcceptUserId();
            UserSession.sendNotice(acceptUserId,"你接受的任务被发布者强制取消了！");


            //代完成
            helpRepository.delete(helpId);

            return ResultUtil.success();
        }

    }

    /**
     * 上传任务图片
     * 返回图片地址
     */
    public String uploadHelpImages(MultipartFile file){
        String imgs="";
        if (file==null)
            return imgs;
        String imgPath = "G:/EasyLife/public/helpImage/";
        String imgName = UUID.randomUUID() + file.getOriginalFilename();
        try {
            UploadFileUtil.uploadFile(file.getBytes(), imgPath, imgName);
            imgs = "helpImage/"+ imgName;
        } catch (Exception e) {
            throw new MyException(ResultEnum.IMAGE_LOAD_FAIL);
        }
        return imgs;
    }



    /**
     * 接收者
     * 接受help
     */
    public Result acceptHelp(int helpId, int userId) {
        Help help = helpRepository.findOne(helpId);
        User releaseUser = userRepository.findOne(help.getUserId());
        User acceptUser = userRepository.findOne(userId);

        if (help == null)
            throw new MyException(ResultEnum.HELP_FIND_NO);
        if (acceptUser == null)
            throw new MyException(ResultEnum.REQUEST_EXCEPTION);

        help.setStatus(1);
        help.setAcceptUserId(acceptUser.getUserId());
        help.setAcceptDate(new Date());

        //通知
        UserSession.sendNotice(releaseUser.getUserId(),"你发布的任务被接受了");

        return ResultUtil.success(helpRepository.save(help));
    }

    /**
     * 接单者
     * 完成help
     */
    @Transactional
    public Result completeHelp(Integer helpId, Integer acceptUserId) {

        Help help = helpRepository.findOne(helpId);
        User releaseUser = userRepository.findOne(help.getUserId());
        User acceptUser = userRepository.findOne(acceptUserId);

        if (help == null)
            throw new MyException(ResultEnum.HELP_FIND_NO);
        if (acceptUser == null || releaseUser ==null)
            throw new MyException(ResultEnum.REQUEST_EXCEPTION);

        help.setStatus(2); //设置完成 等待付款
        help.setCompleteDate(new Date());

        releaseUser.setLack(true);
        userRepository.save(releaseUser);

        //通知
        UserSession.sendNotice(releaseUser.getUserId(),"你发布的任务已经被完成了");


        return ResultUtil.success(helpRepository.save(help));
    }

    /**
     * 发布者
     * 付款
     */
    @Transactional
    public Result payment(Integer helpId, Integer userId){

        Help help = helpRepository.findOne(helpId);
        User releaseUser = userRepository.findOne(userId);
        if (help == null)
            throw new MyException(ResultEnum.HELP_FIND_NO);
        if (releaseUser == null)
            throw new MyException(ResultEnum.REQUEST_EXCEPTION);

        User acceptUser = userRepository.findOne(help.getAcceptUserId());

        help.setStatus(3);
        help.setPayDate(new Date());
        helpRepository.save(help);

        releaseUser.setCredit(releaseUser.getCredit() + help.getMoney() + 2);
        userRepository.save(releaseUser);

        acceptUser.setCredit(acceptUser.getCredit() + help.getMoney());
        userRepository.save(acceptUser);

        float money = help.getMoney();

        //返回钱包信息
        Wallet wallet = walletService.payment(userId,acceptUser.getUserId(),money);

       // 通知acceptUser去评价
        UserSession.sendNotice(acceptUser.getUserId(),"对方已经完成付款");

        return ResultUtil.success();
    }
}
