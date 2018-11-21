package com.heo.repository;

import com.heo.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    public List<Wallet> findByUserId(Integer userId);
}

