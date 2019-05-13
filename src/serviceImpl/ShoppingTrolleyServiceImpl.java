package serviceImpl;


import java.util.List;

import StoreImpl.ShoppingTrolleyStoreImpl;
import StoreInterface.ShoppingTrolleyStore;
import propertys.Menu_Property;
import propertys.ShoppingTrolleyProperties;
import service.ShoppingTrolleyService;


public class ShoppingTrolleyServiceImpl implements ShoppingTrolleyService {
	private ShoppingTrolleyStore shoppingTrolleyStore = null;
	
	public ShoppingTrolleyServiceImpl() {
		shoppingTrolleyStore = new ShoppingTrolleyStoreImpl();
	}

	@Override
	public String add(ShoppingTrolleyProperties spro) {	
			int index = checkMenuId(spro);
			if (index != -1) {
				shoppingTrolleyStore.addAmount(spro,index);
			}else {
				shoppingTrolleyStore.add(spro);	
			}	
			return "添加成功！";	
	}

	@Override
	public List<ShoppingTrolleyProperties> query() {
		return shoppingTrolleyStore.query();
	}
	
	@Override
	public  List<ShoppingTrolleyProperties> queryUserID(int userId) {
		return shoppingTrolleyStore.queryUserID(userId);
	}

	@Override
	public void delete(int id,int userID) {
		shoppingTrolleyStore.delete(id, userID);	
	}
	
	public boolean check(int id,int userID) {
		ShoppingTrolleyProperties spro = new ShoppingTrolleyProperties();
		spro.setId(id);
		spro.setUserID(userID);
		
		if(shoppingTrolleyStore.query().contains(spro)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean check(String[] idArray,int userID) {
		boolean flag = true;
		
		int id;
		for (int i = 0; i < idArray.length; i++) {
			id = Integer.parseInt(idArray[i]);
			if(!check(id, userID)) {//id输入有误的情况
				System.out.println("您的购物车里没有此序号："+id);
				flag = false;
			}
		}
		return flag;
	}

	@Override
	public float queryMoney(List<ShoppingTrolleyProperties> arrayList,int userID) {
		List<ShoppingTrolleyProperties> list = query();
		float sumMoney = 0;
		for (int i = 0; i < arrayList.size(); i++) {
			ShoppingTrolleyProperties stp = arrayList.get(i);
			for (ShoppingTrolleyProperties trolley : list) {
				if(trolley.getUserID() == userID && trolley.getId() == stp.getId()) {
					sumMoney += trolley.getMoney();
				}
			}
		}
		return sumMoney;
	}

	@Override
	public ShoppingTrolleyProperties queryID(int id) {
		
		return shoppingTrolleyStore.queryID(id);
	}
	
	/**
	 * 校验购物车是否有重复的菜品信息
	 * @param spro
	 * @return
	 */
	public int checkMenuId(ShoppingTrolleyProperties spro) {
		List<ShoppingTrolleyProperties> list = query();
		for (int i = 0; i < list.size(); i++) {
			ShoppingTrolleyProperties stp = list.get(i);
			if(stp.getMenuID()==spro.getMenuID() && spro.getUserID()==stp.getUserID()) {
				return stp.getId();
			}
		}
		return -1;
	}
}
