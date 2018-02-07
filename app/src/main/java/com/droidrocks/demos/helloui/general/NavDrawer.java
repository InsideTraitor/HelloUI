package com.droidrocks.demos.helloui.general;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.droidrocks.demos.helloui.authentication.Login;
import com.droidrocks.demos.helloui.notifications.AlertDialogFragment;
import com.example.hollisinman.helloui.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NavDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AlertDialogFragment.AlertDialogFragmentInteractionListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;


    private ImageView profilePicture;
    private static final String TAG_NAVDRAWER = "TAG_NAVDRAWER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        profilePicture = findViewById(R.id.iv_profilePicture);

        profilePicture.setImageURI(Uri.parse(getFilesDir().getAbsolutePath() + "/" + getFirebaseUser().getPhotoUrl()));

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle alertMessage = new Bundle();
                alertMessage.putString(AlertDialogFragment.DIALOG_MESSAGE, "Are you sure you want to change your profile picture?");
                alertMessage.putString(AlertDialogFragment.TYPE, AlertDialogFragment.ALERT_DIALOG_TYPE_EDIT_PROFILE_PICTURE);
                AlertDialogFragment.newInstance(alertMessage).show(getSupportFragmentManager(), AlertDialogFragment.TAG_ALERT_DIALOG_FRAGMENT);
            }
        });
    }

    private FirebaseUser getFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    private void updateUserPhoto(File file, FirebaseUser firebaseUser) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse(file.getName()))
                .build();

        firebaseUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG_NAVDRAWER, "User profile updated.");
                            Toast.makeText(NavDrawer.this, "Saved new profile photo", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG_NAVDRAWER, "Failed to updated FirebaseUser Profile Picture");
                            Toast.makeText(NavDrawer.this, "Update failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void refreshProfilePicture(Bitmap bitmap) {
        profilePicture.setImageBitmap(bitmap);
    }

    private File getProfilePictureFile(Bitmap bitmap) {
        String filename = getFilesDir().getAbsolutePath() + "/" + getFirebaseUser().getUid() + "_profile_picture.png";
        File dest = new File(filename);

        try {
            FileOutputStream out = new FileOutputStream(dest);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dest;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // Save the new profile picture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            refreshProfilePicture(imageBitmap);
            File file = getProfilePictureFile(imageBitmap);
            updateUserPhoto(file, getFirebaseUser());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Bundle alertMessage = new Bundle();
            alertMessage.putString(AlertDialogFragment.DIALOG_MESSAGE, "Are you sure you want to log off?");
            alertMessage.putString(AlertDialogFragment.TYPE, AlertDialogFragment.ALERT_DIALOG_TYPE_LOGOFF);
            AlertDialogFragment.newInstance(alertMessage).show(getSupportFragmentManager(), AlertDialogFragment.TAG_ALERT_DIALOG_FRAGMENT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_log_off) {
            Bundle alertMessage = new Bundle();
            alertMessage.putString(AlertDialogFragment.DIALOG_MESSAGE, "Are you sure you want to log off?");
            alertMessage.putString(AlertDialogFragment.TYPE, AlertDialogFragment.ALERT_DIALOG_TYPE_LOGOFF);
            AlertDialogFragment.newInstance(alertMessage).show(getSupportFragmentManager(), AlertDialogFragment.TAG_ALERT_DIALOG_FRAGMENT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void logOff() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(NavDrawer.this, Login.class));
    }

    @Override
    public void cancelLogOff() {
        // No implementation
    }

    @Override
    public void changeProfilePicture() {
        dispatchTakePictureIntent();
    }

    @Override
    public void cancelChangeProfilePicture() {
        // No implementation
    }

    @Override
    public void makePhoneCall(String phoneNumber) {
        Intent phoneCall = new Intent(Intent.ACTION_CALL);
        phoneCall.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(phoneCall);
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
         */
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
                Log.e(TAG_NAVDRAWER, e.getMessage());
            }
        }

        /**
         * Downloading file in background thread
         */
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
                    updateProgressBar(numBytesRead);
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
         **/
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

        private void updateProgressBar(int bytesRead) {
            bytesDownloaded += bytesRead;
            progressBar.setProgress((downloadSize / bytesDownloaded) * 100);
        }

        private URL getURLFromString(String stringURL) {
            try {
                url = new URL(stringURL);
            } catch (MalformedURLException e) {
                Log.e(TAG_NAVDRAWER, e.getMessage());
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
                    Log.e(TAG_NAVDRAWER, e.getMessage());
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
