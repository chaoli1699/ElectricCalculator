package cn.cienet.electriccalculator.bean;

public class User {

	private int userId;
	private String userName;
	private String userMore;
	
	private String lastCount;
	private String currentCount;
	
	private float airFee;
	private float publicFee;
	private float totalFee;
	
	
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
	public String getLastCount() {
		return lastCount;
	}
	public void setLastCount(String lastCount) {
		this.lastCount = lastCount;
	}
	public String getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(String currentCount) {
		this.currentCount = currentCount;
	}
	public float getAirFee() {
		return airFee;
	}
	public void setAirFee(float airFee) {
		this.airFee = airFee;
	}
	public float getPublicFee() {
		return publicFee;
	}
	public void setPublicFee(float publicFee) {
		this.publicFee = publicFee;
	}
	public float getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(float totalFee) {
		this.totalFee = totalFee;
	}
	
	
}
