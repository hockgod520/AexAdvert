package com.androidex.advert;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.VideoView;

import java.util.HashMap;

import cn.bluemobi.dylan.welcomevideopager.R;

/**
 * 可以播放视频的View
 * Created by yuandl on 2016-11-10.
 */

public class CustomVideoView extends VideoView {

    private int mVideoWidth;
    private int mVideoHeight;

    public CustomVideoView(Context context) {
        super(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Uri uri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.loveyou);
        getPlayTime(uri.toString());
        int width = SystemUtil.getScreenW(getContext());
        int height = SystemUtil.getScreenH(getContext());
        if (mVideoWidth > 0 && mVideoHeight > 0) {
            if (mVideoWidth * height > width * mVideoHeight) {
                height = width * mVideoHeight / mVideoWidth;
            } else if (mVideoWidth * height < width * mVideoHeight) {
                width = height * mVideoWidth / mVideoHeight;
            } else {

            }
            Log.i("liyp_", "width = " + width + "/height = " + height + " \r\n" +
                    "mVideoWidth = " + mVideoWidth + "/mVideoHeight = " + mVideoHeight);
        }
        setMeasuredDimension(width, height);
        //setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.getSize(heightMeasureSpec));
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD_MR1)
    private void getPlayTime(String mUri) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            if (mUri != null) {
                HashMap<String, String> headers = null;
                if (headers == null) {
                    headers = new HashMap<String, String>();
                    headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    mmr.setDataSource(mUri, headers);
                }
            } else {
                //mmr.setDataSource(mFD, mOffset, mLength);
            }
            String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
            //宽
            String width = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            //高
            String height = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            mVideoWidth = Integer.parseInt(width);
            mVideoHeight = Integer.parseInt(height);

        } catch (Exception ex) {
            Log.e("TAG", "MediaMetadataRetriever exception " + ex);
        } finally {
            mmr.release();
        }

    }

    /**
     * 播放视频
     *
     * @param uri 播放地址
     */
    public void playVideo(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Uri can not be null");
        }
        /**设置播放路径**/
        setVideoURI(uri);
        /**开始播放**/
        start();
        setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                /**设置循环播放**/
                mp.setLooping(true);
            }
        });
        setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return true;
            }
        });
    }
}
