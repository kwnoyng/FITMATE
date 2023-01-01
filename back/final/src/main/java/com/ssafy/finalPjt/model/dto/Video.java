package com.ssafy.finalPjt.model.dto;

public class Video {
	private String v_id;		// PK
	private String title;
	private String v_writer;
	private String reg_date;
	private int view_cnt;
	private int favorite_cnt;
	private String category;
	
	public String getV_id() {
		return v_id;
	}
	public void setV_id(String v_id) {
		this.v_id = v_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getV_writer() {
		return v_writer;
	}
	public void setV_writer(String v_writer) {
		this.v_writer = v_writer;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getView_cnt() {
		return view_cnt;
	}
	public void setView_cnt(int view_cnt) {
		this.view_cnt = view_cnt;
	}
	public int getfavorite_cnt() {
		return favorite_cnt;
	}
	public void setfavorite_cnt(int favorite_cnt) {
		this.favorite_cnt = favorite_cnt;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return "Video [v_id=" + v_id + ", title=" + title + ", v_writer=" + v_writer + ", reg_date=" + reg_date
				+ ", view_cnt=" + view_cnt + ", favorite_cnt=" + favorite_cnt + ", category=" + category + "]";
	}
	
	
}
