package com.droidrocks.demos.helloui.general;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.hollisinman.helloui.R;
import java.io.InputStream;
import java.util.Random;
public class PlaySound extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "PlaySound";
    // Create class variable
    private Button btn_play_sound;
    MediaPlayer mediaPlayer;
    int sounds[] = {R.raw.iguana_fart,R.raw.chicken_sound,R.raw.dog_sound,R.raw.frog_sound};
    // Array to hold resource files from raw folder. Put here as it did not work in onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_sound);
        // Finds view based on button ID
        btn_play_sound = findViewById(R.id.btn_play_sound);
        setupMediaPlayer();
        //int sounds[] = {R.raw.iguana_fart,R.raw.chicken_sound,R.raw.dog_sound,R.raw.frog_sound};
        // Generate random number based off of length of sounds array
        //int randomNumber = new Random().nextInt(sounds.length);
        //int randomSoundFromArray = sounds[randomNumber];
        // Creates instance of MediaPlayer object. Expects an int
        //final MediaPlayer playSound = MediaPlayer.create(this,randomSoundFromArray);
        // Sets up click event to listen for button tap
        btn_play_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(selectRandomTrack());
            }
        });

        btn_play_sound.setOnClickListener(PlaySound.this);
    }
    private int selectRandomTrack() {
        int clickRandomNumber = generateRandomNumber();
        int randomSoundFromArray = sounds[clickRandomNumber];
        return randomSoundFromArray;
    }


    private void playSound(int trackNumber) {
        AssetFileDescriptor assetFileDescriptor = getResources().openRawResourceFd(trackNumber);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mediaPlayer.setDataSource(assetFileDescriptor);
            } else {
                // implement solution for API <= Build.VERSION_CODES.N
            }
            assetFileDescriptor.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        mediaPlayer.prepareAsync();
//        mediaPlayer.create(PlaySound.this, trackNumber);
    }
    // Method to generate random Number based on length of sounds Array
    private int generateRandomNumber(){
        int randomNumber = new Random().nextInt(sounds.length - 1);
        return randomNumber;
    }
    private void setupMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}