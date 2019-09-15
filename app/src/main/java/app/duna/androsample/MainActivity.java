package app.duna.androsample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import app.duna.andro.AndroActivity;
import app.duna.andro.MessageManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton authCard = findViewById(R.id.authCard);
        authCard.setOnClickListener(view -> AndroActivity.startActivity(this));

        MaterialButton resetCard = findViewById(R.id.resetCard);
        resetCard.setOnClickListener(view -> AndroActivity.startActivity(this, true));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(MainActivity.class.getSimpleName(), String.valueOf(requestCode));

        if (requestCode == AndroActivity.REQ) {
            if (resultCode == RESULT_OK) {

                if (data != null) {
                    long result = data.getIntExtra(AndroActivity.SERIAL_TAG, 0);
                    Log.d(AndroActivity.class.getSimpleName(), "MESSAGE > " + MessageManager.getMessage((int) result));
                    Log.d(AndroActivity.class.getSimpleName(), "SERIAL > " + MessageManager.getSerial((int) result));
                }

            }
        }
    }

}
