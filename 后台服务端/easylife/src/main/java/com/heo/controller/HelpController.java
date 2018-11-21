package com.heo.controller;

import com.heo.domain.Help;
import com.heo.domain.Result;
import com.heo.domain.User;
import com.heo.enums.ResultEnum;
import com.heo.exception.MyException;
import com.heo.repository.HelpRepository;
import com.heo.repository.UserRepository;
import com.heo.service.HelpService;
import com.heo.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
public class HelpController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HelpRepository helpRepository;

    @Autowired
    private HelpService helpService;

    /**
     *  发布一个help
     */
    @PostMapping(value = "/help")
    public Result<Help> releaseHelp(@Valid Help help, BindingResult bindingResult,
                                    @RequestParam(value = "image",required = false) MultipartFile image) {

        if (bindingResult.hasErrors()) {
            return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }
        return helpService.releaseHelp(help,image);
    }

    /**
     * 获取所有的发布中的help
     */
    @GetMapping(value = "/help/release")
    public Result<List<Help>> getAllReleaseHelp(){
        return ResultUtil.success(helpRepository.findAllByStatusOrderByReleaseDateDesc(0));
    }

    /**
     *  获的自己发布的help
     */
    @GetMapping(value = "help/self/release/{userId}")
    public Result getSelfRelease(@PathVariable Integer userId ){

        if ( userRepository.findOne(userId) == null){
            return ResultUtil.error(userId);
        }

        return  ResultUtil.success(helpRepository.findAllByUserId(userId));
    }

    /**
     * 获取自己接受的help
     */
    @GetMapping(value = "/help/self/accept/{userId}")
    public Result<List<Help>> getSelfAccept(@PathVariable Integer userId ){

        if ( userRepository.findOne(userId) == null){
            return ResultUtil.error(userId);
        }
        return  ResultUtil.success(helpRepository.findAllByAcceptUserId(userId));
    }

    /**
     * 取消自己发布的help
     */
    @DeleteMapping(value = "/help/{helpId}")
    public Result<?> delSelfReleaseHelp(@PathVariable Integer helpId,@RequestParam("userId") Integer userId){
        return helpService.cancelHelp(helpId,userId);
    }

    /**
     * help已经被别人接受
     * 强制取消自己发布的
     */
    @DeleteMapping(value = "/help/del/{helpId}")
    public Result coercionDelSelfReleaseHelp(@PathVariable Integer helpId, @RequestParam("userId") Integer userId){
        return helpService.coercionDelSelfReleaseHelp(helpId,userId);
    }

    /**
     * 接受help
     */
    @PutMapping(value = "/help/accept/{helpId}")
    public Result acceptHelp(@PathVariable Integer helpId, @RequestParam("userId") Integer userId){
        return helpService.acceptHelp(helpId,userId);
    }

    /**
     * 完成help
     */
    @PutMapping(value = "/help/complete/{helpId}")
    public Result completeHelp(@PathVariable Integer helpId, @RequestParam("userId") Integer userId){
        return helpService.completeHelp(helpId,userId);
    }
    /**
     * 付款
     */
    @PutMapping(value="/help/payment/{helpId}")
    public Result payment(@PathVariable Integer helpId, @RequestParam("userId") Integer userId){
        return helpService.payment(helpId,userId);
    }
}
