package cn.cienet.electriccalculator.presenter;

import android.content.Context;
import cn.cienet.electriccalculator.R;
import cn.cienet.electriccalculator.bean.User;
import cn.cienet.electriccalculator.model.FileSource;
import cn.cienet.electriccalculator.sql.BillDao;
import cn.cienet.electriccalculator.sql.UserDao;
import cn.cienet.electriccalculator.view.MainView;

public class MainPresenter extends BasePresenter<MainView> {

	private Context context;
	
	public MainPresenter(Context context, MainView view){
		// TODO Auto-generated constructor stub
		this.context=context;
		attachView(context, view);
	}
	
	public void calculate(float total_fee){

        float left_fee=total_fee;
        float aver_fee;
        float should_pay;
        if (userList.size()>0){
            for (User u:userList){
            	if (u.getCurrentbill()==null) {
					view.calculateFail(u.getUserName()+context.getResources().getString(R.string.lack_of_user_data));
					return ; 
				}
                if (left_fee<u.getCurrentbill().getAirFee()){
                    view.calculateFail(context.getResources().getString(R.string.total_money_unreasonable));
                    return ;
                }
                left_fee-=u.getCurrentbill().getAirFee();
            }

            aver_fee=left_fee/userList.size();

            for (User u:userList){
                should_pay=u.getCurrentbill().getAirFee()+aver_fee;
//                should_pay=(Math.round(should_pay*100))/100;
                u.getCurrentbill().setPublicFee(aver_fee);
                u.getCurrentbill().setTotalFee(should_pay);
                
                new BillDao(context).insertData(u);
            }

            FileSource.getInstance().delSourceFormFile("userList");
            view.calculateSuccess();
        }
    }
	
	public void removeUserById(int userId){	
		
//		User user;
//		for(int i=0; i<userList.size(); i++){
//		    user=userList.get(i);
//			if (userId==user.getUserId()) {
//				userList.remove(i);
//				FileSource.getInstance().writeSource2File("userList", new Gson().toJson(userList));
//				view.removeUserSuccess();
//				break;
//			}
//		}
		
		new UserDao(context).setUserVisableById(userId, 0);
		FileSource.getInstance().delSourceFormFile("userList");
		FileSource.getInstance().delSourceFormFile("userAmount");
		view.removeUserSuccess();
	}
}
