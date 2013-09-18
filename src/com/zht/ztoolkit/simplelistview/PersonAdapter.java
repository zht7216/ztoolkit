package com.zht.ztoolkit.simplelistview;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zht.ztoolkit.R;

public class PersonAdapter extends ArrayAdapter<Person>{

	 LayoutInflater mLayoutInflater;  
     int resourceId;  
     Context mContext;  

     public PersonAdapter(Context context, int resourceId, Person[] objects) {  

         super(context, resourceId, objects);  
         //获取LayoutInflater 服务,用来从预定义的xml布局创建view对象.  
         this.resourceId = resourceId;  
         mLayoutInflater = LayoutInflater.from(context);  

     }  

     @Override  
     public View getView(int position, View convertView, ViewGroup parent) {  

    	 ViewHolder holder;
         if(convertView == null){  
             //创建新的view视图.  
             convertView = mLayoutInflater.inflate(resourceId, null);  
             holder = new ViewHolder();  
             //查找每个ViewItem中,各个子View,放进holder中  
             holder.name = (TextView) convertView.findViewById(R.id.person_name);  
             holder.age = (TextView) convertView.findViewById(R.id.person_age);  
             holder.email =  (TextView) convertView.findViewById(R.id.person_email);  
             holder.address = (TextView) convertView.findViewById(R.id.person_address);  
             //保存对每个显示的ViewItem中, 各个子View的引用对象  
             convertView.setTag(holder);  
         }else{
        	 holder = (ViewHolder) convertView.getTag();
         }

         //获取当前要显示的数据  
         Person person = getItem(position);  

         holder.name.setText(person.name);  
         holder.age.setText(String.valueOf(person.age));  
         holder.email.setText(person.email);  
         holder.address.setText(person.address);  

         return convertView;  

     }  

 }  

 class ViewHolder {  
     TextView name;  
     TextView age;  
     TextView email;  
     TextView address;  
 }  