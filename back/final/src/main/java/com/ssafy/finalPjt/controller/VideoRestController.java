package com.ssafy.finalPjt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.finalPjt.model.dto.Video;
import com.ssafy.finalPjt.model.dto.VideoSearchCondition;
import com.ssafy.finalPjt.model.service.video.VideoService;

@RestController
@RequestMapping("/api/videoApi")
public class VideoRestController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private VideoService videoService;

	@GetMapping("/video/{id}")
	public ResponseEntity<Video> videoDetail(@PathVariable String id) {

		videoService.viewCntup(id);

		return new ResponseEntity<Video>(videoService.readVideo(id), HttpStatus.OK);
	}

	@GetMapping("/video")
	public ResponseEntity<List<Video>> videoList(String[] category, @RequestParam(defaultValue = "") String keyword,
			String range) {
//		HashMap<String,String> params=new HashMap<>();
		System.out.println(category);
		System.out.println(keyword);
		System.out.println(range);

		// 키워드 없으면 리턴
		if (keyword.equals("")) {
			return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
		}

		String[] ranges = new String[0];

		if (range.equals("전체")) {
			ranges = new String[2];
			ranges[0] = "title";
			ranges[1] = "v_writer";
		} else if (range.equals("제목")) {
			ranges = new String[1];
			ranges[0] = "title";
		} else {
			ranges = new String[1];
			ranges[0] = "v_writer";
		}

		if (category == null) {
			category = new String[0];
		}

		List<Video> videoList = videoService.findVideoList(new VideoSearchCondition(category, ranges, keyword));

		if (videoList.size() == 0) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(videoList, HttpStatus.OK);
		}
//		params.put("category", category);
//		params.put("keyword",keyword);
//		System.out.println("cont"+keyword);
//		try {
//			return new ResponseEntity<List<Video>>(videoService.findVideoList(),HttpStatus.OK);
//		}catch(Exception e){
//			throw new VideoNotFoundException(e+" 해당 비디오는 없습니다.");
//		}

	}

	@GetMapping("/favoriteVideos")
	public ResponseEntity<List<Video>> getFavoriteVideos(String id) {
		return new ResponseEntity<List<Video>>(videoService.getFavoriteVideos(id), HttpStatus.OK);
	}
}
