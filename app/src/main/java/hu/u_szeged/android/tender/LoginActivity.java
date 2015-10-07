package hu.u_szeged.android.tender;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import hu.u_szeged.android.tender.utils.Constants;
import hu.u_szeged.android.tender.utils.Utilities;

public class LoginActivity extends FragmentActivity {
    private BroadcastReceiver headsetStateChangedReceiver;

    private TextView headsetStateTextView;
    private EditText usernameEditText;
    private EditText passEditText;
    private CheckBox rememberCheckBox;

    private ImageView logoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set "No title" feature. This will remove the default header of the app
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Inflate layout
        setContentView(R.layout.activity_login);

        //Set the init value for the headset state
        headsetStateTextView = (TextView) findViewById(R.id.headsetStateTextView);
        updateHeadsetStateText();

        //Setup the BroadcasReceiver
        headsetStateChangedReceiver = new HeadsetStateChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headsetStateChangedReceiver, filter);

        usernameEditText = (EditText) findViewById(R.id.edit_text_username);
        passEditText = (EditText) findViewById(R.id.edit_text_password);
        rememberCheckBox = (CheckBox) findViewById(R.id.check_box_remember);
        logoImageView = (ImageView) findViewById(R.id.image_view_logo);

        //Setup the user data from shared pref
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String username = sharedPref.getString(Constants.PREF_USERNAME, null);
        if (username != null) {
            usernameEditText.setText(username);
            rememberCheckBox.setChecked(true);

            int length = sharedPref.getInt(Constants.PREF_PASSWORD_CHARS, 0);
            StringBuilder outputBuffer = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                outputBuffer.append("*");
            }
            passEditText.setText(outputBuffer.toString());
        }

        //Setup the checkbox
        rememberCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                if (!isChecked) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.remove(Constants.PREF_PASSWORD_CHARS);
                    editor.remove(Constants.PREF_PASSWORD_HASH);
                    editor.remove(Constants.PREF_USERNAME);
                    editor.apply();
                }
            }
        });

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        (new DownloadImageAndShowAsyncTask()).execute(Constants.LOGO_URL);
    }

    public void loginOnClick(View view) {
        String username = usernameEditText.getText().toString();
        if (username.trim().equals("")) {
            usernameEditText.setError("Username cannot be blank!");
            return;
        }
        String pass = passEditText.getText().toString();
        if (pass.trim().equals("")) {
            passEditText.setError("Password cannot be blank!");
            return;
        }
        if (rememberCheckBox.isChecked()) {
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(Constants.PREF_PASSWORD_CHARS, pass.length());
            editor.putString(Constants.PREF_PASSWORD_HASH, Utilities.md5(pass));
            editor.putString(Constants.PREF_USERNAME, username);
            editor.apply();
        }

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(headsetStateChangedReceiver);
        super.onDestroy();
    }

    private class HeadsetStateChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            updateHeadsetStateText();
        }
    }

    private void updateHeadsetStateText() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        if (audioManager.isWiredHeadsetOn()) {
            headsetStateTextView.setText(R.string.text_headset_plugged_in);
            headsetStateTextView.setTextColor(getResources().getColor(R.color.green_500));
        } else {
            headsetStateTextView.setText(R.string.text_headset_not_plugged_in);
            headsetStateTextView.setTextColor(getResources().getColor(R.color.red_500));
        }
    }

    class DownloadImageAndShowAsyncTask extends AsyncTask<String, String, Bitmap> {
        private final String TAG = DownloadImageAndShowAsyncTask.class.getName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "Start image download...");
        }

        @Override
        protected Bitmap doInBackground(String... args) {
            // updating UI from Background Thread
            try {
                URL url = new URL(args[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                return BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap  bitmap) {
            logoImageView.setImageBitmap(bitmap);
            ObjectAnimator.ofFloat(logoImageView, "alpha", 0f, 1f).setDuration(200).start();
            Log.d(TAG, "Image downloaded.");
        }
    }
}
