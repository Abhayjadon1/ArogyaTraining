package com.inventics.ekalarogya.training.helper;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerClickListener implements RecyclerView.OnItemTouchListener {
    private GestureDetector mGestureDetector;
    private OnItemClickListener mListener;

    class RecyclerGestureListener extends SimpleOnGestureListener {
        RecyclerGestureListener() {
        }

        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    public RecyclerClickListener(Context context, OnItemClickListener listener) {
        this.mListener = listener;
        this.mGestureDetector = new GestureDetector(context, new RecyclerGestureListener());
    }

    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView == null || this.mListener == null || !this.mGestureDetector.onTouchEvent(e)) {
            return false;
        }
        this.mListener.onItemClick(childView, view.getChildPosition(childView));
        return true;
    }

    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}
