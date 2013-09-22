package com.zht.ztoolkit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.zht.ztoolkit.asyncimageloader.AsyncImageList;
import com.zht.ztoolkit.simplelistview.SimpleListView;

public class MainActivity extends Activity {

	Button simpleListView;
	Button asyncImageLoader;
	Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		simpleListView = (Button)findViewById(R.id.simple_listview);
		asyncImageLoader = (Button)findViewById(R.id.async_imageloader);
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
	}

}
