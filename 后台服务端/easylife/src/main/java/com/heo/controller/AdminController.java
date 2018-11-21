package com.heo.controller;

import com.heo.domain.*;
import com.heo.mina.UserSession;
import com.heo.repository.HelpRepository;
import com.heo.repository.UserLoginRepository;
import com.heo.repository.UserRepository;
import com.heo.repository.WalletRepository;
import com.heo.service.HelpService;
import com.heo.service.UserService;
import com.heo.utils.ResultUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HelpRepository helpRepository;

    @Autowired
    private HelpService helpService;

    @Autowired
    private WalletRepository walletRepository;

    /**
     * 添加一个用户
     */
    @PostMapping(value = "/admin/user")
    public Result addUser(@Valid UserLogin userLogin,BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }
        return userService.register(userLogin,"admin");

    }
    /**
     * 删除一个用户
     */
    @DeleteMapping(value = "/admin/user/{userId}")
    public Result delUser(@PathVariable Integer userId){
        userRepository.delete(userId);
        userLoginRepository.delete(userId);
        walletRepository.delete(userId);
        return ResultUtil.success();
    }

    /**
     *  查看一个用户资料
     */
    @GetMapping(value = "/admin/user/{userId}")
    public Result getUser(@PathVariable(required = false) Integer userId){
            return ResultUtil.success(userRepository.findOne(userId));
    }

    /**
     *  查看所有用户资料
     */
    @GetMapping(value = "/admin/user/all")
    public Result getAllUser(){
        return ResultUtil.success(userRepository.findAll());
    }

    /**
     * 修改用户资料
     */
    @PostMapping(value = "/admin/userinfo")
    public Result updateUser(@Valid User user,BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return ResultUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        user.setPhoneNumber(userLoginRepository.getOne(user.getUserId()).getPhoneNumber());

        return ResultUtil.success(userRepository.save(user));
    }

    /**
     * 查看所有help
     */
    @GetMapping(value = "/admin/help/all")
    public Result getAllHelp(){
        return ResultUtil.success(helpRepository.findAll());
    }

    /**
     * 发布一条help
     */
    @PostMapping(value = "/admin//help")
    public Result<Help> releaseHelp(@Valid Help help, BindingResult bindingResult,
                                    @RequestParam(value = "image",required = false) MultipartFile image) {

        if (bindingResult.hasErrors()) {
            return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }
        return helpService.releaseHelp(help,image);
    }

    /**
     * 查看所有发布中的help
     */
    @GetMapping(value = "/admin/help/release")
    public Result getAllReleaseHelp(){
        return ResultUtil.success(helpRepository.findAllByStatusOrderByReleaseDateDesc(0));
    }

    /**
     * 删除一个help
     */
    @DeleteMapping(value = "/admin/help/{helpId}")
    public Result delHelp(@PathVariable Integer helpId){
        helpRepository.delete(helpId);
        return ResultUtil.success();
    }

    /**
     * 查看所有用户的余额
     */
    @GetMapping(value = "/admin/wallet")
    public Result getWallet(){
        return ResultUtil.success(walletRepository.findAll());
    }

    /**
     * 充值
     */
    @PostMapping(value = "/admin/wallet/{userId}")
    public Result recharge(@PathVariable(value = "userId") Integer userId,@RequestParam(value = "money") float money){
       Wallet wallet  = walletRepository.findOne(userId);
        if(wallet == null){
            return ResultUtil.success("1","用户不存在");
        }
        wallet.setMoney(wallet.getMoney()+money);
        return ResultUtil.success( walletRepository.save(wallet));
    }

    /**
     * 发布通知
     * @param msg
     * @return
     */
    @PostMapping(value = "/admin/notice")
    public Result sendNotice(@RequestParam(value = "msg") String msg ){

        UserSession.sendNotice(msg);

        return ResultUtil.success();
    }

   @PostMapping(value = "admin/send")
    public Result send(){
       UserSession.sendToUsre(3,2,"1");
       return ResultUtil.success();
   }

}
