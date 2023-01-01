package com.ssafy.finalPjt.model.dao;

import java.util.List;

import com.ssafy.finalPjt.model.dto.Favorite;
import com.ssafy.finalPjt.model.dto.User;

public interface FavoriteDao {

	// 좋아요 증가 
	int favoriteCntUp(Favorite favorite);
	
	// 좋아요 취소 
	int favoriteCntDown(Favorite favorite);
	
	// 좋아요 조회 
	List<User> favoritePeople(Favorite favorite);
}
