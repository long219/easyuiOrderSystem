package propertys;

/**
 * 购物车属性类
 * @author VP
 *
 */
public class ShoppingTrolleyProperties {
	private int id; // 序号 id
	
	private int userID; // 用户ID
	
	private String name; // 商品名称
	
	private int menuID; // 餐品ID
	
	private int amount; // 数量
	
	private int price;//价格
	
	private int discount;//折扣

	private float money; // 金额 
	
	private String createTime; // 选购时间	

	public ShoppingTrolleyProperties(int id, int userID, String name, int menuID, int amount, float money,
			String createTime,int price,int discount) {
		this.id = id;
		this.userID = userID;
		this.name = name;
		this.menuID = menuID;
		this.amount = amount;
		this.money = money;
		this.createTime = createTime;
		this.price=price;
		this.discount=discount;
	}

	
	public ShoppingTrolleyProperties() {
	}

	

	public int getMenuID() {
		return menuID;
	}


	public void setMenuID(int menuID) {
		this.menuID = menuID;
	}

	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getDiscount() {
		return discount;
	}


	public void setDiscount(int discount) {
		this.discount = discount;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ShoppingTrolleyProperties [id=" + id + ", userID=" + userID + ", name=" + name + ", menuID=" + menuID
				+ ", amount=" + amount + ", money=" + money + ", createTime=" + createTime + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + userID;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShoppingTrolleyProperties other = (ShoppingTrolleyProperties) obj;
		if (id != other.id)
			return false;
		if (userID != other.userID)
			return false;
		return true;
	}



}
