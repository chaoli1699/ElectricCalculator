package cn.cienet.electriccalculator.presenter;

import com.google.gson.Gson;

import cn.cienet.electriccalculator.bean.User;
import cn.cienet.electriccalculator.model.DataSource;
import cn.cienet.electriccalculator.view.InputView;

public class InputPresenter extends BasePresenter<InputView>{
	
    private int userSizeMax;
    
	public InputPresenter(InputView view) {
		// TODO Auto-generated constructor stub
		attachView(view);
		userSizeMax=getUserSizeMax();
	}
	
	public void insertUser(String userName){
		
		if (!ifUserExit(userName)) {
			if (userList.size()<userSizeMax) {
				User mUser=new User();
				mUser.setUserId(userList.size()+1);
				mUser.setUserName(userName);
				userList.add(mUser);
				DataSource.getInstance().writeSource2File("userList", new Gson().toJson(userList));
				view.inputComplate(null);
			}else {
				view.inputFail("Can't insert more user, you can reset USERMAX in settings.");
			}
			
		}else {
			view.inputFail("Current user exists.");
		}
	}
	
	public void inpuTotal(float total){
		if (total>0) {
			DataSource.getInstance().writeSource2File("total", "Total is: "+ total);
			view.inputComplate(total+"");
		}
	}
	
	public void setUserSizeMax(int userSize){
		if (userSize>0) {
			DataSource.getInstance().writeSource2File("userSizeMax", "User size is: "+ userSize);
			view.inputComplate(null);
		}
	}
	
	private int getUserSizeMax(){
		String userSizeStr=DataSource.getInstance().readSourceFromFile("userSizeMax");
		if (userSizeStr!=null) {
			String[] temp=userSizeStr.split(":");
			return Integer.valueOf(temp[1].trim());
		}
		return 2;
	}

}
