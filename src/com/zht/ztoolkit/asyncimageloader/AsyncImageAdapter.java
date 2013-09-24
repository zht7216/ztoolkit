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
	 * 记录所有正在下载或等待下载的任务。
	 */
	private Set<BitmapWorkerTask> taskCollection;

	/**
	 * 异步下载图片核心类对象
	 */
	private AsyncImageLoader mAsyncImageLoader;
	
	private BitmapTask mTask;
	
	/**
	 * ListView的实例
	 */
	private ListView mPhotoWall;

	/**
	 * 第一张可见图片的下标
	 */
	private int mFirstVisibleItem;

	/**
	 * 一屏有多少张图片可见
	 */
	private int mVisibleItemCount;

	/**
	 * 记录是否刚打开程序，用于解决进入程序不滚动屏幕，不会下载图片的问题。
	 * 参考http://blog.csdn.net/guolin_blog/article/details/9526203#comments
	 */
	private boolean isFirstEnter = true;

	/**
	 * 当图片异步下载完毕后便会通过handler传回给adapter, 将其设置到imageview中
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
		// 给ImageView设置一个Tag，保证异步加载图片时不会乱序
		holder.icon.setTag(url);
		setImageView(url, holder.icon);
		return convertView;
		/*
		final ImageView photo = (ImageView) view.findViewById(R.id.photo);
		// 给ImageView设置一个Tag，保证异步加载图片时不会乱序
		photo.setTag(url);
		setImageView(url, photo);
		return view;
		*/
	}

	/**
	 * 给ImageView设置图片。首先从LruCache中取出图片的缓存，设置到ImageView上。如果LruCache中没有该图片的缓存，
	 * 就给ImageView设置一张默认图片。
	 * 
	 * @param imageUrl
	 *            图片的URL地址，用于作为LruCache的键。
	 * @param imageView
	 *            用于显示图片的控件。
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
		// 仅当ListView静止时才去下载图片，ListView滑动时取消所有正在下载的任务
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
		// 下载的任务应该由onScrollStateChanged里调用，但首次进入程序时onScrollStateChanged并不会调用，
		// 因此在这里为首次进入程序开启下载任务。
		if (isFirstEnter && visibleItemCount > 0) {
			loadBitmaps(firstVisibleItem, visibleItemCount);
			isFirstEnter = false;
		}
	}

	/**
	 * 加载Bitmap对象。此方法会在LruCache中检查所有屏幕中可见的ImageView的Bitmap对象，
	 * 如果发现任何一个ImageView的Bitmap对象不在缓存中，就会开启异步线程去下载图片。
	 * 
	 * @param firstVisibleItem
	 *            第一个可见的ImageView的下标
	 * @param visibleItemCount
	 *            屏幕中总共可见的元素数
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
	 * 取消所有正在下载或等待下载的任务。
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