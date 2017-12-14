package wasilewd.ee.pw.edu.pl.androidimagetextreading;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by dw on 07/12/17.
 */

public class ProcessTakenPhotoActivity extends AbstractTesseractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tesseract_result);

        String imagePath = getIntent().getStringExtra("data");

        image = BitmapFactory.decodeFile(imagePath);

        ImageView result = (ImageView)findViewById(R.id.imageView);
        result.setImageBitmap(image);

        initTesseractAPI();
    }
}

