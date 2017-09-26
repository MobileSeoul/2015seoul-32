package com.menew.seoul_women_lecture;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015-10-26.
 */
public class ScheduleActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView mTitle;
    private TextView mAddress;
    private Button mBtnMap;
    private Button mBtnHomepage;
    private ListView mListView;
    private ScheduleAdapter mAdapter;

    int mServicePosition = 0;

    private ArrayList<Lecture> mData = new ArrayList<Lecture>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_schedules);

        mServicePosition = getIntent().getIntExtra(Const.PARAM_POSITION, -1);

        initLayout();


        if(mServicePosition > -1) {
//            requestData(position);
            new RequestDataJob().execute(mServicePosition);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.id_schedule_btn_map:
                //https://www.google.co.kr/maps/search/동부여성문화센터
                String baseUrl = "https://www.google.co.kr/maps/search/";
                String keyword = Const.CLASS_NAME[mServicePosition].replace("교육강좌", "");
                if(mServicePosition == 0) {
                    keyword = "강남 여성인력 개발센터";
                }
                Intent map = new Intent(Intent.ACTION_VIEW);
                map.setData(Uri.parse(baseUrl + keyword));
                startActivity(map);
                break;
            case R.id.id_schedule_btn_homepage:
                String url = Const.URLS[mServicePosition];
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Const.PARAM_LECTURE, mAdapter.getItem(i));
        startActivity(intent);

    }

    private void initLayout() {
        mTitle = (TextView) findViewById(R.id.id_schedule_title);
        mAddress = (TextView) findViewById(R.id.id_schedule_address);
        mBtnMap = (Button) findViewById(R.id.id_schedule_btn_map);
        mBtnHomepage = (Button) findViewById(R.id.id_schedule_btn_homepage);
        mListView = (ListView) findViewById(R.id.id_schedule_list);

        mTitle.setText(Const.CLASS_NAME[mServicePosition]);
        mAddress.setText(Const.ADDRESSES[mServicePosition]);

        mBtnMap.setOnClickListener(this);
        mBtnHomepage.setOnClickListener(this);
        mListView.setOnItemClickListener(this);
    }

    public class RequestDataJob extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... values) {
            requestData(values[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private void parseJson(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            JSONObject service = obj.getJSONObject(Const.SERVICE_NAME[mServicePosition]);
            JSONObject result = service.getJSONObject("RESULT");
            String code = result.getString("CODE");

            if(!code.equals("INFO-000")) {
                Toast.makeText(this, "데이터를 가져오는 도중 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            JSONArray row = service.getJSONArray("row");
            mData.clear();
            if(row.length() > 0) {
                for(int i = 0 ; i < row.length() ; i++) {
                    JSONObject cls = (JSONObject)row.get(i);
                    Lecture l = new Lecture(
                            cls.getString(Const.RESP_CLASS_NAME),
                            cls.getString(Const.RESP_ORGAN_NAME),
                            cls.getString(Const.RESP_EDUCATION_FROM),
                            cls.getString(Const.RESP_EDUCATION_TO),
                            cls.getString(Const.RESP_EDUCATION_TIME_FROM),
                            cls.getString(Const.RESP_EDUCATION_TIME_TO),
                            cls.getString(Const.RESP_DIFFICULTY_NAME),
                            cls.getString(Const.RESP_RECEIVE_FROM),
                            cls.getString(Const.RESP_RECEIVE_TO),
                            cls.getString(Const.RESP_RECEIVE_TIME_FROM),
                            cls.getString(Const.RESP_RECEIVE_TIME_TO),
                            cls.getString(Const.RESP_EDUCATE_FEE),
                            cls.getString(Const.RESP_COLLECT_NUM),
                            cls.getString(Const.RESP_SPARE_NUM),
                            cls.getString(Const.RESP_VISIT_RECEIVE_FLAG),
                            cls.getString(Const.RESP_ONLINE_RECEIVE_FLAG),
                            cls.getString(Const.RESP_URL),
                            cls.getString(Const.RESP_MONDAY),
                            cls.getString(Const.RESP_TUESDAY),
                            cls.getString(Const.RESP_WEDNESDAY),
                            cls.getString(Const.RESP_THURSDAY),
                            cls.getString(Const.RESP_FRIDAY),
                            cls.getString(Const.RESP_SATURDAY),
                            cls.getString(Const.RESP_SUNDAY)
                    );
                    mData.add(l);
                    fillContent();
                }
            }
            else {

            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "데이터를 가져오는 도중 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void fillContent() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter = new ScheduleAdapter(ScheduleActivity.this, R.layout.item_schedule, mData);
                mListView.setAdapter(mAdapter);
            }
        });

    }


    private void requestData(int position) {
        try {
            HttpURLConnection conn = null;

            OutputStream os = null;
            InputStream is = null;
            ByteArrayOutputStream baos = null;


            URL url = new URL(Const.CLASS_REQ_URL[position]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(15 * 1000);
            conn.setReadTimeout(15 * 1000);
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);

            String response;

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();

                response = new String(byteData);

                parseJson(response);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public class ScheduleAdapter extends ArrayAdapter<Lecture> {

        ArrayList<Lecture> mItems = new ArrayList<Lecture>();

        public ScheduleAdapter(Context context, int resource, ArrayList<Lecture> mItems) {
            super(context, resource);
            this.mItems = mItems;
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Lecture getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if(v == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.item_schedule, null);
            }
            Lecture l = mItems.get(position);
            TextView className = (TextView) v.findViewById(R.id.id_item_title);
            TextView classOrgan = (TextView) v.findViewById(R.id.id_item_organ);
            TextView classLevel = (TextView) v.findViewById(R.id.id_item_level);
            TextView classFee = (TextView) v.findViewById(R.id.id_item_fee);
            TextView classStatus = (TextView) v.findViewById(R.id.id_item_status);

            className.setText(l.getTitle());
            classOrgan.setText(l.getOrgan());
            classLevel.setText(l.getLevel());
            int fee = Integer.parseInt(l.getFee().replace(".0", ""));
            if(fee == 0) {
                classFee.setText("무료");
            }
            else {
                String price = String.format("%,d%s", fee, "원");
                classFee.setText(price);
            }

            if(l.getStatus() == Const.STATUS_CLOSED) {
                classStatus.setVisibility(View.VISIBLE);
                classStatus.setText("종료됨");
                classStatus.setBackgroundResource(R.drawable.status_close);
            }
            else if(l.getStatus() == Const.STATUS_EDUCATION) {
                classStatus.setVisibility(View.VISIBLE);
                classStatus.setText("교육중");
                classStatus.setBackgroundResource(R.drawable.status_educate);
            }
            else if(l.getStatus() == Const.STATUS_RESERVATION) {
                classStatus.setVisibility(View.VISIBLE);
                classStatus.setText("예약중");
                classStatus.setBackgroundResource(R.drawable.status_reserve);
            }
            else {
                classStatus.setVisibility(View.INVISIBLE);
            }

            return v;
        }
    }

}
