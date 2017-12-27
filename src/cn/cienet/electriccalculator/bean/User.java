package cn.cienet.electriccalculator.bean;

import java.util.List;

public class User {

	private int userId;
	private String userName;
	private String userMore="Пе";
	private Bill currentbill;
	private List<Bill> billHistory;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserMore() {
		return userMore;
	}
	public void setUserMore(String userMore) {
		this.userMore = userMore;
	}
	public Bill getCurrentbill() {
		return currentbill;
	}
	public void setCurrentbill(Bill currentbill) {
		this.currentbill = currentbill;
	}
	public List<Bill> getBillHistory() {
		return billHistory;
	}
	public void setBillHistory(List<Bill> billHistory) {
		this.billHistory = billHistory;
	}
	
	
}
