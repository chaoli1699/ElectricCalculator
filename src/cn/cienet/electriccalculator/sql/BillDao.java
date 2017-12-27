package cn.cienet.electriccalculator.sql;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cn.cienet.electriccalculator.bean.Bill;
import cn.cienet.electriccalculator.bean.User;

public class BillDao {
	
	private SQLiteDatabase db;
	private DBHelper dbHelper;
	
	public BillDao(Context context) {
		// TODO Auto-generated constructor stub
		dbHelper=new DBHelper(context);		
	}
	
	public void insertData(User user){
		
		try {
			Bill bill=user.getCurrentbill();
			db=dbHelper.getWritableDatabase();
	        String sql="INSERT INTO "+ DBHelper.TAB_BILL
	        		+ "(userId, lastCount, currentCount, perEPrice, airFee, publicFee, updateTime) VALUES "
	        		+ "("+user.getUserId()+", "+bill.getLastCount()+", "+bill.getCurrentCount()+", "
	        		+ bill.getPerEPrice()+", "+bill.getAirFee()+", "+bill.getPublicFee()+", "+System.currentTimeMillis()+")";
	        db.execSQL(sql);
	        db.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public Bill getCurrentBillById(int userId){
		
		try {
			db=dbHelper.getReadableDatabase();
			String sql="SELECT * FROM "+DBHelper.TAB_BILL+ " WHERE userId=?";
			Cursor c=db.rawQuery(sql, new String[]{userId+""});
			
			Bill bill=new Bill();
			while(c.moveToLast()){
				bill.setLastCount(c.getInt(c.getColumnIndex("lastCount")));
				bill.setCurrentCount(c.getInt(c.getColumnIndex("currentCount")));
				bill.setPerEPrice(c.getFloat(c.getColumnIndex("perEPrice")));
				bill.setAirFee(c.getFloat(c.getColumnIndex("airFee")));
				bill.setPublicFee(c.getFloat(c.getColumnIndex("publicFee")));
				bill.setUpdateTime(c.getLong(c.getColumnIndex("updateTime")));
				break;
			}
			db.close();
			return bill;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Bill> queryBillHistoryById(int userId){
		
		try {
			db=dbHelper.getReadableDatabase();
			String sql="SELECT * FROM "+DBHelper.TAB_BILL+ " WHERE userId=? ORDER BY updateTime DESC";
			Cursor c=db.rawQuery(sql, new String[]{userId+""});
			
			List<Bill> bills=new ArrayList<Bill>();
			while(c.moveToNext()){
				Bill bill=new Bill();
				bill.setLastCount(c.getInt(c.getColumnIndex("lastCount")));
				bill.setCurrentCount(c.getInt(c.getColumnIndex("currentCount")));
				bill.setPerEPrice(c.getFloat(c.getColumnIndex("perEPrice")));
				bill.setAirFee(c.getFloat(c.getColumnIndex("airFee")));
				bill.setPublicFee(c.getFloat(c.getColumnIndex("publicFee")));
				bill.setUpdateTime(c.getLong(c.getColumnIndex("updateTime")));
				bills.add(bill);
			}
			db.close();
			return bills;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
