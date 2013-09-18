package com.zht.ztoolkit.simplelistview;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.zht.ztoolkit.R;

public class SimpleListView extends ListActivity{

	/*
	final String[] data = new String[]{  
            "第一章","第二章","第三章","第四章","第五章"  
        };  
        */
	Person[] data = new Person[]{  
            new Person("蔡志坤",25,"ffczk86@gmail.com","厦门市"),  
            new Person("李杰华",25,"aa@bb.com","漳州市"),  
            new Person("张亮",25,"cc@gmail.com","厦门市"),  
            new Person("陈旭",25,"ccadd@gmail.com","厦门市"),  
            new Person("刘玄德",25,"ffczk86@gmail.com","福州市")  
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
