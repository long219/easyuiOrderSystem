package StoreImpl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import StoreInterface.ShoppingTrolleyStore;
import menu_server.MySQLConnection;
import menu_server.Utils;
import propertys.ShoppingTrolleyProperties;



/**
 * 购物车的存储层
 * @author VP
 *
 */
public class ShoppingTrolleyStoreImpl implements ShoppingTrolleyStore {
	private Connection conn = null;
	
	/**
	 * 构造方法：
	 * 		1.建立与数据库的连接
	 */
	public ShoppingTrolleyStoreImpl() {
//		建立连接
		conn = MySQLConnection.getConnectionInstance();
	}
	
	private List<ShoppingTrolleyProperties> shoppingList = new ArrayList<>();
	
	@Override
	public void add(ShoppingTrolleyProperties spro) {
		PreparedStatement add = null;
		String createTime = Utils.getDate();
		//把数据加入数据库里
		try {
			add = conn.prepareStatement("insert into order_shopping_trolley_t (user_id,menu_id,create_time,amount) VALUES (?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);

			add.setInt(1, spro.getUserID());
			add.setInt(2, spro.getMenuID());
			add.setString(3, createTime);
			add.setInt(4, spro.getAmount());
			
			add.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	public void delete(int id,int userID) {
		PreparedStatement delete = null;
//		删除数据库中的对应数据
		try {
			delete = conn.prepareStatement("delete from order_shopping_trolley_t where id = ?");

			delete.setInt(1, id);
			
			delete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(delete != null) {
					delete.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	@Override
	public  List<ShoppingTrolleyProperties> queryUserID(int id) {
		shoppingList = new ArrayList<>();
		PreparedStatement queryID = null;
		try {
			queryID = conn.prepareStatement("select r.id,r.user_id,r.menu_id,DATE_FORMAT(r.create_time,'%Y-%m-%d %H:%i:%S') create_time,r.amount,(r.amount*(m.price-m.discount_price)) money,m.name ,m.price,m.discount_price from order_shopping_trolley_t r left join order_menu_t m on r.menu_id = m.id WHERE user_id=?;\r\n" + 
					"");
			queryID.setInt(1, id);
			
			ResultSet set = queryID.executeQuery();
			while(set.next()) {
				
				shoppingList.add(new ShoppingTrolleyProperties(set.getInt("id"), set.getInt("user_id"), set.getString("name"), 
						set.getInt("menu_id"), set.getInt("amount"), set.getFloat("money"),
						set.getString("create_time"),set.getInt("price"),set.getInt("discount_price")));
			}			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(queryID != null) {
					queryID.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return shoppingList;
	}
	
	
	@Override
	public ShoppingTrolleyProperties queryID(int id) {
		ShoppingTrolleyProperties spro = new ShoppingTrolleyProperties();
		PreparedStatement queryID = null;
		try {
			queryID = conn.prepareStatement("select id,user_id,menu_id,DATE_FORMAT(create_time,'%Y-%m-%d %H:%i:%S') create_time,amount from order_shopping_trolley_t where id = ?");
			queryID.setInt(1, id);
			
			ResultSet set = queryID.executeQuery();
			while(set.next()) {
				spro.setId(set.getInt("id"));
				spro.setUserID(set.getInt("user_id"));
				spro.setMenuID(set.getInt("menu_id"));
				spro.setCreateTime(set.getString("create_time"));
				spro.setAmount(set.getInt("amount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(queryID != null) {
					queryID.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return spro;
	}
	
	public List<ShoppingTrolleyProperties> query() {
		shoppingList = new ArrayList<>();
		PreparedStatement query = null;
		try {
			query = conn.prepareStatement("select r.id,r.user_id,r.menu_id,DATE_FORMAT(r.create_time,'%Y-%m-%d %H:%i:%S') create_time,r.amount,(r.amount*(m.price-m.discount_price)) money,m.name,m.price,m.discount_price from order_shopping_trolley_t r left join order_menu_t m on r.menu_id = m.id");

			ResultSet set = query.executeQuery();
			
			while(set.next()) {// 把数据迭代出来，封装到属性类对象中，然后把属性对象加载进集合中
				
				shoppingList.add(new ShoppingTrolleyProperties(set.getInt("id"), set.getInt("user_id"), set.getString("name"), 
						set.getInt("menu_id"), set.getInt("amount"), set.getFloat("money"),
						set.getString("create_time"),set.getInt("price"),set.getInt("discount_price")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(query != null) {
					query.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return shoppingList;
	}


	@Override
	public void addAmount(ShoppingTrolleyProperties stp,int shoppingId) {
		PreparedStatement ps = null;
		try {
			ps=conn.prepareStatement("UPDATE order_shopping_trolley_t s SET s.amount=(s.amount + ?) WHERE s.id=?");
			ps.setInt(1, stp.getAmount());
			ps.setInt(2, shoppingId);
			ps.executeUpdate();
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
	}

	

}
