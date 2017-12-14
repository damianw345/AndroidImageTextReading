package wasilewd.ee.pw.edu.pl.androidimagetextreading;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by dw on 14/12/17.
 */

public class ProcessLoadedPhotoActivity extends AbstractTesseractActivity {

    private String imagePath;
    private Uri imageUri;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tesseract_result);

        imageUri  = (Uri)getIntent().getExtras().get("data");
        imagePath = (String)imageUri.getEncodedPath();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to WRITE_EXTERNAL_STORAGE - requesting it");
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            }
        }
        //init image
        image = null;
        try {
            image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(image);

        initTesseractAPI();
    }
}

