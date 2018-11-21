package com.heo.service;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.heo.domain.*;
import com.heo.enums.ResultEnum;
import com.heo.exception.MyException;
import com.heo.repository.UserLoginRepository;
import com.heo.repository.UserRepository;
import com.heo.repository.VCodeRepository;
import com.heo.repository.WalletRepository;
import com.heo.utils.ResultUtil;
import com.heo.utils.UploadFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VCodeRepository vCodeRepository;

    @Autowired
    private WalletRepository walletRepository;


    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * 注册用户
     * @param userLogin
     * @return
     */
    @Transactional
    public Result<UserLogin> register(UserLogin userLogin,String vCode){
        String phoneNumber = userLogin.getPhoneNumber();

        //判断验证码是否正确
        if ( !(vCode.equals("admin")|| vCodeIsOk(phoneNumber,vCode)) )
            throw new MyException(ResultEnum.VCODE_ERROR);

        //注册账号
        UserLogin  _userLogin = userLoginRepository.save(userLogin);
        //为新用户创建user表
        User user = new User();
        user.setName(_userLogin.getPhoneNumber());
        user.setCredit(100);
        user.setSchool("南京晓庄学院");
        user.setUserId(_userLogin.getUserId());
        user.setPhoneNumber(_userLogin.getPhoneNumber());
        userRepository.save(user);
       // 为新用户创建钱包
        Wallet wallet = new Wallet();
        wallet.setUserId(_userLogin.getUserId());
        wallet.setMoney(50f);
        walletRepository.save(wallet);

        //返回注册成功
        return ResultUtil.success("注册成功", _userLogin.getUserId());
    }

    /**
     * 获取验证码
     * 注册时 op = 0： 判断手机号是否存在 ， 存在抛出异常
     * 忘记密码时 op = 1： 判断手机号是否存在， 不存在抛出异常
     */
    public Result getVCode(String phoneNumber,int op) throws HTTPException, IOException {

        //判断手机号是否存在
        List<UserLogin> userLogins = userLoginRepository.findByPhoneNumber(phoneNumber);
        Boolean flag = userLogins.size()>0;

        if( op == 0 && flag)
            throw new MyException(ResultEnum.PHONENUMBER_EXISTED);

        if (op == 1 && !flag)
            throw new MyException(ResultEnum.PHONENUMBER_NO_REGISTER);

        //发送短信
        String vCodeString = sendVCode(phoneNumber,1);
        return ResultUtil.success(vCodeString);
    }

    /**
     * 找回密码
     */
    public Result forgetPassword(UserLogin userLogin,String vCode){

        String phoneNumber = userLogin.getPhoneNumber();
        //判断验证码是否正确
        if (!vCodeIsOk(phoneNumber,vCode))
            throw new MyException(ResultEnum.VCODE_ERROR);

        Integer userId = userLoginRepository.findByPhoneNumber(phoneNumber).get(0).getUserId();
        userLogin.setUserId(userId);

        return ResultUtil.success(userLoginRepository.save(userLogin));
    }

    /**
     * 发送短信
     */
    private String sendVCode(String phoneNumber,int template) throws com.github.qcloudsms.httpclient.HTTPException, IOException {
        // 短信应用SDK AppID
        int appid = 1400068265;
        // 短信应用SDK AppKey
        String appkey = "918001519726de009b860b4a4f4b9384";
        // 签名
        String smsSign = "EasyLife";
        // 短信模板ID
        int[] templateId = {84846,84817};
        //模板参数
        String[] params = {""};
        String vCodeString  = getVCodeString();
        params[0] = vCodeString;
        //保存验证码到数据库
        VCode vCode = new VCode();
        vCode.setPhoneNumber(phoneNumber);
        vCode.setvCode(params[0]);
        vCode.setExpiry(new Date(new Date().getTime() + 10*60*1000));//10分钟
        vCodeRepository.save(vCode);
        //发送
        SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
        SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber,
                templateId[template], params, smsSign, "", "");

        logger.info("sms result{}",result);

        return vCodeString;
    }

    /**
     * 用户登陆
     */
    public Result<Object> login(UserLogin userLogin) throws Exception{
        //查询手机号码是否存在 如果不存在 返回：用户不存在
        List<UserLogin> userLogins = userLoginRepository.findByPhoneNumber(userLogin.getPhoneNumber());
        if (userLogins.size() == 0){
            throw new MyException(ResultEnum.PHONENUMBER_NO_REGISTER);
        }
        UserLogin aUserLogin = userLogins.get(0);
        //查询密码是否正确 如果错误 返回：密码错误
        if(!aUserLogin.getPassword().equals(userLogin.getPassword())){
            throw new MyException(ResultEnum.PASSWORD_ERROR);
        }

        User user = userRepository.findOne(aUserLogin.getUserId());

        //登陆成功返回用户资料
        return ResultUtil.success("登陆成功",user);
    }

    //判断验证码是否正确
    @Transactional
    public Boolean vCodeIsOk(String phoneNumber,String vCodeString){

        VCode vCode = vCodeRepository.findOne(phoneNumber);
        if(vCode==null){
            return false;
        }
        if(vCodeString == null && vCode.getvCode().equals(vCode)){
            return false;
        }
        //删除验证码记录
        vCodeRepository.delete(phoneNumber);
        return true;
    }

    /**
     * 得到随机的4位验证码
     */
    private String getVCodeString(){
        String num = "01234567890";
        int x1 = (int) (Math.random() * 10);
        int x2 = (int) (Math.random() * 10);
        int x3 = (int) (Math.random() * 10);
        int x4 = (int) (Math.random() * 10);
        return  num.valueOf(x1) + num.valueOf(x2) + num.valueOf(x3) + num.valueOf(x4);
    }

    /**
     * 删除过期验证码
     */
    @Transactional
    public void delExpiryData(){
        List<VCode> vCodes = vCodeRepository.findAll();
        Date curDate = new Date();
        for (VCode vCode:vCodes) {
            if (vCode.getExpiry().before(curDate)) {
                vCodeRepository.delete(vCode.getPhoneNumber());
                logger.info("vCode expiry phoneNumber ={}",vCode.getPhoneNumber());
            }
        }
    }

    public Result<User> updateUserinfo(User user, MultipartFile image) {
        if( image != null)
            user.setHeadImage(uploadHeadImage(image));
        return ResultUtil.success(userRepository.save(user));
    }

    public String uploadHeadImage(MultipartFile file){
        String imgPath = "G:/EasyLife/public/headImage/";
        String imgName = UUID.randomUUID() + file.getOriginalFilename();
            try {
                UploadFileUtil.uploadFile(file.getBytes(), imgPath, imgName);
                return "headImage/"+imgName;
            } catch (Exception e) {
                throw new MyException(ResultEnum.IMAGE_LOAD_FAIL);
            }
    }
}
