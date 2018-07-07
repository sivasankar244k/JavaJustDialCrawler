package com.justdial.crawler;

public class UrlDetailer {
	private String baseUrlString;
	private String place;
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	private String fromPageNum;
	private String toPage;
	private String status;
	private String inputDate;


	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBaseUrlString() {
		return baseUrlString;
	}

	public void setBaseUrlString(String baseUrlString) {
		this.baseUrlString = baseUrlString;
	}

	public String getFromPageNum() {
		return fromPageNum;
	}

	public void setFromPageNum(String fromPageNum) {
		this.fromPageNum = fromPageNum;
	}

	public String getToPage() {
		return toPage;
	}

	public void setToPage(String toPage) {
		this.toPage = toPage;
	}

}
