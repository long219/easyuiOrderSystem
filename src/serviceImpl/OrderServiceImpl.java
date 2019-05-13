package serviceImpl;

import java.util.List;

import StoreImpl.OrderStoreImpl;
import propertys.Order_Property;
import propertys.ShoppingTrolleyProperties;
import service.OrderService;
import service.ShoppingTrolleyService;


/**
 * 订单逻辑层
 * @author VP
 *
 */
public class OrderServiceImpl implements OrderService {
	private OrderStoreImpl orderStoreImpl = null;
	
	public OrderServiceImpl() {
		orderStoreImpl = new OrderStoreImpl();
	}

	@Override
	public void add(List<ShoppingTrolleyProperties> arrayList, ShoppingTrolleyServiceImpl shoppingTrolleyService,UserServiceImpl userService,double sumMoney) {	
		
		//增加订单表与关系表的数据
		orderStoreImpl.add(arrayList);
		
		int userId = arrayList.get(0).getUserID();
		//修改账户余额
		userService.updateMoney(sumMoney, userId);
		userService.rechargeMoney(sumMoney, 1);
		//删除购物车中的数据
		deleteTrolley(arrayList,shoppingTrolleyService);
	}
	
	/**
	 * 删除购物车中的数据
	 * @param userID  用户ID
	 * @param idArray  装购物车ID的数组 
	 * @param shoppingTrolleyService
	 */
	public void deleteTrolley(List<ShoppingTrolleyProperties> arrayList,ShoppingTrolleyService shoppingTrolleyService) {
		for (int i = 0; i < arrayList.size(); i++) {
			ShoppingTrolleyProperties stp = arrayList.get(i);
			shoppingTrolleyService.delete(stp.getId(), stp.getUserID());
		}
	}

	@Override
	public List<Order_Property> queryUserIdOrder(int userId){
		
		return orderStoreImpl.queryUserIdOrder(userId);
	}

	@Override
	public List<Order_Property> queryStateOrder(int state, int userId,int page,int rows) {
		
		return orderStoreImpl.queryStateOrder(state, userId,page,rows);
	}
	
	public int queryStateOrderTotal(int state,int page,int rows ,int userId) {
		return orderStoreImpl.queryStateOrderTotal(state,userId);
	}

	@Override
	public void stateUpdateOrder(int orderId, int state,String cancel) {
		
		orderStoreImpl.updateStateOrder(state, orderId,cancel);
	}
	
	
	/*@Override
	public Map<Integer,OrderProperties> querySomeone(int userID) {
		
		return orderStore.querySomeone(userID);
	}

	@Override
	public OrderProperties queryID(int userID, int id) {
		
		return orderStore.queryID(userID, id);
	}

	@Override
	public void update(OrderProperties opro,UserService userService) {
//		取消订单、修改状态、修改取消原因
		orderStore.update(opro,"5");
		
//		修改用户的余额
		userService.updateMoney(0-opro.getMoney(), opro.getUserID());
	}

	@Override
	public boolean check(OrderProperties opro) {
		if("待接单".equals(opro.getState()) || "已接单".equals(opro.getState())) {
			return true;
		}
		return false;
	}

	@Override
	public void update(OrderProperties opro, FoodService foodService) {
		orderStore.update(opro, foodService);
	}

	@Override
	public List<FoodDetailsProperties> queryDetails(int orderID) {
		
		return orderStore.queryDetails(orderID);
	}
	
	@Override
	public List<OrderVO> query(String state){
		
		return orderStore.query(state);
	}
	
	

	
	
	*//**
	 * 根据订单ID查询菜品信息
	 * @param orderID
	 * @return
	 *//*
	public List<FoodProperties> findOrderIDquery(int orderID){
		
		return orderStore.findMenuByOrderId(orderID);
	}
	
	*//**
	 * 根据订单ID查询用户信息
	 * @param orderID
	 * @return
	 *//*
	public List<UserProperties> findUserInfoquery(int orderID){
		
		return orderStore.findUserInfoquery(orderID);
	}
	
	
	@Override
	public void updateOrderState(int id,String state) {
		orderStore.updateOrderState(id , state);	
	}

	@Override
	public void update(int id, String state) {
		orderStore.update(id, state);
	}

	public void updateOrderOrAdd(int orderId, String cancel) {
		
		orderStore.updateOrderOrAdd(orderId, cancel);
	}
	
	*//**
	 * 根据菜品Id增加菜品销量
	 * @param menuId
	 *//*
	public void menuIdUpdateVolume(int menuId) {
		orderStore.menuIdUpdateVolume(menuId);
		
	}*/


}
