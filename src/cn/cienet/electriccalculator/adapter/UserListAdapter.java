package cn.cienet.electriccalculator.adapter;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.cienet.electriccalculator.R;
import cn.cienet.electriccalculator.bean.User;
import cn.cienet.electriccalculator.utils.FormatUtils;

public class UserListAdapter extends BaseAdapter{
	
	private List<User> userList;
	private Context context;
	private boolean delMode= false;
	
	public UserListAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	
	public void setUserSource(List<User> userList){
		this.userList=userList;
	}
	
	public void setDelMode(boolean del) {
		// TODO Auto-generated method stub
        this.delMode=del;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return userList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return userList.get(position);
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
                    R.layout.item_user_list, null);  
            holder.userHead = (ImageView) convertView.findViewById(R.id.item_user_head);  
            holder.userName = (TextView) convertView.findViewById(R.id.item_user_name);  
            holder.userMore = (TextView) convertView.findViewById(R.id.item_user_more);  
            holder.userDelete=(ImageView) convertView.findViewById(R.id.item_user_delete);
  
            // Bind holder to convertView  
            convertView.setTag(holder);  
        } else {  
            holder = (ViewHolder) convertView.getTag();  
        } 
        
        //Bind data...
        holder.userName.setText(userList.get(position).getUserName());
        float totalPay=userList.get(position).getCurrentbill().getAirFee()+userList.get(position).getCurrentbill().getPublicFee();
        holder.userMore.setText(context.getResources().getString(R.string.should_pay)+": ￥"+FormatUtils.format2Bit(totalPay));
        if (delMode) {
			holder.userDelete.setVisibility(View.VISIBLE);
		}else {
			holder.userDelete.setVisibility(View.GONE);
		}
        
		return convertView;
	}
	
	final class ViewHolder {  
        ImageView userHead;  
        TextView userName;  
        TextView userMore; 
        ImageView userDelete;
    } 

}
