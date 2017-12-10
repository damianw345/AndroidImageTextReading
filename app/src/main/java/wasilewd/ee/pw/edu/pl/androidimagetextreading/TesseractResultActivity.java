package wasilewd.ee.pw.edu.pl.androidimagetextreading;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * Created by dw on 07/12/17.
 */

public class TesseractResultActivity extends Activity {

    Bitmap image;
    private TessBaseAPI mTess;
    String datapath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tesseract_result);

//        test image
//        image = BitmapFactory.decodeResource(getResources(), R.drawable.paragon);

        byte[] byteArray = getIntent().getByteArrayExtra("image");
        image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        ImageView result = (ImageView)findViewById(R.id.taken_photo_view);
        result.setImageBitmap(image);

        //initialize Tesseract API
        String language = "pol";
        datapath = getFilesDir()+ "/tesseract/";
        mTess = new TessBaseAPI();

        checkFile(new File(datapath + "tessdata/"));

        mTess.init(datapath, language);
    }

    public void processImage(View view){
        String OCRresult = null;
        mTess.setImage(image);
        OCRresult = mTess.getUTF8Text();
        TextView OCRTextView = (TextView) findViewById(R.id.OCRTextView);
        OCRTextView.setText(OCRresult);
    }

    private void checkFile(File dir) {
        if (!dir.exists()&& dir.mkdirs()){
            copyFiles();
        }
        if(dir.exists()) {
//            String datafilepath = datapath+ "/tessdata/eng.traineddata";
            String datafilepath = datapath+ "/tessdata/pol.traineddata";
            File datafile = new File(datafilepath);

            if (!datafile.exists()) {
                copyFiles();
            }
        }
    }

    private void copyFiles() {
        try {
//            String filepath = datapath + "/tessdata/eng.traineddata";
            String filepath = datapath + "/tessdata/pol.traineddata";
            AssetManager assetManager = getAssets();

            InputStream instream = assetManager.open("tessdata/pol.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }

            outstream.flush();
            outstream.close();
            instream.close();

            File file = new File(filepath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

