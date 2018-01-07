package com.droidrocks.demos.helloui.general;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.hollisinman.helloui.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class HomePage extends AppCompatActivity {

    private ImageView profilePicture;
    private static final String HOMEPAGE_TAG = "HomePage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Log.d(HOMEPAGE_TAG, "Hello from onCreate()");

        profilePicture = findViewById(R.id.iv_profilePicture);

        GetProfileAsyncTask startProfileDownload = new GetProfileAsyncTask(getApplicationContext());
        startProfileDownload.execute("https://dummyimage.com/2000x2000/000/fff&text=YOLO!!!");
    }

    private class GetProfileAsyncTask extends AsyncTask<String, String, String> {

        ProgressBar downloadProgress;
        File profilePictureFile;
        Context context;
        
        GetProfileAsyncTask(Context context) {
            this.context = context;
        }


        /**
         * Before starting background thread
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Starting download");

            downloadProgress = new ProgressBar(getApplicationContext());
            downloadProgress.setIndeterminate(false);
            downloadProgress.setProgress(0);
            downloadProgress.setVisibility(View.VISIBLE);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... stringURL) {
            try {
                // Declare the path for the File we're going to download and save
                String profilePictureFilePath = getFilesDir().getAbsolutePath() + "/profilePicture.jpg";

                // Create a URL from the first String parameter
                URL url = new URL(stringURL[0]);

                // Open a connection to that URL
                URLConnection connection = url.openConnection();
                connection.connect();

                // Create the File we're going to write to
                profilePictureFile = new File(profilePictureFilePath);

                // Input stream to read file - with 2k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 2048);

                // Output stream to write file
                OutputStream output = new FileOutputStream(profilePictureFile);

                // Create an array of bytes with size 1KB
                byte[] buffer = new byte[1024];
                int read;

                // While there is data to be read, write that data
                while ((read = input.read(buffer)) != -1) {
                    // Writing data to file
                    output.write(buffer, 0, read);
                }

                // Flushing output
                output.flush();

                // Closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * After completing background task
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // Display the downloaded picture to the user
            profilePicture.setImageURI(Uri.fromFile(profilePictureFile));
        }
    }
}
