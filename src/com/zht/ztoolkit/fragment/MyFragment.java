package com.zht.ztoolkit.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zht.ztoolkit.R;

public class MyFragment extends Fragment{
	private static final String TAG = "MyFragment";
	private String mHello;
	
	static MyFragment newInstance(String s) {
		MyFragment newFragment = new MyFragment();
		Bundle bundle = new Bundle();
		bundle.putString("hello", s);
		newFragment.setArguments(bundle);
		return newFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		mHello = bundle != null ? bundle.getString("hello") : "Hello World";
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		 View view = inflater.inflate(R.layout.fragment_content, container, false);
		 TextView viewhello = (TextView) view.findViewById(R.id.tv_hello);
		 viewhello.setText(mHello);
		 return view;
	}
	
	@Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MyFragment-----onDestroy");
    }
}
