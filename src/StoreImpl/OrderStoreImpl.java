package StoreImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import StoreInterface.OrderStore;
import menu_server.MySQLConnection;
import menu_server.Utils;
import propertys.Order_Property;
import propertys.ShoppingTrolleyProperties;

public class OrderStoreImpl implements OrderStore{
	
	private  Connection conn  = null;
	
	public OrderStoreImpl() {
		//创建连接
		conn = MySQLConnection.getConnectionInstance();
	}
	
	@Override
	public void add(List<ShoppingTrolleyProperties> arrayList) {
		// TODO Auto-generated method stub
		String createTime = Utils.getDate();
		int orderId = -1;
		PreparedStatement insertOrder = null;
		try {
			insertOrder = conn.prepareStatement("insert into order_ordert_t (user_id,create_time) values(?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			//插入数据到订单表
			insertOrder.setInt(1, arrayList.get(0).getUserID());
			insertOrder.setString(2, createTime);
			insertOrder.executeUpdate();
			//获取主键
			ResultSet set = insertOrder.getGeneratedKeys();
			while(set.next()) {
				orderId = set.getInt(1);
				break;
			}
			for (int i = 0; i < arrayList.size(); i++) {
				ShoppingTrolleyProperties stp = arrayList.get(i);		
				//插入数据到订单与菜单的关系表
				insertRelation(orderId,stp);
			}			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//下单时增加订单关系表
	private void insertRelation(int orderID, ShoppingTrolleyProperties spro) throws SQLException {
		
		PreparedStatement insertRelation = conn.prepareStatement("insert into order_order_menu_relation_t (order_id,menu_id,amount) values(?,?,?)");	
		insertRelation.setInt(1, orderID);
		insertRelation.setInt(2, spro.getMenuID());
		insertRelation.setInt(3, spro.getAmount());
		insertRelation.executeUpdate();
		
		insertRelation.close();
		
	}
	
	//查询所有订单信息
	@Override
	public List<Order_Property> query(String stateby,int page,int rows) {
		List<Order_Property> list = new ArrayList<>();
		PreparedStatement query = null;
		
		try {
			if(stateby==null || "all".equals(stateby)) {
				query = conn.prepareStatement("select x.id,x.user_id,DATE_FORMAT(x.create_time,'%Y-%m-%d %H:%i:%S') create_time,DATE_FORMAT(x.recieve_time,'%Y-%m-%d %H:%i:%S') recieve_time,\r\n" + 
						"DATE_FORMAT(x.end_time,'%Y-%m-%d %H:%i:%S') end_time,x.state,\r\n" + 
						"(CASE x.state when '1' then '待接单' when '2' then '已接单' when '3' then '待收货' when 4 then '已收货' else '已取消订单' END) stateName,\r\n" + 
						"x.cancel_reason from order_ordert_t x LIMIT ?,?");
				query.setInt(1, (page-1)*rows);
				query.setInt(2, rows);
				
			}else {
				query = conn.prepareStatement("select x.id,x.user_id,DATE_FORMAT(x.create_time,'%Y-%m-%d %H:%i:%S') create_time,DATE_FORMAT(x.recieve_time,'%Y-%m-%d %H:%i:%S') recieve_time,\r\n" + 
						"DATE_FORMAT(x.end_time,'%Y-%m-%d %H:%i:%S') end_time,x.state,\r\n" + 
						"(CASE x.state when '1' then '待接单' when '2' then '已接单' when '3' then '待收货' when 4 then '已收货' else '已取消订单' END) stateName,\r\n" + 
						"x.cancel_reason from order_ordert_t x where state=?");
				query.setString(1, stateby);
			}
			ResultSet set = query.executeQuery();
			while(set.next()) {
				//将字符串时间转为时间类型
				long time = set.getTimestamp("create_time").getTime();
				//获取当前时间
				long now = new Date().getTime();
				
				Order_Property	opro = new Order_Property(set.getInt("id"), set.getInt("user_id"),
						set.getString("create_time"),set.getString("recieve_time"), 
						set.getString("end_time"), set.getString("state"), set.getString("cancel_reason"),set.getString("stateName"));
				
				//Data create_times = new Data(create_times);
				
				long hour=(now-time)/(1000*3600);
				
				if(set.getString("state").equals("1") && hour>=2) {
					opro.setComment("尽快处理此单");
				}else {
					opro.setComment("");
				}
				list.add(opro);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(query != null) {
					query.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	//根据用户Id查询个人订单
	@Override
	public List<Order_Property> queryUserIdOrder(int stateby) {
		List<Order_Property> list = new ArrayList<>();
		PreparedStatement query = null;
		try {
				query = conn.prepareStatement("select x.id,x.user_id,DATE_FORMAT(x.create_time,'%Y-%m-%d %H:%i:%S') create_time,DATE_FORMAT(x.recieve_time,'%Y-%m-%d %H:%i:%S') recieve_time,\r\n" + 
						"DATE_FORMAT(x.end_time,'%Y-%m-%d %H:%i:%S') end_time,x.state,\r\n" + 
						"(CASE x.state when '1' then '待接单' when '2' then '已接单' when '3' then '待收货' when 4 then '已收货' else '已取消订单' END) stateName,\r\n" + 
						"x.cancel_reason from order_ordert_t x where user_id=?");
				query.setInt(1, stateby);
				ResultSet set = query.executeQuery();
				while(set.next()) {
					Order_Property	opro = new Order_Property(set.getInt("id"), set.getInt("user_id"),
							set.getString("create_time"),set.getString("recieve_time"), 
							set.getString("end_time"), set.getString("state"), set.getString("cancel_reason"),set.getString("stateName"));
					list.add(opro);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(query != null) {
					query.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public int total() {
		int totalCount = 0;
		PreparedStatement ps = null;
		
		try {
			ps =conn.prepareStatement("select COUNT(1) FROM order_ordert_t");
			
			ResultSet set=  ps.executeQuery();
			set.next();
			totalCount = set.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return totalCount;
	}
	
	//根据     状态和用户Id 查询订单(管理员)
	@Override
	public List<Order_Property> queryStateOrder(int state,int userId,int page,int rows) {
		List<Order_Property> list = new ArrayList<>();
		PreparedStatement query = null;
		try {
			if (userId != -1) {
				query = conn.prepareStatement("select x.id,x.user_id,DATE_FORMAT(x.create_time,'%Y-%m-%d %H:%i:%S') create_time,DATE_FORMAT(x.recieve_time,'%Y-%m-%d %H:%i:%S') recieve_time,\r\n" + 
						" DATE_FORMAT(x.end_time,'%Y-%m-%d %H:%i:%S') end_time,x.state,(CASE x.state when '1' then '待接单' when '2' then '已接单' when '3' then '待收货' when 4 then '已收货' else '已取消订单' END) stateName, \r\n" + 
						" x.cancel_reason from order_ordert_t x where  x.user_id=? and x.state=? LIMIT ?,?;");
				query.setInt(1, userId);
				query.setInt(2, state);
				query.setInt(3, (page-1)*rows);
				query.setInt(4, rows);			
			}else {
				query = conn.prepareStatement("select x.id,x.user_id,DATE_FORMAT(x.create_time,'%Y-%m-%d %H:%i:%S') create_time,DATE_FORMAT(x.recieve_time,'%Y-%m-%d %H:%i:%S') recieve_time,\r\n" + 
							"	DATE_FORMAT(x.end_time,'%Y-%m-%d %H:%i:%S') end_time,x.state,(CASE x.state when '1' then '待接单' when '2' then '已接单' when '3' then '待收货' when 4 then '已收货' else '已取消订单' END) stateName, \r\n" + 
							"	x.cancel_reason from order_ordert_t x where x.state=? LIMIT ?,?;");
				query.setInt(1, state);
				query.setInt(2, (page-1)*rows);
				query.setInt(3, rows);
			}
			ResultSet set = query.executeQuery();
			
			while(set.next()) {
				Order_Property	opro = new Order_Property(set.getInt("id"), set.getInt("user_id"),set.getString("create_time"),set.getString("recieve_time"), set.getString("end_time"), set.getString("state"), set.getString("cancel_reason"),set.getString("stateName"));
				list.add(opro);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据订单状态和用户id 查询总行记录(total)
	 * @param state
	 * @param page
	 * @param rows
	 * @param userId
	 * @return
	 */
	public int queryStateOrderTotal(int state,int userId) {
		int total = -1;
		PreparedStatement tot = null;
		try {
			if(userId !=-1) {
				tot=conn.prepareStatement("SELECT COUNT(1) FROM order_ordert_t x where user_id=? AND x.state=? ");
				tot.setInt(1, userId);
				tot.setInt(2, state);
			}else {
				tot=conn.prepareStatement("SELECT COUNT(1) FROM order_ordert_t x where x.state=? ");	
				tot.setInt(1, state);
			}
			
			ResultSet executeQuery = tot.executeQuery();
			executeQuery.next();
			total= executeQuery.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total;
	}
	
	//根据订单id修改状态

	@Override
	public void updateStateOrder(int state, int orderId,String cancel) {
		PreparedStatement ps = null;	
		try {
			if (cancel !=null) {
				ps= conn.prepareStatement("UPDATE order_ordert_t o SET o.state=? , o.cancel_reason=? WHERE o.id = ?");
				ps.setInt(1, state);
				ps.setString(2, cancel);
				ps.setInt(3, orderId);
				
			}else {
				ps= conn.prepareStatement("UPDATE order_ordert_t o SET o.state=?  WHERE o.id = ?");
				ps.setInt(1, state);
				ps.setInt(2, orderId);
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	

	
}
