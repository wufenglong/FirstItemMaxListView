/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.guagua.face.FirstItemMaxListView;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NearFragment extends Fragment {
    private FirstItemMaxListView mListView;
    private Context mContext;
    private FirstItemMaxAdapter mAdapter;
    private int mItemHeight;//标准item高
    private int mScrollY;//Y方向滚动高
    private int mLastScrollY;//记忆上次滚动高

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.near_fragment_layout, null);
        mListView = (FirstItemMaxListView) view.findViewById(R.id.firstItemMaxListView);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mItemHeight = getResources().getDimensionPixelSize(R.dimen.item_height);

        mAdapter = new FirstItemMaxAdapter();
        mListView.setAdapter(mAdapter);
        mListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mListView.computeScrollY();
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mScrollY = 0;

                if (mListView.scrollYIsComputed()) {
                    mScrollY = mListView.getComputedScrollY();
                }
                if (mListView.scrollYIsComputed()) {
                    View item0 = mListView.getChildAt(0);
                    View item1 = mListView.getChildAt(1);
                    int changeHeight = item0.getHeight() - (mScrollY - mLastScrollY) - 2;
                    if (changeHeight >= mItemHeight * 2) {
                        changeHeight = mItemHeight * 2;
                    }
                    if (changeHeight <= mItemHeight) {
                        changeHeight = mItemHeight;
                    }
                    int changeHeight1 = (mScrollY - mLastScrollY) + item1.getHeight();
                    if (changeHeight1 >= mItemHeight * 2) {
                        changeHeight1 = mItemHeight * 2;
                    }
                    if (changeHeight1 <= mItemHeight) {
                        changeHeight1 = mItemHeight;
                    }
                    item0.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, changeHeight));
                    item1.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, changeHeight1));
                    mLastScrollY = mScrollY;
                }
            }
        });
    }

    class FirstItemMaxAdapter extends BaseAdapter {
        private ArrayList<Item> mDataSources;

        public FirstItemMaxAdapter() {
            mDataSources = new ArrayList<Item>();
            Item item0 = new Item("张一", R.drawable.image1, null);
            Item item1 = new Item("李二", R.drawable.image2, null);
            Item item2 = new Item("张三", R.drawable.image3, null);
            Item item3 = new Item("李四", R.drawable.image4, null);
            Item item4 = new Item("张五", R.drawable.image5, null);
            Item item5 = new Item("李六", R.drawable.image6, null);
            Item item6 = new Item("张七", R.drawable.image7, null);
            Item item7 = new Item("李八", R.drawable.image1, null);
            Item item8 = new Item("张九", R.drawable.image2, null);
            Item item9 = new Item("李十", R.drawable.image3, null);
            Item item10 = new Item("张十一", R.drawable.image4, null);
            Item item11 = new Item("李十二", R.drawable.image5, null);
            Item item12 = new Item("张十三", R.drawable.image6, null);
            Item item13 = new Item("李十四", R.drawable.image7, null);
            Item item14 = new Item("张十五", R.drawable.image1, null);
            Item item15 = new Item("李十六", R.drawable.image2, null);
            Item item16 = new Item("张十七", R.drawable.image3, null);
            Item item17 = new Item("李十八", R.drawable.image4, null);
            Item item18 = new Item("张十九", R.drawable.image5, null);
            Item item19 = new Item("李二十", R.drawable.image6, null);
            mDataSources.add(item0);
            mDataSources.add(item1);
            mDataSources.add(item2);
            mDataSources.add(item3);
            mDataSources.add(item4);
            mDataSources.add(item5);
            mDataSources.add(item6);
            mDataSources.add(item7);
            mDataSources.add(item8);
            mDataSources.add(item9);
            mDataSources.add(item10);
            mDataSources.add(item11);
            mDataSources.add(item12);
            mDataSources.add(item13);
            mDataSources.add(item14);
            mDataSources.add(item15);
            mDataSources.add(item16);
            mDataSources.add(item17);
            mDataSources.add(item18);
            mDataSources.add(item19);
        }

        @Override
        public int getCount() {
            return mDataSources.size();
        }

        @Override
        public Object getItem(int i) {
            return mDataSources.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.first_item_max_item, null);
                viewHolder = new ViewHolder();
                viewHolder.cover = (ImageView) view.findViewById(R.id.cover);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.cover.setImageResource(mDataSources.get(position).imgId);
            return view;
        }

        class ViewHolder {
            TextView name;
            ImageView cover;
        }
    }

    class Item {
        String name;
        int imgId;
        String videoUrl;

        Item(String name, int imgId, String videoUrl) {
            this.name = name;
            this.imgId = imgId;
            this.videoUrl = videoUrl;
        }
    }
}