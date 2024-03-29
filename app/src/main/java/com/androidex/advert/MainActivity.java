package com.androidex.advert;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bluemobi.dylan.welcomevideopager.R;

public class MainActivity extends AppCompatActivity {
    private ViewPager vp;
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private Button bt_start;
    private List<Fragment> fragments;
    private RelativeLayout rl_main;
    private boolean isTouchUp = false;


    private void assignViews() {
        rl_main = (RelativeLayout) findViewById(R.id.rl_main);
        vp = (ViewPager) findViewById(R.id.vp);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        bt_start = (Button) findViewById(R.id.bt_start);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        //全屏设置，隐藏窗口所有装饰
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);//清除FLAG
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, MusicServer.class);
        startService(intent);
        Log.e("MainActivity", "MusicSerice onCreate()");
        assignViews();
        //全屏的两种方式
        //sendBroadcast(new Intent("com.android.action.hide_navigationbar"));
        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        rl_main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    /**
     * 初始化数据,添加三个Fragment
     */
    private void initData() {
        fragments = new ArrayList<>();
        Fragment fragment1 = new Guild2Fragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("index", 3);
        fragment1.setArguments(bundle1);
        fragments.add(fragment1);

    }

    /**
     * 设置ViewPager的适配器和滑动监听
     */
    private void initView() {
        vp.setOffscreenPageLimit(1);
        vp.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        vp.addOnPageChangeListener(new MyPageChangeListener());
        vp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                vp.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        Log.e("liyp_", "手抬起");
                        isTouchUp = true;
                        startTimer();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        Log.e("liyp_", "手按下");
                        isTouchUp = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        isTouchUp = false;
                        Log.e("liyp_", "手移动");
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        isTouchUp = true;
                        Log.e("liyp_", "取消手势");
                        startTimer();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        if ((visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0) {
                            Log.e("liyp_", "底部菜单显示");
                            // TODO: The system bars are visible. Make any desired
                            isTouchUp = true;
                            startTimer();
                        } else {
                            // TODO: The system bars are NOT visible. Make any desired
                            Log.e("liyp_", "底部菜单隐藏");
                            isTouchUp = false;
                        }
                    }
                });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    vp.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    break;
                default:
                    break;
            }

        }
    };

    public void startTimer() {
        final Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (isTouchUp) {
                    handler.sendEmptyMessage(0);
                }
                Log.e("liyp_", "隐藏底部菜单");
            }
        };
        timer.schedule(timerTask, 3000);
    }

    /**
     * ViewPager适配器
     */
    private class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    /**
     * ViewPager滑动页面监听器
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 根据页面不同动态改变红点和在最后一页显示立即体验按钮
         *
         * @param position
         */
        @Override
        public void onPageSelected(int position) {
            bt_start.setVisibility(View.GONE);
            iv1.setImageResource(R.mipmap.dot_normal);
            iv2.setImageResource(R.mipmap.dot_normal);
            iv3.setImageResource(R.mipmap.dot_normal);
            if (position == 0) {
                iv1.setImageResource(R.mipmap.dot_focus);
            } else if (position == 1) {
                iv2.setImageResource(R.mipmap.dot_focus);
            } else {
                iv3.setImageResource(R.mipmap.dot_focus);
                bt_start.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
