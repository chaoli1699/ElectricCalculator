package cn.cienet.electriccalculator.model;

import java.io.IOException;
import android.os.Environment;
import cn.cienet.electriccalculator.utils.FileUtils;

public class FileSource {
	
	private static final String DATA_PATH=Environment.getExternalStorageDirectory().getAbsolutePath()+"/cn.cienet.ecal/data/";
	private volatile static FileSource instance;
	
	private FileSource(){}
	
	public static FileSource getInstance(){
		
		if(instance==null){
    		synchronized(FileSource.class){
    			if(instance==null){
    				instance=new FileSource();
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
