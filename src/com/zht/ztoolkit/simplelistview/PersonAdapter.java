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
         //��ȡLayoutInflater ����,������Ԥ�����xml���ִ���view����.  
         this.resourceId = resourceId;  
         mLayoutInflater = LayoutInflater.from(context);  

     }  

     @Override  
     public View getView(int position, View convertView, ViewGroup parent) {  

    	 ViewHolder holder;
         if(convertView == null){  
             //�����µ�view��ͼ.  
             convertView = mLayoutInflater.inflate(resourceId, null);  
             holder = new ViewHolder();  
             //����ÿ��ViewItem��,������View,�Ž�holder��  
             holder.name = (TextView) convertView.findViewById(R.id.person_name);  
             holder.age = (TextView) convertView.findViewById(R.id.person_age);  
             holder.email =  (TextView) convertView.findViewById(R.id.person_email);  
             holder.address = (TextView) convertView.findViewById(R.id.person_address);  
             //�����ÿ����ʾ��ViewItem��, ������View�����ö���  
             convertView.setTag(holder);  
         }else{
        	 holder = (ViewHolder) convertView.getTag();
         }

         //��ȡ��ǰҪ��ʾ������  
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