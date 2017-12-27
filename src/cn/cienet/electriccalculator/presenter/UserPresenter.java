package cn.cienet.electriccalculator.presenter;

import com.google.gson.Gson;

import android.content.Context;
import cn.cienet.electriccalculator.bean.User;
import cn.cienet.electriccalculator.model.FileSource;
import cn.cienet.electriccalculator.view.UserView;

public class UserPresenter extends BasePresenter<UserView>{

	public UserPresenter(Context context, UserView view) {
		// TODO Auto-generated constructor stub
		attachView(context, view);
	}
	
	public void refreshUser(User mUser){
		if (mUser!=null) {
			for(User user: userList){
				if (user.getUserId()==mUser.getUserId()) {
					user=mUser;
					
					FileSource.getInstance().writeSource2File("userList", new Gson().toJson(userList));
					view.refreshUserSuccess();
					break;
				}
			}
		}
	}
}
