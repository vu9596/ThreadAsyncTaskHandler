package vunt.com.vn.threadasynctaskhandler;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Handler;
import android.widget.SeekBar;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    public static final int INDEX_1 = 1;
    private static final int WHAT_LOAD_PATH_FINISH = 9311;
    public static final String[] EXTENSIONS = {"jpg", "png", "jpeg"};
    public static final String RESOURCE_IMAGE_PATH = "storage/emulated/0/DCIM/Camera";
    public static final String EXTRA_IMAGE = "vunt.com.vn.threadasynctaskhandler.EXTRA_IMAGE";
    private List<String> mPathFiles;
    private SeekBar mSeekBar;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT_LOAD_PATH_FINISH) {
                splashToMain();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPathFiles = new ArrayList<>();
        mSeekBar = findViewById(R.id.seek_bar_progress);
        new LoadImageAsyncTask(mHandler).execute(RESOURCE_IMAGE_PATH);
    }

    private void splashToMain() {
        Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
        mainIntent.putExtra(EXTRA_IMAGE, (Serializable) mPathFiles);
        SplashActivity.this.startActivity(mainIntent);
        SplashActivity.this.finish();
    }

    public class LoadImageAsyncTask extends AsyncTask<String, Integer, Void> {
        private Handler mHandler;

        public LoadImageAsyncTask(Handler handler) {
            mHandler = handler;
        }

        @Override
        protected Void doInBackground(String... strings) {
            File parentFile = new File(strings[0]);
            File[] files = parentFile.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    for (String extension : EXTENSIONS) {
                        if (file.getName().toLowerCase().endsWith(extension)) return true;
                    }
                    return false;
                }
            });
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                mPathFiles.add(file.getPath());
                publishProgress(files.length, i + INDEX_1);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mSeekBar.setMax(values[0]);
            mSeekBar.setProgress(values[INDEX_1]);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mHandler.sendEmptyMessage(WHAT_LOAD_PATH_FINISH);
            super.onPostExecute(aVoid);
        }
    }
}
