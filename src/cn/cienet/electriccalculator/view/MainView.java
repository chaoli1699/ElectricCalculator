package cn.cienet.electriccalculator.view;

public interface MainView extends BaseView{

	void calculateSuccess();
	void calculateFail(String msg);
	
	void removeUserSuccess();
}
