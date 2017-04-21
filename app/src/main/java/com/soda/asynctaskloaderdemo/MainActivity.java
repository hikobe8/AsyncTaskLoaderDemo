package com.soda.asynctaskloaderdemo;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LoaderManager.LoaderCallbacks<List<Bread>> mCallbacks;
    //面包房
    private Bakery mBakery;
    //面包师
    private Baker mBaker;
    //面包需求量
    private int mNeededBreads;
    //唯一标识
    private final int mLoaderId = 42;

    private Baker.BakeryCallback mBreadCallback = new Baker.BakeryCallback() {
        @Override
        public int getNeededBreads() {
            return mNeededBreads;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNeededBreads = 0;
        mBaker = new Baker(this, mBreadCallback);
        mBakery = new Bakery(mBaker);
        mCallbacks = new LoaderManager.LoaderCallbacks<List<Bread>>() {

            @Override
            public Loader<List<Bread>> onCreateLoader(int id, Bundle args) {
                if (mBaker == null) {
                    mBaker = new Baker(MainActivity.this, mBreadCallback);
                }
                return mBaker;
            }

            @Override
            public void onLoadFinished(Loader<List<Bread>> loader, List<Bread> data) {
                mNeededBreads = 0 ;
                //面包师完成面包烤制
                Log.d("scott", "sell " + data.size() + " breads") ;
            }

            @Override
            public void onLoaderReset(Loader<List<Bread>> loader) {

            }
        };
        //面包师开始工作
        getLoaderManager().restartLoader(mLoaderId, null, mCallbacks);
        //顾客开始上门
        mockCustomer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBakery);
    }

    //模拟源源不断的顾客需求
    private void mockCustomer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(3000);
                        Random random = new Random();
                        mNeededBreads =random.nextInt(10);
                        Intent intent = new Intent(Bakery.CUSTOMER_ACTION);
                        sendBroadcast(intent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
