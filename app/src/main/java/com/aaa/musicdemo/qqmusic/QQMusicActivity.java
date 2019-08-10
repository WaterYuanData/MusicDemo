package com.aaa.musicdemo.qqmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SeekBar;
import android.widget.Toast;

import com.aaa.musicdemo.App;
import com.aaa.musicdemo.R;
import com.aaa.musicdemo.databinding.ActivityQqmusicBinding;
import com.aaa.musicdemo.qqmusic.bean.QQMusic;

import java.text.SimpleDateFormat;

public class QQMusicActivity extends AppCompatActivity {
    private static final String TAG = "QQMusicActivity";
    private ActivityQqmusicBinding mBinding;
    private QQMusicDataViewModel mQQMusicDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_qqmusic);

        mQQMusicDataViewModel = ViewModelProviders.of(this).get(QQMusicDataViewModel.class);

        initViewModel();

        initBinding();

        mBinding.setViewModel(mQQMusicDataViewModel);
    }

    private void initBinding() {
        // 读取保存的值
//        String string = mQQMusicDataViewModel.getSongName().get();
        Log.i(TAG, "initBinding: 先于xml绑定");
        String string = mQQMusicDataViewModel.getSongName_().getValue(); // 均可以
        mBinding.etSearch.setText(string);// IndexOutOfBoundsException: setSpan (4 ... 4) ends beyond length 0
        mBinding.etSearch.setSelection(string.length()); // 缺少setText会导致索引越界异常的原因是，先执行了此处还没执行xml中的绑定的数据
        Log.i(TAG, "initBinding: 先于xml绑定");

        mBinding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mQQMusicDataViewModel.getDuration() != null && mQQMusicDataViewModel.getDuration().getValue() != null) {
                    int percent = (int) (progress * 100.0f / mQQMusicDataViewModel.getDuration().getValue());
                    if (seekBar.isPressed()) {
                        Log.i(TAG, "onProgressChanged: progress=" + progress
                                + " 百分比=" + percent + "%"
                                + " seekBar=" + seekBar
                                + " mBinding.seekBar=" + mBinding.seekBar); // 是同一个实例对象
                        mQQMusicDataViewModel.doSeek(progress);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mBinding.rvMusicInfo.setLayoutManager(new LinearLayoutManager(getApplication(), RecyclerView.VERTICAL, false));
        mBinding.rvMusicInfo.setAdapter(new MyRecyclerViewAdapter(mQQMusicDataViewModel.getMutableLiveData()));
    }

    private void initViewModel() {
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
//                    e.printStackTrace();
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
            int showFlag = 0;

            @Override
            public void onChanged(Integer integer) {
                Integer postion = mQQMusicDataViewModel.getCurrentPosition().getValue();
                Integer duration = mQQMusicDataViewModel.getDuration().getValue();
                if (duration == null) {
                    duration = 0;
                }

                if (postion > duration) {
                    // postion=226273493 duration=329305 onChanged: isPressed=false datePos=51:13 dateDur=05:29
                    postion = 0;
                }

                mBinding.seekBar.setProgress(postion);
                mBinding.seekBar.setSecondaryProgress(postion + 1000);
                mBinding.seekBar.setMax(duration);

                String datePos = new SimpleDateFormat("mm:ss").format(postion);
                String dateDur = new SimpleDateFormat("mm:ss").format(duration);
                if (showFlag < postion) {
                    showFlag += 10000;
                    Log.i(TAG, "onChanged: postion=" + postion
                            + " duration=" + duration
                            + " onChanged: isPressed=" + mBinding.seekBar.isPressed()
                            + " datePos=" + datePos
                            + " dateDur=" + dateDur + " " + this); // 该回调只有一个实例，共享 showFlag
                    if (duration - postion < 30000) {
                        showFlag = 0;
                        Log.i(TAG, "onChanged: 重置标志");
                    }
                }
                mBinding.tvPostion.setText(datePos);
                mBinding.tvDuration.setText(dateDur);
            }
        });

        mQQMusicDataViewModel.getMutableLiveData().observe(this, new Observer<QQMusic>() {
            @Override
            public void onChanged(QQMusic qqMusic) {
                mBinding.rvMusicInfo.getAdapter().notifyDataSetChanged();
            }
        });
    }

    public void doSearch(View view) {
        mQQMusicDataViewModel.searchQQMusicData(mBinding.etSearch.getText().toString().trim());

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
