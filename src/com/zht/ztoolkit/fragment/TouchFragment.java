package com.zht.ztoolkit.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zht.ztoolkit.R;

public class TouchFragment extends Fragment{
	private static final String TAG = "TouchFragment";
	private String mHello;
	
	static TouchFragment newInstance(String s) {
		TouchFragment newFragment = new TouchFragment();
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
		Log.d(TAG, "TouchFragment-----onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//return super.onCreateView(inflater, container, savedInstanceState);
		 View view = inflater.inflate(R.layout.fragment_content, container, false);
		 TextView viewhello = (TextView) view.findViewById(R.id.tv_hello);
		 viewhello.setText(mHello);
		 Log.d(TAG, "TouchFragment-----onCreateView");
		 return view;
	}
	
	@Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "TouchFragment-----onDestroy");
    }
}
