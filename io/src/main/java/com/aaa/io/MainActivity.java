package com.aaa.io;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;

import com.aaa.io.bean.RootBean;
import com.aaa.io.utils.VkeyUtil;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        long currentTimeMillis = System.currentTimeMillis();
        VkeyUtil.get().registerRootBeanChangeListener(new Observer<RootBean>() {
            @Override
            public void onChanged(RootBean rootBean) {
                Log.i(TAG, "onChanged: " + rootBean);
                String vkey = rootBean.getData().getItems().get(0).getVkey();
                Log.i(TAG, "onChanged: " + vkey);
//                2BF859C2084A2075A989BB0B76219C09F80EA8F3BC16E758D041662D7FD7DD1CAB505907E8B5708971949BDD0FE3DE38F74A6AD09A81814F
            }
        });
        Log.i(TAG, "onCreate: " + (System.currentTimeMillis() - currentTimeMillis));
    }
}
