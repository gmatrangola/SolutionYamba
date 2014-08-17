package com.thenewcircle.solutionyamba;



import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class StatusFragment extends Fragment implements TextWatcher {
    private static final String TAG = "newcircle.Yamba." + StatusFragment.class.getSimpleName();
    private EditText editTextStatusMessage;
    private TextView textViewCharsRemaining;
    private int maxCharacters;
    private int warningLength;
    private int warningColor;
    private int okColor;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

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

    public static interface PostHandler {
        public void postMessage(String message);
    }

    public StatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_status, container, false);
        Button buttonPostStatus = (Button) layout.findViewById(R.id.buttonPostStatus);
        editTextStatusMessage = (EditText) layout.findViewById(R.id.editTextStatusMessage);

        final PostHandler postHandler = (PostHandler)getActivity();
        buttonPostStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Posting: " + editTextStatusMessage.getText());
                String message = editTextStatusMessage.getText().toString();
                postHandler.postMessage(message);
            }
        });

        editTextStatusMessage.addTextChangedListener(this);
        textViewCharsRemaining = (TextView) layout.findViewById(R.id.textViewCharsRemaining);
        maxCharacters = getResources().getInteger(R.integer.maximumCharacters);
        warningLength = getResources().getInteger(R.integer.warningLength);
        warningColor = getResources().getColor(R.color.warningColor);
        okColor = getResources().getColor(android.R.color.black);
        return layout;
    }


}
