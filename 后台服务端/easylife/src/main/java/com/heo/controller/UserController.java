package com.heo.controller;


import com.heo.domain.Result;
import com.heo.domain.User;
import com.heo.domain.UserLogin;
import com.heo.domain.VCode;
import com.heo.repository.UserLoginRepository;
import com.heo.repository.UserRepository;
import com.heo.repository.VCodeRepository;
import com.heo.service.UserService;
import com.heo.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private VCodeRepository vCodeRepository;

    /**
     * 注册新用户
     */
    @PostMapping(value = "/register")
    public Result register(@Valid UserLogin userLogin, BindingResult bindingResult,@RequestParam("vCode") String vCode) {
        delExpiryData();

        if (bindingResult.hasErrors()) {
            return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }
        return userService.register(userLogin,vCode);
    }

    /**
     *  获取验证码
     */
    @GetMapping(value = "/getVCode/{operation}")
    public Result getVCode(@RequestParam("phoneNumber") String phoneNumber,@PathVariable String operation) throws HTTPException, IOException, com.github.qcloudsms.httpclient.HTTPException {
            delExpiryData();
            int op = -1;
            if (operation.equals("register"))
                op = 0;
            else if (operation.equals("forgetPassword"))
                op = 1;
            else
                return ResultUtil.error(1,"请求错误！");
            return userService.getVCode(phoneNumber,op);
    }

    @PostMapping(value = "/vCode")
    public Result vCodeIsOk(@RequestParam(value = "phoneNumber") String phoneNumber,
                            @RequestParam(value = "vCode")String vCode){
        if (userService.vCodeIsOk(phoneNumber,vCode))
            return ResultUtil.success();
        else
            return ResultUtil.error(-1,"验证码错误");
    }




    /**
     * 用户登陆
     */
    @PostMapping(value = "/login")
    public Result login(@Valid UserLogin userLogin, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }
        return userService.login(userLogin);
    }

    /**
     * 修改密码
     */
    @PostMapping(value = "/update/password")
    public Result<UserLogin> updatePassword(@Valid UserLogin userLogin, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }

        userLogin.setPhoneNumber(userLoginRepository.findOne(userLogin.getUserId()).getPhoneNumber());

        return ResultUtil.success("修改成功", userLoginRepository.save(userLogin));
    }

    /**
     * 忘记密码
     */
    @PostMapping(value = "/forget/password")
    public Result forgetPassword(@Valid UserLogin userLogin,BindingResult bindingResult,@RequestParam("vCode") String vCode)throws Exception{
        delExpiryData();
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }

        return userService.forgetPassword(userLogin,vCode);
    }

    /**
     * 获取用户资料
     */
    @GetMapping(value = "/userinfo/{id}")
    public Result<User> getUserInfo(@PathVariable("id") Integer id) {
        User user = userRepository.findOne(id);
        return ResultUtil.success(user);
    }

    @GetMapping(value = "/userHeadImage/{id}")
    public String getUserHeadImage(@PathVariable(value = "id") Integer id){
        User user = userRepository.findOne(id);
        return user.getHeadImage();
    }

    /**
     * 修改用户资料
     */
    @PostMapping(value = "/userinfo")
    public Result<User> updateUserInfo(@Valid User user, BindingResult bindingResult,
                                       @RequestParam(value = "image",required = false)MultipartFile image) {

        if (bindingResult.hasErrors()) {
            return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }

        User user1 = userRepository.findOne(user.getUserId());

        user1.setBirthday(user.getBirthday());
        user1.setSignature(user.getSignature());
        user1.setSex(user.getSex());
        user1.setHometown(user.getHometown());

        return userService.updateUserinfo(user1,image);
    }

    /**
     * 校园认证
     */
    @PostMapping(value = "/authent")
    public Result authent(@RequestParam(value = "userId")Integer userId,
                          @RequestParam(value = "name") String  name,
                          @RequestParam(value = "major") String major,
                          @RequestParam(value = "number") String number){
        User user = userRepository.findOne(userId);
        user.setName(name);
        user.setMajor(major);
        user.setNumber(number);
        user.setSchool("南京晓庄学院");
        return ResultUtil.success(userRepository.save(user));
    }

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
}
