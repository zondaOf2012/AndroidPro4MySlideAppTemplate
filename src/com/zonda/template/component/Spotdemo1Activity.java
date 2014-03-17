package com.zonda.template.component;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.zonda.cjson.ZondaDJson;
import com.zonda.second.R;

public class Spotdemo1Activity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button btn = (Button) findViewById(R.id.action_settings);
        Button btn01 = (Button) findViewById(R.id.action_settings);
        Button btn02 = (Button) findViewById(R.id.action_settings);
        Button btn03 = (Button) findViewById(R.id.action_settings);
        //设置插屏广告每组的个数
		final ZondaDJson spotManager = ZondaDJson.getIs(getApplicationContext(),
				"11154c8bd0ae27fd549f9ae8cada1850");
		
		// 初始化,预加载插屏广告到本地,必须调用且调用一次即可,提升广告显示速度
		spotManager.lcp(getApplicationContext());
		//spotManager.newcfg(getApplicationContext(), false, false, false, 1);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//传入this
				spotManager.scp(Spotdemo1Activity.this);
			}
		});
		btn01.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//传入getApplicationContext(),需注册activity
				spotManager.scp(getApplicationContext());
			}
		});
		btn02.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				spotManager.newcfg(getApplicationContext(), true, true, 0);
			}
		});
		btn03.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//传入getApplicationContext(),需注册activity
				spotManager.newcfg(getApplicationContext(), true, false, 0);
			}
		});
		

    }
    
	//可监听返回按钮,执行退弹广告
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			ZondaDJson spotManager = ZondaDJson.getIs(getApplicationContext(),
			"11154c8bd0ae27fd549f9ae8cada1850");
			spotManager.exit(Spotdemo1Activity.this);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}