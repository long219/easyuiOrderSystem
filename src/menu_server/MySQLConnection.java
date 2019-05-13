package menu_server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnection {
	private MySQLConnection() {// 私有化构造函数，保证单例模式
		
	}
	
	private static Connection conn = null;
	
	public static Connection getConnectionInstance(){
	
		try {
			if(conn == null) {
				//加载驱动com.mysql.jdbc.Driver
				Class.forName("com.mysql.jdbc.Driver");
				
				//从资源文件中获取配置
				Properties p = new Properties();
				p.load(MySQLConnection.class.getResourceAsStream("MySQLConnection.properties"));
				
				
				//获取连接
				conn = DriverManager.getConnection(p.getProperty("url"),p.getProperty("username"),p.getProperty("password"));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
}
