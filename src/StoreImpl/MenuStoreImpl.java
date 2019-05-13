package StoreImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import StoreInterface.MenuStore;
import menu_server.MySQLConnection;
import menu_server.Utils;
import propertys.Menu_Property;


public class MenuStoreImpl implements MenuStore{
	
	private Connection con = null;
	
	private List <Menu_Property> list =null ;
	
	public MenuStoreImpl() {
		//建立连接
		con = MySQLConnection.getConnectionInstance();
	}
	
	/**
	 * 增加菜品
	 */
	public void add(Menu_Property fpro) {
		PreparedStatement add = null;
		try {
			add = con.prepareStatement("insert into order_menu_t (name,price,discount_price,create_time,is_top,state,sales_volume) values(?,?,?,?,?,?,?)");
			
			//给？赋值
			add.setString(1, fpro.getName());
			add.setDouble(2, fpro.getPrice());
			add.setDouble(3, fpro.getDiscount_price());
			add.setString(4, Utils.getDate().toString());	
			if("置顶".equals(fpro.getIsTop())) {
				add.setString(5,"Y");
			}else {
				add.setString(5,"N");
			}
			add.setString(6, fpro.getState());
			add.setInt(7, fpro.getSales_volume());
			
			//执行sql
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
	public void menuDelete(int id) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("update order_menu_t set state=0 WHERE id=?");
			ps.setInt(1,id);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 修改菜品
	 * @param fpro
	 */
	public void updateMu(Menu_Property fpro) {
			String sql="update order_menu_t set name=?,price=?,discount_price=?,is_top=?,state=? WHERE id=?";
			PreparedStatement ps = null;
			try {
				ps= con.prepareStatement(sql);
				ps.setString(1, fpro.getName());
				ps.setDouble(2, fpro.getPrice());
				ps.setDouble(3, fpro.getDiscount_price());
				ps.setString(4,fpro.getIsTop());
				ps.setString(5, fpro.getState());
				ps.setInt(6, fpro.getId());
				//执行sql
				ps.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
	}
	
	/**
	 * 根据 菜品ID 查餐品的信息，返回Menu_Property对象，当对象等于null时，证明没有该名称的餐品
	 * @param id
	 * @return
	 */
	public Menu_Property queryID(int id) {
		Menu_Property fpro = null;
		PreparedStatement queryID = null;
		
		try {
			queryID = con.prepareStatement("select id,name,price,discount_price,(case is_top when 'N' then '不置顶' else '置顶' end) 'is_top',\r\n" + 
					"DATE_FORMAT(create_time,'%Y-%m-%d %H:%i:%S') 'create_time',state,(case state when '0' then '删除'  when '1' then '上架'  when '2' then '下架' end) 'stateName',sales_volume from order_menu_t where id = ?");
			queryID.setInt(1, id);
			

			ResultSet set = queryID.executeQuery();
			while(set.next()) {//			迭代结果集，并把数据封装进FoodProperties对象fpro中
				
				fpro = new Menu_Property(set.getInt("id"), set.getString("name"), set.getFloat("price"), set.getFloat("discount_price"), 
						set.getString("is_top"), set.getString("state"), set.getString("create_time"), set.getInt("sales_volume"));
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
		return fpro;
	}
	
	
	/**
	 * 根据名称查餐品的信息，返回Menu_Property对象，当对象等于null时，证明没有该名称的餐品
	 * @param name
	 * @return
	 */
	public Menu_Property queryName(String name) {
		PreparedStatement  queryName = null;
		Menu_Property fpro = null;
		try {
			queryName = con.prepareStatement("select id,name,price,discount_price,(case is_top when 'N' then '不置顶' else '置顶' end) 'is_top',\r\n" + 
					"DATE_FORMAT(create_time,'%Y-%m-%d %H:%i:%S') create_time,state,sales_volume from order_menu_t where name = ?");
			queryName.setString(1, name);
			
			//迭代结果集
			ResultSet set = queryName.executeQuery();
			while(set.next()) {
				fpro = new Menu_Property(set.getInt("id"), set.getString("name"), set.getFloat("price"), set.getFloat("discount_price"), 
						set.getString("is_top"), set.getString("state"), set.getString("create_time"), set.getInt("sales_volume"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			
			try {
				if(queryName != null) {
					queryName.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fpro;
	}
	
	/**
	 * 根据订单ID查询菜品信息
	 * @param orderID
	 * @return
	 */
	public List<Menu_Property> findMenuByOrderId(int orderID){
		List<Menu_Property> list = new ArrayList<>();
		PreparedStatement ps = null;
		try {
			ps=con.prepareStatement("SELECT o.id,o.name,o.price,o.discount_price,o.is_top,DATE_FORMAT(o.create_time,'%Y-%m-%d %H:%i:%S') 'create_time',o.state,(CASE o.state WHEN '0' THEN '删除'	WHEN '1' THEN '上架' 	WHEN '2' THEN'下架'\r\n" + 
					" END ) 'stateName' , o.sales_volume , t.amount FROM order_order_menu_relation_t t LEFT JOIN order_menu_t o ON o.id = t.menu_id WHERE t.order_id =?;") ;
					
			ps.setInt(1, orderID);
		ResultSet resu	=ps.executeQuery();
		
		while(resu.next()) {//			迭代结果集，并把数据封装进FoodProperties对象fpro中
				
			int id = resu.getInt("id");
			float price = resu.getFloat("price");
			String name = resu.getString("name");
			float discount_price = resu.getFloat("discount_price");
			String is_top = resu.getString("is_top");
			String state = resu.getString("state");
			String create_time = resu.getString("create_time");
			int sales_volume = resu.getInt("sales_volume");
			String stateName = resu.getString("stateName");
			int amount = resu.getInt("amount");
			
			Menu_Property menuFood = new Menu_Property();
			
			menuFood.setId(id);
			menuFood.setPrice(price);
			menuFood.setName(name);
			menuFood.setCreateTime(create_time);
			menuFood.setDiscount_price(discount_price);
			menuFood.setIsTop(is_top);
			menuFood.setSales_volume(sales_volume);
			menuFood.setState(state);
			menuFood.setStateName(stateName);	
			menuFood.setAmount(amount);
			
			list.add(menuFood);
		}
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
		return list;
	}
	
	
/**
 * 查询菜品信息
 */
public List<Menu_Property> menuQuery() {
	
	//存储菜品信息
	List<Menu_Property> menuList = null;				
	PreparedStatement ps = null;	
	try {
		ps=con.prepareStatement("select m.id, m.name, m.price, m.discount_price, m.is_top, m.create_time, m.state, m.sales_volume FROM order_menu_t m;");			
		menuList = iterates(ps.executeQuery());				
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
			try {
				if(ps != null) {						
					ps.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
		}
	}
	return menuList;
}

public List<Menu_Property> menuPricePage(int page, int rows ,String  menuPrice , String sort,String order) {
	
	//存储排序字段和格式
	String orderBys = null;
	
	if(sort!=null) {
		//要排序的字段	
		String[] sortArray = sort.split(",");
		
		String[] orderArray = order.split(",");	
		
		if(sortArray.length==0) {
			
			orderBys=sort+" "+order;
			
		}else {
			for (int j = 0; j < sortArray.length; j++) {
				if(j==0) {
					orderBys=sortArray[j]+" "+orderArray[j];
				}else {
					orderBys+=( j==0 ? sortArray[j]+" "+orderArray[j] : ","+sortArray[j]+" "+orderArray[j]);
				}	
			}
		}
	}
	
	//存储菜品信息
	List<Menu_Property> menuList = null;				
	PreparedStatement ps = null;	
	try {
		int menuPrices = Integer.parseInt(menuPrice);
		if (menuPrices==0) {	
			//排序
			if(orderBys !=null  ) {
				ps=con.prepareStatement("select m.id, m.name, m.price, m.discount_price, m.is_top, m.create_time, m.state, m.sales_volume FROM order_menu_t m ORDER BY "+orderBys+" LIMIT ?,?");
			}else {
				ps=con.prepareStatement("select m.id, m.name, m.price, m.discount_price, m.is_top, m.create_time, m.state, m.sales_volume FROM order_menu_t m LIMIT ?,?");
			}					
		}else {
			//根据菜品价格查询相差5 或 10 之间的菜品信息			
			ps=con.prepareStatement("select m.id, m.name, m.price, m.discount_price, m.is_top, m.create_time, m.state, m.sales_volume FROM order_menu_t m where price>=("+menuPrices+"-5) and price<=("+menuPrices+"+5) LIMIT ?,?");
		}	
		
		ps.setInt(1, (page-1)*rows);
		ps.setInt(2, rows);
		menuList = iterates(ps.executeQuery());				
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
			try {
				if(ps != null) {						
					ps.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
		}
	}
	return menuList;
}



/**
 * 菜品分页	
 * @param page
 * @param rows
 * @return
 */
public List<Menu_Property> menuPages(int page, int rows ,String menuName ,String isTop,String state ,String srot,String orderB) {
	
	//存储排序字段和格式
	String orderBys = null;
	
	if(srot!=null) {
		//要排序的字段	
		String[] sortArray = srot.split(",");
		
		String[] orderArray = orderB.split(",");	
		
		if(sortArray.length==0) {
			
			orderBys=srot+" "+orderB;
			
		}else {
			for (int j = 0; j < sortArray.length; j++) {
				if(j==0) {
					orderBys=sortArray[j]+" "+orderArray[j];
				}else {
					orderBys+=( j==0 ? sortArray[j]+" "+orderArray[j] : ","+sortArray[j]+" "+orderArray[j]);
				}	
			}
		}
	}
	
	//存储菜品信息
	List<Menu_Property> menuList = null;				
	PreparedStatement ps = null;	
	try {
		if(menuName==null && isTop == null && state==null) {
			if(orderBys !=null) {
				ps=con.prepareStatement("select m.id, m.name, m.price, m.discount_price, m.is_top, m.create_time, m.state, m.sales_volume FROM order_menu_t m  ORDER BY  "+orderBys+"  LIMIT ?,?");
				ps.setInt(1, (page-1)*rows);
				ps.setInt(2, rows);
			}else {
				ps=con.prepareStatement("select m.id, m.name, m.price, m.discount_price, m.is_top, m.create_time, m.state, m.sales_volume FROM order_menu_t m LIMIT ?,?");
				ps.setInt(1, (page-1)*rows);
				ps.setInt(2, rows);
			}
		}else {
			//根据菜品名模糊查询
			ps=con.prepareStatement("select m.id, m.name, m.price, m.discount_price, m.is_top, m.create_time, m.state, m.sales_volume FROM order_menu_t m where m.name LIKE concat('%',?,'%') and m.is_top LIKE ? and m.state like ? LIMIT ?,?");						
			
			//校验name是否为空
			if("".equals(menuName)) {
				ps.setString(1, "");
			}else {
				ps.setString(1, menuName);
			}
			
			//校验isTop是否为空
			if("".equals(isTop)) {
				ps.setString(2, "%");
			}else {
				ps.setString(2, isTop);
				System.out.println("+++"+isTop);
			}
			
			//校验state是否为空
			if("".equals(state)) {
				ps.setString(3, "%");
			}else {
				ps.setString(3, state);
			}
			
			ps.setInt(4, (page-1)*rows);
			ps.setInt(5, rows);
		}
		
		menuList = iterates(ps.executeQuery());				
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
			try {
				if(ps != null) {						
					ps.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
		}
	}
	return menuList;
}

	
/**
 * 菜品分页	
 * @param page
 * @param rows
 * @return
 */
public List<Menu_Property> menuPage(int page, int rows ,String menuName , String sort,String order) {
	
	//存储排序字段和格式
	String orderBys = null;
	
	if(sort!=null) {
		//要排序的字段	
		String[] sortArray = sort.split(",");
		
		String[] orderArray = order.split(",");	
		
		if(sortArray.length==0) {
			
			orderBys=sort+" "+order;
			
		}else {
			for (int j = 0; j < sortArray.length; j++) {
				if(j==0) {
					orderBys=sortArray[j]+" "+orderArray[j];
				}else {
					orderBys+=( j==0 ? sortArray[j]+" "+orderArray[j] : ","+sortArray[j]+" "+orderArray[j]);
				}	
			}
		}
	}
	
	//存储菜品信息
	List<Menu_Property> menuList = null;				
	PreparedStatement ps = null;	
	try {
		if (menuName==null ) {
			 //排序
			if(orderBys !=null  ) {
				ps=con.prepareStatement("select m.id, m.name, m.price, m.discount_price, m.is_top, m.create_time, m.state, m.sales_volume FROM order_menu_t m ORDER BY "+orderBys+" LIMIT ?,?");
			}else {
				ps=con.prepareStatement("select m.id, m.name, m.price, m.discount_price, m.is_top, m.create_time, m.state, m.sales_volume FROM order_menu_t m LIMIT ?,?");
			}
								
		}else {
			//根据菜品名模糊查询
			ps=con.prepareStatement("select m.id, m.name, m.price, m.discount_price, m.is_top, m.create_time, m.state, m.sales_volume FROM order_menu_t m where name LIKE '%"+menuName+"%' LIMIT ?,?");
		}	
		
		ps.setInt(1, (page-1)*rows);
		ps.setInt(2, rows);
		menuList = iterates(ps.executeQuery());				
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
			try {
				if(ps != null) {						
					ps.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
		}
	}
	return menuList;
}


//数据的总条数量
public int totals(String menuName ,String isTop,String state) {
	
	int totalRow =0;
	
	PreparedStatement ps = null;	
	try {
		if(menuName!=null) {
			ps= con.prepareStatement("select count(1) FROM order_menu_t m where m.name LIKE concat('%',?,'%') and m.is_top LIKE ? and m.state like ? ");
			
			//校验name是否为空
			if("".equals(menuName)) {				
				ps.setString(1, "");
			}else {
				ps.setString(1, menuName);
			}
			
			//校验isTop是否为空
			if("".equals(isTop)) {
				ps.setString(2, "%");
			}else {
				ps.setString(2, isTop);
				System.out.println("+++"+isTop);
			}
			
			//校验state是否为空
			if("".equals(state)) {
				ps.setString(3, "%");
			}else {
				ps.setString(3, state);
			}
			//ps=con.prepareStatement("select count(1) FROM order_menu_t where name  LIKE '%"+menuName+"%'");
		}else {
			ps=con.prepareStatement("select count(1) FROM order_menu_t ");	
		}	
		
		ResultSet set=ps.executeQuery();
		set.next();
		totalRow = set.getInt(1);
				
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
			try {
				if(ps != null) {						
					ps.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
		}
	}
	return totalRow;
}


//数据的总条数量
public int total(String menuName , String menuPrices) {
	
	int totalRow =0;
	
	PreparedStatement ps = null;	
	try {
		if(menuName!=null ) {
			
			ps=con.prepareStatement("select count(1) FROM order_menu_t where name  LIKE '%"+menuName+"%'");
			
		}else if(menuPrices != null){
			
			int menuPrice = Integer.parseInt(menuPrices);
			ps=con.prepareStatement("select count(1) FROM order_menu_t where  price>=("+menuPrice+"-5) and price<=("+menuPrice+"+5)");
			
		}else {
			ps=con.prepareStatement("select count(1) FROM order_menu_t ");	
		}	
		
		ResultSet set=ps.executeQuery();
		set.next();
		totalRow = set.getInt(1);
				
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
			try {
				if(ps != null) {						
					ps.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
		}
	}
	return totalRow;
}
	
public List<Menu_Property> iterates(ResultSet set) throws SQLException {
	list = new ArrayList<>();
	
	while(set.next()) {//把数据迭代出来
		//把每一行的数据封装进属性对象中
		Menu_Property	menuProery=new Menu_Property(set.getInt("id"),set.getString("name"),set.getLong("price"),set.getFloat("discount_price"),
				set.getString("is_top"),set.getString("create_time"),set.getString("state"),set.getInt("sales_volume"));
		
		//把属性对象add进集合里
		list.add(menuProery);
	}
	
	return list;
}

@Override
public List<Menu_Property> queryOne() {
	List<Menu_Property> foodList = null;
	PreparedStatement queryOne = null;
	try {
		queryOne = con.prepareStatement("select id,name,price,discount_price,(case is_top when 'N' then '不置顶' else '置顶' end) 'is_top',\r\n" + 
				"DATE_FORMAT(create_time,'%Y-%m-%d %H:%i:%S') 'create_time',state,(case state when '0' then '删除'  when '1' then '上架'  when '2' then '下架' end) 'stateName',sales_volume from order_menu_t WHERE state!=0 order by is_top desc");
		//迭代sql查询出的信息
		foodList = iterates(queryOne.executeQuery());
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		
		try {
			if(queryOne != null) {
				queryOne.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return foodList;
}
//查询状态不等于 0 的总条数
public int totalOne() {
	PreparedStatement toOne = null;
	int total = 0;
	try {
		toOne=con.prepareStatement("SELECT COUNT(1) FROM order_menu_t  m WHERE  m.state!=0");
		ResultSet executeQuery = toOne.executeQuery();
		executeQuery.next();
		total=executeQuery.getInt(1);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return total;
}


@Override
public List<Menu_Property> queryTwo() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<Menu_Property> queryThree() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public int updatePrice(int id, double price) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int updateFoodName(int id, String name) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int updateDiscountPrice(int id, double discountPrice) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int updateIsTop(int id, String isTop) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int updateState(int id, String state) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public void updateSalesVolume(int id, int amount) {
	// TODO Auto-generated method stub
	
}

@Override
public void deleteShoppingId(int id) {
	// TODO Auto-generated method stub
	
}

}
