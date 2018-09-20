package request.elgroupinternational.com.exoplayerdemo.AuoPlayVideoRecyclerView.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import request.elgroupinternational.com.exoplayerdemo.AuoPlayVideoRecyclerView.interfaces.PlayerCallbacks;
import request.elgroupinternational.com.exoplayerdemo.R;
import request.elgroupinternational.com.exoplayerdemo.AuoPlayVideoRecyclerView.VideoActivity;
import request.elgroupinternational.com.exoplayerdemo.AuoPlayVideoRecyclerView.VideoModel;
import request.elgroupinternational.com.exoplayerdemo.AuoPlayVideoRecyclerView.interfaces.VideoItemClicked;
import request.elgroupinternational.com.exoplayerdemo.utils.ExoPlayerHandler;
import request.elgroupinternational.com.exoplayerdemo.utils.RippleBackground;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> implements PlayerCallbacks {
    private ArrayList<VideoModel> arrayList;
    private Context context;
    VideoActivity activity;
    ViewHolder currentholder;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;
    private int mResumeWindow;
    private long mResumePosition;
    private VideoItemClicked videoItemClicked;
    public VideoAdapter(final Activity context, ArrayList<VideoModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        activity= (VideoActivity) context;
        ExoPlayerHandler.getInstance().intializePlayer();
        videoItemClicked= (VideoItemClicked) context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_row_layout, viewGroup, false);
        return new ViewHolder(view);
    }
    private void setPlayPause(boolean play){
        if (play){
           ExoPlayerHandler.getInstance().playExoPlayer();
        }
        else {
            ExoPlayerHandler.getInstance().pauseExoPlayer();
        }


    }
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        if (arrayList.get(i).isIs_selected()){

            viewHolder.play.setVisibility(View.GONE);

            viewHolder.mExoPlayerView.setVisibility(View.VISIBLE );
            String userAgent = Util.getUserAgent(context, context.getApplicationInfo().packageName);


            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(context, null, httpDataSourceFactory);
            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mVideoSource = new ExtractorMediaSource(Uri.parse(arrayList.get(i).getVideo_url()), dataSourceFactory, extractorsFactory, null, null);
            viewHolder.mExoPlayerView.setPlayer(ExoPlayerHandler.getInstance().getPlayer(this));
            LoopingMediaSource loopingSource = new LoopingMediaSource(mVideoSource, 1);
            if (ExoPlayerHandler.getInstance().getPlayer(this)==null){
                ExoPlayerHandler.getInstance().intializePlayer();
            }
            ExoPlayerHandler.getInstance().getPlayer(this).prepare(loopingSource);
            viewHolder. mExoPlayerView.getPlayer().setPlayWhenReady(true);

            initFullscreenDialog(viewHolder. mExoPlayerView);
            initFullscreenButton(viewHolder. mExoPlayerView);

        }else {

            viewHolder.play.setVisibility(View.VISIBLE);
            try {

                viewHolder.mExoPlayerView.setPlayer(null);
                viewHolder.mExoPlayerView.setVisibility(View.GONE);
            }catch (Exception e){
                Toast.makeText(context,"exp  oocured",Toast.LENGTH_SHORT).show();
            String error=    e.getMessage();
                Log.d("error_message:",error);
            }

        }
        viewHolder.video_number.setText("Video "+i);
    }


    private void initFullscreenDialog(final SimpleExoPlayerView mExoPlayerView) {

        mFullScreenDialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog(mExoPlayerView);
                super.onBackPressed();
            }
        };
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void pauseplayerBuffer()
    {
    if (currentholder!=null){
        currentholder.rippleBackground.stopRippleAnimation();
        currentholder.rippleBackground.setVisibility(View.GONE);
    }
    }

    public void start_stopplayer(int pos,ViewHolder viewHolder,int prevposition_){
        currentholder=viewHolder;
        if (prevposition_==-1){
            arrayList.get(pos).setIs_selected(true);
        }else {
            arrayList.get(pos).setIs_selected(true);
            arrayList.get(prevposition_).setIs_selected(false);
        }

        notifyDataSetChanged();
    }
    private void openFullscreenDialog(SimpleExoPlayerView mExoPlayerView) {

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_fullscreen_exit_white_24dp));
        mExoPlayerFullscreen = true;
        videoItemClicked.changeToLandscaped();
        mFullScreenDialog.show();
    }


    private void closeFullscreenDialog(SimpleExoPlayerView mExoPlayerView) {

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
         currentholder.main_media_frame.addView(mExoPlayerView);
        videoItemClicked.changeTOPotrait();
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_fullscreen));
    }


    private void initFullscreenButton(final SimpleExoPlayerView mExoPlayerView) {

        PlaybackControlView controlView = mExoPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog(mExoPlayerView);
                else
                    closeFullscreenDialog(mExoPlayerView);
            }
        });
    }

    @Override
    public void onExoPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onExoPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_BUFFERING){
            //   currentholder.progress_bar.setVisibility(View.VISIBLE);


            currentholder.rippleBackground.setVisibility(View.VISIBLE);

            //   actionVideoButtonsLayout = view.findViewById(R.id.element_set_video_buttons);
            currentholder.rippleBackground.startRippleAnimation();

        } else {
            //  currentholder.progress_bar.setVisibility(View.INVISIBLE);
            if (currentholder!=null&&currentholder.rippleBackground!=null) {
                currentholder.rippleBackground.stopRippleAnimation();
                currentholder.rippleBackground.setVisibility(View.GONE);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout play;
        private SimpleExoPlayerView mExoPlayerView;

        RippleBackground rippleBackground;
        TextView video_number;
        FrameLayout main_media_frame;

        public ViewHolder(View view) {
            super(view);
            play=view.findViewById(R.id.play);
            mExoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.exoplayer);
            main_media_frame=   ((FrameLayout)view. findViewById(R.id.main_media_frame));
            video_number=view.findViewById(R.id.video_number);
            rippleBackground=view.findViewById(R.id.rippleBack);
        }
    }

}
