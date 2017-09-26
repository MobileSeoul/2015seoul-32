package com.menew.seoul_women_lecture;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

/**
 * Created by 하람하랑 on 2015-10-29.
 */
public class DetailActivity extends Activity implements View.OnClickListener {

    TextView mTitle, mOrgan, mLevel, mReceive, mEducate, mDay, mCollect, mSpare, mFee, mMethod;
    Button mBtnDetail, mBtnSchedule,mBtnShare;
    Lecture mLecture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mLecture = (Lecture) getIntent().getSerializableExtra(Const.PARAM_LECTURE);

        initLayout();
        initValue();
    }

    private void initLayout() {
        mTitle = (TextView) findViewById(R.id.id_detail_title);
        mOrgan = (TextView) findViewById(R.id.id_detail_organ);
        mLevel = (TextView) findViewById(R.id.id_detail_level);
        mReceive = (TextView) findViewById(R.id.id_detail_receive);
        mEducate = (TextView) findViewById(R.id.id_detail_educate);
        mDay = (TextView) findViewById(R.id.id_detail_day);
        mCollect = (TextView) findViewById(R.id.id_detail_collect);
        mSpare = (TextView) findViewById(R.id.id_detail_spare);
        mFee = (TextView) findViewById(R.id.id_detail_fee);
        mMethod = (TextView) findViewById(R.id.id_detail_method);

        mBtnDetail = (Button) findViewById(R.id.id_detail_btn_desc);
        mBtnSchedule = (Button) findViewById(R.id.id_detail_btn_calendar);
        mBtnShare = (Button) findViewById(R.id.id_detail_btn_share);

        mBtnDetail.setOnClickListener(this);
        mBtnSchedule.setOnClickListener(this);
        mBtnShare.setOnClickListener(this);
    }

    private void initValue() {
        mTitle.setText(mLecture.getTitle());
        mOrgan.setText(mLecture.getOrgan());
        mCollect.setText(mLecture.getCollectNum().replace(".0", "") + "명");
        mSpare.setText(mLecture.getSpareNum().replace(".0", "") + "명");

        int fee = Integer.parseInt(mLecture.getFee().replace(".0", ""));
        if(fee == 0) {
            mFee.setText("무료");
        }
        else {
            String price = String.format("%,d%s", fee, "원");
            mFee.setText(price);
        }

        if(mLecture.getVisitReceiveFlag().equals("Y") && mLecture.getOnlineREceiveFlag().equals("Y")) {
            mMethod.setText("방문접수 / 온라인접수");
        }
        else if(mLecture.getVisitReceiveFlag().equals("Y")) {
            mMethod.setText("방문접수");
        }
        else if(mLecture.getOnlineREceiveFlag().equals("Y")) {
            mMethod.setText("온라인접수");
        }
        else {
            mMethod.setText("");
        }

        String day = mLecture.getMonday().equals("Y") ? " 월" : "";
        day += mLecture.getTuesday().equals("Y") ? " 화" : "";
        day += mLecture.getWednesday().equals("Y") ? " 수" : "";
        day += mLecture.getThursday().equals("Y") ? " 목" : "";
        day += mLecture.getFriday().equals("Y") ? " 금" : "";
        day += mLecture.getSaturday().equals("Y") ? " 토" : "";
        day += mLecture.getSunday().equals("Y") ? " 일" : "";

        mDay.setText(day.trim());

        Calendar receiveStart = Calendar.getInstance();
        receiveStart.setTimeInMillis(mLecture.getReceiveStartDateTime());

        Calendar receiveEnd = Calendar.getInstance();
        receiveEnd.setTimeInMillis(mLecture.getReceiveEndDateTime());

        mReceive.setText(String.format("%d.%d.%d %02d:%02d\n ~ %d.%d.%d %02d:%02d",
                receiveStart.get(Calendar.YEAR),
                receiveStart.get(Calendar.MONTH) + 1,
                receiveStart.get(Calendar.DATE),
                receiveStart.get(Calendar.HOUR_OF_DAY),
                receiveStart.get(Calendar.MINUTE),
                receiveEnd.get(Calendar.YEAR),
                receiveEnd.get(Calendar.MONTH) + 1,
                receiveEnd.get(Calendar.DATE),
                receiveEnd.get(Calendar.HOUR_OF_DAY),
                receiveEnd.get(Calendar.MINUTE)));

        Calendar educateStart = Calendar.getInstance();
        educateStart.setTimeInMillis(mLecture.getEducateStartDateTime());

        Calendar educateEnd = Calendar.getInstance();
        educateEnd.setTimeInMillis(mLecture.getEducateEndDateTime());

        mEducate.setText(String.format("%d.%d.%d %02d:%02d\n ~ %d.%d.%d %02d:%02d",
                educateStart.get(Calendar.YEAR),
                educateStart.get(Calendar.MONTH) + 1,
                educateStart.get(Calendar.DATE),
                educateStart.get(Calendar.HOUR_OF_DAY),
                educateStart.get(Calendar.MINUTE),
                educateEnd.get(Calendar.YEAR),
                educateEnd.get(Calendar.MONTH) + 1,
                educateEnd.get(Calendar.DATE),
                educateEnd.get(Calendar.HOUR_OF_DAY),
                educateEnd.get(Calendar.MINUTE)));

        mLevel.setText(mLecture.getLevel());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_detail_btn_desc:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(mLecture.getUrl()));
                startActivity(i);
                break;
            case R.id.id_detail_btn_calendar:
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", mLecture.getEducateStartDateTime());
                intent.putExtra("allDay", false);
                intent.putExtra("endTime", mLecture.getEducateEndDateTime());
                intent.putExtra("title", mLecture.getTitle());
                startActivity(intent);
                break;
            case R.id.id_detail_btn_share:
                int fee = Integer.parseInt(mLecture.getFee().replace(".0", ""));
                String strFee;
                if(fee == 0) {
                    strFee = "무료";
                }
                else {
                    strFee = String.format("%,d%s", fee, "원");
                }

                Calendar receiveStart = Calendar.getInstance();
                receiveStart.setTimeInMillis(mLecture.getReceiveStartDateTime());

                Calendar receiveEnd = Calendar.getInstance();
                receiveEnd.setTimeInMillis(mLecture.getReceiveEndDateTime());

                String strReceive = String.format("%d.%d.%d %02d:%02d ~ %d.%d.%d %02d:%02d",
                        receiveStart.get(Calendar.YEAR),
                        receiveStart.get(Calendar.MONTH) + 1,
                        receiveStart.get(Calendar.DATE),
                        receiveStart.get(Calendar.HOUR_OF_DAY),
                        receiveStart.get(Calendar.MINUTE),
                        receiveEnd.get(Calendar.YEAR),
                        receiveEnd.get(Calendar.MONTH) + 1,
                        receiveEnd.get(Calendar.DATE),
                        receiveEnd.get(Calendar.HOUR_OF_DAY),
                        receiveEnd.get(Calendar.MINUTE));

                Calendar educateStart = Calendar.getInstance();
                educateStart.setTimeInMillis(mLecture.getEducateStartDateTime());

                Calendar educateEnd = Calendar.getInstance();
                educateEnd.setTimeInMillis(mLecture.getEducateEndDateTime());

                String strEducate = String.format("%d.%d.%d %02d:%02d ~ %d.%d.%d %02d:%02d",
                        educateStart.get(Calendar.YEAR),
                        educateStart.get(Calendar.MONTH) + 1,
                        educateStart.get(Calendar.DATE),
                        educateStart.get(Calendar.HOUR_OF_DAY),
                        educateStart.get(Calendar.MINUTE),
                        educateEnd.get(Calendar.YEAR),
                        educateEnd.get(Calendar.MONTH) + 1,
                        educateEnd.get(Calendar.DATE),
                        educateEnd.get(Calendar.HOUR_OF_DAY),
                        educateEnd.get(Calendar.MINUTE));

                String strDay = mLecture.getMonday().equals("Y") ? " 월" : "";
                strDay += mLecture.getTuesday().equals("Y") ? " 화" : "";
                strDay += mLecture.getWednesday().equals("Y") ? " 수" : "";
                strDay += mLecture.getThursday().equals("Y") ? " 목" : "";
                strDay += mLecture.getFriday().equals("Y") ? " 금" : "";
                strDay += mLecture.getSaturday().equals("Y") ? " 토" : "";
                strDay += mLecture.getSunday().equals("Y") ? " 일" : "";

                String strShare = mLecture.getTitle() + "(" + mLecture.getOrgan() + ")\n";
                strShare += "난이도 : " + mLecture.getLevel() + ", 수강료 : " + strFee + "\n";
                strShare += "수강신청 : " + strReceive + "\n";
                strShare += "교육시간 : " + strEducate + "(" + strDay.trim() + ")\n";
                strShare += "상세정보 : " + mLecture.getUrl();

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, strShare);
                startActivity(Intent.createChooser(sharingIntent, "공유"));

                break;
        }
    }
}
