package service;


import java.util.List;

import propertys.Menu_Property;


public interface MenuService {
	/**
	 * 校验餐品名称是否已存在，存在：返回true，否则返回false
	 */
	public boolean check(String name);
	
	/**
	 * 根据ID查餐品的信息，返回FoodProperties对象，当对象等于null时，证明没有该名称的餐品
	 */
	public Menu_Property queryID(int id);
	
	/**
	 * 校验餐品ID是否存在，存在：返回true，否则返回false
	 */
	public boolean check(int id);
	
	/**
	 * 增加餐品
	 */
	public void add(Menu_Property fpro);
	
	
	/**
	 * 通过ID删除数据
	 */
	public void menuDelete(int id);
	
	/**
	 * 1、查询餐品（置顶的在上面）
	 */
	public List<Menu_Property> queryOne();
	
	/**
	 * 2、按金额排序查询（降序）
	 *//*
	public List<Menu_Property> queryTwo();
	
	*//**
	 * 3、按销量排序查询（降序）
	 *//*
	public List<Menu_Property> queryThree();
	
	*//**
	 * 修改餐品价格，返回“修改成功”、“修改失败，ID不存在”
	 *//*
	public String updatePrice(int id, double price);
	
	*//**
	 * 修改餐品名称，返回“修改成功”、“修改失败，ID不存在”
	 *//*
	public String updateFoodName(int id,String name);
	
	*//**
	 * 修改优惠金额，返回“修改成功”、“修改失败，ID不存在”
	 *//*
	public String updateDiscountPrice(int id,double discountPrice);
	
	*//**
	 * 修改是否置顶，返回“修改成功”、“修改失败，ID不存在”
	 *//*
	public String updateIsTop(int id,String isTop);
	
	*//**
	 * 修改上下架，返回“修改成功”、“修改失败，ID不存在”
	 *//*
	public String updateState(int id,String state);
	
	*//**
	 * 	修改销量（不可直接修改）
	 * @param id 餐品id
	 * @param amount 数量
	 *//*
	public void updateSalesVolume(int id,int amount);
	
	*//**
	 * 根据购物车ID删除购物车数据
	 * @param shoppingID
	 *//*
	public void deleteShoppingID(int shoppingID);*/
}
