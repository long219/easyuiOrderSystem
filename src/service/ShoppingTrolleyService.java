package service;

import java.util.List;

import propertys.ShoppingTrolleyProperties;
import serviceImpl.MenuServiceImpl;


public interface ShoppingTrolleyService {
	
	/**
	 * 新增购物车数据 
	 *    spro 购物车属性对象，封装了：
	 *    		amounts  餐品数量
	 *    		menuID  餐品ID
	 *    		userID  用户ID
	 */
	public String add(ShoppingTrolleyProperties spro);
	
	/**
	 * 查询
	 */
	public List<ShoppingTrolleyProperties> query();
	
	/**
	 * 按购物车表的id查询数据
	 */
	public ShoppingTrolleyProperties queryID(int id);
	
	/**
	 * 获取选中的序号的总金额
	 */
	public float queryMoney(List<ShoppingTrolleyProperties> arrayList,int userID);
	
	
	/**
	 * 删除
	 * 		校验id是否在集合中，在： 删除购物车中的对应数据
	 * 
	 * @param id
	 */
	public void delete(int id,int userID);
	
	/**
	 * 校验是否都存在于购物车,都存在：返回true
	 */
	public boolean check(String[] idArray,int userID);
	
	/**
	 * 校验单号是否在购物车里、用户ID是否对应，在、对应，返回：true
	 */
	public boolean check(int id,int userID);
	
	/**
	 * 根据用户ID来查询个人购物车
	 * @return
	 */
	public  List<ShoppingTrolleyProperties> queryUserID(int userId);


	

}
