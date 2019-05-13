package propertys;

import java.io.Serializable;
import java.util.List;

public class Order_Property implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id; //订单号 id
	
	private String createTime; //下单时间  order_time（默认为当下）
	
	private String receiveTime; //接单时间  receive_time（默认为null）
	
	private String endTime; //收货时间  end_time（默认为null）
	
	private String state; //订单状态  state
	
	private String stateName; //	
	
	private String cancelReason; //取消原因  cancel_reason
	
	private int userId; //用户ID
	
	private List<Float> sumMoney; //总金融 和折扣

	private List<Menu_Property> menuByde; //菜品信息

	private List<UserProperties > userlis; //用户信息
	
	private String comment; //备注


	public Order_Property(int id, int userId, String createTime, String receiveTime, String endTime, String state,
			String cancelReason ,String stateName) {
	
		this.id = id;
		this.userId = userId;
		this.createTime = createTime;
		this.receiveTime = receiveTime;
		this.endTime = endTime;
		this.state = state;
		this.cancelReason = cancelReason;
		this.stateName = stateName;
	}

	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	
	public List<Float> getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(List<Float> sumMoney) {
		this.sumMoney = sumMoney;
	}


	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	public List<UserProperties> getUserlis() {
		return userlis;
	}

	public void setUserlis(List<UserProperties> userlis) {
		this.userlis = userlis;
	}
	
	public List<Menu_Property> getMenuByde() {
		return menuByde;
	}

	public void setMenuByde(List<Menu_Property> menuByde) {
		this.menuByde = menuByde;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	
	
}
