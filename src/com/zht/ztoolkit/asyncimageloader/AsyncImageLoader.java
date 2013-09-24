package com.zht.ztoolkit.asyncimageloader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

public class AsyncImageLoader {

	
	
	/**
	 * 图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
	 */
	private LruCache<String, Bitmap> mMemoryCache;
	
	private Context mContext;
	private Handler mRemoteHandler;
	private BitmapTask mTask;

	/**
	 * 操作文件相关类对象的引用
	 */
	private FileUtils fileUtils;

	public AsyncImageLoader(Context context, Handler handler){
		mContext = context;
		mRemoteHandler = handler;
		//获取系统分配给每个应用程序的最大内存，每个应用系统分配32M
		int maxMemory = (int) Runtime.getRuntime().maxMemory();  
		int mCacheSize = maxMemory / 4;
		//给LruCache分配1/4=8M
		mMemoryCache = new LruCache<String, Bitmap>(mCacheSize){

			//必须重写此方法，来测量Bitmap的大小
			@Override
			protected int sizeOf(String key, Bitmap value) {
				//return value.getRowBytes() * value.getHeight();
				return value.getByteCount();
			}

		};

		fileUtils = new FileUtils(context);
	}
	/**
	 * 将一张图片存储到LruCache中。
	 * 
	 * @param key
	 *            LruCache的键，这里传入图片的URL地址。
	 * @param bitmap
	 *            LruCache的键，这里传入从网络上下载的Bitmap对象。
	 */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemoryCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	/**
	 * 从LruCache中获取一张图片，如果不存在就返回null。
	 * 
	 * @param key
	 *            LruCache的键，这里传入图片的URL地址。
	 * @return 对应传入键的Bitmap对象，或者null。
	 */
	public Bitmap getBitmapFromMemoryCache(String key) {
		return mMemoryCache.get(key);
	}
	
	/**
	 * 异步下载图片的回调接口, 暂时无用，已经用handler send message来实现
	 * @author 
	 *
	 */
	public interface onImageLoaderListener{
		void onImageLoader(Bitmap bitmap, String url);
	}
	
	
	class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

		/**
		 * 图片的URL地址
		 */
		private String imageUrl;

		@Override
		protected Bitmap doInBackground(String... params) {
			imageUrl = params[0];
			final String subUrl = String.valueOf(imageUrl.hashCode());//缺省将URL　hashcode作为文件名, 也可以采用MD5加密
			// 在后台开始下载图片
			//Bitmap bitmap = downloadBitmap(params[0]);
			Bitmap bitmap = showCacheBitmap(subUrl);
			if(bitmap != null){
				return bitmap;
			}else{
				bitmap = downloadBitmap(params[0]);
				if (bitmap != null) {
					try {
						//保存在SD卡或者手机目录, 下面的正则表达式是将URL中的标点符号去掉作为文件名
						//final String subUrl = imageUrl.replaceAll("[^\\w]", "");
						fileUtils.savaBitmap(subUrl, bitmap);
					} catch (IOException e) {
						e.printStackTrace();
					}
					// 图片下载完成后缓存到LrcCache中
					addBitmapToMemoryCache(subUrl, bitmap);
				}
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);
			BitmapTask task = new BitmapTask();
			task.url = imageUrl;
			task.bitmap = bitmap;
			task.task = this;
			Message msg = mRemoteHandler.obtainMessage();
			msg.obj = task;
			mRemoteHandler.sendMessage(msg);
		}

		/**
		 * 建立HTTP请求，并获取Bitmap对象。
		 * 
		 * @param imageUrl
		 *            图片的URL地址
		 * @return 解析后的Bitmap对象
		 */
		private Bitmap downloadBitmap(String imageUrl) {
			Bitmap bitmap = null;
			HttpURLConnection con = null;
			try {
				URL url = new URL(imageUrl);
				con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(5 * 1000);
				con.setReadTimeout(10 * 1000);
				con.setDoInput(true);
				con.setDoOutput(true);
				bitmap = BitmapFactory.decodeStream(con.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					con.disconnect();
				}
			}
			return bitmap;
		}
		
		/**
		 * 获取Bitmap, 内存中没有就去手机或者sd卡中获取，这一步在getView中会调用，比较关键的一步
		 * @param url
		 * @return
		 */
		public Bitmap showCacheBitmap(String url){
			Log.d("GKTW87", "\033[32m Enter showCacheBitmap. \033[0m"+url);
			if(getBitmapFromMemoryCache(url) != null){
				Log.d("GKTW87", "\033[33m get bitmap from memory cache. \033[0m");
				return getBitmapFromMemoryCache(url);
			}else if(fileUtils.isFileExists(url) && fileUtils.getFileSize(url) != 0){
				//从SD卡获取手机里面获取Bitmap
				Log.d("GKTW87", "\033[32m Enter fileUtils.getBitmap. \033[0m");
				Bitmap bitmap = fileUtils.getBitmap(url);

				//将Bitmap 加入内存缓存
				addBitmapToMemoryCache(url, bitmap);
				return bitmap;
			}

			return null;
		}

	}
	
	public class BitmapTask {
		public Bitmap bitmap;
		public BitmapWorkerTask task;
		public String url;
	}
}
