package com.zht.ztoolkit.simplelistview;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.zht.ztoolkit.R;

public class SimpleListView extends ListActivity{

	/*
	final String[] data = new String[]{  
            "��һ��","�ڶ���","������","������","������"  
        };  
        */
	Person[] data = new Person[]{  
            new Person("��־��",25,"ffczk86@gmail.com","������"),  
            new Person("��ܻ�",25,"aa@bb.com","������"),  
            new Person("����",25,"cc@gmail.com","������"),  
            new Person("����",25,"ccadd@gmail.com","������"),  
            new Person("������",25,"ffczk86@gmail.com","������")  
        };  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
   
        super.onCreate(savedInstanceState);  

        setContentView(R.layout.simple_listview);  
        /*
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  
                android.R.layout.simple_list_item_1, data);  
         */
        ArrayAdapter<Person> adapter = new PersonAdapter(this,  
        		R.layout.simple_listview_item, data);  

        setListAdapter(adapter);  

    }  
}
