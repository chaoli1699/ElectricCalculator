package cn.cienet.electriccalculator.bean;

public class Bill {
	
	private int lastCount;
	private int currentCount;
	
	private float perEPrice;
	private float totalMoney;
	
	private float airFee;
	private float publicFee;
	private float totalFee;
	
	private long updateTime;

	public int getLastCount() {
		return lastCount;
	}

	public void setLastCount(int lastCount) {
		this.lastCount = lastCount;
	}

	public int getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}

	public float getPerEPrice() {
		return perEPrice;
	}

	public void setPerEPrice(float perEPrice) {
		this.perEPrice = perEPrice;
	}

	public float getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(float totalMoney) {
		this.totalMoney = totalMoney;
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

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
