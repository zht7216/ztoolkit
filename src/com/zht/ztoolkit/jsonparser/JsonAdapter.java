package com.zht.ztoolkit.jsonparser;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zht.ztoolkit.R;

public class JsonAdapter extends BaseAdapter{

	LayoutInflater mLayoutInflater;  
    int resourceId;  
    Context mContext;  
    List<Student> mList = null;

    public JsonAdapter(Context context, int resourceId) {  

        //��ȡLayoutInflater ����,������Ԥ�����xml���ִ���view����.  
    	this.mContext = context;
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
            holder.icon = (ImageView) convertView.findViewById(R.id.json_icon);
            holder.name = (TextView) convertView.findViewById(R.id.json_name);  
            holder.age = (TextView) convertView.findViewById(R.id.json_age);  
            holder.className =  (TextView) convertView.findViewById(R.id.json_class);  
            //�����ÿ����ʾ��ViewItem��, ������View�����ö���  
            convertView.setTag(holder);  
        }else{
       	 holder = (ViewHolder) convertView.getTag();
        }

        //��ȡ��ǰҪ��ʾ������  
        Student student = (Student)getItem(position);  

        holder.icon.setImageResource(student.iconId);
        holder.name.setText(student.name);  
        holder.age.setText(String.valueOf(student.age));  
        holder.className.setText(student.className);  

        return convertView;  
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}  

    public void setList(List<Student> list) {
        if (mList != null) {
            mList.clear();
        }

        mList = list;
    }

}  

class ViewHolder {  
	ImageView icon;
    TextView name;  
    TextView age;  
    TextView className;  
}  

class Student {
	public int iconId;
	public String name;  
    public int age;  
    public String className;  
    
    public Student(int iconId, String name, int age, String className) {  
        this.iconId = iconId;
        this.name = name;  
        this.age = age;  
        this.className = className;  
    }  
}