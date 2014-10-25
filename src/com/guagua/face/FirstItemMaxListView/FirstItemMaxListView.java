/*
 * Copyright 2014 Lars wufenglong
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
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * 通过手势滑动控制Listview的滚动
 * write by wufenglong.
 */
public class FirstItemMaxListView extends ListView {
    private int ITEM_HEIGHT;//标准item高,
    private int mITEM_MAX_HEIGHT = 0;
    private int mLastFirstVisiblePosition = 0;
    private int distanceOneItem;//记录滚动距离，向上滚动时-ITEM_HEIGHT到0，向下滚动是0到ITEM_HEIGHT,当listview FirstVisiblePosition 设置为0
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
            smoothScrollBy(Math.round(distanceY), 0);

            if (canScrollVertically(Math.round(distanceY))) {
                distanceOneItem += Math.round(distanceY);
            } else {
                distanceOneItem = 0;
                if (distanceY > 0) {
                    mLastDistanceOneItem = -1;
                } else {
                    mLastDistanceOneItem = 1;
                }
            }

            if (getFirstVisiblePosition() == mLastFirstVisiblePosition) {
                if ((distanceY < 0 && (mLastDistanceOneItem >= 0 && distanceOneItem < 0))
                        || (distanceY > 0 && (mLastDistanceOneItem < 0 && distanceOneItem >= 0))) {//从正变负或从负变正，但是firstposition没变
                    return false;
                } else {
                    mLastDistanceOneItem = distanceOneItem;
                }
                mLastFirstVisiblePosition = getFirstVisiblePosition();
            } else {
                mLastFirstVisiblePosition = getFirstVisiblePosition();
                distanceOneItem = 0;
                if (distanceY > 0) {
                    mLastDistanceOneItem = 1;
                } else {
                    mLastDistanceOneItem = -1;
                }
            }
            changeItemHeightOnScroll();
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

    public FirstItemMaxListView(Context context) {
        super(context);
        init();
    }

    public FirstItemMaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }

    private void changeItemHeightOnScroll() {
        View item0 = getChildAt(0);
        View item1 = getChildAt(1);

        int changeHeight1;
        int change;
        int changeHeight;
        if (distanceOneItem == 0) return;
        if (distanceOneItem > 0) {
            changeHeight1 = distanceOneItem * mITEM_MAX_HEIGHT / ITEM_HEIGHT;//放大

            if (changeHeight1 > mITEM_MAX_HEIGHT) {
                changeHeight1 = mITEM_MAX_HEIGHT;
            }
            if (changeHeight1 <= ITEM_HEIGHT) {
                changeHeight1 = ITEM_HEIGHT;
            }
            change = changeHeight1 - item1.getHeight();
            changeHeight = item0.getHeight() - change;
            if (changeHeight > mITEM_MAX_HEIGHT) {
                changeHeight = mITEM_MAX_HEIGHT;
            }
            if (changeHeight <= ITEM_HEIGHT) {
                changeHeight = ITEM_HEIGHT;
            }
            item0.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, changeHeight));
            item1.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, changeHeight1));
        } else {
            changeHeight1 = (ITEM_HEIGHT + distanceOneItem) * mITEM_MAX_HEIGHT / ITEM_HEIGHT;//缩小
            if (changeHeight1 > mITEM_MAX_HEIGHT) {
                changeHeight1 = mITEM_MAX_HEIGHT;
            }
            if (changeHeight1 <= ITEM_HEIGHT) {
                changeHeight1 = ITEM_HEIGHT;
            }
            change = item1.getHeight() - changeHeight1;
            changeHeight = item0.getHeight() + change;//放大
            if (changeHeight > mITEM_MAX_HEIGHT) {
                changeHeight = mITEM_MAX_HEIGHT;
            }
            if (changeHeight <= ITEM_HEIGHT) {
                changeHeight = ITEM_HEIGHT;
            }
            item0.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, changeHeight));
            item1.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, changeHeight1));
        }
    }

    public int getItemHeight() {
        return ITEM_HEIGHT;
    }

    public void setItemHeight(int itemHeight) {
        this.ITEM_HEIGHT = itemHeight;
    }

    public int getItemMaxHeight() {
        return mITEM_MAX_HEIGHT;
    }

    public void setItemMaxHeight(int itemMaxHeight) {
        this.mITEM_MAX_HEIGHT = itemMaxHeight;
    }

    /**
     * Check if this view can be scrolled vertically in a certain direction.
     *
     * @param direction Negative to check scrolling up, positive to check scrolling down.
     * @return true if this view can be scrolled in the specified direction, false otherwise.
     */
    public boolean canScrollVertically(int direction) {
        final int offset = computeVerticalScrollOffset();
        final int range = computeVerticalScrollRange() - computeVerticalScrollExtent();
        if (range == 0) return false;
        if (direction < 0) {
            return offset > 0;
        } else {
            return offset < range - 1;
        }
    }
}