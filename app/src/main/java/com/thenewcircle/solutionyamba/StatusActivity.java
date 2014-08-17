package com.thenewcircle.solutionyamba;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

import org.w3c.dom.Text;


public class StatusActivity extends Activity implements TextWatcher {

    private static final String TAG = "newcircle.Yamba." + StatusActivity.class.getSimpleName();
    private EditText editTextStatusMessage;
    private TextView textViewCharsRemaining;
    private int maxCharacters;
    private int warningLength;
    private int warningColor;
    private int okColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Button buttonPostStatus = (Button) findViewById(R.id.buttonPostStatus);
        editTextStatusMessage = (EditText) findViewById(R.id.editTextStatusMessage);

        buttonPostStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Posting: " + editTextStatusMessage.getText());
                YambaClient client = new YambaClient("student", "password");
                try {
                    client.postStatus(editTextStatusMessage.getText().toString());
                } catch (YambaClientException e) {
                    Log.e(TAG, "Error posting " + editTextStatusMessage.getText().toString(), e);
                }
            }
        });

        editTextStatusMessage.addTextChangedListener(this);
        textViewCharsRemaining = (TextView) findViewById(R.id.textViewCharsRemaining);
        maxCharacters = getResources().getInteger(R.integer.maximumCharacters);
        warningLength = getResources().getInteger(R.integer.warningLength);
        warningColor = getResources().getColor(R.color.warningColor);
        okColor = getResources().getColor(android.R.color.black);
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        Log.d(TAG, "beforeTextChanged(" + s + ", " + start + "," + count + "," + after + ")");
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        Log.d(TAG, "onTextChanged(" + s + ", " + start + "," + count + ")");
    }

    @Override
    public void afterTextChanged(Editable s) {
        int remaining = maxCharacters - s.length();
        Log.d(TAG, "afterTextChanged(" + s + ") count:" + s.length());
        textViewCharsRemaining.setText(remaining + "");
        if(remaining > warningLength) {
            textViewCharsRemaining.setTextColor(okColor);
        }
        else {
            textViewCharsRemaining.setTextColor(warningColor);
        }
    }
}
