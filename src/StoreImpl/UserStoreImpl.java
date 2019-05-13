package StoreImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import StoreInterface.UserStore;
import menu_server.MySQLConnection;
import propertys.UserProperties;


/**
 * 用户存储层
 * @author VP
 *
 */
public class UserStoreImpl implements UserStore {
	private Connection conn = null;
	
	/**
	 * 构造方法：
	 * 		1.建立与数据库的连接
	 */
	public UserStoreImpl() {
		//建立连接
		conn = MySQLConnection.getConnectionInstance();
	}
			

	@Override
	public UserProperties querySomeone(String phone) {
		UserProperties upro = null;
		PreparedStatement querySomeone = null;
		try {
			querySomeone = conn.prepareStatement("select id,user_name,password,phone,address,user_types,money from order_user_t where phone = ?");

			querySomeone.setString(1, phone);
			ResultSet set = querySomeone.executeQuery();
			while(set.next()) {
				upro = new UserProperties(set.getInt("id"), set.getString("user_name"), set.getString("password"),
						set.getString("phone"), set.getString("address"), set.getString("user_types"),set.getFloat("money"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(querySomeone != null) {
					querySomeone.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return upro;
	}
	
	/**
	 * 根据用户ID查询
	 * @param phone
	 * @return
	 */
	public UserProperties querySomeone(int userid) {
		UserProperties upro = null;
		PreparedStatement querySomeone = null;
		try {
			querySomeone = conn.prepareStatement("select id,user_name,password,phone,address,user_types,money from order_user_t where id = ?");

			querySomeone.setInt(1, userid);
			ResultSet set = querySomeone.executeQuery();
			while(set.next()) {
				upro = new UserProperties(set.getInt("id"), set.getString("user_name"), set.getString("password"),
						set.getString("phone"), set.getString("address"), set.getString("user_types"),set.getFloat("money"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(querySomeone != null) {
					querySomeone.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return upro;
	}

	@Override
	public void add(UserProperties upro) {
		PreparedStatement add = null;
		try {
			add = conn.prepareStatement("insert into order_user_t (user_name,password,phone,address,user_types,money) values(?,?,?,?,?,?)");

			//给？赋值
			add.setString(1, upro.getUsername());
			add.setString(2, upro.getPassword());
			add.setString(3, upro.getPhone());
			add.setString(4, upro.getAddress());
			add.setString(5, "C");
			add.setFloat(6, 0);
			
			//执行sql
			add.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(add != null) {
					add.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateMoney(double money, int userID) {
		PreparedStatement updateMoney = null;
		try {
			updateMoney = conn.prepareStatement("update order_user_t set money = (money - ?) where id = ?");

			updateMoney.setDouble(1, money);
			updateMoney.setInt(2, userID);
			
			updateMoney.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(updateMoney != null) {
					updateMoney.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@Override
	public void rechargeMoney(double money, int userID) {
		PreparedStatement updateMoney = null;
		try {
			updateMoney = conn.prepareStatement("update order_user_t set money = (money + ?) where id = ?");

			updateMoney.setDouble(1, money);
			updateMoney.setInt(2, userID);
			
			updateMoney.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(updateMoney != null) {
					updateMoney.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据订单ID查询用户信息
	 */
	public List<UserProperties> findUserInfoquery(int orderID){
		List<UserProperties> list = new ArrayList<>();
		PreparedStatement queryDetails = null;
		try {
			queryDetails = conn.prepareStatement("SELECT t.id,t.user_name,t.phone,t.address ,t.user_types,t.money from order_user_t t WHERE t.id=(SELECT o.user_id FROM order_ordert_t o WHERE o.id= ?);\r\n" + 
					"");
			queryDetails.setInt(1, orderID);
			ResultSet set =  queryDetails.executeQuery();
			while(set.next()) {
				int id = set.getInt(1);
				String user_name = set.getString(2);
				String phone = set.getString(3);
				String address = set.getString(4);
				String user_types = set.getString(5);
				float  money= set.getFloat(6);
				
				UserProperties userlis = new UserProperties();
				
				userlis.setId(id);
				userlis.setPhone(phone);
				userlis.setAddress(address);
				userlis.setMoney(money);
				userlis.setUsername(user_name);
				userlis.setUser_types(user_types);
				
				list.add(userlis);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				queryDetails.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}
}
