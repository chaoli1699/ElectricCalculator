package cn.cienet.electriccalculator.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	private static final String DB_NAME="calulator.db";
	protected static final String TAB_USER="user";
	protected static final String TAB_BILL="bill";
	private static final int DB_VERSION=1;
	
	public DBHelper(Context context) {
		// TODO Auto-generated constructor stub
		this(context, DB_NAME, null, DB_VERSION);
	}
	
	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		String sql="CREATE TABLE IF NOT EXISTS "+TAB_USER+" ("
				+ "id INTEGER PRIMARY KEY, "
				+ "userId INTEGER, "
				+ "userName TEXT, "
				+ "userMore TEXT, "
				+ "visable INTEGER, "
				+ "updateTime DATETIME)";
		
		String sql2="CREATE TABLE IF NOT EXISTS "+TAB_BILL+" ("
				+ "id INTEGER PRIMARY KEY, "
				+ "userId INTEGER, "
				+ "lastCount INTEGER, "
				+ "currentCount INTEGER, "
				+ "perEPrice FLOAT, "
				+ "airFee FLOAT, "
				+ "publicFee FLOAT, "
				+ "updateTime DATETIME)";
		
		db.execSQL(sql);
		db.execSQL(sql2);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql="DROP TABLE IF EXITS "+ TAB_USER;
		String sql2="DROP TABLE IF EXITS "+ TAB_BILL;
		db.execSQL(sql);
		db.execSQL(sql2);
		onCreate(db);
	}

}
