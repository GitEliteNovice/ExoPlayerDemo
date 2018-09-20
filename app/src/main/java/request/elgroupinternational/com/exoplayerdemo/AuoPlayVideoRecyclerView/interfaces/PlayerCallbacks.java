package request.elgroupinternational.com.exoplayerdemo.AuoPlayVideoRecyclerView.interfaces;

import com.google.android.exoplayer2.ExoPlaybackException;

public interface PlayerCallbacks {
    void onExoPlayerError(ExoPlaybackException error);
    void onExoPlayerStateChanged(boolean playWhenReady, int playbackState);
}
