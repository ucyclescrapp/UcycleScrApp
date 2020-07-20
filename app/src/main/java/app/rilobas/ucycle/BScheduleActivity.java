// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class BScheduleActivity extends Activity {

    String wr_email;
    String wr_code;
    String wr_qty;
    String wr_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bschedule);

        Bundle bundle = getIntent().getExtras();
        wr_email = bundle.getString("user_email");
        wr_code = bundle.getString("user_code");
        wr_qty = bundle.getString("user_qty");
        wr_price = bundle.getString("user_price");
    }
}
