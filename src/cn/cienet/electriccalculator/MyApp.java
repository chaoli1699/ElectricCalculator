package cn.cienet.electriccalculator;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

public class MyApp extends Application {

	protected List<Activity> activities; 
	private long firstTime=0;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		activities=new ArrayList<Activity>();
	}
	
	protected void exit(){
		
		if (System.currentTimeMillis()-firstTime>2000){
            Toast.makeText(this,getResources().getString(R.string.click_twice_to_exit),Toast.LENGTH_SHORT).show();
            firstTime=System.currentTimeMillis();
        }else{
        	for(Activity activity: activities){
    			activity.finish();
    		}
            System.exit(0);
        }
		
		
	}
}
