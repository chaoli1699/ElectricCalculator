package cn.cienet.electriccalculator.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.cienet.electriccalculator.bean.User;
import cn.cienet.electriccalculator.model.DataSource;

public class BasePresenter<V> {
	
	protected List<User> userList;

	public V view;
	
	public void attachView(V view){
		this.view=view;
		getUserList();
	};
	
	public void detachView(V view){
		this.view=view;
	}
	
	public List<User> getUserList(){
		String userListStr=DataSource.getInstance().readSourceFromFile("userList");
		if (userListStr!=null) {
			userList=new Gson().fromJson(userListStr, new TypeToken<List<User>>(){}.getType());
		}else {
			userList=new ArrayList<User>();
		}
		
		return userList;
	}
	
	public User getUserById(int userId){
		if (userId>0) {
			for(User user: userList){
				if (user.getUserId()==userId) {
					return user;
				}
			}
		}
		return null;
	}
	
	protected boolean ifUserExit(String userName){
		if (userList!=null) {
			for(User u: userList){
				if (u.getUserName().equals(userName)) {
					return true;
				}
			}
		}
		
		return false;
	}
}
