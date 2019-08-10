package com.aaa.musicdemo.qqmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.aaa.musicdemo.App;
import com.aaa.musicdemo.R;
import com.aaa.musicdemo.databinding.ActivityQqmusicBinding;

public class QQMusicActivity extends AppCompatActivity {
    private static final String TAG = "QQMusicActivity";
    private ActivityQqmusicBinding mBinding;
    private QQMusicDataViewModel mQQMusicDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_qqmusic);

        mQQMusicDataViewModel = ViewModelProviders.of(this).get(QQMusicDataViewModel.class);

        // 建立Binding与ViewModel的联系
        mQQMusicDataViewModel.getIsLoaded().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loaded) {
                mBinding.btPlay.setBackgroundColor(loaded ? getColor(R.color.colorGreen) : getColor(R.color.colorRed)); // 测试通过
                mBinding.btPlay.setClickable(loaded);
//                mBinding.setViewModel(mQQMusicDataViewModel); // 测试visibility属性通过，测试background属性未通过
                String songmid = null;
                try {
                    songmid = mQQMusicDataViewModel.getMutableLiveData().getValue().getData().getSong().getList().get(0).getSongmid();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(App.getInstance(), !loaded ? "查询中" : "查询成功 " + songmid, Toast.LENGTH_SHORT).show();
            }
        });

        mQQMusicDataViewModel.getIsPrepared().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean prepared) {
                Toast.makeText(App.getInstance(), !prepared ? "加载中" : "加载成功 ", Toast.LENGTH_SHORT).show();
            }
        });

        mQQMusicDataViewModel.getCurrentPosition().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Integer postion = mQQMusicDataViewModel.getCurrentPosition().getValue();
                Integer duration = mQQMusicDataViewModel.getDuration().getValue();
//                Log.i(TAG, "onChanged: postion=" + postion + " duration=" + duration);
                mBinding.seekBar.setProgress(postion);
                mBinding.seekBar.setMax(duration);
            }
        });

        mBinding.setViewModel(mQQMusicDataViewModel);
    }

    public void doQuery(View view) {
        mQQMusicDataViewModel.queryQQMusicData(mBinding.etSearch.getText().toString().trim());

        // 隐藏软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void doPlay(View view) {
        mQQMusicDataViewModel.doPlay();
    }

    public void doStop(View view) {
        mQQMusicDataViewModel.doPause();
    }


}
