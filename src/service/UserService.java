package service;

import propertys.UserProperties;

public interface UserService {
	
	/**
	 * 校验手机号码是否唯一，若唯一：返回true，否则返回false
	 */
	public boolean check(String phone);
	//public boolean check(int phone);
	
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
	 * 校验账号密码是否匹配，匹配返回：true，否则返回false
	 */
	public boolean check(UserProperties upro,String password,String phone);
	
	/**
	 * 账户扣余额
	 */
	public void updateMoney(double money,int userID);
	
	/**
	 * 用户加余额
	 * @param money
	 * @param userID
	 */
	public void rechargeMoney(double money, int userID);

	

	
}
