package com.aaa.musicdemo.qqmusic;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.aaa.musicdemo.R;
import com.aaa.musicdemo.databinding.MusicItemBinding;
import com.aaa.musicdemo.qqmusic.bean.QQMusic;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewHolder> {
    private static final String TAG = "MyRecyclerViewAdapter";
    private QQMusicDataViewModel mViewModel;
    private MutableLiveData<QQMusic> mData;
    private MutableLiveData<Integer> mSelectedPosition;

    public MyRecyclerViewAdapter(QQMusicDataViewModel qqMusicDataViewModel) {
        mViewModel = qqMusicDataViewModel;
        mData = qqMusicDataViewModel.getMutableLiveData();
        mSelectedPosition = qqMusicDataViewModel.getSelectedPosition();
        Log.i(TAG, "MyRecyclerViewAdapter: " + mData);
        if (mData != null && mData.getValue() != null) {
        } else {
            // TODO: 2019/8/10 缓存
        }
    }

    public MutableLiveData<QQMusic> getData() {
        return mData;
    }

    public void setData(MutableLiveData<QQMusic> data) {
        mData = data;
    }

    public MutableLiveData<Integer> getSelectedPosition() {
        return mSelectedPosition;
    }

    public void setSelectedPosition(MutableLiveData<Integer> selectedPosition) {
        mSelectedPosition = selectedPosition;
    }

    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");
        // TODO: 2019/8/11 如何复用 
        MusicItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.music_item, parent, false);
        MusicItemBinding binding2 = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.music_item, parent, false);
        Log.i(TAG, "onCreateViewHolder: binding" + binding + " binding2=" + binding2);
        binding.setViewModel(mViewModel);
        return new MyRecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyRecyclerViewHolder holder, final int position) {
        try {
            // 参考 https://blog.csdn.net/hhzzcc_/article/details/78810132
            int albumId = mData.getValue().getData().getSong().getList().get(position).getAlbumid();
            String url = "http://imgcache.qq.com/music/photo/album_300/" + (albumId % 100) + "/300_albumpic_" + albumId + "_0.jpg";
            MyBindingAdapter.loadImg(holder.mBinding.ivSongImg, url);
            holder.mBinding.tvSong.setText(mData.getValue().getData().getSong().getList().get(position).getSongname());
            // 只显示每首歌的第一作者
            holder.mBinding.tvSinger.setText(mData.getValue().getData().getSong().getList().get(position).getSinger().get(0).getName());
            holder.mBinding.tvAlbumName.setText(mData.getValue().getData().getSong().getList().get(position).getAlbumname());

            // 功能：只有一个item处于选中状态
            Log.i(TAG, "onBindViewHolder: position=" + position);
            holder.mBinding.clItemLayout.setSelected(mSelectedPosition.getValue() == position);
            holder.mBinding.clItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSelectedPosition.getValue() != position) {
                        notifyItemChanged(mSelectedPosition.getValue()); // 取消上一个选中的
                        mSelectedPosition.setValue(position);
                        notifyItemChanged(mSelectedPosition.getValue()); // 选中当前选中的

                        // 点击item就播放
                        mViewModel.doPlay();
                    }
                }
            });

            // 跑马灯
            holder.mBinding.tvSong.setSelected(true);
            holder.mBinding.tvSinger.setSelected(true);
            holder.mBinding.tvAlbumName.setSelected(true);

        } catch (Exception e) {
            Log.i(TAG, "onBindViewHolder: position=" + position);
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        try {
            if (mData.getValue() != null) {
                count = mData.getValue().getData().getSong().getList().size();
                // TODO: 2019/8/10
//                Log.i(TAG, "MyRecyclerViewAdapter: " + mData.getValue().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "getItemCount: " + count); // todo 为啥会调用多次
//        Log.i(TAG, "getItemCount: " + Log.getStackTraceString(new Throwable()));
        return count;
    }

    class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

        private MusicItemBinding mBinding;

        public MyRecyclerViewHolder(@NonNull MusicItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
