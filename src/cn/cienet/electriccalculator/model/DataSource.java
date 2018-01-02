package cn.cienet.electriccalculator.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.os.Environment;
import cn.cienet.electriccalculator.bean.User;
import cn.cienet.electriccalculator.sql.BillDao;
import cn.cienet.electriccalculator.sql.UserDao;
import cn.cienet.electriccalculator.utils.FileUtils;

public class DataSource {
	
	private static final String DATA_PATH=Environment.getExternalStorageDirectory().getAbsolutePath()+"/cn.cienet.ecal/data/";
	private volatile static DataSource instance;
	
	private DataSource(){}
	
	public static DataSource getInstance(){
		
		if(instance==null){
    		synchronized(DataSource.class){
    			if(instance==null){
    				instance=new DataSource();
    			}
    		}
    	}
    	return instance;
	}
	
	public void insertUserSource(Context context, User mUser){
		
		new UserDao(context).insertUser(mUser);
		DataSource.getInstance().delSourceFormFile("userList");
		DataSource.getInstance().delSourceFormFile("userAmount");
	}
	
	public void removeUserSource(Context context, int userId){
		
		new UserDao(context).setUserVisableById(userId, 0);
		DataSource.getInstance().delSourceFormFile("userList");
		DataSource.getInstance().delSourceFormFile("userAmount");
	}
	
	public int getUserAmountSource(Context context){
		
		int userAmount=0;
		String userAmountStr=readSourceFromFile("userAmount");
		if(userAmountStr!=null){
			String[] temp=userAmountStr.split(":");
		    userAmount= Integer.valueOf(temp[1].trim());
		}else {
			writeSource2File("userAmount", "User amount is:"+ new UserDao(context).getUserAmount());
			getUserAmountSource(context);
		}
		
		return userAmount;
	}
	
	public List<User> getUserListSource(Context context){
		
		List<User> userList=new ArrayList<User>();
		String userListStr=readSourceFromFile("userList");
		if (userListStr!=null) {
			userList=new Gson().fromJson(userListStr, new TypeToken<List<User>>(){}.getType());
		}else {
			userList=new UserDao(context).queryUsers();
			if (userList!=null) {
				writeSource2File("userList", new Gson().toJson(userList));
				getUserListSource(context);
				getUserAmountSource(context);
			}
		}
		
		return userList;
	}
	
	public void insertDataSource(Context context, User user){
		
		new BillDao(context).insertData(user);
	}
	
	public String readSourceFromFile(String fileName){
		try {
			String str=FileUtils.readStringFromLocalFile(DATA_PATH, fileName);
			if (str!=null) {
				return str;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void writeSource2File(String fileName, String content){
		try {
			FileUtils.writeString2LocalFile(DATA_PATH, fileName, content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void delSourceFormFile(String fileName){
		FileUtils.deleteFile(DATA_PATH, fileName);
	}
	
	
}
