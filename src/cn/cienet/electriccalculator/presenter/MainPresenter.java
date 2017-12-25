package cn.cienet.electriccalculator.presenter;

import com.google.gson.Gson;

import cn.cienet.electriccalculator.bean.User;
import cn.cienet.electriccalculator.model.DataSource;
import cn.cienet.electriccalculator.view.MainView;

public class MainPresenter extends BasePresenter<MainView> {

	public MainPresenter(MainView view){
		// TODO Auto-generated constructor stub
		attachView(view);
	}
	
	public void calculate(float total_fee){

        float left_fee=total_fee;
        float aver_fee;
        float should_pay;
        if (userList.size()>0){
            for (User u:userList){
                if (left_fee<u.getAirFee()){
                    view.calculateFail("Total is unreasonable, please check and recalculate.");
                    return ;
                }
                left_fee-=u.getAirFee();
            }

            aver_fee=left_fee/userList.size();

            for (User u:userList){
                should_pay=u.getAirFee()+aver_fee;
//                should_pay=(Math.round(should_pay*100))/100;
                u.setPublicFee(aver_fee);
                u.setTotalFee(should_pay);
            }

			DataSource.getInstance().writeSource2File("userList", new Gson().toJson(userList));
            view.calculateSuccess();
        }
    }
	
	public void removeUserById(int userId){
		User user;
		for(int i=0; i<userList.size(); i++){
		    user=userList.get(i);
			if (userId==user.getUserId()) {
				userList.remove(i);
				DataSource.getInstance().writeSource2File("userList", new Gson().toJson(userList));
				view.removeUserSuccess();
				break;
			}
		}
	}
}
