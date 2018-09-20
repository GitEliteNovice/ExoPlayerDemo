package request.elgroupinternational.com.exoplayerdemo.AuoPlayVideoRecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;


import java.util.ArrayList;

import request.elgroupinternational.com.exoplayerdemo.R;
import request.elgroupinternational.com.exoplayerdemo.utils.Constant;
import request.elgroupinternational.com.exoplayerdemo.AuoPlayVideoRecyclerView.adapter.VideoAdapter;
import request.elgroupinternational.com.exoplayerdemo.AuoPlayVideoRecyclerView.interfaces.VideoItemClicked;
import request.elgroupinternational.com.exoplayerdemo.utils.ExoPlayerHandler;

import static android.widget.LinearLayout.VERTICAL;

public class VideoActivity extends AppCompatActivity implements VideoItemClicked, View.OnClickListener {
   RecyclerView video_recyclerView;
   VideoAdapter adapterVideoList;
   boolean ispotrait=true;
    ArrayList<VideoModel> urls;
    int filedownloading;
  //  Snackbar snackbar;
    LinearLayout video_main_layout;
    int prevposition=-1;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
      setUI();

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setUI() {

        video_recyclerView = findViewById(R.id.video_recyclerView);
        final LinearLayoutManager layoutmanger=new LinearLayoutManager(this);
        video_recyclerView.setLayoutManager(layoutmanger);
        DividerItemDecoration itemDecor = new DividerItemDecoration(VideoActivity.this, VERTICAL);
        video_recyclerView.addItemDecoration(itemDecor);
        adapterVideoList = new VideoAdapter(this,prepareData());
        video_recyclerView.setItemViewCacheSize(urls.size());
        video_recyclerView.setAdapter(adapterVideoList);

        video_main_layout=findViewById(R.id.video_main_layout);
        video_recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                int completeVisiblePostion = layoutmanger.findFirstCompletelyVisibleItemPosition();
                VideoAdapter.ViewHolder viewHolder= (VideoAdapter.ViewHolder) video_recyclerView.findViewHolderForLayoutPosition(completeVisiblePostion);
           if (completeVisiblePostion==-1){
               completeVisiblePostion=0;
           }
                if (adapterVideoList!=null&&prevposition!=completeVisiblePostion&&ispotrait){

                adapterVideoList.start_stopplayer(completeVisiblePostion,viewHolder,prevposition);
                prevposition=completeVisiblePostion;
                }

            }
        });

        /*    snackbar = Snackbar
                .make(video_main_layout, "Downloading!", Snackbar.LENGTH_LONG)
                .setAction("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PRDownloader.cancel(filedownloading);
                    }
                });
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);*/
    }
    private ArrayList<VideoModel> prepareData() {
       int video_count = Integer.parseInt(getResources().getString(R.string.video_count));
       String video_url = getResources().getString(R.string.video_base_url);
       urls = new ArrayList<>();
        for (int i = 1; i < video_count; i++) {
           VideoModel videoModel=new VideoModel(video_url+i+ Constant.StringKey.MP4,false);
            urls.add(videoModel);

        }
        return urls;
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ExoPlayerHandler.getInstance().pauseExoPlayer();
        if (adapterVideoList!=null){
            adapterVideoList.pauseplayerBuffer();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void ItemClicked(int position) {
     //   downloadfile(position);
    }

    @Override
    public void changeToLandscaped() {
        ispotrait=false;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    public void changeTOPotrait() {
        ispotrait=true;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

/*
    public void downloadfile(int pos) {
        final String dirPath =Environment.getExternalStorageDirectory() + "/Android/Data/" + getApplicationContext().getPackageName() + "/CannaCon/";
        filedownloading =  PRDownloader.download(urls.get(pos).getVideo_url(), dirPath, "video.mp4")
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                        Toast.makeText(VideoActivity.this,"download start",Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {
                        snackbar.setText("Download canceled");
                        snackbar.dismiss();
                        Toast.makeText(VideoActivity.this,"download cancel",Toast.LENGTH_SHORT).show();

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {

                        if (snackbar==null){

                            snackbar = Snackbar
                                    .make(video_main_layout, "Downloading!", Snackbar.LENGTH_LONG)
                                    .setAction("CANCEL", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            PRDownloader.cancel(filedownloading);
                                        }
                                    });
                        }
                        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
                        snackbar.setText("Downloading!");
                         snackbar.show();
                     *//*   stop_ringtone_downloading.setVisibility(View.VISIBLE);
                        ringtone_downloading_progress.setVisibility(View.VISIBLE);*//*
                   //     long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                   //     ringtone_downloading_progress.setProgress((int) progressPercent);
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {

                        Toast.makeText(VideoActivity.this,"download complete",Toast.LENGTH_SHORT).show();

                        snackbar.setText("Download complete");
                        snackbar.dismiss();


                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        Uri screenshotUri = Uri.parse(dirPath+"video.mp4");
                        sharingIntent.setType("video/mp4");
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                        startActivity(Intent.createChooser(sharingIntent, "Share image using"));
                    }

                    @Override
                    public void onError(Error error) {

                        snackbar.setText("Download error");
                        Toast.makeText(VideoActivity.this,"download error",Toast.LENGTH_SHORT).show();

                    }

                });
        snackbar.setText("Downloading!");
        snackbar.show();
*//*
        stop_ringtone_downloading.setVisibility(View.VISIBLE);
        ringtone_downloading_progress.setVisibility(View.VISIBLE);*//*

    }*/

    @Override
    public void onClick(View view) {

    }
}
