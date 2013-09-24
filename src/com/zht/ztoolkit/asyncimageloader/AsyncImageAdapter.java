package com.zht.ztoolkit.asyncimageloader;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zht.ztoolkit.R;
import com.zht.ztoolkit.asyncimageloader.AsyncImageLoader.BitmapTask;
import com.zht.ztoolkit.asyncimageloader.AsyncImageLoader.BitmapWorkerTask;

public class AsyncImageAdapter extends ArrayAdapter<String> implements OnScrollListener {

	/**
	 * ��¼�����������ػ�ȴ����ص�����
	 */
	private Set<BitmapWorkerTask> taskCollection;

	/**
	 * �첽����ͼƬ���������
	 */
	private AsyncImageLoader mAsyncImageLoader;
	
	private BitmapTask mTask;
	
	/**
	 * ListView��ʵ��
	 */
	private ListView mPhotoWall;

	/**
	 * ��һ�ſɼ�ͼƬ���±�
	 */
	private int mFirstVisibleItem;

	/**
	 * һ���ж�����ͼƬ�ɼ�
	 */
	private int mVisibleItemCount;

	/**
	 * ��¼�Ƿ�մ򿪳������ڽ��������򲻹�����Ļ����������ͼƬ�����⡣
	 * �ο�http://blog.csdn.net/guolin_blog/article/details/9526203#comments
	 */
	private boolean isFirstEnter = true;

	/**
	 * ��ͼƬ�첽������Ϻ���ͨ��handler���ظ�adapter, �������õ�imageview��
	 */
	private final Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			mTask = (BitmapTask)msg.obj;
			Bitmap bitmap = mTask.bitmap;
			String imageUrl = mTask.url;
			BitmapWorkerTask task = mTask.task;
			ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(imageUrl);
			if (imageView != null && bitmap != null) {
				imageView.setImageBitmap(bitmap);
			}
			taskCollection.remove(task);
		}
	};
	public AsyncImageAdapter(Context context, int textViewResourceId, String[] objects,
			ListView photoWall) {
		super(context, textViewResourceId, objects);
		mPhotoWall = photoWall;
		taskCollection = new HashSet<BitmapWorkerTask>();
		mAsyncImageLoader = new AsyncImageLoader(context, mHandler);
		mPhotoWall.setOnScrollListener(this);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final String url = getItem(position);
		//View view;
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.async_imagelist_item, null);
			holder = new ViewHolder();
			holder.icon = (ImageView)convertView.findViewById(R.id.photo);
			convertView.setTag(holder);
			//view = LayoutInflater.from(getContext()).inflate(R.layout.async_imagelist_item, null);
		} else {
			//view = convertView;
			holder = (ViewHolder)convertView.getTag();
		}
		// ��ImageView����һ��Tag����֤�첽����ͼƬʱ��������
		holder.icon.setTag(url);
		setImageView(url, holder.icon);
		return convertView;
		/*
		final ImageView photo = (ImageView) view.findViewById(R.id.photo);
		// ��ImageView����һ��Tag����֤�첽����ͼƬʱ��������
		photo.setTag(url);
		setImageView(url, photo);
		return view;
		*/
	}

	/**
	 * ��ImageView����ͼƬ�����ȴ�LruCache��ȡ��ͼƬ�Ļ��棬���õ�ImageView�ϡ����LruCache��û�и�ͼƬ�Ļ��棬
	 * �͸�ImageView����һ��Ĭ��ͼƬ��
	 * 
	 * @param imageUrl
	 *            ͼƬ��URL��ַ��������ΪLruCache�ļ���
	 * @param imageView
	 *            ������ʾͼƬ�Ŀؼ���
	 */
	private void setImageView(String imageUrl, ImageView imageView) {
		final String subUrl = String.valueOf(imageUrl.hashCode());
		Bitmap bitmap = mAsyncImageLoader.getBitmapFromMemoryCache(subUrl);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			imageView.setImageResource(R.drawable.ic_empty);
		}
	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// ����ListView��ֹʱ��ȥ����ͼƬ��ListView����ʱȡ�������������ص�����
		if (scrollState == SCROLL_STATE_IDLE) {
			loadBitmaps(mFirstVisibleItem, mVisibleItemCount);
		} else {
			cancelAllTasks();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
			int totalItemCount) {
		mFirstVisibleItem = firstVisibleItem;
		mVisibleItemCount = visibleItemCount;
		// ���ص�����Ӧ����onScrollStateChanged����ã����״ν������ʱonScrollStateChanged��������ã�
		// ���������Ϊ�״ν����������������
		if (isFirstEnter && visibleItemCount > 0) {
			loadBitmaps(firstVisibleItem, visibleItemCount);
			isFirstEnter = false;
		}
	}

	/**
	 * ����Bitmap���󡣴˷�������LruCache�м��������Ļ�пɼ���ImageView��Bitmap����
	 * ��������κ�һ��ImageView��Bitmap�����ڻ����У��ͻῪ���첽�߳�ȥ����ͼƬ��
	 * 
	 * @param firstVisibleItem
	 *            ��һ���ɼ���ImageView���±�
	 * @param visibleItemCount
	 *            ��Ļ���ܹ��ɼ���Ԫ����
	 */
	private void loadBitmaps(int firstVisibleItem, int visibleItemCount) {
		try {
			for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++) {
				String imageUrl = ImageUrls.imageThumbUrls[i];
				Bitmap bitmap = mAsyncImageLoader.getBitmapFromMemoryCache(imageUrl);
				if (bitmap == null) {
					BitmapWorkerTask task = mAsyncImageLoader.new BitmapWorkerTask();
					taskCollection.add(task);
					task.execute(imageUrl);
				} else {
					ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(imageUrl);
					if (imageView != null && bitmap != null) {
						imageView.setImageBitmap(bitmap);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ȡ�������������ػ�ȴ����ص�����
	 */
	public void cancelAllTasks() {
		if (taskCollection != null) {
			for (BitmapWorkerTask task : taskCollection) {
				task.cancel(false);
			}
		}
	}
	
	class ViewHolder {  
		 ImageView icon;
	 }  

}