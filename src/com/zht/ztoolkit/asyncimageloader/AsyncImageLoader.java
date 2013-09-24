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
	 * ͼƬ���漼���ĺ����࣬���ڻ����������غõ�ͼƬ���ڳ����ڴ�ﵽ�趨ֵʱ�Ὣ�������ʹ�õ�ͼƬ�Ƴ�����
	 */
	private LruCache<String, Bitmap> mMemoryCache;
	
	private Context mContext;
	private Handler mRemoteHandler;
	private BitmapTask mTask;

	/**
	 * �����ļ��������������
	 */
	private FileUtils fileUtils;

	public AsyncImageLoader(Context context, Handler handler){
		mContext = context;
		mRemoteHandler = handler;
		//��ȡϵͳ�����ÿ��Ӧ�ó��������ڴ棬ÿ��Ӧ��ϵͳ����32M
		int maxMemory = (int) Runtime.getRuntime().maxMemory();  
		int mCacheSize = maxMemory / 4;
		//��LruCache����1/4=8M
		mMemoryCache = new LruCache<String, Bitmap>(mCacheSize){

			//������д�˷�����������Bitmap�Ĵ�С
			@Override
			protected int sizeOf(String key, Bitmap value) {
				//return value.getRowBytes() * value.getHeight();
				return value.getByteCount();
			}

		};

		fileUtils = new FileUtils(context);
	}
	/**
	 * ��һ��ͼƬ�洢��LruCache�С�
	 * 
	 * @param key
	 *            LruCache�ļ������ﴫ��ͼƬ��URL��ַ��
	 * @param bitmap
	 *            LruCache�ļ������ﴫ������������ص�Bitmap����
	 */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemoryCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	/**
	 * ��LruCache�л�ȡһ��ͼƬ����������ھͷ���null��
	 * 
	 * @param key
	 *            LruCache�ļ������ﴫ��ͼƬ��URL��ַ��
	 * @return ��Ӧ�������Bitmap���󣬻���null��
	 */
	public Bitmap getBitmapFromMemoryCache(String key) {
		return mMemoryCache.get(key);
	}
	
	/**
	 * �첽����ͼƬ�Ļص��ӿ�, ��ʱ���ã��Ѿ���handler send message��ʵ��
	 * @author 
	 *
	 */
	public interface onImageLoaderListener{
		void onImageLoader(Bitmap bitmap, String url);
	}
	
	
	class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

		/**
		 * ͼƬ��URL��ַ
		 */
		private String imageUrl;

		@Override
		protected Bitmap doInBackground(String... params) {
			imageUrl = params[0];
			final String subUrl = String.valueOf(imageUrl.hashCode());//ȱʡ��URL��hashcode��Ϊ�ļ���, Ҳ���Բ���MD5����
			// �ں�̨��ʼ����ͼƬ
			//Bitmap bitmap = downloadBitmap(params[0]);
			Bitmap bitmap = showCacheBitmap(subUrl);
			if(bitmap != null){
				return bitmap;
			}else{
				bitmap = downloadBitmap(params[0]);
				if (bitmap != null) {
					try {
						//������SD�������ֻ�Ŀ¼, �����������ʽ�ǽ�URL�еı�����ȥ����Ϊ�ļ���
						//final String subUrl = imageUrl.replaceAll("[^\\w]", "");
						fileUtils.savaBitmap(subUrl, bitmap);
					} catch (IOException e) {
						e.printStackTrace();
					}
					// ͼƬ������ɺ󻺴浽LrcCache��
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
		 * ����HTTP���󣬲���ȡBitmap����
		 * 
		 * @param imageUrl
		 *            ͼƬ��URL��ַ
		 * @return �������Bitmap����
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
		 * ��ȡBitmap, �ڴ���û�о�ȥ�ֻ�����sd���л�ȡ����һ����getView�л���ã��ȽϹؼ���һ��
		 * @param url
		 * @return
		 */
		public Bitmap showCacheBitmap(String url){
			Log.d("GKTW87", "\033[32m Enter showCacheBitmap. \033[0m"+url);
			if(getBitmapFromMemoryCache(url) != null){
				Log.d("GKTW87", "\033[33m get bitmap from memory cache. \033[0m");
				return getBitmapFromMemoryCache(url);
			}else if(fileUtils.isFileExists(url) && fileUtils.getFileSize(url) != 0){
				//��SD����ȡ�ֻ������ȡBitmap
				Log.d("GKTW87", "\033[32m Enter fileUtils.getBitmap. \033[0m");
				Bitmap bitmap = fileUtils.getBitmap(url);

				//��Bitmap �����ڴ滺��
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
