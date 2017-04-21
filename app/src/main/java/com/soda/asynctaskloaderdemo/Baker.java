package com.soda.asynctaskloaderdemo;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: yr
 * 17-4-21 下午5:44.
 */

public class Baker extends AsyncTaskLoader<List<Bread>> {
    // 用于查询当前需要多少个面包
    BakeryCallback mCallback;

    //面包房回调，用于获得当面面包需求量
    interface BakeryCallback {
        int getNeededBreads();
    }

    public Baker(Context context, BakeryCallback callback) {
        super(context);
        mCallback = callback;
    }

    @Override
    public List<Bread> loadInBackground() {
        List<Bread> breads = new ArrayList<Bread>();
        //获得当前需要做的面包
        int needs = mCallback.getNeededBreads();
        for (int i = 0; i < needs; i++) {
            //制作面包，耗时操作
            breads.add(new Bread());
        }
        //面包制作完成
        return breads;
    }

    @Override
    public void deliverResult(List<Bread> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
    }
}
