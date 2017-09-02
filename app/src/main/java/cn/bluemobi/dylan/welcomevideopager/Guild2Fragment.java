package cn.bluemobi.dylan.welcomevideopager;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yuandl on 2016-11-10.
 */

public class Guild2Fragment extends LazyLoadFragment {
    private CustomVideoView customVideoView;
    private int index;
    private TextView exit;

    @Override
    protected int setContentView() {
        return R.layout.fm_guild;
    }

    @Override
    protected void lazyLoad() {
        customVideoView = findViewById(R.id.cv);
        exit = findViewById(R.id.tv_exit);
        /**获取参数，根据不同的参数播放不同的视频**/
        index = getArguments().getInt("index");
        Log.d("Guild2Fragment","开始播放视频="+index);
        Uri uri;
        if (index == 1) {
            uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.loveyou);
        } else if (index == 2) {
            uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.loveyou);
        } else {
            uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.welecome_c);
        }
        /**播放视频**/
        customVideoView.playVideo(uri);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }

    @Override
    protected void stopLoad() {
        super.stopLoad();
        if (customVideoView != null) {
            customVideoView.stopPlayback();
        }
    }
}
