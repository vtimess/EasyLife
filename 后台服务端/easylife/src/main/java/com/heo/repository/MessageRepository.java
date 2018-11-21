package com.heo.repository;

import com.heo.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer>{
    public List<Message> findAllByToUserIdOrderByDateDesc(Integer toUserId);
}
