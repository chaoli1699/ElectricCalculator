package cn.cienet.electriccalculator.adapter;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.cienet.electriccalculator.R;
import cn.cienet.electriccalculator.bean.Bill;
import cn.cienet.electriccalculator.utils.FormatUtils;

public class BillHistoryListAdapter extends BaseAdapter{
	
	private List<Bill> billList;
	private Context context;
	
	public BillHistoryListAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	
	public void setBillsSource(List<Bill> billList){
		this.billList=billList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return billList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return billList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;  
        if (convertView == null) {  
            holder = new ViewHolder();  
            convertView = LayoutInflater.from(context).inflate(  
                    R.layout.item_bill_history_list, null);  
            holder.lastCount = (TextView) convertView.findViewById(R.id.item_bill_history_lastCount);  
            holder.currentCount = (TextView) convertView.findViewById(R.id.item_bill_history_currentCount);  
            holder.perEPrice = (TextView) convertView.findViewById(R.id.item_bill_history_perEPrice);  
            holder.airFee = (TextView) convertView.findViewById(R.id.item_bill_history_airFee);
            holder.publicFee = (TextView) convertView.findViewById(R.id.item_bill_history_publicFee);
            holder.updateTime = (TextView) convertView.findViewById(R.id.item_bill_history_updateTime);
  
            // Bind holder to convertView  
            convertView.setTag(holder);  
        } else {  
            holder = (ViewHolder) convertView.getTag();  
        } 
        
        //Bind data...
        holder.lastCount.setText(billList.get(position).getLastCount()+"");
        holder.currentCount.setText(billList.get(position).getCurrentCount()+"");
        holder.perEPrice.setText(billList.get(position).getPerEPrice()+"");
        holder.airFee.setText(FormatUtils.format2Bit(billList.get(position).getAirFee())+"");
        holder.publicFee.setText(FormatUtils.format2Bit(billList.get(position).getPublicFee()+""));
        holder.updateTime.setText(FormatUtils.formatDate(billList.get(position).getUpdateTime(), "yy/MM/dd HH:mm:ss"));
		return convertView;
	}
	
	final class ViewHolder {  
        TextView lastCount;  
        TextView currentCount;  
        TextView perEPrice; 
        TextView airFee;
        TextView publicFee;
        TextView updateTime;
    } 

}
