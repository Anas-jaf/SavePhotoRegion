package xyz.net7.savephotoregion;

import android.content.Context;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xyz.net7.savephotoregion.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements PhotoFragment.OnFragmentInteractionListener {

    private ActivityMainBinding binding ;

    private EditText ipAddressInput;
    private OkHttpClient httpClient;

    int PERMISSION_ALL = 1;
    boolean flagPermissions = false;

    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        httpClient = new OkHttpClient();   // اضفت هذا السطر
        setContentView(binding.getRoot());
        checkPermissions();
        binding.makePhotoButton.setOnClickListener(view -> onClickScanButton());   // اضفت هذا السطر
        binding.requestButton.setOnClickListener(view -> sendGetRequest());  // اضفت هذا السطر

    }

//    اضف هذه الدالة انا من عندي
    public void sendGetRequest() {
        String ipAddress = binding.ipAddressInput2.getText().toString();
        String port = "8000"; // Change this to your desired port number
        String url = "http://" + ipAddress + ":" + port;
        Request request = new Request.Builder()
                .url(url)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, @NonNull Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Successfull request", Toast.LENGTH_SHORT).show();
//            Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    void onClickScanButton() {
        // check permissions
        if (!flagPermissions) {
            checkPermissions();
            return;
        }
        //start photo fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.res_photo_layout, new PhotoFragment())
                .addToBackStack(null)
                .commit();
    }

    void checkPermissions() {
        if (!hasPermissions(this, PERMISSIONS)) {
            requestPermissions(PERMISSIONS,
                    PERMISSION_ALL);
            flagPermissions = false;
        }
        flagPermissions = true;

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void onFragmentInteraction(Bitmap bitmap) {
        if (bitmap != null) {
            ImageFragment imageFragment = new ImageFragment();
            imageFragment.imageSetupFragment(bitmap);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.res_photo_layout, imageFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
