package com.zht.ztoolkit.listview.arrayadapter;

public class ArrayAdapterItem {
    public int mIconId;
    public String mTitle;
    public String mContent;
    
	public ArrayAdapterItem(int iconId, String title, String content){
		mIconId = iconId;
		mTitle = title;
		mContent = content;
	}
	
	@Override  
    public String toString() {  
		return "[title="+mTitle+", content="+mContent+"]";
    }  
}
