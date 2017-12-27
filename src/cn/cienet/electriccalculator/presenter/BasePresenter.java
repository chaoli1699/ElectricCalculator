package cn.cienet.electriccalculator.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import cn.cienet.electriccalculator.bean.User;
import cn.cienet.electriccalculator.model.FileSource;
import cn.cienet.electriccalculator.sql.UserDao;

public class BasePresenter<V> {
	
	protected List<User> userList;

	public V view;
	private Context context;
	
	public void attachView(Context context, V view){
		this.context=context;
		this.view=view;
		getUserList();
	};
	
	public void detachView(V view){
		this.view=view;
	}
	
	public int getUserAmount(){
		
		int userAmount=0;
		String userAmountStr=FileSource.getInstance().readSourceFromFile("userAmount");
		if(userAmountStr!=null){
			String[] temp=userAmountStr.split(":");
		    userAmount= Integer.valueOf(temp[1].trim());
		}else {
			FileSource.getInstance().writeSource2File("userAmount", "User amount is:"+ new UserDao(context).getUserAmount());
			getUserAmount();
		}
		
		return userAmount;
	}
	
	public List<User> getUserList(){
		String userListStr=FileSource.getInstance().readSourceFromFile("userList");
		if (userListStr!=null) {
			userList=new Gson().fromJson(userListStr, new TypeToken<List<User>>(){}.getType());
		}else {
			userList=new UserDao(context).queryUsers();
			if (userList!=null) {
				FileSource.getInstance().writeSource2File("userList", new Gson().toJson(userList));
				getUserList();
				getUserAmount();
			}else {
				userList=new ArrayList<User>();
			}	
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
