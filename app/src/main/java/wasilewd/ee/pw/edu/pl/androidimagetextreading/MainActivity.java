package wasilewd.ee.pw.edu.pl.androidimagetextreading;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jumpToTakePhotoActivity(View view){
        Intent intent = new Intent(getApplicationContext(),TakePhotoActivity.class);
        startActivity(intent);
    }


    public void jumpToLoadPhotoActivity(View view){
        Intent intent = new Intent(getApplicationContext(),LoadPhotoActivity.class);
        startActivity(intent);
    }

    public void jumpToShowResultsActivity(View view){
        Intent intent = new Intent(getApplicationContext(),ShowResultsActivity.class);
        startActivity(intent);
    }

    public void jumpToCropperActivity(View view){
        Intent intent = new Intent(getApplicationContext(),ProcessPhotoActivity.class);
        startActivity(intent);
    }
}
