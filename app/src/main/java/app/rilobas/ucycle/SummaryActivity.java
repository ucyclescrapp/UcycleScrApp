// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class SummaryActivity extends Activity {

    private Button btnProceed;
    private TextView txtResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_summary);

        Bundle bundle = getIntent().getExtras();
        final String u_id = bundle.getString("user_id");
        final String u_email = bundle.getString("wr_email");
        final String u_response = bundle.getString("u_response");

        txtResponse = (TextView)findViewById(R.id.textView32);
        txtResponse.setText(u_response);

        btnProceed = (Button) findViewById(R.id.button2);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });



    }
}
