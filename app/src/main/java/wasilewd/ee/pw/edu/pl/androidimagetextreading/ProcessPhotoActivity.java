package wasilewd.ee.pw.edu.pl.androidimagetextreading;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;


/**
 * Created by dw on 17/12/17.
 */

public class ProcessPhotoActivity extends AbstractTesseractActivity {

    private CropImageView unCroppedImageView;
    private ImageView croppedImageView;
    private Bitmap croppedImage;
    private Bitmap unCroppedImage;
    private Bitmap rotatedBitmap;
    private Button cropButton;
    private Button acceptCroppedImageButton;
    private SeekBar seekBar;
    private boolean photoTaken = false;
    private boolean photoLoaded = false;
    private Uri receivedUri;
    private String imagePath;
    private Uri takenPhotoUri;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.process_photo_activity);

        unCroppedImageView = (CropImageView) findViewById(R.id.CropImageView);
        croppedImageView = (ImageView) findViewById(R.id.croppedImageView);
        cropButton = (Button) findViewById(R.id.Button_crop);
        acceptCroppedImageButton = (Button) findViewById(R.id.Button_next_activity);
        acceptCroppedImageButton.setVisibility(View.GONE);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(360);
        seekBar.setVisibility(View.GONE);


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
        unCroppedImageView.setImageBitmap(unCroppedImage);

        cropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                croppedImage = unCroppedImageView.getCroppedImage();
                croppedImageView.setImageBitmap(croppedImage);
                acceptCroppedImageButton.setVisibility(View.VISIBLE);

                seekBar.setVisibility(View.VISIBLE);
                cropButton.setText(R.string.crop_again);
            }
        });

        acceptCroppedImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.tesseract_result);
                ImageView imageView = (ImageView) findViewById(R.id.imageView);

                croppedImage = rotatedBitmap;
                imageView.setImageBitmap(croppedImage);
                initTesseractAPI(croppedImage);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int degrees = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                croppedImageView.setRotation(progress);
                degrees = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                rotatedBitmap = croppedImage;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                Toast.makeText(ProcessPhotoActivity.this, "Degrees: " + degrees
                        , Toast.LENGTH_SHORT).show();

                rotatedBitmap = rotateBitmap(croppedImage, degrees);
            }
        });
    }

    private static Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}

