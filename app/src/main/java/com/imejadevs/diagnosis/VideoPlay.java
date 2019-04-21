package com.imejadevs.diagnosis;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class VideoPlay extends AppCompatActivity {
  VideoView video;
  ProgressDialog pd;
  private int mCurrentPostion = 0;
  private final String PLAYBACK_TIME= "play_time";    @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_video_play);
    pd = new ProgressDialog(VideoPlay.this);
    pd.setMessage("Buffering video please wait...");
    String uri=getIntent().getStringExtra("video");
    video=findViewById(R.id.videoview);
    video.setVideoURI(Uri.parse(uri));
    video.setMediaController(new MediaController(this));
    video.requestFocus();
    video.start();

    video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
      @Override
      public void onPrepared(MediaPlayer mp) {
        //close the progress dialog when buffering is done
        pd.dismiss();

      }
    });

    if (savedInstanceState !=null){
      mCurrentPostion = savedInstanceState.getInt(PLAYBACK_TIME);
    }

    MediaController controller=new MediaController(this);
    controller.setMediaPlayer(video);

    video.setMediaController(controller);
  }
  private void initializePlayer(){

    if (mCurrentPostion>0){
      video.seekTo(mCurrentPostion);
    }
    else {
      //start showing the video other than black screen.
      video.seekTo(1);
    }
    video.start();
  }
  private void releasePlayer(){
    video.stopPlayback();
  }
  @Override
  protected  void onStart(){
    super.onStart();
    initializePlayer();
  }
  @Override
  protected void onStop(){
    super.onStop();

    releasePlayer();
  }
  protected void onPause(){
    super.onPause();

    if (Build.VERSION.SDK_INT< Build.VERSION_CODES.N){
      video.pause();
    }
  }
  protected void onSaveInstanceState(Bundle outState){
    super.onSaveInstanceState(outState);

    outState.putInt(PLAYBACK_TIME,video.getCurrentPosition());
  }
}
