package com.zht.ztoolkit.jsonparser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.zht.ztoolkit.R;

public class JsonParserActivity extends Activity{
	private static final String TAG = "JsonParserActivity";
	private static final boolean DEBUG = true;
	private static final int GET_JSON_ITEMS = 1;
	private HttpPost mPost = new HttpPost("http://10.32.173.61:8080/test/json.jsp");
	private String mRequest = null;
	private String mResult = null;
	private StringEntity mSE;
	private HttpResponse mHttpResponse;
	private Button json_parser;
	private ListView json_list;
	private JsonAdapter mAdapter = null;
	private	List<Student> studentList = null;
	
	
	final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Log.e(TAG, "GKTW87 Handle message, what: " + msg.what);
			switch (msg.what) {
			case GET_JSON_ITEMS:
				updateJsonList(mResult);
				break;
			default:
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.json_parser);
		mAdapter = new JsonAdapter(this, R.layout.json_item);
		studentList = new ArrayList<Student>();
		mAdapter.setList(studentList);
		json_list = (ListView)findViewById(R.id.json_listview);
		json_list.setAdapter(mAdapter);
		json_parser = (Button)findViewById(R.id.json_parser);
		json_parser.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				doPost(GET_JSON_ITEMS);
			}
		});
	}
	
	public void doPost(final int FuncId) {
		new Thread() {
			public void run(){
				if(DEBUG){
					Log.d(TAG, "GKTW87 mRequest:"+mRequest);
				}
				List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
				pairs.add(new BasicNameValuePair("reqData", mRequest));
				try {    
					mPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));   
					mHttpResponse = new DefaultHttpClient().execute(mPost);
					Log.d(TAG, "GKTW87 after DefaultHttpClient().execute.");
					// get response with json format  
					mResult = EntityUtils.toString(mHttpResponse.getEntity());
					Message message = mHandler.obtainMessage(FuncId, mResult);
					mHandler.sendMessage(message);
					Log.d(TAG, "GKTW87 mResult is:"+mResult);
				} catch (UnsupportedEncodingException e1) {  
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					//Message message = mHandler.obtainMessage(FuncId, mResult);
					//mHandler.sendMessage(message);
					Log.d(TAG, "GKTW87 mResult is:"+mResult);
				}
			}
		}.start();
	}
	
	private void updateJsonList(String retSrc){
		if(studentList != null){
			studentList.clear();
		}
		try {
			JSONObject result = new JSONObject(retSrc);
			String className = result.getString("class");  
			JSONArray students = result.getJSONArray("students");
			if(DEBUG){
				Log.d(TAG, "class name is:"+className);
				Log.d(TAG, "Array length is:"+students.length());
			}
			for(int index=0; index<students.length(); index++){
				JSONObject item = students.getJSONObject(index);
				String name = item.getString("name");
				int age = item.getInt("age");
				Student s = new Student(0, name, age, className);
				studentList.add(s);
			}
			mAdapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
