package com.mobvoi.ticwear.apisimpledemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by pd on 16-4-20.
 */
public class DataTransferActivity extends FragmentActivity{

    private PageListOfFragment fragmentList = new PageListOfFragment();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_thansfer);

        ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        fragmentList.add("NodeAPI"     ,new Fragment())
                    .add("MessageAPI"  ,new Fragment())
                    .add("DataAPI"     ,new Fragment());
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),fragmentList));

    }

    private class PageListOfFragment{
        /**
         * 页面list titles 和 页面list，包含有：
         * NodeApi(CapabilityApi),MessageApi,DataItem,Asset WearableListenerServer
         * DataListener,ChannelApi
         **/
        private List<CharSequence> fragTitles = new ArrayList<CharSequence>();
        private List<Fragment> fragPages = new ArrayList<Fragment>();

        public PageListOfFragment(){

        }
        public PageListOfFragment(int titleResId, Fragment fragPage){
            this.fragTitles.add(getResources().getString(titleResId));
            this.fragPages.add(fragPage);
        }

        public PageListOfFragment add(int titleResId, Fragment fragPage){
            this.fragTitles.add(getResources().getString(titleResId));
            this.fragPages.add(fragPage);
            return this;
        }

        public PageListOfFragment add(CharSequence title, Fragment fragPage){
            this.fragTitles.add(title);
            this.fragPages.add(fragPage);
            return this;
        }

        public int size(){
            return fragPages.size();
        }
        public Fragment getFragment(int arg0){
            return fragPages.get(arg0);
        }
        public CharSequence getTitle(int arg0){
            return fragTitles.get(arg0);
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter{

        private PageListOfFragment fagmentList = null;

        public MyPagerAdapter(FragmentManager fm,PageListOfFragment fagmentList){
            super(fm);
            this.fagmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int arg0){
            return (fagmentList==null || fagmentList.size()==0) ?
                            null : fagmentList.getFragment(arg0);
        }

        @Override
        public CharSequence getPageTitle(int position){
            return (fagmentList==null || fagmentList.size()==0) ?
                            null : fagmentList.getTitle(position);
        }

        @Override
        public int getCount(){
            return (fagmentList==null || fagmentList.size()==0) ?
                            0 : fagmentList.size();
        }
    }
}
