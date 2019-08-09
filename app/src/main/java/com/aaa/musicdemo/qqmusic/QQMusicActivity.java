package com.aaa.musicdemo.qqmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
//                mBinding.setViewModel(mQQMusicDataViewModel); // 测试visibility属性通过，测试background属性未通过

                try {
                    String songmid = mQQMusicDataViewModel.getMutableLiveData().getValue().getData().getSong().getList().get(0).getSongmid();
                    Toast.makeText(App.getInstance(), "" + songmid, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
            }
        });

        mBinding.setViewModel(mQQMusicDataViewModel);
    }

    public void doQuery(View view) {
        mQQMusicDataViewModel.queryQQMusicData(mBinding.etSearch.getText().toString().trim());
        // TODO: 2019/8/9 隐藏按键盘 
    }

    public void doPlay(View view) {
        mQQMusicDataViewModel.doPlay();
    }

    public void doStop(View view) {
        mQQMusicDataViewModel.doPause();
    }


}
