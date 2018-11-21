package com.heo.controller;

import com.heo.domain.Result;
import com.heo.domain.Wallet;
import com.heo.repository.WalletRepository;
import com.heo.service.WalletService;
import com.heo.utils.ResultUtil;
import com.sun.org.glassfish.external.statistics.annotations.Reset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class WalletController {

    @Autowired
    public WalletRepository walletRepository;

    @Autowired
    public WalletService walletService;

    /**
     * 获取余额
     * @param userId
     * @return
     */
    @GetMapping(value = "/wallet/{userId}")
    public float getWallet(@PathVariable(value = "userId") Integer userId){
        return  walletRepository.getOne(userId).getMoney();
    }

    /**
     * 判断密码是否相同
     * @param userId
     * @param payPassword
     * @return
     */
    @PostMapping(value = "/wallet/pay/{userId}")
    public Result pay(@PathVariable(value = "userId") Integer userId,
                      @RequestParam(value = "payPassword") String payPassword){
        if(walletRepository.findOne(userId).getPayPassword().equals(payPassword))
            return ResultUtil.success();
        return ResultUtil.error(1,"密码错误");
    }

    /**
     * 设置支付密码(修改)
     * @param userId
     * @param password
     * @return
     */
    @PostMapping(value = "/set/pay/password")
    public Result setPayPassword(@RequestParam(value = "userId")Integer userId,
                                 @RequestParam(value = "password") String password){

        return walletService.setPayPassword(userId,password);
    }


}
