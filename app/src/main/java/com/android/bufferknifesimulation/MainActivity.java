package com.android.bufferknifesimulation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.bufferknifesimulation.annotation.BindView;
import com.android.bufferknifesimulation.annotation.InjectEvent;
import com.android.bufferknifesimulation.annotation.OnClick;
import com.android.bufferknifesimulation.annotation.OnLongClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.button1)
    Button button1;

    @BindView(R.id.button2)
    private Button button2;

    @BindView(R.id.button2)
    public Button button3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            InjectEvent.inject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.button1, R.id.button2})
    public void onClick(View view) {
        if (view.getId() == R.id.button1) {
            Log.d(TAG, "button1 onClick");
        } else if (view.getId() == R.id.button2) {
            Log.d(TAG, "button2 onClick");
        }
    }

    @OnLongClick({R.id.button1, R.id.button2})
    public boolean onLongClick(View view) {
        if (view.getId() == R.id.button1) {
            Log.d(TAG, "button1 onLongClick");
        } else if (view.getId() == R.id.button2) {
            Log.d(TAG, "button2 onLongClick");
        }
        return false;
    }
}