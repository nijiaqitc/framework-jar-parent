package com.njq.common.util.other;

/**
 * cookie超时设定，规范时间为15min、30min、45min、60min 四个档次
 *
 */
public enum CookieExpire {
	
	/**
	 * FIFTEEN 15min
	 * THIRTY  30min
	 * FOURTY_FIVE 45min
	 * SIXTY   60min
	 */
	ONE(1),FIVE(5),FIFTEEN(15),THIRTY(30),FOURTY_FIVE(45), SIXTY(60),Day(1440),SEVEN_DAY(10080);

	private int expireTime; 
	
	private CookieExpire(int expireTime){
		this.expireTime = expireTime;
	}

	public int getExpireTime() {
		return expireTime * 60;
	}
	
}
