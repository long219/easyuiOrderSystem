package propertys;

public class FoodDetailsProperties {
	private String foodName; // 餐品名称
	
	private int amount; // 餐品数量
	
	private float money; // 总金额

	public FoodDetailsProperties(String foodName, int amount, float money) {
		this.foodName = foodName;
		this.amount = amount;
		this.money = money;
	}

	@Override
	public String toString() {
		return foodName + "\t" + amount + "\t" + money;
	}
}
