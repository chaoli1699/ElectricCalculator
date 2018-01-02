package cn.cienet.electriccalculator.presenter;

import java.util.List;

import android.content.Context;
import cn.cienet.electriccalculator.R;
import cn.cienet.electriccalculator.bean.User;
import cn.cienet.electriccalculator.model.DataSource;
import cn.cienet.electriccalculator.view.InputView;

public class InputPresenter extends BasePresenter<InputView>{
	
    private int userSizeMax;
    private Context context;
    
	public InputPresenter(Context context, InputView view) {
		// TODO Auto-generated constructor stub
		this.context=context;
		attachView(context, view);
		userSizeMax=getUserSizeMax();
	}
	
	public void insertUser(String userName){
		
		List<User> userList=getUserList();
		if (!ifUserExit(userName)) {
			if (userList.size()<userSizeMax) {
				User mUser=new User();
				mUser.setUserId(getUserAmount()+1);
				mUser.setUserName(userName);
				
				DataSource.getInstance().insertUserSource(context, mUser);
//				new UserDao(context).insertUser(mUser);
//				DataSource.getInstance().delSourceFormFile("userList");
//				DataSource.getInstance().delSourceFormFile("userAmount");
				view.inputComplate(null);
			}else {
				view.inputFail(context.getResources().getString(R.string.user_max_alert));
			}
			
		}else {
			view.inputFail(context.getResources().getString(R.string.user_exist));
		}
	}
	
	public void inpuTotal(float total){
		if (total>0) {
//			FileSource.getInstance().writeSource2File("totalFee", "Total fee is: "+ total);
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
