package cn.bluemobi.dylan.welcomevideopager;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

/**
 * Created by cts on 16/11/19.
 */

public class MyApplication extends Application {

      private static MyApplication myApplication;
      @Override
      public void onCreate() {
            super.onCreate();

            if (myApplication==null){
                  myApplication=new MyApplication();
            }
            //通过主动的方式通知系统我们需要文件列表，要向系统发送广播
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));

      }
      public static MyApplication getInstance(){
            return myApplication;
      }

}
