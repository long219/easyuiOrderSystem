package service;

import java.util.List;

import propertys.Order_Property;
import propertys.ShoppingTrolleyProperties;
import serviceImpl.ShoppingTrolleyServiceImpl;
import serviceImpl.UserServiceImpl;

public interface OrderService {
	
	/**
	 * 新增订单
	 * 	修改用户余额
	 * 	需要参数：user_id 用户ID、menu_id 餐品ID、amount 某种餐品的数量、 money订单总金额
	 */
	public void add(List<ShoppingTrolleyProperties> arrayList,ShoppingTrolleyServiceImpl shoppingTrolleyService,UserServiceImpl userService,double sumMoney);

	/**
	 * 根据用户id查询订单
	 * @param userId
	 * @return
	 */
	public List<Order_Property> queryUserIdOrder(int userId);
	
	/**
	 * 根据状态和用户iD查询订单
	 * @param state
	 * @param userId
	 * @return
	 */
	public  List<Order_Property> queryStateOrder( int state,int userId,int page,int rows);
	
	/**
	 * 根据订单修改状态
	 * @param orderId 
	 * @param state
	 */
	public void stateUpdateOrder(int orderId,int state,String cancel);

/*	*//**
	 * 查询某人的全部订单信息
	 * @param userID 用户ID
	 * @param id  订单ID
	 *//*
	public Map<Integer,OrderProperties> querySomeone(int userID);
	
	*//**
	 * 查询某人的某条订单的信息
	 * @param userID 用户ID
	 * @param id  订单ID
	 * @return
	 *//*
	public OrderProperties queryID(int userID,int id);
	
	*//**
	 * 修改订单状态（只有待接单的、已接单的能取消订单）、退回金额
	 * @param opro  订单属性对象
	 * @param userService  用户逻辑层对象
	 *//*
	public void update(OrderProperties opro,UserService userService);
	
	*//**
	 * 修改订单状态为2（已接单）,4 (完成订单)、修改接单时间
	 *//*
	public void updateOrderState(int id, String state);
	
	*//**
	 *   修改状态 为待收货
	 * @param id 订单id
	 *//*
	public void update(int id,String state);
	
	*//**
	 * 修改管理员的账户余额（暂时不写）
	 * 	修改状态
		修改销量"
	 * 
	 * @param opro  订单属性对象
	 * @param foodService  餐品逻辑层对象
	 *//*
	public void update(OrderProperties opro,FoodService foodService);
	
	*//**
	 * 校验状态是否是待接单的、已接单的，是：返回true
	 *//*
	public boolean check(OrderProperties opro);
	
	
	*//**
	 * 通过订单号获取某些菜品明细
	 *     名称、份数、总价
	 *//*
	public List<FoodDetailsProperties> queryDetails(int orderID);
	
	*//**
	 * 查询所有的数据
	 *//*
	public List<OrderVO> query(String state);

	*/


	


	
}
