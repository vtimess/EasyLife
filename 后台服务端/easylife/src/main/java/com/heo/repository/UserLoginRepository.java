package com.heo.repository;

import com.heo.domain.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLoginRepository extends JpaRepository<UserLogin, Integer> {

    //通过手机号码查询
    public List<UserLogin> findByPhoneNumber(String phoneNumber);

}
