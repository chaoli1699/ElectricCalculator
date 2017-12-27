package cn.cienet.electriccalculator.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.util.Log;

public class FileUtils {

	private static final String TAG="FileUtils";

	/**
	 * writeString2LocalFile
	 * @param filePath
	 * @param fileName
	 * @param content
	 * @throws IOException
	 */
	public static void writeString2LocalFile(String filePath, String fileName, String content)throws IOException{
    	File file=new File(filePath, fileName);
    	
    	if(!file.exists()){
    		if(file.getParentFile().mkdirs()){
    			Log.i(TAG,"Create "+ fileName+ " successed!");
    		}else{
    			Log.i(TAG, "Create "+ fileName+ " failed!");
    		}
    	}
    	
    	FileWriter  fo=new FileWriter (file);
		fo.write(content);
		fo.flush();
		fo.close();
    }
    
	/**
	 * readStringFromLocalFile
	 * @param filePath
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
    public static String readStringFromLocalFile(String filePath, String fileName) throws IOException{
    	
    	File file=new File(filePath, fileName);
    	
    	if(!file.exists()){
    		return null;
    	}
    	
    	BufferedReader in=new BufferedReader(new FileReader(file));
    	String str=in.readLine();
    	in.close();
    	
    	return str;
    }
    
    /**
     * deleteFile
     * @param filePath
     * @param fileName
     * @param onDelFileListener
     */
    public static void deleteFile(String filePath, String fileName){
    	File file=new File(filePath, fileName);
    	if (file.exists()) {
    		boolean result = file.delete();
    		String str;
    		if (result) {
    		    str="Del "+fileName+" success!";
				Log.i(TAG, str);
				
			}else {
				str="Del "+fileName+ " fail!";
				Log.i(TAG, str);
			}
		}
    }
}
