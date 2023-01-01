package com.ssafy.finalPjt.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.finalPjt.model.dto.Message;
import com.ssafy.finalPjt.model.service.message.MessageService;

@RestController
@RequestMapping("/api/messageApi")
public class MessageRestController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private MessageService messageService;

	@PostMapping("/")
	public ResponseEntity<String> registMessage(Message message) {
		int res = messageService.registMessage(message);
		System.out.println(res);

		if (res == 1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}

	@GetMapping("/received")
	public ResponseEntity<List<Message>> getReceivedMessage(String id) {
		List<Message> list = messageService.getReceivedMessage(id);
		return new ResponseEntity<List<Message>>(list, HttpStatus.OK);
	}

	@GetMapping("/sent")
	public ResponseEntity<List<Message>> getSentMessage(String id) {
		List<Message> list = messageService.getSentMessage(id);
		for(Message x : list) {
			System.out.println(x.getM_id());
			System.out.println(x.getFrom_user());
			System.out.println(x.getTo_user());
			System.out.println(x.getContent());
			
			System.out.println();
			System.out.println();
			System.out.println();
		}
		return new ResponseEntity<List<Message>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/received/{userId}")
	public ResponseEntity<List<Message>> getReceivedMessageFromUser(String myId, @PathVariable String userId) {
	    return new ResponseEntity<List<Message>>(messageService.getReceivedMessageFromUser(myId, userId),
	            HttpStatus.OK);

	}

	@GetMapping("/sent/{userId}")
	public ResponseEntity<List<Message>> getsentMessageFromUser(String myId, @PathVariable String userId) {
	    return new ResponseEntity<List<Message>>(messageService.getsentMessageFromUser(myId, userId), HttpStatus.OK);

	}

}
