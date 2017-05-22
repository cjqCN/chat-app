package com.cjq.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjq.serverhandler.SubscribeServerHandler;
import com.example.a15656.test5.cjq.action.pojo.UserState;
import com.example.a15656.test5.cjq.action.response.LoginResponse;



public class JsonUtil {
	
	private static Logger logger = Logger.getLogger(JsonUtil.class);
	public static JsonUtil instance;
	private JSONArray jsonArray;
	
	private static final String userlistFile = ".\\json\\Users.json";   //用户账号文件
	
	private static final String userFriendFolder = ".\\json\\userfriend";   //用户好友文件夹
	
	
	public static JsonUtil getInstance(){
		if(instance==null) {
			instance = new JsonUtil();
			instance.jsonArray = instance.getUsersjsonArry(); 
		}
		return instance;
	}

	
    /**
     * Read File
     * @param Path
     * @return File to String 
     */
    private String ReadFile(String Path){
        BufferedReader reader = null;
        String laststr = "";
        try{
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                laststr += tempString;
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
        }
    
    
    /**
     * @param filePath
     * @param sets
     * @throws IOException
     */
    private void writeFile(String filePath, String sets) throws IOException {
        FileWriter fw = new FileWriter(filePath);
        PrintWriter out = new PrintWriter(fw);
        out.write(sets);
        out.println();
        fw.close();
        out.close();
      }
    
    
    /**
     * @return
     */
    private JSONArray getUsersjsonArry(){
    	String JsonContext = ReadFile(userlistFile);
		jsonArray = JSON.parseArray(JsonContext);
		return jsonArray;
    }
    
    /**
     * fresh User json File
     */
    private void freshUsers(){
    	if(jsonArray == null) {
    		logger.info("jsonArray == null");
    		return;
    	}
    	try {
			writeFile(userlistFile,jsonArray.toJSONString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    /**
     * 新建文件户
     * @param fileNamePath
     */
    public static boolean createFile(String fileNamePath){
        Boolean bool = false;
        File file = new File(fileNamePath);
        try {
            //如果文件不存在，则创建新的文件
            if(!file.exists()){
                file.createNewFile();
                bool = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }   
        return bool;
    }
    
    
    
    /**
     * 登录检查    用户名不存在或密码错误 return：0   
     *         允许登录return：1   重复登录return：2   
     * @param userName
     * @param passWord
     * @return
     */
    public int userlogin(String userName,String passWord){
    
    	if(jsonArray == null)  return 0;
    	int size = jsonArray.size();
		JSONObject jsonObject;
    	for(int  i = 0; i < size; i++){
    		jsonObject = jsonArray.getJSONObject(i);
    		if(jsonObject.get("name").equals(userName)&&jsonObject.get("password").equals(passWord)){
    			Logger.getRootLogger().info("name:"+jsonObject.get("name")+"  state:"+jsonObject.get("state"));
    			if(jsonObject.get("state").equals("0")){
    				jsonObject.put("state", "1");
    				getInstance().freshUsers();  	
    				return 1;
    			}else {
    				return 2;
    			}
    		}
    	}
    	return 0;
    }  
    
    
    /**
     * 注册检查   用户名已存在return：0   允许登录return：1
     * @param userName
     * @param passWord
     * @return
     */
    public int userRegister(String userName,String passWord){
    	
    	JSONObject jsonObject;
    	if(jsonArray!=null){
	    	int size = jsonArray.size();			
	    	for(int  i = 0; i < size; i++){
	    		jsonObject = jsonArray.getJSONObject(i);
	    		if(jsonObject.get("name").equals(userName)){
	    			return 0;
	    		}
	    	}
	   	} else  jsonArray = new JSONArray();
    	
    	jsonObject = new JSONObject();
    	jsonObject.put("name", userName);
    	jsonObject.put("password", passWord);
    	jsonObject.put("state", "0");
    	jsonArray.add(jsonObject);
    	createFile(userFriendFolder+"\\"+userName+".json");
    	freshUsers(); 
    	
    	return 1;
    }  
    
    
    public List getUserFriends(String name){
    	
    	String JsonContext = ReadFile(userFriendFolder+"\\"+name+".json");
		JSONArray userFriendsArray = JSON.parseArray(JsonContext);
		if(jsonArray == null) return null;		
		
    	List<UserState> userFriendList = new ArrayList<UserState>();
    	if(userFriendsArray == null)  return  userFriendList;
    	
    	int size = userFriendsArray.size();	  	
    	String friendName;
    	for(int  i = 0; i < size; i++){
    		friendName = userFriendsArray.getJSONObject(i).get("name").toString();
    		userFriendList.add(new UserState(friendName));
    		logger.info("friendName:"+friendName);
    	}
    	
    	return userFriendList;
    }
    
    public int addFriends(String username,String fname){
    	JSONObject jsonObject;
    	if(jsonArray!=null){
	    	int size = jsonArray.size();			
	    	for(int  i = 0; i < size; i++){
	    		jsonObject = jsonArray.getJSONObject(i);
	    		if(jsonObject.get("name").equals(fname)){
	    	    	String JsonContext = ReadFile(userFriendFolder+"\\"+username+".json");
	    			JSONArray userFriendsArray = JSON.parseArray(JsonContext);
	    			
	    	    	if(userFriendsArray == null) userFriendsArray = new JSONArray();
	    	    	else{
	    	    		String temp;
	    	    		int size1 = userFriendsArray.size();
	    	    		for(int  j = 0; j < size1; j++){
	    	    			temp = userFriendsArray.getJSONObject(j).get("name").toString();
	    	    			if(temp.equals(fname)) return 2;
	    	        	}
	    	        	
	    	    	}
	    	    	jsonObject = new JSONObject();
	    	    	jsonObject.put("name", fname);
	    	    	userFriendsArray.add(jsonObject);
	    	    	try {
						writeFile(userFriendFolder+"\\"+username+".json",userFriendsArray.toJSONString());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	    	return 1;
	    		}
	    	}
	   	}
    	
    	return 0;
    }

       
}