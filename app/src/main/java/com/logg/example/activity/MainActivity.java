package com.logg.example.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.logg.example.helper.TestInterceptor;
import com.logg.interceptor.LoggInterceptor;
import com.logg.interceptor.callback.GlobalCallback;
import com.logg.interceptor.callback.LoggCallback;
import com.logg.printer.Type;
import com.tool.log.example.R;

import com.logg.Logg;
import com.logg.example.helper.DataHelper;

/**
 * 测试
 */
public class MainActivity extends AppCompatActivity {

    private Button btnDefaultLog;
    private Button btnJsonLog;
    private Button btnXMLLog;
    private Button btnBigLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.logTest();
    }

    private void logTest() {
        btnDefaultLog = (Button) findViewById(R.id.btn_default_log);
        btnJsonLog = (Button) findViewById(R.id.btn_json_log);
        btnXMLLog = (Button) findViewById(R.id.btn_xml_log);
        btnBigLog = (Button) findViewById(R.id.btn_big_log);

        View.OnClickListener onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_default_log:
                        // 基本数据类型 byte short int long float double char boolean
                        Logg.v(3.1415926);
                        String[] str = {"aaaaaaaa","bbbbb"};
                        Logg.v(str);
                        Logg.v("test", 3.1415926);
                        // 数组
                        Logg.d(DataHelper.getArray());
                        // Map
                        Logg.i(DataHelper.getMap());
                        // List
                        Logg.w(DataHelper.getList());
                        // Intent
                        Logg.e(DataHelper.getIntent());
                        break;
                    case R.id.btn_json_log:
                        Logg.json(DataHelper.getJson());
                        break;
                    case R.id.btn_xml_log:
                        Logg.xml(DataHelper.getXml());
                        break;
                    case R.id.btn_big_log:
                        Logg.d(DataHelper.getBigString(MainActivity.this));
                        break;
                    default:
                        break;
                }
            }
        };

        btnDefaultLog.setOnClickListener(onClickListener);
        btnJsonLog.setOnClickListener(onClickListener);
        btnXMLLog.setOnClickListener(onClickListener);
        btnBigLog.setOnClickListener(onClickListener);

        // 添加一个拦截器
        Logg.addInterceptor(new TestInterceptor());

        // 添加一个全局监听
        GlobalCallback.getInstance().addCallback(new LoggCallback() {

            @Override
            public void logg(Type type, String tag, String message) {
                Log.e(tag, message);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_github:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/RockyQu")));
                break;
            case R.id.action_Logg:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/RockyQu/Logg.git")));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
