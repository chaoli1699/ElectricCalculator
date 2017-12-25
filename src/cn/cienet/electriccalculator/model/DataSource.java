package cn.cienet.electriccalculator.model;

import java.io.IOException;

import android.os.Environment;
import cn.cienet.electriccalculator.utils.FileUtils;

public class DataSource {
	
	private static final String DATA_PATH=Environment.getExternalStorageDirectory().getAbsolutePath()+"/cn.cienet.mcal/data/";
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
	
	public String readSourceFromFile(String fileName){
		try {
			String str=FileUtils.readStringFromLocalFile(DATA_PATH, fileName);
			if (str!=null) {
				return str;
			}else {
				//read from DB
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
	
	
}
