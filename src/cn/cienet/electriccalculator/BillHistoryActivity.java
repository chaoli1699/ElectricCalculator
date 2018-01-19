package cn.cienet.electriccalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.cienet.electriccalculator.adapter.BillHistoryListAdapter;
import cn.cienet.electriccalculator.bean.User;
import cn.cienet.electriccalculator.presenter.BillHistoryPresenter;
import cn.cienet.electriccalculator.view.BillHistoryView;

public class BillHistoryActivity extends BaseActivity<BillHistoryPresenter> implements BillHistoryView {
	
	private ListView billHistoryList;
	private BillHistoryListAdapter billHistoryListAdapter;
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		int userId=getIntent().getIntExtra("userId", -1);
	    user= mPresenter.getUserById(userId);
	    initActionBar(getResources().getString(R.string.bill_history_title)+"("+user.getUserName()+")", false, true);
		setContentView(R.layout.activity_bill_history);
		
		initView();
	}
	
	private void initView(){
		billHistoryList=(ListView) findViewById(R.id.bill_history_list);
		
		if (user.getBillHistory()!=null) {
			billHistoryListAdapter=new BillHistoryListAdapter(this);
			billHistoryListAdapter.setBillsSource(user.getBillHistory());
			billHistoryList.setAdapter(billHistoryListAdapter);
			
		}
		
		billHistoryList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.putExtra("lastCount", user.getBillHistory().get(position).getCurrentCount());
				BillHistoryActivity.this.setResult(RESULT_OK, intent);
				BillHistoryActivity.this.finish();
			}
		});
		
	}

	@Override
	protected BillHistoryPresenter createPresenter() {
		// TODO Auto-generated method stub
		return new BillHistoryPresenter(BillHistoryActivity.this, this);
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.bill_history, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			BillHistoryActivity.this.setResult(RESULT_CANCELED);
			BillHistoryActivity.this.finish();
			break;
		case R.id.bill_history_chart:
			Intent intent=new Intent(BillHistoryActivity.this, ChartActivity.class);
			intent.putExtra("userId", user.getUserId());
			startAct(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
