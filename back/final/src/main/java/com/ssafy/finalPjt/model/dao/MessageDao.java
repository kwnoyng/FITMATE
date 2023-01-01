package com.ssafy.finalPjt.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.finalPjt.model.dto.Message;

public interface MessageDao {

	// 메세지 등록
	int createMessage(Message message);

	// 내가 받은 모든 메세지
	List<Message> receivedMessage(String id);
	
	// 내가 보낸 모든 메세지
	List<Message> sentMessage(String id);
	
	// 내가 해당 유저에게 받은 모든 메세지
	List<Message> receivedMessageFromUser(@Param("myId") String myId, @Param("userId") String userId);

	// 내가 해당 유저에게 보낸 모든 메세지
	List<Message> sentMessageFromUser(@Param("myId") String myId, @Param("userId") String userId);
}
