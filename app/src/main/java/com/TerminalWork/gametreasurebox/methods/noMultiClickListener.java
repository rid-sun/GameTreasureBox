package com.TerminalWork.gametreasurebox.methods;

import android.util.SparseArray;
import android.view.View;

public abstract class noMultiClickListener implements View.OnClickListener {

    // 两次点击按钮之间的最小点击间隔时间(单位:ms)
    private static final int MIN_CLICK_DELAY_TIME = 3000;
    // 记录所有绑定该监听器View的最后一次点击时间
    private SparseArray<Long> lastClickViewArray = new SparseArray<>();

    @Override
    public void onClick(View v) {// 限制多次点击
        long currentTime = System.currentTimeMillis();
        long lastClickTime = lastClickViewArray.get(v.getId(), -1L);// 获取该view最后一次的点击时间,默认为-1

        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {// 两次点击的时间间隔大于最小限制时间，则触发点击事件
            lastClickViewArray.put(v.getId(), currentTime);
            onNoMultiClick(v);
        }
    }

    /**
     * 点击事件(相当于@link{android.view.View.OnClickListener})
     *
     * @param v 使用该限制点击的View
     */
    public abstract void onNoMultiClick(View v);

}