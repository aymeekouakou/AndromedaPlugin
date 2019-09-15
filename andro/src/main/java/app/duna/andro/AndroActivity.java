package app.duna.andro;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.identita.audioapp.AudioApp;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.List;

public class AndroActivity extends AppCompatActivity {

    private static String RESET_TAG = "reset_tag";

    public static String SERIAL_TAG = "serial_tag";
    public static int REQ = 333;

    MultiplePermissionsListener multiplePermissionsListener;

    static {
        System.loadLibrary("AudioApp");
    }

    public static void startActivity(final Activity activity) {
        startActivity(activity, false);
    }

    public static void startActivity(final Activity activity, boolean reset) {
        startActivity(activity, reset, null);
    }

    public static void startActivity(final Activity activity, final boolean reset, Bundle options) {
        Intent i = new Intent(activity, AndroActivity.class);
        i.putExtra(RESET_TAG, reset);
        ActivityCompat.startActivityForResult(activity, i, REQ, options);
    }

    private void showPermissionRationale(final PermissionToken token) {
        new AlertDialog
                .Builder(this)
                .setTitle("Storage & audio permissions")
                .setMessage("Both storage and audio permission are needed to use Duna your card features.")
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    token.cancelPermissionRequest();
                    setResult(RESULT_CANCELED);
                    finish();
                })
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    token.continuePermissionRequest();
                })
                .show();
    }

    private void startRecording() {
        boolean success = true;
        File folder = new File(String.format("%s%sDunaAudioRecord", Environment.getExternalStorageDirectory(), File.separator));
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (!success) return;

        String location = String.format("%scard.wav", folder);

        String url = getIntent().getBooleanExtra(RESET_TAG, false) ?
                getString(R.string.reset_url) : getString(R.string.rest_url);

        new AcousticTask().execute(url, location);
    }

    private class AcousticTask extends AsyncTask<String, Void, Integer> {

        AudioApp audioApp;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            audioApp = new AudioApp();
        }

        @Override
        protected Integer doInBackground(String... args) {
            return audioApp.StartRecord(32000, 5, args[0], args[1]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            Intent intent = new Intent();
            intent.putExtra(SERIAL_TAG, result);

            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andro);

        multiplePermissionsListener = new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (!report.areAllPermissionsGranted()) {
                    setResult(RESULT_CANCELED);
                    finish();
                }
            }

            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                showPermissionRationale(token);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                )
                .withListener(multiplePermissionsListener)
                .onSameThread()
                .check();

        startRecording();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_CANCELED);
    }

}
