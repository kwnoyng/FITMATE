package com.ssafy.finalPjt.model.service.favorite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.finalPjt.model.dao.FavoriteDao;
import com.ssafy.finalPjt.model.dto.Favorite;
import com.ssafy.finalPjt.model.dto.User;

@Service
public class FavoriteServiceImpl implements FavoriteService {
	
	@Autowired
	private FavoriteDao favoritedao;
	
	// 좋댓구알
	@Override
	public boolean like(Favorite favorite) {
		System.err.println("ser" + favorite.toString());
		return favoritedao.favoriteCntUp(favorite) == 1;
	}
	
	
	// 좆댓구요
	public boolean disLike(Favorite favorite) {
		System.err.println("ser" + favorite.toString());
		return favoritedao.favoriteCntDown(favorite) == 1;
	}
	
	public List<User> likePeople(Favorite favorite){
		System.err.println("ser" + favorite.toString());
		System.out.println(favoritedao.favoritePeople(favorite));
		return favoritedao.favoritePeople(favorite);
	}

}
