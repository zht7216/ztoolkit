package com.zht.ztoolkit.listview.arrayadapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zht.ztoolkit.R;

public class ArrayAdapterActivity extends Activity{
	
	ListView mList = null;
	ZArrayAdapter mAdapter = null;
	List<ArrayAdapterItem> mItems = new ArrayList<ArrayAdapterItem>();
	ArrayAdapterItem[] data = new ArrayAdapterItem[]{  
            new ArrayAdapterItem(R.drawable.ic_launcher, "Title1","Content1"),  
            new ArrayAdapterItem(R.drawable.ic_launcher, "Title2","Content2"),  
            new ArrayAdapterItem(R.drawable.ic_launcher, "Title3","Content3"),  
            new ArrayAdapterItem(R.drawable.ic_launcher, "Title4","Content4"),  
            new ArrayAdapterItem(R.drawable.ic_launcher, "Title5","Content5")
        };  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.arrayadapter_activity);
		initData();
		initView();
		//1. use fixed number of items in layout
		//mAdapter = new ZArrayAdapter(this, R.layout.arrayadapter_item, data);
		//2. use initData() to load variable number of items in layout
	}
	
	public void initView(){
		mList = (ListView)findViewById(R.id.list);
		mAdapter = new ZArrayAdapter(this, R.layout.arrayadapter_item, mItems);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				/*
				//position used for fixed number of items
				switch(arg2){
					case 1:
						break;
					case 2:
						break;
					default: 
						break;
				}
				*/
			}
		});
	}
	
	public void initData(){
		for(int i=0; i<100; i++){
			String title = "title"+(i+1);
			String content = "content"+(i+1);
			int id = R.drawable.ic_launcher;
			ArrayAdapterItem item = new ArrayAdapterItem(id, title, content);
			mItems.add(item);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
