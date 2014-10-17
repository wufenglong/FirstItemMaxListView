package com.guagua.face.FirstItemMaxListView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by wufl on 14-10-17.
 */
public class MainActivity extends Activity {
    private FirstItemMaxListView mListView;
    private Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mListView = (FirstItemMaxListView) findViewById(R.id.firstItemMaxListView);

    }

    class FirstItemMaxAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
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
            if (view == null) {
                //view = LayoutInflater.from(mContext).inflate()
            }
            return null;
        }
    }
}