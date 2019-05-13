package StoreInterface;

import java.util.List;

import propertys.Order_Property;
import propertys.ShoppingTrolleyProperties;

public interface OrderStore {
	/**
	 * 订单增加
	 * @param spro
	 */
	public void add(List<ShoppingTrolleyProperties> arrayList);
	
	/**
	 * 查询订单所有数据
	 * @param stateby
	 * @return
	 */
	public List<Order_Property> query(String stateby,int page,int rows);
	
	
	/**
	 * 根据用户Id查询订单
	 * @param stateby
	 * @return
	 */
	public List<Order_Property> queryUserIdOrder(int stateby);

	/**
	 * 根据状态和用户Id查询订单
	 * @param state
	 * @return
	 */
	public List<Order_Property> queryStateOrder(int state,int userId,int page,int rows);
	
	/**
	 * 根据订单状态和用户id 查询总行记录(total)
	 * @param state
	 * @param page
	 * @param rows
	 * @param userId
	 * @return
	 */
	public int queryStateOrderTotal(int state,int userId);
	
	/**
	 * 根据订单id修改状态
	 * @param state
	 * @param orderId
	 */
	public void updateStateOrder(int state,int orderId,String cancel);
}
