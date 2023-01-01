package com.ssafy.finalPjt.model.dto;
import com.google.gson.Gson;

public class User {

	private String u_id; // PK
	private String name;
	private String pw;
	private String email;
	private String gender;
	private String phone_no;
	private String nickname;
	private String state_message;
	private String img;
	private String address;
	private String x;
	private String y;

	public String getState_message() {
		return state_message;
	}

	public void setState_message(String state_message) {
		this.state_message = state_message;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

//	@Override
//	public String toString() {
//		return "User [u_id=" + u_id + ", name=" + name + ", pw=" + pw + ", email=" + email + ", gender=" + gender
//				+ ", phone_no=" + phone_no + ", nickname=" + nickname + "]";
//	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
