package serviceImpl;

import java.util.List;

import StoreImpl.MenuStoreImpl;
import propertys.Menu_Property;
import service.MenuService;


/**
 * 餐品逻辑层
 * @author VP
 *
 */
public class MenuServiceImpl implements MenuService {
	private MenuStoreImpl menuStoreImpl = null; // 存储层借口对象
	
	public MenuServiceImpl() {
		menuStoreImpl = new MenuStoreImpl();
	}

	@Override
	public boolean check(String name) {
		if(queryName(name) == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 根据名称查餐品的信息，返回FoodProperties对象，当对象等于null时，证明没有该名称的餐品
	 */
	public Menu_Property queryName(String name) {
		
		return menuStoreImpl.queryName(name);
	}

	@Override
	public void add(Menu_Property fpro) {
		menuStoreImpl.add(fpro);	
	}
	
	
	@Override
	public boolean check(int id) {
		if(queryID(id) == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 根据ID查餐品的信息，返回FoodProperties对象，当对象等于null时，证明没有该名称的餐品
	 */
	public Menu_Property queryID(int id) {
		
		return menuStoreImpl.queryID(id);
	}

	
	//修改菜品
	public String updateMU(Menu_Property fpro) {
		menuStoreImpl.updateMu(fpro);
		return "修改成功";
	}
	
	/**
	 * 菜品分页
	 * @param page
	 * @param rows
	 * @param name
	 * @return
	 */
	public List<Menu_Property> menuPages(int page, int rows, String name , String isTop , String state,String sort,String orderB) {
		
		return menuStoreImpl.menuPages(page, rows, name,isTop,state,sort,orderB);
	}
	
	/**
	 * 获取数据的总数量
	 * @param name
	 * @param price
	 * @return
	 */
	public int total(String name ,String price) {
		
		return menuStoreImpl.total(name, price);
	}
	/**
	 * 根据name isTop state 查询总数量
	 * @param name
	 * @param isTop
	 * @param state
	 * @return
	 */
	public int totals(String name, String isTop, String state) {
		
		return menuStoreImpl.totals(name, isTop, state);
	}

	@Override
	public void menuDelete(int menuId) {
		menuStoreImpl.menuDelete(menuId);	
	}
	
	@Override
	public List<Menu_Property> queryOne() {
		
		return menuStoreImpl.queryOne();
	}
	
	/**
	 * 查询总记录条数
	 * @return
	 */
	public int totalOne() {
		return menuStoreImpl.totalOne();
		
	}
	
	
	/*//以id查询
	public List<Menu_Property> menuUpdateQuery(int id){
		return menuStoreImpl.menuUpdateQuery(id);
	}

	@Override
	public List<Menu_Property> queryTwo() {

		return menuStoreImpl.queryTwo();
		
	}

	@Override
	public List<Menu_Property> queryThree() {

		return menuStoreImpl.queryThree();
	}

	@Override
	public String updatePrice(int id, double price) {
		if(menuStoreImpl.updatePrice(id, price) == 1) {
			return "修改成功";
		}
		return "修改失败";
	}

	@Override
	public String updateFoodName(int id, String name) {
		if(menuStoreImpl.updateFoodName(id, name) == 1) {
			return "修改成功";
		}
		return "修改失败";
	}

	@Override
	public String updateDiscountPrice(int id, double discountPrice) {
		if(menuStoreImpl.updateDiscountPrice(id, discountPrice) == 1) {
			return "修改成功";
		}
		return "修改失败";
	}
*/
	
	/*@Override
	public String updateIsTop(int id, String isTop) {
		if(menuStoreImpl.updateIsTop(id, isTop) == 1) {
			return "修改成功";
		}
		return "修改失败";
	}

	@Override
	public String updateState(int id, String state) {
		if(menuStoreImpl.updateState(id, state) == 1) {
			return "修改成功";
		}
		return "修改失败";
	}

	@Override
	public void deleteFood(int id) {
		menuStoreImpl.deleteFood(id);
	}
	
	@Override
	public void deleteShoppingID(int shoppingID) {
		menuStoreImpl.deleteShoppingId(shoppingID);
	}
	@Override
	public void updateSalesVolume(int id, int amount) {
		menuStoreImpl.updateSalesVolume(id, amount);		
	}
	
	public void updateDeleteOrDown(int id ,String state) {
		menuStoreImpl.updateDeleteORdown(id, state);
	}
*/
}
