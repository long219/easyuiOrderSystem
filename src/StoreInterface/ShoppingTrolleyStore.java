package StoreInterface;

import java.util.List;

import propertys.ShoppingTrolleyProperties;


/**
 * 购物车 存储层
 * @author VP
 *
 */
public interface ShoppingTrolleyStore {

	/**
	 * 新增购物车数据 (同时把数据增加到集合与数据库中)
	 * 
	 *    spro 购物车属性对象，封装了：
	 *    		amounts  餐品数量
	 *    		menuID  餐品ID
	 *    		userID  用户ID
	 *    		name    餐品名称
	 *    		money   该餐品的总金额
	 */
	public void add(ShoppingTrolleyProperties spro);
	
	/**
	 * 查询
	 */
	public List<ShoppingTrolleyProperties> query();
	
	/**
	 * 删除
	 * 		同时把数据从集合与数据库中删除
	 * @param id
	 */
	public void delete(int id,int userID);
	
	/**
	 * 按购物车表的id查询数据
	 */
	public ShoppingTrolleyProperties queryID(int id);
	
	/**
	 * 根据用户ID查询购物车
	 * @param id
	 * @return
	 */
	public  List<ShoppingTrolleyProperties> queryUserID(int userid);
	
	/**
	 * 相同的菜品添加数量
	 * @param index
	 */
	public void addAmount(ShoppingTrolleyProperties stp,int shoppingId);
}
