package com.aaa.musicdemo.metadata;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.aaa.musicdemo.qqmusic.SharedPreferenceUtil;

import java.util.HashMap;
import java.util.Map;

public class MetaDataManager {
    private static final String TAG = "MetaDataManager";

    private MetaDataManager() {
        Log.i(TAG, "MetaDataManager: ");
    }

    public void getMetaData() {
        try {
//            String uri = "http://sc1.111ttt.cn/2017/1/05/09/298092035545.mp3";
            String uri = "http://aqqmusic.tc.qq.com/amobile.music.tc.qq.com/C400003iHc0e2UIgMC"
                    + SharedPreferenceUtil.getString("vkey", "");
            MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
            Map<String, String> headers = new HashMap<>();
            metadataRetriever.setDataSource(uri, headers);

            String title = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            Log.i(TAG, "getMetaData: title=" + title);

            byte[] embeddedPicture = metadataRetriever.getEmbeddedPicture();
            Log.i(TAG, "getMetaData: getEmbeddedPicture=" + embeddedPicture);
            if (embeddedPicture != null) {
                Log.i(TAG, "getMetaData: getEmbeddedPicture=" + embeddedPicture.length);
            }

            String frameCount = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT);
            Log.i(TAG, "getMetaData: frameCount=" + frameCount);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                Bitmap bitmap = metadataRetriever.getFrameAtIndex(0);
            }
        } catch (Exception e) {
            Log.e(TAG, "getMetaData: ");
            e.printStackTrace();
        }
    }

    public static class Holder {
        public static MetaDataManager sMetaDataManager = new MetaDataManager();
    }

}
