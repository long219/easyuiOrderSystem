package StoreInterface;

import propertys.UserProperties;

public interface UserStore {
	
	/**
	 * 通过手机号码获取某个人的信息，如果返回值为null，则该手机号码还未注册
	 */
	public UserProperties querySomeone(String phone);
	//public UserProperties querySomeone(int userId);
	
	/**
	 * 新增用户
	 */
	public void add(UserProperties upro);
	
	/**
	 * 账户扣余额
	 */
	public void updateMoney(double money,int userID);
	
	/**
	 * 账户加余额
	 * @param money
	 * @param userID
	 */
	public void rechargeMoney(double money, int userID);

	

	
}
