package serviceImpl;

import StoreImpl.UserStoreImpl;
import propertys.UserProperties;
import service.UserService;


public class UserServiceImpl implements UserService {
	private UserStoreImpl userStoreImpl = null;
	
	public UserServiceImpl() {
		userStoreImpl = new UserStoreImpl();
	}

	
	@Override
	public void add(UserProperties upro) {
		userStoreImpl.add(upro);	
	}

	
	@Override
	public UserProperties querySomeone(String phone) {
		
		return userStoreImpl.querySomeone(phone);
	}
	
	
	public UserProperties querySomeone(int userid) {
		
		return userStoreImpl.querySomeone(userid);
	}
	@Override
	public boolean check(String phone) {
		if(querySomeone(phone) == null) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean check(UserProperties upro,String password,String phone) {
		if(password.equals(upro.getPassword()) && phone.equals(upro.getPhone())) {
			return true;
		}
		return false;
	}


	@Override
	public void updateMoney(double money, int userID) {
		
		userStoreImpl.updateMoney(money, userID);
	}
	
	@Override
	public void rechargeMoney(double money, int userID) {
		
		userStoreImpl.rechargeMoney(money, userID);
	}


}
