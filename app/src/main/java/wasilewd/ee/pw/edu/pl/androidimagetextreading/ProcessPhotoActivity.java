package wasilewd.ee.pw.edu.pl.androidimagetextreading;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;


/**
 * Created by dw on 17/12/17.
 */

public class ProcessPhotoActivity extends AbstractTesseractActivity {

    private CropImageView cropImageView;
    private ImageView croppedImageView;
    private Bitmap croppedImage;
    private Bitmap unCroppedImage;
    private Button cropButton;
    private Button acceptCroppedImageButton;

    private boolean photoTaken = false;
    private boolean photoLoaded = false;
    private Uri receivedUri;
    private String imagePath;
    private Uri takenPhotoUri;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cropper_activity);

        cropImageView = (CropImageView) findViewById(R.id.CropImageView);
        croppedImageView = (ImageView) findViewById(R.id.croppedImageView);
        cropButton = (Button) findViewById(R.id.Button_crop);
        acceptCroppedImageButton = (Button) findViewById(R.id.Button_next_activity);
        acceptCroppedImageButton.setVisibility(View.GONE);

        if ((Uri)getIntent().getExtras().get("loadedPhoto") != null){
            photoLoaded = true;
            receivedUri = (Uri)getIntent().getExtras().get("loadedPhoto");
        }

        if ((Uri)getIntent().getExtras().get("takenPhoto") != null){
            photoTaken = true;
            receivedUri = (Uri)getIntent().getExtras().get("takenPhoto");
            imagePath = (String) receivedUri.getEncodedPath();
            takenPhotoUri = Uri.fromFile(new File(imagePath));
        }

        try {
            if (photoLoaded){
                unCroppedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), receivedUri);
            } else if (photoTaken){
                unCroppedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), takenPhotoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set tesseractImage to crop
        cropImageView.setImageBitmap(unCroppedImage);

        cropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                croppedImage = cropImageView.getCroppedImage();
                croppedImageView.setImageBitmap(croppedImage);
                acceptCroppedImageButton.setVisibility(View.VISIBLE);
                cropButton.setText(R.string.crop_again);
            }
        });

        acceptCroppedImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.tesseract_result);
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(croppedImage);
                tesseractImage = croppedImage;
                initTesseractAPI();
            }
        });
    }
}

