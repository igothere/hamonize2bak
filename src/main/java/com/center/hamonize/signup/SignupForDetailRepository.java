package com.center.hamonize.signup;


import java.util.List;

import com.center.hamonize.user.UsersDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignupForDetailRepository extends JpaRepository<UsersDetail, Integer>{
	List<UsersDetail> findAll();
   List<UsersDetail> findByEnterpriseno(Integer enterpriseno);
   
}
