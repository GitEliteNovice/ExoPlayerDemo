package request.elgroupinternational.com.exoplayerdemo.Application;

import android.app.Application;
import android.util.DisplayMetrics;

public class AppController extends Application {
        private static AppController mInstance;

        @Override
        public void onCreate() {
            mInstance=this;
            super.onCreate();
        }


        public static AppController getInstance() {
            if (mInstance == null) {
                mInstance = new AppController();
            } else {
                return mInstance;
            }
            return mInstance;
        }


        public int getWidthFromDevice() {
            int width = 0;
            try {

                DisplayMetrics metrics = getResources().getDisplayMetrics();
                width = metrics.widthPixels;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return width;
        }

        public int getHeightFromDevice() {
            int height = 0;
            try {

                DisplayMetrics metrics = getResources().getDisplayMetrics();

                height = metrics.heightPixels;


            } catch (Exception e) {
                e.printStackTrace();
            }

            return height;
        }
    }

