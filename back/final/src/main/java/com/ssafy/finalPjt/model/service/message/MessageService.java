package com.ssafy.finalPjt.model.service.message;

import java.util.List;

import com.ssafy.finalPjt.model.dto.Message;

public interface MessageService {

	// 메시지 등록
	int registMessage(Message message);
	
	// 내가 받은 모든 메세지
	List<Message> getReceivedMessage(String id);
	
	// 내가 보낸 모든 메세지
	List<Message> getSentMessage(String id);
	
	// 내가 해당 유저에게 받은 모든 메세지
	List<Message> getReceivedMessageFromUser(String myId, String userId);

	// 내가 해당 유저에게 보낸 모든 메세지
	List<Message> getsentMessageFromUser(String myId, String userId);
}
