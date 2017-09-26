package com.menew.seoul_women_lecture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class IntroActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                if(Utils.isNetworkAvailable(IntroActivity.this)) {
                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
                    IntroActivity.this.finish();
                }
                else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(IntroActivity.this, "네트워크 연결이 불안정합니다.", Toast.LENGTH_SHORT).show();
                            IntroActivity.this.finish();
                        }
                    });
                }
            }
        }, 2000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.intro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
