package request.elgroupinternational.com.exoplayerdemo.utils;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import request.elgroupinternational.com.exoplayerdemo.Application.AppController;
import request.elgroupinternational.com.exoplayerdemo.AuoPlayVideoRecyclerView.interfaces.PlayerCallbacks;

public final class ExoPlayerHandler {
    private static ExoPlayerHandler instance;
   private   PlayerCallbacks playerCallbacks;
   private SimpleExoPlayer player;

    public static ExoPlayerHandler getInstance(){
        if(instance == null){
            synchronized (ExoPlayerHandler.class) {
                if(instance == null){
                    instance = new ExoPlayerHandler();
                }
            }
        }
        return instance;
    }

    public SimpleExoPlayer getPlayer(PlayerCallbacks playerCallbacks) {
        this.playerCallbacks=playerCallbacks;
        return player;
    }

    public SimpleExoPlayer intializePlayer(){
         if (player==null){
             BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
             TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
             TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
             LoadControl loadControl = new DefaultLoadControl();
             player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(AppController.getInstance().getApplicationContext()), trackSelector, loadControl);
             player.addListener(new ExoPlayer.EventListener() {

                 @Override
                 public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

                 }

                 @Override
                 public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}

                 @Override
                 public void onLoadingChanged(boolean isLoading) {}

                 @Override
                 public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                     playerCallbacks.onExoPlayerStateChanged(playWhenReady,playbackState);
                 }

                 @Override
                 public void onRepeatModeChanged(int repeatMode) {

                 }

                 @Override
                 public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                 }

                 @Override
                 public void onPlayerError(ExoPlaybackException error) {
                     playerCallbacks.onExoPlayerError(error);

                 }

                 @Override
                 public void onPositionDiscontinuity(int reason) {

                 }



                 @Override
                 public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {}

                 @Override
                 public void onSeekProcessed() {

                 }
             });
         }

        return player;
    }

    public void pauseExoPlayer(){
        if (player!=null)
        player.setPlayWhenReady(false);
    }
    public void playExoPlayer(){
        if (player!=null)
        player.setPlayWhenReady(true);
    }
    public void seekTo(int pos){
        if (player!=null)
            player.seekTo(pos);

    }
}
