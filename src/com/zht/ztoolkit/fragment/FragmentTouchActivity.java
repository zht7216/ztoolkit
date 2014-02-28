package com.zht.ztoolkit.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.zht.ztoolkit.R;

public class FragmentTouchActivity extends Activity implements OnClickListener{
	
	private Fragment tab1Fragment; 
	private Fragment tab2Fragment;
	private TextView tvTab1;
	private TextView tvTab2;
	private View indicator1;
	private View indicator2;
	private FragmentManager fragmentManager;
	//FragmentTransaction mTransaction;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_touch);
		initViews();
		fragmentManager = this.getFragmentManager();
		//mTransaction = fragmentManager.beginTransaction();
		setTabSelection(0);
	}
	
	public void initViews(){
		tvTab1 = (TextView)findViewById(R.id.touch_tab1);
		tvTab2 = (TextView)findViewById(R.id.touch_tab2);
		tvTab1.setOnClickListener(this);
		tvTab2.setOnClickListener(this);
		indicator1 = (View)findViewById(R.id.indicator1);
		indicator2 = (View)findViewById(R.id.indicator2);
		//tab2Fragment = TouchFragment.newInstance("World");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.touch_tab1:
			Log.e("GKTW87", "touch tab1 clicked");
			setTabSelection(0);
			break;
		case R.id.touch_tab2:
			Log.e("GKTW87", "touch tab2 clicked");
			setTabSelection(1);
			break;
		default:
			break;
		}
	}
	
	public void setTabSelection(int id){
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragments(transaction);
		switch(id){
		case 0:
			tvTab1.setTextColor(Color.RED);
			tvTab2.setTextColor(Color.BLACK);
			indicator1.setVisibility(View.VISIBLE);
			indicator2.setVisibility(View.INVISIBLE);
			if(tab1Fragment == null){
				tab1Fragment = TouchFragment.newInstance("Hello");
				transaction.add(R.id.content, tab1Fragment);
			}else{
				Log.e("GKTW87", "show tab1 fragment");
				transaction.show(tab1Fragment);
			}
			break;
		case 1:
			tvTab1.setTextColor(Color.BLACK);
			tvTab2.setTextColor(Color.RED);
			indicator1.setVisibility(View.INVISIBLE);
			indicator2.setVisibility(View.VISIBLE);
			if(tab2Fragment == null){
				tab2Fragment = TouchFragment.newInstance("World");
				transaction.add(R.id.content, tab2Fragment);
			}else{
				Log.e("GKTW87", "show tab2 fragment");
				transaction.show(tab2Fragment);
			}
			break;
		default:
			break;
		}
		transaction.commit();
	}
	
	public void hideFragments(FragmentTransaction transaction){
		if(tab1Fragment != null){
			transaction.hide(tab1Fragment);
		}
		if(tab2Fragment != null){
			transaction.hide(tab2Fragment);
		}
	}
}
