package cn.cienet.electriccalculator.presenter;

import android.content.Context;
import cn.cienet.electriccalculator.view.BillHistoryView;

public class BillHistoryPresenter extends BasePresenter<BillHistoryView>{
	
	public BillHistoryPresenter(Context context, BillHistoryView view) {
		// TODO Auto-generated constructor stub
		attachView(context, view);
	}

}
