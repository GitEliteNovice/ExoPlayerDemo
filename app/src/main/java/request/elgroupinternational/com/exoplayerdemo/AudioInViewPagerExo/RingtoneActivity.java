package request.elgroupinternational.com.exoplayerdemo.AudioInViewPagerExo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import request.elgroupinternational.com.exoplayerdemo.AuoPlayVideoRecyclerView.interfaces.PlayerCallbacks;
import request.elgroupinternational.com.exoplayerdemo.R;
import request.elgroupinternational.com.exoplayerdemo.AudioInViewPagerExo.adapter.RingtonePagerAdapter;
import request.elgroupinternational.com.exoplayerdemo.utils.ExoPlayerHandler;


public class RingtoneActivity extends AppCompatActivity implements View.OnClickListener, PlayerCallbacks {
    private LinearLayout btnPlay;
    private boolean isPlaying = false;
    ArrayList<String> urls;
    ViewPager viewpager;
    RingtonePagerAdapter ringtonePagerAdapter;
    LinearLayout ll_move_back,ll_move_forward,ll_setting;
    ImageView btn_icon;
    ImageView stop_ringtone_downloading;
    ProgressBar ringtone_downloading_progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringtone);


        int video_count = Integer.parseInt(getResources().getString(R.string.ringtone_count));
        String video_url = getResources().getString(R.string.ringtone_base_url);
        urls = new ArrayList<>();
        for (int i = 0; i < video_count; i++) {

            urls.add(video_url+i+".ogg");

        }
        btn_icon=findViewById(R.id.btn_icon);
        ringtonePagerAdapter = new RingtonePagerAdapter(this,urls);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(ringtonePagerAdapter);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                // Check if this is the page you want.

           prepareExoPlayerFromURL(Uri.parse(urls.get(position)));
            setPlayPause(false);
            }
        });

        ll_move_back=findViewById(R.id.ll_move_back);
        ll_move_forward=findViewById(R.id.ll_move_forward);
        ll_move_back.setOnClickListener(this);
        ll_move_forward.setOnClickListener(this);
       if (ExoPlayerHandler.getInstance().getPlayer(this)==null){
           ExoPlayerHandler.getInstance().intializePlayer();
       }
        prepareExoPlayerFromURL(Uri.parse(urls.get(0)));


        stop_ringtone_downloading=findViewById(R.id.stop_ringtone_downloading);
        stop_ringtone_downloading.setOnClickListener(this);
    }
    private void prepareExoPlayerFromURL(Uri uri){




        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "exoplayer2example"), null);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource audioSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);

        ExoPlayerHandler.getInstance().getPlayer(this).prepare(audioSource);
        initMediaControls();
    }
    private void setPlayPause(boolean play){
        isPlaying = play;
        ExoPlayerHandler.getInstance().getPlayer(this).setPlayWhenReady(play);
        if(!isPlaying){

            btn_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_icon));
        }else{
            btn_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
        }
    }
    private void initMediaControls() {
        initPlayButton();
    }

    private void initPlayButton() {
        btnPlay = (LinearLayout) findViewById(R.id.btnPlay);
        btnPlay.requestFocus();
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPlayPause(!isPlaying);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view==ll_move_forward){
            viewpager.setCurrentItem(viewpager.getCurrentItem()+1);
        }
        if (view==ll_move_back){
            viewpager.setCurrentItem(viewpager.getCurrentItem()-1);
        }
        if (view==stop_ringtone_downloading){

        }
    }

    @Override
    public void onExoPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onExoPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState){
            case ExoPlayer.STATE_ENDED:

                ExoPlayerHandler.getInstance().pauseExoPlayer();
                ExoPlayerHandler.getInstance().seekTo(0);
                break;
            case ExoPlayer.STATE_READY:
                //         Log.i(TAG,"ExoPlayer ready! pos: "+exoPlayer.getCurrentPosition()
                //                +" max: "+stringForTime((int)exoPlayer.getDuration()));
                // setProgress();
                break;
            case ExoPlayer.STATE_BUFFERING:
                //           Log.i(TAG,"Playback buffering!");
                break;
            case ExoPlayer.STATE_IDLE:
                //      Log.i(TAG,"ExoPlayer idle!");
                break;
        }
    }
}
