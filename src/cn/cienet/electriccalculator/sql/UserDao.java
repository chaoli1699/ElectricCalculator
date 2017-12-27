package cn.cienet.electriccalculator.sql;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cn.cienet.electriccalculator.bean.Bill;
import cn.cienet.electriccalculator.bean.User;

public class UserDao {

	private Context context;
	private DBHelper dbHelper;
	private SQLiteDatabase db;
	
	public UserDao(Context context) {
		// TODO Auto-generated constructor stub'
		this.context=context;
		dbHelper=new DBHelper(context);
	}
	
	public void insertUser(User user){
		
		try {
			db=dbHelper.getWritableDatabase();
	        String sql="INSERT INTO "+ DBHelper.TAB_USER
	        		+ "(userId, userName, userMore, visable, updateTime) VALUES "
	        		+ "("+user.getUserId()+", '"+user.getUserName()+"', '"+user.getUserMore()+"', "+1+ ", "+System.currentTimeMillis()+")";
	        db.execSQL(sql);   
	        db.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void setUserVisableById(int userId, int visable) {
		
		try {
			db=dbHelper.getWritableDatabase();
			String sql="UPDATE "+DBHelper.TAB_USER+ " SET visable="+visable+" WHERE userId="+userId;
			db.execSQL(sql);
			db.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public int getUserAmount(){	
		
		int amount=0;
		try {
			db=dbHelper.getReadableDatabase();
			String sql="SELECT * FROM "+DBHelper.TAB_USER;
			Cursor c=db.rawQuery(sql, null);
		
			while(c.moveToNext()){
				amount++;
			}
			db.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return amount;
	}
	
	public List<User> queryUsers(){
		
		try {
			db=dbHelper.getReadableDatabase();
			String sql="SELECT * FROM "+DBHelper.TAB_USER+ " WHERE visable=?";
			Cursor c=db.rawQuery(sql, new String[]{"1"});
			
			BillDao billDao=new BillDao(context);
			List<User> users=new ArrayList<User>();
			while(c.moveToNext()){
				User user=new User();
				user.setUserId(c.getInt(c.getColumnIndex("userId")));
				user.setUserName(c.getString(c.getColumnIndex("userName")));
				user.setUserMore(c.getString(c.getColumnIndex("userMore")));
				Bill bill=billDao.getCurrentBillById(user.getUserId());
				if (bill!=null) {
					user.setCurrentbill(bill);
				}
				List<Bill> bills=billDao.queryBillHistoryById(user.getUserId());
				if (bills!=null) {
					user.setBillHistory(bills);
				}
				users.add(user);
			}
			db.close();
			return users;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
