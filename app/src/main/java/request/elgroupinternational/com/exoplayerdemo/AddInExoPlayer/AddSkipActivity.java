package request.elgroupinternational.com.exoplayerdemo.AddInExoPlayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import request.elgroupinternational.com.exoplayerdemo.R;

public class AddSkipActivity extends AppCompatActivity {
    SimpleExoPlayerView simpleExoPlayerView;
    AdsLoader imaAdsLoader;
    SimpleExoPlayer simpleExoPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_skip);
           setUI();
    }

    private void setUI() {

        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.exoplayer);
       // imaAdsLoader = new AdsLoader(this, getAdTagUri());
    }

    @Override
    protected void onStart() {
        super.onStart();
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        simpleExoPlayerView.setPlayer(simpleExoPlayer);
    /*    MediaSource mediaSourceWithAds = new AdsMediaSource(
                getContentMediaSource(),
                dataSourceFactory,
                imaAdsLoader,
                simpleExoPlayerView.getOverlayFrameLayout());
       // simpleExoPlayer.seekTo(contentPosition);
        simpleExoPlayer.prepare(mediaSource);*/
        simpleExoPlayer.setPlayWhenReady(true);
    }
}
