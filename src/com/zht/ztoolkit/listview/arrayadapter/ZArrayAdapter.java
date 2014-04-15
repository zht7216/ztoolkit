package com.zht.ztoolkit.listview.arrayadapter;

import java.util.List;

import com.zht.ztoolkit.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ZArrayAdapter extends ArrayAdapter<ArrayAdapterItem>{

	LayoutInflater mLayoutInflater;
	public int mResourceId;
	
	public ZArrayAdapter(Context context, int resource, List<ArrayAdapterItem> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		mResourceId = resource;
		mLayoutInflater = LayoutInflater.from(context);
	}

	public ZArrayAdapter(Context context, int resource, ArrayAdapterItem[] objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		mResourceId = resource;
		mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//return super.getView(position, convertView, parent);
		ViewHolder holder;
		if(convertView == null){
			convertView = mLayoutInflater.inflate(mResourceId, null);
			holder = new ViewHolder();
			holder.icon = (ImageView)convertView.findViewById(R.id.icon);
			holder.title = (TextView)convertView.findViewById(R.id.title);
			holder.content = (TextView)convertView.findViewById(R.id.content);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		ArrayAdapterItem item = getItem(position);
		holder.icon.setImageResource(item.mIconId);
		holder.title.setText(item.mTitle);
		holder.content.setText(item.mContent);
		return convertView;
	}
	
}

class ViewHolder {
	ImageView icon;
	TextView title;
	TextView content;
}