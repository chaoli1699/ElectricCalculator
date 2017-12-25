package cn.cienet.electriccalculator;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import cn.cienet.electriccalculator.adapter.UserListAdapter;
import cn.cienet.electriccalculator.bean.User;
import cn.cienet.electriccalculator.presenter.MainPresenter;
import cn.cienet.electriccalculator.view.MainView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {

	private ListView userList;
	private UserListAdapter userListAdapter;
	private List<User> userSource;
	private boolean delMode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActionBar(getResources().getString(R.string.app_name), false, false);
		setContentView(R.layout.activity_main);
		
		getUserData();
		initView();
	}
	
	private void initView(){
		userList=(ListView) findViewById(R.id.main_user_list);
		
		userListAdapter=new UserListAdapter(this);
		userListAdapter.setUserSource(userSource);
		userList.setAdapter(userListAdapter);
		
		userList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (delMode) {
					mPresenter.removeUserById(userSource.get(position).getUserId());
				}else {
					Intent intent=new Intent(MainActivity.this, UserActivity.class);
					intent.putExtra("userId", userSource.get(position).getUserId());
					startAct(intent);
				}
			}
		});
		
		userList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (delMode) {
					delMode=false;
				}else {
					delMode=true;
				}
				userListAdapter.setDelMode(delMode);
				userList.setAdapter(userListAdapter);
				return true;
			}
		});
	}
	
	private void getUserData(){
		userSource=mPresenter.getUserList();
	}
	
	private void refreshView(){
		getUserData();
		userListAdapter.setUserSource(userSource);
		userListAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void calculateSuccess() {
		// TODO Auto-generated method stub
		refreshView();
	}
	
	@Override
	public void calculateFail(String msg) {
		// TODO Auto-generated method stub
	    showToast(msg);	
	}
	
	@Override
	public void removeUserSuccess() {
		// TODO Auto-generated method stub
	     delMode=false;
	     userListAdapter.setDelMode(delMode);
	     userList.setAdapter(userListAdapter);
	}

	@Override
	protected MainPresenter createPresenter() {
		// TODO Auto-generated method stub
		return new MainPresenter(this);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode==RESULT_OK) {
			switch (requestCode) {
			case INSERT_USER:
				refreshView();
				break;

			case INPUT_TOTAL:
				String total= data.getExtras().getString("total");
				mPresenter.calculate(Float.valueOf(total));
				break;
			default:
				break;
			}
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(MainActivity.this, InputActivity.class);

		switch (item.getItemId()) {
		case R.id.main_insert_user:
			intent.putExtra("requestCode", INSERT_USER);
			startAct4Result(intent, INSERT_USER);
			break;

		case R.id.main_input_total:
			intent.putExtra("requestCode", INPUT_TOTAL);
			startAct4Result(intent, INPUT_TOTAL);
			break;
			
		case R.id.main_setting:
			intent.putExtra("requestCode", SET_USER_SIZE);
			startAct(intent);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
