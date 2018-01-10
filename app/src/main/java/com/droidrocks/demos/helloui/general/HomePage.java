package com.droidrocks.demos.helloui.general;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
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
import java.net.MalformedURLException;
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

    private class GetProfileAsyncTask extends AsyncTask<String, Integer, String> {

        ProgressBar progressBar;
        File profilePictureFile;
        Context context;
        URL url = null;
        int downloadSize = 0;
        int bytesDownloaded = 0;
        int readTimeout = 5000;
        int connectionTimeout = 5000;
        
        GetProfileAsyncTask(Context context) {
            this.context = context;
        }

        /**
         * Before starting background thread
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.progressBar);
            // Declare the path for the File we're going to download and save
            String profilePictureFilePath = getFilesDir().getAbsolutePath() + "/profilePicture.jpg";
            try {
                // Create the File we're going to write to
                profilePictureFile = new File(profilePictureFilePath);
            } catch (Exception e) {
                Log.e(HOMEPAGE_TAG, e.getMessage());
            }
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... stringURL) {
            try {
                // Attempt to get the size of the download
                downloadSize = connectToURL(stringURL[0]);

                publishProgress(downloadSize);

                // Input stream to read file - with 8k buffer (the default size of a BufferedInputStream)
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file
                FileOutputStream output = new FileOutputStream(profilePictureFile);

                // Create an array of bytes with size 2KB
                byte[] buffer = new byte[2048];

                // When numBytesRead = -1, we have reached the end of the InputStream
                int numBytesRead;

                // While there is data to be read, read that data into the buffer
                while ((numBytesRead = input.read(buffer, 0, buffer.length)) != -1) {
                    /*
                     Write the data contained in the byte[] "buffer"
                     Offset by nothing, start reading the "buffer" at index 0
                     Read no more than "numBytesRead", which will be equal to the number of bytes actually read in the while statement above
                      */
                    output.write(buffer, 0, numBytesRead);
                    updateProgress(numBytesRead);
                }

                // Flushing output - forces any remaining bytes in the FileOutputStream buffer to be written
                output.flush();

                // Closing streams - releases any resources associated with the streams
                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            } finally {

            }

            return null;
        }

        // Allows us to work on objects in the UIThread
        // Attempting to work on objects created by the UIThread without using this method will produce an error
        // Update the progress displayed in the ProgressBar here
        @Override
        protected void onProgressUpdate(Integer... values) {
            prepareIndeterminateProgressBar(downloadSize);
        }

        /**
         * After completing background task
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // Remove the ProgressBar from the layout
            progressBar.setVisibility(View.GONE);

            // Display the downloaded picture to the user
            profilePicture.setImageURI(Uri.fromFile(profilePictureFile));
        }

        // Return true if ProgressBar is indeterminate
        private boolean prepareIndeterminateProgressBar(int downloadSize) {
            boolean isIndeterminate = true;
            if (downloadSize > 0) {
                isIndeterminate = false;
                progressBar.setProgress(0);
            }
            progressBar.setIndeterminate(isIndeterminate);
            progressBar.setVisibility(View.VISIBLE);
            return isIndeterminate;
        }

        private void updateProgress(int bytesRead) {
            bytesDownloaded += bytesRead;
            progressBar.setProgress((downloadSize / bytesDownloaded) * 100);
        }

        private URL getURLFromString(String stringURL) {
            try {
                url = new URL(stringURL);
            } catch (MalformedURLException e) {
                Log.e(HOMEPAGE_TAG, e.getMessage());
            }
            return url;
        }

        private int connectToURL(String stringURL) {
            int contentLength = 0;
            // Create a URL from the first String parameter
            url = getURLFromString(stringURL);
                // Open a connection to that URL
            if (url != null) {
                try {
                    URLConnection connection = url.openConnection();
                    connection.setReadTimeout(readTimeout);
                    connection.setConnectTimeout(connectionTimeout);
                    connection.connect();
                    contentLength = connection.getContentLength();
                } catch (Exception e) {
                    Log.e(HOMEPAGE_TAG, e.getMessage());
                }
            }

            // Return the contentLength or 0 if we couldn't get the length
            if (contentLength != -1 && contentLength < Integer.MAX_VALUE) {
                return contentLength;
            } else {
                return 0;
            }
        }

    }
}
