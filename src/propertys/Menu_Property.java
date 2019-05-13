package propertys;

import java.io.Serializable;

public class Menu_Property  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id; //菜品Id
	private String name; //菜品名字
	private float price; //菜品价格
	private float discount_price; //折扣
	private String isTop; //是否置顶
	private String createTime; //创建时间
	private String state; //状态
	private String stateName; 
	private int sales_volume; //销量
	public int getSales_volume() {
		return sales_volume;
	}

	public void setSales_volume(int sales_volume) {
		this.sales_volume = sales_volume;
	}


	private int amount;

	public Menu_Property() {
		
	}
	
	public Menu_Property(int id, String name, float price, float discount_price, String isTop, String createTime,
			String state, int sales_volume) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.discount_price = discount_price;
		this.isTop = isTop;
		this.createTime = createTime;
		this.state = state;
		this.sales_volume = sales_volume;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public float getDiscount_price() {
		return discount_price;
	}

	public void setDiscount_price(float discount_price) {
		this.discount_price = discount_price;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getIsTop() {
		return isTop;
	}
	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}


	@Override
	public String toString() {
		return "Menu_Property [id=" + id + ", name=" + name + ", price=" + price + ", discount=" + discount_price + ", isTop="
				+ isTop + ", createTime=" + createTime + ", state=" + state + ", salesVolume=" + sales_volume + "]";
	}
	
}
