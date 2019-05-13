package StoreInterface;

import java.util.List;

import propertys.Menu_Property;



/**
 * 餐品存储层 接口
 * @author VP
 *
 */
public interface MenuStore {
	/**
	 * 根据名称查餐品的信息，返回FoodProperties对象，当对象等于null时，证明没有该名称的餐品
	 */
	public Menu_Property queryName(String name);
	
	/**
	 * 根据ID查餐品的信息，返回FoodProperties对象，当对象等于null时，证明没有该名称的餐品
	 */
	public Menu_Property queryID(int ID);
	
	/**
	 * 增加餐品
	 */
	public void add(Menu_Property fpro);
	
	/**
	 * 1、查询餐品（置顶的在上面）
	 */
	public List<Menu_Property> queryOne();
	
	/**
	 * 2、按金额排序查询（降序）
	 */
	public List<Menu_Property> queryTwo();
	
	/**
	 * 3、按销量排序查询（降序）
	 */
	public List<Menu_Property> queryThree();
	
	/**
	 * 修改餐品价格，修改成功:返回1 
	 */
	public int updatePrice(int id, double price);
	
	/**
	 * 修改餐品名称，修改成功:返回1 
	 */
	public int updateFoodName(int id, String name);
	
	/**
	 * 修改优惠金额，修改成功:返回1 
	 */
	public int updateDiscountPrice(int id,double discountPrice);
	
	/**
	 * 修改是否置顶，修改成功:返回1 
	 */
	public int updateIsTop(int id,String isTop);
	
	/**
	 * 修改上下架，修改成功:返回1 
	 */
	public int updateState(int id,String state);
	
	/**
	 * 通过ID删除数据(软删除将状态改为  删除状态 )
	 */
	public void menuDelete(int id);
	
	/**
	 * 		修改销量（不可直接修改）
	 * @param id 餐品id
	 * @param amount 数量
	 */
	public void updateSalesVolume(int id,int amount);

	/**
	 * 根据购物车ID删除购物车数据
	 * @param id
	 */
	public void deleteShoppingId(int id);
}
