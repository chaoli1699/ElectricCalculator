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
	private TextView userId, userName, airCount, airFee;
	private static final float ELETRIC_PRICE=0.55f;
	private String AIR_COUNT;
	private String AIR_FEE;
	
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
		userId=(TextView) findViewById(R.id.user_id);
		userName=(TextView) findViewById(R.id.user_name);
		
		lastData=(EditText) findViewById(R.id.user_lastData_value);
		currentData=(EditText) findViewById(R.id.user_currentData_value);
		
		airCount=(TextView) findViewById(R.id.user_airCount);
		airFee=(TextView) findViewById(R.id.user_airFee);
		
		AIR_COUNT=getResources().getString(R.string.air_count);
		AIR_FEE=getResources().getString(R.string.air_fee);
		
		userId.setText(getResources().getString(R.string.user_id)+": "+ mUser.getUserId());
		userName.setText(getResources().getString(R.string.user_name)+": "+ mUser.getUserName());
		
		if (mUser.getLastCount()!=null && mUser.getCurrentCount()!=null) {
			lastData.setText(mUser.getLastCount());
			currentData.setText(mUser.getCurrentCount());
			float airCountValue=Float.valueOf(mUser.getCurrentCount())-Float.valueOf(mUser.getLastCount());
			airCount.setText(AIR_COUNT+": "+ airCountValue+ "kW.h");
			airFee.setText(AIR_FEE+": гд"+ mUser.getAirFee());
		}	
	}
	
	protected UserPresenter createPresenter() {
		return new UserPresenter(this);
	};
	
	@Override
	public void refreshUserSuccess() {
		// TODO Auto-generated method stub
	    showToast(getResources().getString(R.string.apply_data_success));	
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
			UserActivity.this.setResult(RESULT_CANCELED);
			UserActivity.this.finish();
			break;

		case R.id.user_ok:
			String lastStr=lastData.getText().toString().trim();
			String currentStr=currentData.getText().toString().trim();
			if (lastStr.length()>0 && currentStr.length()>0) {
				float airCountValue = Float.valueOf(currentStr)-Float.valueOf(lastStr);
				airCount.setText(AIR_COUNT+": "+ airCountValue+ "kW.h");
				float airFeeValue=ELETRIC_PRICE* airCountValue;
				airFee.setText(AIR_FEE+": гд"+ airFeeValue);
				mUser.setLastCount(lastStr);
				mUser.setCurrentCount(currentStr);
				mUser.setAirFee(airFeeValue);
				mPresenter.refreshUser(mUser);
				UserActivity.this.setResult(RESULT_OK);
				UserActivity.this.finish();
			}
			break;
			
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
