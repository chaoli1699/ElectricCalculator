package cn.cienet.electriccalculator;

import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import cn.cienet.electriccalculator.bean.Bill;
import cn.cienet.electriccalculator.bean.User;
import cn.cienet.electriccalculator.chart.anim.Anim;
import cn.cienet.electriccalculator.chart.data.LineChartData;
import cn.cienet.electriccalculator.chart.view.LineChart;
import cn.cienet.electriccalculator.presenter.ChartPresenter;
import cn.cienet.electriccalculator.utils.FormatUtils;
import cn.cienet.electriccalculator.view.ChartView;

public class ChartActivity extends BaseActivity<ChartPresenter> implements ChartView {
	
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		int userId=getIntent().getIntExtra("userId", 0);
	    user= mPresenter.getUserById(userId);
		initActionBar(getResources().getString(R.string.chart)+"("+user.getUserName()+")", false, true);
		setContentView(R.layout.activity_chart);
		
		setData((LineChart) findViewById(R.id.chart_line));
	}
	
	private void setData(LineChart lineChart){
		
		List<Bill> bills= user.getBillHistory();
        
		final int xCount=10;
		int minIndex= bills.size()<=xCount? 0:bills.size()-xCount;
		
		String[] xData = new String[xCount];
		float[] yData = new float[xCount];
		for(int i=0; i<xCount; i++){
			xData[i]="";
		}
		        
        for(int i=0; i<bills.size()-minIndex; i++){
        	Bill bill=bills.get(i);
        	int index=xCount-1-i;
        	xData[index]=FormatUtils.formatDate(bill.getUpdateTime(),"yyyy-MM-dd");
        	yData[index]=FormatUtils.format2Bit(bill.getAirFee()+bill.getPublicFee());
        }
		
		LineChartData lineChartData = LineChartData.builder()
				.setChartTitle("最近"+xCount+"次缴费账单分析")
		        .setXdata(xData)//x轴数据
		        .setYdata(yData)//y轴数据
		        .setXpCount(xCount)//x轴刻度数量
//		        .setYpCount(num)//y轴刻度数量
		        .setStartFrom0(false) //不从原点开始划线
		        .setCoordinatesColor(getResources().getColor(android.R.color.holo_orange_dark))
		        .setAnimType(Anim.ANIM_ALPHA)//动画效果，目前仅支持两种
		        .build();
		lineChart.setChartData(lineChartData);
		   
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
