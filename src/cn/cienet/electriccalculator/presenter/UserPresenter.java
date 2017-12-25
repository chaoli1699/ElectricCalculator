package cn.cienet.electriccalculator.presenter;

import com.google.gson.Gson;

import cn.cienet.electriccalculator.bean.User;
import cn.cienet.electriccalculator.model.DataSource;
import cn.cienet.electriccalculator.view.UserView;

public class UserPresenter extends BasePresenter<UserView>{

	public UserPresenter(UserView view) {
		// TODO Auto-generated constructor stub
		attachView(view);
	}
	
	public void refreshUser(User mUser){
		if (mUser!=null) {
			for(User user: userList){
				if (user.getUserId()==mUser.getUserId()) {
					user=mUser;
					DataSource.getInstance().writeSource2File("userList", new Gson().toJson(userList));
					view.refreshUserSuccess();
				}
			}
		}
	}
}
