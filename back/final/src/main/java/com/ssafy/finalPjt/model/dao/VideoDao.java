package com.ssafy.finalPjt.model.dao;

import java.util.List;

import com.ssafy.finalPjt.model.dto.Video;
import com.ssafy.finalPjt.model.dto.VideoSearchCondition;

public interface VideoDao {
	
	// 한개 조회 
	Video selectOne(String id);
	
	// 카테고리 + 검색 
	List<Video> getVideoList(VideoSearchCondition condition);

	// 조회수 증가
	void updateCnt(String id);
	
	List<Video> favoriteVideos(String id);
}
