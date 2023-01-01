package com.ssafy.finalPjt.model.service.video;

import java.util.List;

import com.ssafy.finalPjt.model.dto.Video;
import com.ssafy.finalPjt.model.dto.VideoSearchCondition;

public interface VideoService {
	
	//한개 조회
	Video readVideo(String id);
	
	//카테고리 + 검색 
	List<Video> findVideoList(VideoSearchCondition condition);
	
	//조회수 증가 
	void viewCntup(String id);
	
	List<Video> getFavoriteVideos(String id);
}
