package menu_server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import StoreImpl.MenuStoreImpl;
import StoreImpl.OrderStoreImpl;
import StoreImpl.UserStoreImpl;
import propertys.Menu_Property;
import propertys.MessageVO;
import propertys.Order_Property;
import propertys.ShoppingTrolleyProperties;
import propertys.UserProperties;

import serviceImpl.OrderServiceImpl;
import serviceImpl.ShoppingTrolleyServiceImpl;
import serviceImpl.UserServiceImpl;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/orderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//订单存储层
	private OrderStoreImpl orderStoreImpl;
	//菜品存储层
	private  MenuStoreImpl menuStoreImpl;
	//用户存储层
	private UserStoreImpl userStoreImpl;
	
	private OrderServiceImpl orderServiceImpl;
	
	private ShoppingTrolleyServiceImpl ShoppingTrolleyServiceImpl;
	
	private UserServiceImpl userServiceImpl;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
        super();
        
        orderStoreImpl = new OrderStoreImpl();
        menuStoreImpl = new MenuStoreImpl();
        userStoreImpl = new UserStoreImpl();
        orderServiceImpl = new OrderServiceImpl();
        ShoppingTrolleyServiceImpl = new ShoppingTrolleyServiceImpl();
        userServiceImpl = new UserServiceImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//设置服务端编码
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/html;charse=UTF-8");
		//存储响应给前台是信息
		String msg = null;
		String  mothed = request.getParameter("mothed");
		
		if("addOrder".equals(mothed)) {
			//添加订单
			msg = addOrder(request, response);
		}else if("queryUserIdOrder".equals(mothed)){
			//查询个人的订单
			msg = queryUserOrder(request, response);
			//queryStateOrder
		}else if("queryStateOrder".equals(mothed)){
			//根据状态和用户Id查询订单(分页)
			msg=queryStateOrder(request, response);
		}else if("updataStateOrder".equals(mothed)){
			//根据订单id修改状态
			msg=stateUpdateOrder(request, response);
		}else if("shoppingDelete".equals(mothed)){
			//根据订单删除购物车
			msg = deleteShopping(request, response);
		}else if("toUpMoney".equals(mothed)){
			//用户充值
			msg = toUPMoney(request, response);
		}else {
			//查询所有订单信息
			msg = queryOrderAll(request, response);
		}
		
		//将数据发送给前台
		response.getWriter().println(msg);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/**
	 * 添加订单
	 * @param request
	 * @param response
	 * @return
	 */
	public String addOrder(HttpServletRequest request, HttpServletResponse response) {
		
		String dataList = request.getParameter("listOrder");
		List<ShoppingTrolleyProperties> arrayList = new ArrayList<>();
		//将JSON数组转为java数组
		JSONArray jsonArray = JSON.parseArray(dataList);
		arrayList = jsonArray.toJavaList(ShoppingTrolleyProperties.class);
		
		//判断账户余额是否足够
		float sumMoney = ShoppingTrolleyServiceImpl.queryMoney(arrayList, arrayList.get(0).getUserID());
		if(sumMoney > userServiceImpl.querySomeone(arrayList.get(0).getUserID()).getMoney()) {
			System.out.println();
			return "账户余额不够了，请先充值";
		}	
		orderServiceImpl.add(arrayList, ShoppingTrolleyServiceImpl, userServiceImpl, sumMoney);
		return "下单成功  ！ 您一共支付了"+sumMoney+"元";
		
	}
	
	/**
	 * 查询个人订单信息
	 * @param request
	 * @param response
	 * @return
	 */
	public String queryUserOrder(HttpServletRequest request, HttpServletResponse response) {
		
		String userIDOrder = request.getParameter("userIdOrder");
		
		int userIdOrder = Integer.valueOf(userIDOrder);
		
		//根据用户id查询个人订单 
		List <Order_Property>  orderVO=orderServiceImpl.queryUserIdOrder(userIdOrder);
		
		for (int i = 0; i < orderVO.size(); i++) {
			
			//存储OrderVO对象
			Order_Property orderVOs = orderVO.get(i);
			//获取订单ID
			int orderId = orderVOs.getId();	
			//根据订单ID查询菜品信息
			List<Menu_Property> menus =menuStoreImpl.findMenuByOrderId(orderId);;	
			orderVOs.setMenuByde(menus);	
			//根据订单ID查询用户信息
			List  <UserProperties> userList = userStoreImpl.findUserInfoquery(orderId);
			orderVOs.setUserlis(userList);
		}
		
		//将JAVA对象转为字符串 发送给前台
		String orderby = JSONObject.toJSONString(orderVO);	
		return orderby;
	}
	
	/**
	 * 用户充值
	 * @param request
	 * @param response
	 * @return
	 */
	public String userRechargeMoney(HttpServletRequest request, HttpServletResponse response) {
		
		int money=Integer.valueOf(request.getParameter("moneys"));
		int userID = Integer.valueOf(request.getParameter("userID"));
		userServiceImpl.rechargeMoney(money, userID);
		return "充值成功！";
	}

	
	/**
	 * 查询所有订单（管理员）
	 * @param request
	 * @param response
	 * @return
	 */
	public String queryOrderAll(HttpServletRequest request, HttpServletResponse response) {
		
			//获取页数
			int page = Integer.valueOf(request.getParameter("page"));	
			//展示的行数
			int rows = Integer.valueOf(request.getParameter("rows"));
			
			String stateby = null;
			
			MessageVO<Object> mv = new MessageVO<>();
			
			List <Order_Property> list = orderStoreImpl.query(stateby,page,rows);
			for (int i = 0; i < list.size(); i++) {
				
				Order_Property vo = list.get(i);
				
				//获取订单ID
				int orderId = vo.getId();
				
				//根据订单Id查询菜品信息
				List<Menu_Property> menus = menuStoreImpl.findMenuByOrderId(orderId);
				vo.setMenuByde(menus);		
				
				//获取总金融和折扣
				List<Float> sumMoneyOrDiscount= getSumMoney(menus);
				vo.setSumMoney(sumMoneyOrDiscount);
				
				//根据订单Id查询用户信息
				List<UserProperties> users = userStoreImpl.findUserInfoquery(orderId);
				vo.setUserlis(users);		
				
			}
			//获取总行数
			mv.setTotal(orderStoreImpl.total());
			mv.setRows(list);
			
			String lis = JSONObject.toJSONString(mv);
			return lis;
	}
	
	
	/**
	 * 计算总金融和折扣
	 * @param menus
	 * @return
	 */
	public List<Float> getSumMoney(List<Menu_Property> menus) {
		List<Float> list = new ArrayList<>();	
		
		float sumMoney = 0;	
		float discount = 0;
		
		for (int i = 0; i < menus.size(); i++) {
			Menu_Property Menu_Property = menus.get(i);			
			//总金融
			sumMoney += Menu_Property.getAmount()*(Menu_Property.getPrice());
			//折扣
			discount+=Menu_Property.getDiscount_price();
		}
		list.add(sumMoney);
		list.add(discount);
		return list;
	}

	/**
	 * 根据状态查询订单
	 * @param request
	 * @param response
	 * @return
	 */
	public String  queryStateOrder(HttpServletRequest request, HttpServletResponse response) {
		//获取状态
		int  state = Integer.parseInt(request.getParameter("state"));
		//获取用户id
		int  userId = Integer.parseInt(request.getParameter("userIdOr"));	
		//获取页数
		int page = Integer.valueOf(request.getParameter("page"));	
		//展示的行数
		int rows = Integer.valueOf(request.getParameter("rows"));
		
		System.out.println(state+"  "+userId+"  "+page+" "+rows);
		
		MessageVO<Object> mv = new MessageVO<>();
		List <Order_Property> list=orderServiceImpl.queryStateOrder(state, userId,page , rows);
		for (int i = 0; i < list.size(); i++) {
			
			Order_Property vo = list.get(i);	
			//获取订单ID
			int orderId = vo.getId();	
			
			//根据订单Id查询菜品信息
			List<Menu_Property> menus = menuStoreImpl.findMenuByOrderId(orderId);
			vo.setMenuByde(menus);		
			
			//获取总金融和折扣
			List<Float> sumMoneyOrDiscount= getSumMoney(menus);
			vo.setSumMoney(sumMoneyOrDiscount);
			
			//根据订单Id查询用户信息
			List<UserProperties> users = userStoreImpl.findUserInfoquery(orderId);
			vo.setUserlis(users);		
		}
		mv.setTotal(orderServiceImpl.queryStateOrderTotal(state, page, rows, userId));
		mv.setRows(list);
		String msg = JSON.toJSONString(mv);
		
		return msg;
	}
	
	/**
	 * 根据订单Id修改订单状态
	 * @param request
	 * @param response
	 * @return
	 */
	public String stateUpdateOrder(HttpServletRequest request, HttpServletResponse response) {
		//获取订单id
		int orderId = Integer.parseInt(request.getParameter("orderId")); 
		//获取要修改的状态
		int state = Integer.parseInt(request.getParameter("state"));
		//获取取消原因
		String cancel = request.getParameter("cancelReason");
		
		if(state==5) {
			//获取要支付的金融
			double money = Double.parseDouble(request.getParameter("money"));
			//获取用户Id
			int userId = Integer.parseInt(request.getParameter("userId"));
			//取消订单后扣除管理金额
			userServiceImpl.updateMoney(money, 1);
			//取消订单后将金额返回给用户
			userServiceImpl.rechargeMoney(money, userId);
		}
		
		orderServiceImpl.stateUpdateOrder(orderId, state,cancel);
		return "修改成功！";
	}

	/**
	 * 根据订单号删除购物车
	 * @param request
	 * @param response
	 * @return
	 */
	public String deleteShopping(HttpServletRequest request, HttpServletResponse response) {
		String deleteList = request.getParameter("listOrderDelete");
		List<ShoppingTrolleyProperties> arrayList = new ArrayList<>();
		
		//将JSON数组转为java数组
		JSONArray jsonArray = JSON.parseArray(deleteList);
		arrayList = jsonArray.toJavaList(ShoppingTrolleyProperties.class);
		orderServiceImpl.deleteTrolley(arrayList,ShoppingTrolleyServiceImpl);
		return "删除成功！";
	}
	
	public String toUPMoney(HttpServletRequest request, HttpServletResponse response) {
		//获取要支付的金融
		double money = Double.parseDouble(request.getParameter("money"));
		//获取用户Id
		int userId = Integer.parseInt(request.getParameter("userId"));
		//用户充值
		userServiceImpl.rechargeMoney(money, userId);
		return "充值成功！";
	}
}
