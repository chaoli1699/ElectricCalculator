package cn.cienet.electriccalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.cienet.electriccalculator.bean.Bill;
import cn.cienet.electriccalculator.bean.User;
import cn.cienet.electriccalculator.presenter.UserPresenter;
import cn.cienet.electriccalculator.utils.Constants;
import cn.cienet.electriccalculator.view.UserView;

public class UserActivity extends BaseActivity<UserPresenter> implements UserView{
	
	private User mUser; 
	private EditText lastData, currentData;
	private TextView userId, userName, billHistory, airCount, airFee;
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
		billHistory=(TextView) findViewById(R.id.user_bill_history);
		
		lastData=(EditText) findViewById(R.id.user_lastData_value);
		currentData=(EditText) findViewById(R.id.user_currentData_value);
		
		airCount=(TextView) findViewById(R.id.user_airCount);
		airFee=(TextView) findViewById(R.id.user_airFee);
		
		AIR_COUNT=getResources().getString(R.string.air_count);
		AIR_FEE=getResources().getString(R.string.air_fee);
		
		userId.setText(getResources().getString(R.string.user_id)+": "+ mUser.getUserId());
		userName.setText(getResources().getString(R.string.user_name)+": "+ mUser.getUserName());	
		
		billHistory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(UserActivity.this, BillHistoryActivity.class);
				intent.putExtra("userId", mUser.getUserId());
				startAct4Result(intent, BILL_HISTORY);
			}
		});
	}
	
	@Override
	public void refreshUserSuccess() {
		// TODO Auto-generated method stub
	    showToast(getResources().getString(R.string.apply_data_success));
	    this.setResult(RESULT_OK);
		this.finish();
	}
	
	protected UserPresenter createPresenter() {
		return new UserPresenter(UserActivity.this, this);
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==RESULT_OK) {
			if (requestCode==BILL_HISTORY) {
				lastData.setText(data.getExtras().getInt("lastCount")+"");
			}
		}
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
				float airFeeValue=Constants.PER_E_PRICE* airCountValue;
				airFee.setText(AIR_FEE+": гд"+ airFeeValue);
				Bill bill=new Bill();
				bill.setLastCount(Integer.valueOf(lastStr));
				bill.setCurrentCount(Integer.valueOf(currentStr));
				bill.setPerEPrice(Constants.PER_E_PRICE);
				bill.setAirFee(airFeeValue);
				mUser.setCurrentbill(bill);
				mPresenter.refreshUser(mUser);			
			}
			break;
			
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
