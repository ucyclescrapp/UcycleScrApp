// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

    boolean connected = false;
    ConnectivityManager connectivityManager;
    String rilobas_version = "u-cycle-v101.8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(), rilobas_version
                , Toast.LENGTH_SHORT).show();

        final ImageButton ibc = (ImageButton) findViewById(R.id.imageButton);
        ibc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                TryConnect();

                if (connected) {
                    Animation anim3 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim3);
                    ibc.startAnimation(anim3);

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "u-cycle: Please check internet status.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //

        final ImageButton ib = (ImageButton) findViewById(R.id.imageButton2);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "developed by https://rilobas.app", Toast.LENGTH_SHORT).show();

            }
        });
        //
    }

    private void TryConnect() {
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else {
            connected = false;
        }
    }
}







