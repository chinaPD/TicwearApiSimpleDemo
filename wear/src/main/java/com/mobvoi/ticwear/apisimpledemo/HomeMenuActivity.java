package com.mobvoi.ticwear.apisimpledemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;

public class HomeMenuActivity extends Activity implements WearableListView.ClickListener{

    private static final String TAG = "HomeMenu";

    private final String[] mElements = {"数据传输", "挠挠", "地理位置", "健康数据", "天气", "手势", "语音识别",
            "语音合成", "语义", "搜索", "快捷卡片", "传感器", "UI库Demo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

        WearableListView listView =
                (WearableListView) findViewById(R.id.wearable_list);

        // the List Adapter is defined below
        listView.setAdapter(new ListAdapter(this, mElements));

        listView.setClickListener(this);
    }

    @Override
    public void onClick(WearableListView.ViewHolder v){
        Integer tag = (Integer) v.itemView.getTag();
        switch (tag){
            case 0:{
                Intent startIntent = new Intent(this, DataTransferActivity.class);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startIntent);
                break;
            }
            case 1:{
                Intent startIntent = new Intent(this, NaonaoActivity.class);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startIntent);
                break;
            }
        }
    }

    @Override
    public void onTopEmptyRegionClick(){

    }
}
