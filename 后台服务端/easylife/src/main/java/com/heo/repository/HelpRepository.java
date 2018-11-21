package com.heo.repository;

import com.heo.domain.Help;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HelpRepository extends JpaRepository<Help,Integer> {

    public List<Help> findAllByUserId(Integer userId);

    public List<Help> findAllByAcceptUserId(Integer releaseId);

    public List<Help> findAllByStatusOrderByReleaseDateDesc(Integer status);

}
