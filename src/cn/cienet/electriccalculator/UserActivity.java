package cn.cienet.electriccalculator;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import cn.cienet.electriccalculator.bean.User;
import cn.cienet.electriccalculator.presenter.UserPresenter;
import cn.cienet.electriccalculator.view.UserView;

public class UserActivity extends BaseActivity<UserPresenter> implements UserView{
	
	private User mUser; 
	private EditText lastData, currentData;
	private TextView airCount, airFee;
	private static final float ELETRIC_PRICE=0.55f;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		int userId= getIntent().getIntExtra("userId", 0);
		mUser= mPresenter.getUserById(userId);
		if (mUser!=null) {
			initActionBar(mUser.getUserName(), false, true);
		}
		setContentView(R.layout.activity_user);
		
		initView();
	}
	
	private void initView(){
		lastData=(EditText) findViewById(R.id.user_lastData_value);
		currentData=(EditText) findViewById(R.id.user_currentData_value);
		
		airCount=(TextView) findViewById(R.id.user_airCount);
		airFee=(TextView) findViewById(R.id.user_airFee);
		
		if (mUser.getLastCount()!=null && mUser.getCurrentCount()!=null) {
			lastData.setText(mUser.getLastCount());
			currentData.setText(mUser.getCurrentCount());
			float airCountValue=Float.valueOf(mUser.getCurrentCount())-Float.valueOf(mUser.getLastCount());
			airCount.setText("Air Count: "+ airCountValue+ "kW.h");
			airFee.setText("Air Fee: гд"+ mUser.getAirFee());
		}	
	}
	
	protected UserPresenter createPresenter() {
		return new UserPresenter(this);
	};
	
	@Override
	public void refreshUserSuccess() {
		// TODO Auto-generated method stub
	    showToast("Apply data success.");	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.user, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		case R.id.user_ok:
			String lastStr=lastData.getText().toString().trim();
			String currentStr=currentData.getText().toString().trim();
			if (lastStr.length()>0 && currentStr.length()>0) {
				float airCountValue = Float.valueOf(currentStr)-Float.valueOf(lastStr);
				airCount.setText("Air Count: "+ airCountValue+ "kW.h");
				float airFeeValue=ELETRIC_PRICE* airCountValue;
				airFee.setText("Air Fee: гд"+ airFeeValue);
				mUser.setLastCount(lastStr);
				mUser.setCurrentCount(currentStr);
				mUser.setAirFee(airFeeValue);
				mPresenter.refreshUser(mUser);
			}
			break;
			
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
