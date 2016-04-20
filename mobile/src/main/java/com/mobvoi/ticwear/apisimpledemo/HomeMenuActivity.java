package com.mobvoi.ticwear.apisimpledemo;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeMenuActivity extends ListActivity{

    private static ListOfItem[] mItemList = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

        mItemList = new ListOfItem[]{
                new ListOfItem(R.string.dataTransfer,DataTransferActivity.class),
                new ListOfItem(R.string.notify2Wear,NotificationActivity.class),
        };

        setListAdapter(new ArrayAdapter<ListOfItem>(this,
                R.layout.support_simple_spinner_dropdown_item,
                mItemList));
    }

    @Override
    protected void onListItemClick(ListView listView, View view,int position,long id){
        startActivity(new Intent(this, mItemList[position].activityClass));
    }

    private class ListOfItem {
        private CharSequence title = null;
        private Class<? extends Activity> activityClass = null;

        public ListOfItem(int titleResId, Class<? extends Activity> activityClass) {
            this.activityClass = activityClass;
            this.title = getResources().getString(titleResId);
        }

        @Override
        public String toString() {
            return title.toString();
        }
    }
}