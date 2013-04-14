package com.FourIL.AlarmApp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class StartMainActivity extends Activity {

	public Button setAlarmButton;
	public Button enterDownloadButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_main);
        
        setAlarmButton = (Button) findViewById(R.id.SetAlarm);
        setAlarmButton.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(StartMainActivity.this, DeskClockMainActivity.class));
				
			}
		});
        enterDownloadButton = (Button) findViewById(R.id.Download);
        enterDownloadButton.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(StartMainActivity.this, DownloadPageActivity.class));
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.start_main, menu);
        return true;
    }
}
