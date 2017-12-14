package wasilewd.ee.pw.edu.pl.androidimagetextreading;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by dw on 07/12/17.
 */

public class IntroActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_screen);


        Thread startingThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(1500);

                    //zmien tutaj zeby inna klasa startowala pierwsza
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                    Intent intent = new Intent(getApplicationContext(),ProcessTakenPhotoActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        };
        startingThread.start();
    }
}
