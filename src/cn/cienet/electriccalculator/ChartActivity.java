package cn.cienet.electriccalculator;

import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import cn.cienet.electriccalculator.bean.Bill;
import cn.cienet.electriccalculator.bean.User;
import cn.cienet.electriccalculator.presenter.ChartPresenter;
import cn.cienet.electriccalculator.utils.FormatUtils;
import cn.cienet.electriccalculator.view.ChartView;
import cn.cienet.electriccalculator.widget.LineChart;

public class ChartActivity extends BaseActivity<ChartPresenter> implements ChartView {
	
	private LineChart lineChart;
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		int userId=getIntent().getIntExtra("userId", 0);
	    user= mPresenter.getUserById(userId);
		initActionBar(getResources().getString(R.string.chart)+"("+user.getUserName()+")", false, true);
		setContentView(R.layout.activity_chart);
		
		initView();
	}
	
	private void initView() {
		lineChart=(LineChart) findViewById(R.id.chart_line);
		
		setData();
	}
	
	private void setData(){
		String[] xLabel = new String[7];
        String[] data = new String[7];
		List<Bill> bills= user.getBillHistory();
	
		for(int k=0; k<7; k++){
			data[k]=FormatUtils.format2Bit("0.00");
        	xLabel[k]=FormatUtils.formatDate(System.currentTimeMillis(), "MM-dd");
		}
        
		int num= bills.size()>6? 7:bills.size();
		Bill bill;
		for(int i=0; i<num; i++){
        	bill=bills.get(i);
        	data[6-i]=FormatUtils.format2Bit(bill.getAirFee()+bill.getPublicFee())+"";
        	xLabel[6-i]=FormatUtils.formatDate(bill.getUpdateTime(),"MM-dd");
        }
           
        lineChart.setxLabel(xLabel);
        lineChart.setData(data);
        lineChart.fresh();    
	}
	
	@Override
	protected ChartPresenter createPresenter() {
		// TODO Auto-generated method stub
		return new ChartPresenter(ChartActivity.this, this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.chart, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
