package propertys;

import java.io.Serializable;

public class UserProperties implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id; // 用户ID
	
	private String username; // 用户名  
	
	private String password; // 密码  
	
	private String phone; // 联系电话 
	
	private String address; // 送餐地址  
	
	private String user_types; // 用户类型 
	
	private float money; // 账户余额  
	
	
	public UserProperties() {
	}

	public UserProperties(int id, String username, String password, String phone, String address,String user_types,float money) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.user_types=user_types;
		this.money = money;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUser_types() {
		return user_types;
	}

	public void setUser_types(String user_types) {
		this.user_types = user_types;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "id=" + id + ", username=" + username + ", phone=" + phone
				+ ", address=" + address + ", user_types=" + user_types + ", money=" + money ;
	}
	
}
