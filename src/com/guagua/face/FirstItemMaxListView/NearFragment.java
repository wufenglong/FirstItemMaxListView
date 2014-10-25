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
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
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
    private int mScrollState = -1;
    private int mScreenWidth = 0;
    private int mLastFirstVisiblePosition = 0;
    private int distanceOneItem;
    private int mLastDistanceOneItem = 1;
    private GestureDetector mGestureDetector = new GestureDetector(new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mListView.smoothScrollBy(Math.round(distanceY), 0);
            if (distanceY > 0) {

            } else {

            }
            if (mScrollState != AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                if (mListView.canScrollVertically(Math.round(distanceY))) {
                    distanceOneItem += Math.round(distanceY);
                } else {
                    distanceOneItem = 0;
                }
            }

            Log.d("wufl", "distanceOneItem=" + distanceOneItem + ",mLastDistanceOneItem=" + mLastDistanceOneItem);
            if (mListView.getFirstVisiblePosition() == mLastFirstVisiblePosition) {
                if ((distanceY < 0 && (mLastDistanceOneItem >= 0 && distanceOneItem < 0))
                        || (distanceY > 0 && (mLastDistanceOneItem <= 0 && distanceOneItem > 0))) {//从正变负，但是firstposition没变
//                    distanceOneItem = mLastDistanceOneItem;
                    Log.d("wufl", "distanceOneItem 从正变负，但是firstposition没变 return");
                    return false;
                } else {
                    mLastDistanceOneItem = distanceOneItem;
                }
                mLastFirstVisiblePosition = mListView.getFirstVisiblePosition();
            } else {
                mLastFirstVisiblePosition = mListView.getFirstVisiblePosition();
                distanceOneItem = 0;
                Log.d("wufl", "onFirstPostionChanged...");
                if (distanceY > 0) {
                    mLastDistanceOneItem = 1;
                } else {
                    mLastDistanceOneItem = -1;
                }
            }

            View item0 = mListView.getChildAt(0);
            View item1 = mListView.getChildAt(1);


            int changeHeight1;
            int change;
            int changeHeight;
            if (distanceOneItem == 0) return false;
            if (distanceOneItem > 0) {
                changeHeight1 = distanceOneItem * mScreenWidth / mItemHeight;//放大

                if (changeHeight1 > mScreenWidth) {
                    changeHeight1 = mScreenWidth;
                }
                if (changeHeight1 <= mItemHeight) {
                    changeHeight1 = mItemHeight;
                }
                change = changeHeight1 - item1.getHeight();
                changeHeight = item0.getHeight() - change;
                if (changeHeight > mScreenWidth) {
                    changeHeight = mScreenWidth;
                }
                if (changeHeight <= mItemHeight) {
                    changeHeight = mItemHeight;
                }

                item0.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, changeHeight));
                item1.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, changeHeight1));

                Log.d("wufl", "向上 distanceOneItem=" + distanceOneItem + ",changeHeight1=" + changeHeight1 + ",change=" + change + ",changeHeight=" + changeHeight);
            } else {
                changeHeight1 = (mItemHeight + distanceOneItem) * mScreenWidth / mItemHeight;//缩小
                if (changeHeight1 > mScreenWidth) {
                    changeHeight1 = mScreenWidth;
                }
                if (changeHeight1 <= mItemHeight) {
                    changeHeight1 = mItemHeight;
                }
                change = item1.getHeight() - changeHeight1;
                changeHeight = item0.getHeight() + change;//放大
                if (changeHeight > mScreenWidth) {
                    changeHeight = mScreenWidth;
                }
                if (changeHeight <= mItemHeight) {
                    changeHeight = mItemHeight;
                }
                item0.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, changeHeight));
                item1.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, changeHeight1));
                Log.d("wufl", "向下 distanceOneItem=" + distanceOneItem + ",changeHeight1=" + changeHeight1 + ",change=" + change + ",changeHeight=" + changeHeight);
            }

            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    });
    private boolean isFisrt = true;

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
        WindowManager wm = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);

        mScreenWidth = wm.getDefaultDisplay().getWidth();
        mItemHeight = getResources().getDimensionPixelSize(R.dimen.item_height);
        Log.d("wufl", "itemHeight=" + mItemHeight + ",mScreenWidth=" + mScreenWidth);
        mAdapter = new FirstItemMaxAdapter();
        mListView.setAdapter(mAdapter);
        /*mListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!mListView.scrollYIsComputed()) {
                    mListView.computeScrollY();
                }
            }
        });*/
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                mScrollState = scrollState;

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


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
            Item item20 = new Item("张十一", R.drawable.image4, null);
            Item item21 = new Item("李十二", R.drawable.image5, null);
            Item item22 = new Item("张十三", R.drawable.image6, null);
            Item item23 = new Item("李十四", R.drawable.image7, null);
            Item item24 = new Item("张十五", R.drawable.image1, null);
            Item item25 = new Item("李十六", R.drawable.image2, null);
            Item item26 = new Item("张十七", R.drawable.image3, null);
            Item item27 = new Item("李十八", R.drawable.image4, null);
            Item item28 = new Item("张十九", R.drawable.image5, null);
            Item item29 = new Item("李二十", R.drawable.image6, null);
            Item item30 = new Item("张十一", R.drawable.image4, null);
            Item item31 = new Item("李十二", R.drawable.image5, null);
            Item item32 = new Item("张十三", R.drawable.image6, null);
            Item item33 = new Item("李十四", R.drawable.image7, null);
            Item item34 = new Item("张十五", R.drawable.image1, null);
            Item item35 = new Item("李十六", R.drawable.image2, null);
            Item item36 = new Item("张十七", R.drawable.image3, null);
            Item item37 = new Item("李十八", R.drawable.image4, null);
            Item item38 = new Item("张十九", R.drawable.image5, null);
            Item item39 = new Item("李二十", R.drawable.image6, null);
            Item item40 = new Item("张十一", R.drawable.image4, null);
            Item item41 = new Item("李十二", R.drawable.image5, null);
            Item item42 = new Item("张十三", R.drawable.image6, null);
            Item item43 = new Item("李十四", R.drawable.image7, null);
            Item item44 = new Item("张十五", R.drawable.image1, null);
            Item item45 = new Item("李十六", R.drawable.image2, null);
            Item item46 = new Item("张十七", R.drawable.image3, null);
            Item item47 = new Item("李十八", R.drawable.image4, null);
            Item item48 = new Item("张十九", R.drawable.image5, null);
            Item item49 = new Item("李二十", R.drawable.image6, null);
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
            mDataSources.add(item20);
            mDataSources.add(item21);
            mDataSources.add(item22);
            mDataSources.add(item23);
            mDataSources.add(item24);
            mDataSources.add(item25);
            mDataSources.add(item26);
            mDataSources.add(item27);
            mDataSources.add(item28);
            mDataSources.add(item29);
            mDataSources.add(item30);
            mDataSources.add(item31);
            mDataSources.add(item32);
            mDataSources.add(item33);
            mDataSources.add(item34);
            mDataSources.add(item35);
            mDataSources.add(item36);
            mDataSources.add(item37);
            mDataSources.add(item38);
            mDataSources.add(item39);
            mDataSources.add(item40);
            mDataSources.add(item41);
            mDataSources.add(item42);
            mDataSources.add(item43);
            mDataSources.add(item44);
            mDataSources.add(item45);
            mDataSources.add(item46);
            mDataSources.add(item47);
            mDataSources.add(item48);
            mDataSources.add(item49);
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
            view = LayoutInflater.from(getActivity()).inflate(R.layout.first_item_max_item, null);
            viewHolder = new ViewHolder();
            if (position == 0 && isFisrt) {
                isFisrt = false;
                view.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, mScreenWidth));
            } else {
                view.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, mItemHeight));
            }
            viewHolder.cover = (ImageView) view.findViewById(R.id.cover);

            viewHolder.cover.setScaleType(ImageView.ScaleType.CENTER_CROP);
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