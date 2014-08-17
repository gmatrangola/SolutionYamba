package com.thenewcircle.solutionyamba;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;


import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

import org.w3c.dom.Text;


public class StatusActivity extends Activity implements StatusFragment.PostHandler {

    private static final String TAG = "newcircle.Yamba." + StatusActivity.class.getSimpleName();
    private PostTask postTask;

    @Override
    public void postMessage(String message) {
        postTask.execute(new String[]{message});
    }

    private class PostTask extends AsyncTask<String, Integer, Long> {
        @Override
        protected Long doInBackground(String... messages) {
            long start = System.currentTimeMillis();
            YambaClient client = new YambaClient("student", "password");
            for(String message : messages) {
                try {
                    client.postStatus(message);
                } catch (YambaClientException e) {
                    Log.d(TAG, "Unable to post message " + message, e);
                }
            }
            return System.currentTimeMillis() - start;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            Log.d(TAG, "Posted " + aLong);
            AlertDialog.Builder builder = new AlertDialog.Builder(StatusActivity.this);
            builder.setTitle("Post time").setMessage(aLong + " ms");
            builder.setCancelable(true);
            builder.create().show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postTask = new PostTask();
        setContentView(R.layout.activity_status);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
