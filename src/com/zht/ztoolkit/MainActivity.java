package com.zht.ztoolkit;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.zht.ztoolkit.asyncimageloader.AsyncImageList;
import com.zht.ztoolkit.simplelistview.SimpleListView;
import com.zht.ztoolkit.jsonparser.JsonParserActivity;
import com.zht.ztoolkit.fragment.FragmentPagerActivity;
import com.zht.ztoolkit.fragment.FragmentTouchActivity;
import com.zht.ztoolkit.listview.arrayadapter.ArrayAdapterActivity;

public class MainActivity extends Activity {

	Button simpleListView;
	Button asyncImageLoader;
	Button sendSMS;
	Button parseJson;
	Button fragmentPager;
	Button fragmentTouch;
	Button arrayAdapter;
	Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		simpleListView = (Button)findViewById(R.id.simple_listview);
		asyncImageLoader = (Button)findViewById(R.id.async_imageloader);
		sendSMS = (Button)findViewById(R.id.send_sms);
		parseJson = (Button)findViewById(R.id.parse_json);
		fragmentPager = (Button)findViewById(R.id.fragment_pager);
		fragmentTouch = (Button)findViewById(R.id.fragment_touch);
		arrayAdapter = (Button)findViewById(R.id.array_adapter);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void init(){
        mContext = this.getApplicationContext();		
		simpleListView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(mContext, SimpleListView.class);
				getApplicationContext().startActivity(intent);
			}
		});
		asyncImageLoader.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(mContext, AsyncImageList.class);
				getApplicationContext().startActivity(intent);
			}
		});
		sendSMS.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SmsManager sms=SmsManager.getDefault();

                //閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵔閿熸枻鎷烽敓锟�閿熻锝忔嫹閿熸枻鎷烽敓鏂ゆ嫹涓洪敓鏂ゆ嫹閿熸枻鎷�
                List<String> texts=sms.divideMessage("Test, haha");

                //閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓閰佃鎷烽敓鏂ゆ嫹
                for(String text:texts)
                {
                    sms.sendTextMessage("13488759668", null, text, null, null);
                }                
			}
		});
		parseJson.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(mContext, JsonParserActivity.class);
				getApplicationContext().startActivity(intent);
			}
		});
		
		fragmentPager.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Intent intent = new Intent(MainActivity.this, com.zht.ztoolkit.fragment.FragmentPagerActivity.class);
				Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(mContext, FragmentPagerActivity.class);
				startActivity(intent);
			}
		});
		
		fragmentTouch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(mContext, FragmentTouchActivity.class);
				startActivity(intent);
				
			}
		});
		
		arrayAdapter.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(mContext, ArrayAdapterActivity.class);
				startActivity(intent);
			}
		});
	}

}
