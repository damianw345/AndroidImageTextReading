package wasilewd.ee.pw.edu.pl.androidimagetextreading;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by dw on 14/12/17.
 */

public abstract class AbstractTesseractActivity extends Activity {

    protected TessBaseAPI mTess;
    protected String datapath = "";
    protected Bitmap tesseractImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initTesseractAPI(Bitmap tesseractImage) {

        this.tesseractImage = tesseractImage;

        String language = "pol";
        datapath = getFilesDir() + "/tesseract/";
        mTess = new TessBaseAPI();

        checkFile(new File(datapath + "tessdata/"));
        mTess.init(datapath, language);

    }

    protected void processImage(View view) {
        String OCRresult = null;
        mTess.setImage(tesseractImage);
        OCRresult = mTess.getUTF8Text();
        TextView OCRTextView = (TextView) findViewById(R.id.EditableText);
        EditText editText = findViewById(R.id.EditableText);
        OCRTextView.setText(OCRresult);
        editText.setText(OCRresult);
    }

    private void checkFile(File dir) {
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles();
        }
        if (dir.exists()) {
//            String datafilepath = datapath+ "/tessdata/eng.traineddata";
            String datafilepath = datapath + "/tessdata/pol.traineddata";
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
