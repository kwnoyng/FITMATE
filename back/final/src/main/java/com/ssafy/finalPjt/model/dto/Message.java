package com.ssafy.finalPjt.model.dto;

public class Message {
	
	private int m_id; // pk
	private String from_user;
	private String to_user;
	private String content;
	
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public String getFrom_user() {
		return from_user;
	}
	public void setFrom_user(String from_user) {
		this.from_user = from_user;
	}
	public String getTo_user() {
		return to_user;
	}
	public void setTo_user(String to_user) {
		this.to_user = to_user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "Message [m_id=" + m_id + ", from_user=" + from_user + ", to_user=" + to_user + ", content=" + content
				+ "]";
	}
	
	
}
