package cn.bluemobi.dylan.welcomevideopager.recives;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

/**
 * Created by cts on 16/11/19.
 */

public class MediaScannerReceiver extends BroadcastReceiver {
      private final static String TAG = "MediaScannerReceiver";

      @Override
      public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Uri uri = intent.getData();
            String externalStoragePath = Environment.getExternalStorageDirectory().getPath();
            if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
                  // scan internal storage
                 // scan(context, MediaProvider.INTERNAL_VOLUME);
            } else {
                  if (uri.getScheme().equals("file")) {
                        // handle intents related to external storage
                        String path = uri.getPath();
                        if (action.equals(Intent.ACTION_MEDIA_MOUNTED) &&
                                  externalStoragePath.equals(path)) {
                            //  scan(context, MediaProvider.EXTERNAL_VOLUME);
                        } else if (action.equals(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE) &&
                                  path != null && path.startsWith(externalStoragePath + "/")) {
                              scanFile(context, path);
                        }
                  }
            }
      }

      private void scan(Context context, String volume) {
            Bundle args = new Bundle();
            args.putString("volume", volume);

//            context.startService(
//                      //new Intent(context, MediaScannerService.class).putExtras(args));
      }

      private void scanFile(Context context, String path) {
            Bundle args = new Bundle();
            args.putString("filepath", path);
//            context.startService(
//                      new Intent(context, MediaScannerService.class).putExtras(args));
      }

}
