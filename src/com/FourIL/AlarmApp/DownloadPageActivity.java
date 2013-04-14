package com.FourIL.AlarmApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DownloadPageActivity extends Activity {

	Button downButton;
	Button infoButton;
	ImageView myimgView;
	InfoPack infoPack;
	TextView mylblView, mytxtView;
	int nowpage;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_page);
        

        myimgView = (ImageView) findViewById(R.id.imgview);
        mylblView = (TextView) findViewById(R.id.labelview);
        mytxtView = (TextView) findViewById(R.id.textview);
        
        nowpage = 2;
        new DownloadNewTask().execute("http://10.72.134.71:3000/movies/");
        
        //downButton = (Button) findViewById(R.id.down);
       /* downButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//HttpDownloader downloader = new HttpDownloader();
				//int result = downloader.downFile("http://10.72.134.71:3000/x.bmp", "FILFiles/Pictures", "x.bmp");
				//Log.v("xxx", "" + result);
				//myimgView.setBackgroundDrawable(getResources().getDrawable(R.drawable.clock_launcher));
				 /* try {   
						URL url = new URL("http://hiphotos.baidu.com/baidu/pic/item/7d8aebfebf3f9e125c6008d8.jpg");
						HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
						urlConn.setDoInput(true);   
						urlConn.setConnectTimeout(100000);
						urlConn.setReadTimeout(100000);
						urlConn.connect();
						InputStream myis = urlConn.getInputStream();
						Bitmap mybitmap = BitmapFactory.decodeStream(myis);
						myimgView.setImageBitmap(mybitmap);
				  } catch (Exception e) {   
					   e.printStackTrace();   
				  }   
				new DownloadImageTask().execute("http://10.72.134.71:3000/movies/1");
			}
		});*/
        
    }
    
    private class DownloadNewTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return params[0];
		}

		protected void onPostExecute(String result) {
			Log.v("rec", "" + nowpage);
			String str;
			try {
				HttpDownloader downloader = new HttpDownloader();
				str = downloader.download(result + nowpage);
			} catch (Exception e) {
				--nowpage;
				new DownloadPackTask().execute(result + nowpage);				
				return;
			}
			int pos=0;
			for (int i=0;i+8<str.length();i++)
			   if (str.substring(i,i+9).contentEquals("INFOMAGIC"))
			   {
				   pos=i+9;
				   break;
			   }
			if (pos > 0) {
				++nowpage;
				new DownloadNewTask().execute(result);
			} else {
				--nowpage;
				new DownloadPackTask().execute(result + nowpage);
			}
		}
    	
    }
    
    private class DownloadPackTask extends AsyncTask<String, Void, InfoPack> {

    	InfoPack recInfoPack;
    	
		@Override
		protected InfoPack doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			HttpDownloader downloader = new HttpDownloader();
			String str = downloader.download(params[0]);
			
			/*StringBuffer sb = new StringBuffer();
			String line = null;
			BufferedReader buffer = null;
			buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			while( (line = buffer.readLine()) != null){
				//Log.v("oo", line);
				sb.append(line);
				
			}
			Log.v("aaa", sb.toString());
			line = sb.toString();
			byte[] b = Base64.decode(line, Base64.DEFAULT);
            Bitmap mybitmap = BitmapFactory.decodeByteArray(b, 0, b.length);*/
			//if (mybitmap == null) Log.v("xx1", "xx1");
			//return mybitmap;
			
			InfoPack infoPack = new InfoPack();
			int pos=0;
			for (int i=0;i+8<str.length();i++)
			   if (str.substring(i,i+9).contentEquals("INFOMAGIC"))
			   {
				   pos=i+9;
				   break;
			   }
			while (str.charAt(pos)=='\n'||str.charAt(pos)==' '||str.charAt(pos)=='$')
				pos++;
			while (str.charAt(pos)!='$')
			{
			   String tmp1="",tmp2="";
			   while (str.charAt(pos)!=':')
			   {
				   tmp1+=str.charAt(pos);
				   pos++;
			   }
			   pos++;
			   while (str.charAt(pos)!=';')
			   {
				   tmp2+=str.charAt(pos);
				   pos++;
			   }
			   pos++;
			   //Log.v("***"+tmp1,tmp1+tmp2+tmp1);
			   if (tmp1.contentEquals("url")) infoPack.url = tmp2;
			   else
			   if (tmp1.contentEquals("name")) {
				   infoPack.name = tmp2;
				   Log.v(infoPack.name, infoPack.name);
			   }
			   else
			   if (tmp1.contentEquals("images")) {
				   infoPack.images = tmp2;
				//Log.v(infoPack.images, infoPack.images);
			   }
			   else
			   if (tmp1.contentEquals("music")) infoPack.music = tmp2;
			   else
			   if (tmp1.contentEquals("short_description")) {
				   infoPack.shortDescription = tmp2;
				   Log.v(tmp2, tmp2);
			   }
			   else
			   if (tmp1.contentEquals("description")) infoPack.longDescription = tmp2;
			}
			//Log.v("", "");
			return infoPack;
		}
		
		protected void onPostExecute(InfoPack result) {
			recInfoPack = result;
			InfoPackList.getInstance().addInfoPack(result);
	        	infoButton = (Button) findViewById(R.id.infoBtn);
	        	infoButton.setVisibility(View.VISIBLE);
	        	infoButton.setText("�����µĹ�����");
	        	final String imgurl = result.images;
	        	infoButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Log.v("oo", "bb");
						// TODO Auto-generated method stub
						new DownloadImageTask().execute(imgurl);
					}
				});
	        }
    	
	    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	
			@Override
			protected Bitmap doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				try {
					HttpDownloader downloader = new HttpDownloader();
					InputStream myis = downloader.getInputStreamFromURL(params[0]);
					Bitmap mybitmap = BitmapFactory.decodeStream(myis);
					return mybitmap;
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
			
			protected void onPostExecute(Bitmap result) {
				myimgView.setImageBitmap(result);
				mylblView.setTextColor(Color.WHITE);
				mytxtView.setTextColor(Color.WHITE);
				mylblView.setText(recInfoPack.shortDescription);
				mytxtView.setText(recInfoPack.longDescription);
				infoButton.setVisibility(View.INVISIBLE);
			}
	    	
	    }
    	
    }
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.download_page, menu);
        return true;
    }
}
