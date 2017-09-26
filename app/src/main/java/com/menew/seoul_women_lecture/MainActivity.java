package com.menew.seoul_women_lecture;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Administrator on 2015-10-26.
 */
public class MainActivity extends Activity {

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
    }

    private void initLayout() {
        mListView = (ListView) findViewById(R.id.id_main_listview);
        mListView.setAdapter(new MainListAdapter());
        mListView.setOnItemClickListener(mItemClickListener);
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
            Log.e("_____", "________ i : " + i);
            intent.putExtra(Const.PARAM_POSITION, i);
            intent.putExtra(Const.PARAM_TITLE, Const.CLASS_NAME[i]);
            startActivity(intent);
        }
    };

    public class MainListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return Const.CLASS_NAME.length;
        }



        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View convertView = view;
            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_main, null);
            }
            ImageView icon = (ImageView) convertView.findViewById(R.id.id_item_main_icon);
            TextView title = (TextView) convertView.findViewById(R.id.id_item_main_title);
            TextView sub = (TextView) convertView.findViewById(R.id.id_item_main_sub);

            icon.setImageResource(Const.ICONS[i]);
            title.setText(Const.CLASS_NAME[i]);
            sub.setText(Const.ADDRESSES[i]);

            return convertView;
        }
    }
}
