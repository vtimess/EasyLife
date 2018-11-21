package com.heo.service;

import com.heo.domain.Result;
import com.heo.domain.Wallet;
import com.heo.enums.ResultEnum;
import com.heo.exception.MyException;
import com.heo.repository.WalletRepository;
import com.heo.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletService {

    private static float PROFIT =  0.9f;

    @Autowired
    private WalletRepository walletRepository;

    /**
     * 付钱
     * @return
     */
    @Transactional
    public Wallet payment(Integer releaseId, Integer acceptId, float money){
        Wallet releaseWallet = walletRepository.findOne(releaseId);
        Wallet acceptWallet = walletRepository.findOne(acceptId);

        float releaseMoney = releaseWallet.getMoney();
        if (releaseMoney < money)
            throw new MyException(ResultEnum.MONEY_NO_ENOUGH);

        releaseWallet.setMoney(releaseMoney - money);

        acceptWallet.setMoney(acceptWallet.getMoney() + money * PROFIT);

        releaseWallet = walletRepository.save(releaseWallet);
        walletRepository.save(acceptWallet);

        return releaseWallet;
    }

    /**
     * 设置密码
     */
    @Transactional
    public Result setPayPassword(Integer userId,String password){
        Wallet wallet = walletRepository.findOne(userId);
        wallet.setPayPassword(password);
        walletRepository.save(wallet);
        return ResultUtil.success();
    }


    /**
     * 充值
     */

    /**
     * 提现
     */
}
