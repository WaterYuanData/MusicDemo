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
    private MutableLiveData<QQMusic> mData;

    public MyRecyclerViewAdapter(MutableLiveData<QQMusic> data) {
        mData = data;
        Log.i(TAG, "MyRecyclerViewAdapter: " + mData);
        if (mData != null && mData.getValue() != null) {
        } else {
            // TODO: 2019/8/10 缓存
        }
    }

    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MusicItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.music_item, parent, false);
        return new MyRecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position) {
        try {
            holder.mBinding.tvSong.setText(mData.getValue().getData().getSong().getList().get(position).getSongname());
            // 只显示每首歌的第一作者
            holder.mBinding.tvSinger.setText(mData.getValue().getData().getSong().getList().get(position).getSinger().get(0).getName());
            holder.mBinding.tvAlbumName.setText(mData.getValue().getData().getSong().getList().get(position).getAlbumname());
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
                Log.i(TAG, "MyRecyclerViewAdapter: " + mData.getValue().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "getItemCount: " + count); // todo 为啥会调用多次
        return count;
    }

    class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

        private MusicItemBinding mBinding;

        public MyRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public MyRecyclerViewHolder(@NonNull MusicItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
