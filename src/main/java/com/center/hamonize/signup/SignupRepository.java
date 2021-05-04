package com.center.hamonize.signup;


import java.util.List;

import com.center.hamonize.user.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignupRepository extends JpaRepository<Users, String>{
	List<Users> findAllByUseremail(String userEmail);
	boolean existsByUseremail(String userEmail);
    List<Users> findByUserid(String userId);
    List<Users> findByUseremail(String userEmail);

}

