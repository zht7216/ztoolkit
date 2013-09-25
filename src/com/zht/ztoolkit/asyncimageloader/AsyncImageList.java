package com.zht.ztoolkit.asyncimageloader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.zht.ztoolkit.R;

/**
 * �ο�ʵ�֣� http://blog.csdn.net/xiaanming/article/details/9825113
 *           http://blog.csdn.net/guolin_blog/article/details/9526203
 * @author tao.zt
 *
 */
public class AsyncImageList extends Activity{
	private ListView mListView;
	private String [] imageThumbUrls = ImageUrls.imageThumbUrls; 
	//private ImageAdapter mImageAdapter;
	private AsyncImageAdapter mImageAdapter;
	//����Ҫ���̻���Ļ�����ȥ��fileUtils��ش���
	private FileUtils fileUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.async_imagelist);
		fileUtils = new FileUtils(this);
		mListView = (ListView)findViewById(R.id.async_listview);
		//mImageAdapter = new ImageAdapter(this, mListView, imageThumbUrls);
		mImageAdapter = new AsyncImageAdapter(this, 0, imageThumbUrls, mListView);
		mListView.setAdapter(mImageAdapter);
	}

	@Override
	protected void onDestroy() {
		//mImageAdapter.cancelTask();
		mImageAdapter.cancelAllTasks();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add("ɾ���ֻ���ͼƬ����");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			fileUtils.deleteFile();
			Toast.makeText(getApplication(), "��ջ���ɹ�", Toast.LENGTH_SHORT).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
