package com.ssafy.finalPjt.model.dao;


import java.util.List;

import com.ssafy.finalPjt.model.dto.User;

public interface UserDao {

	// 회원 등록
	int createUser(User user);

	// 로그인, 회원정보 상세
	User findUser(String id);

	// 회원정보 수정
	int updateUser(User user);

	// 탈퇴
	void deleteUser(String id);

	// 회원 중복 확인
	int chkUserID(String id);

	int chkEmail(String id);

	int chkPhoneNo(String id);

	int chkNickname(String id);
	
	// 모든 유저 찾기
	List<User> findAllUser();
	
	List<User> getNicknames();
}
