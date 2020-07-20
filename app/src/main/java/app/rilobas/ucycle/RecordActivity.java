// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class RecordActivity extends Activity {

    ImageView imgTime;
    ImageView imgStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_record);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("_id");
        String orders = bundle.getString("_orders");
        String total_price = bundle.getString("_total_price");
        String total_weight = bundle.getString("_total_weight");
        String username = bundle.getString("_username");
        String pickup_location = bundle.getString("_pickup_location");
        String pickup_date = bundle.getString("_pickup_date");
        String pickup_time = bundle.getString("_pickup_time");
        String status = bundle.getString("_status");

        imgTime = (ImageView) findViewById(R.id.imageView7);

        switch(pickup_time){
            case("M"):
                imgTime.setImageResource(R.drawable.m1);
                pickup_time = "MORNING";
                break;
            case("A"):
                imgTime.setImageResource(R.drawable.a1);
                pickup_time = "AFTERNOON";
                break;
            case("E"):
                imgTime.setImageResource(R.drawable.e1);
                pickup_time = "EVENNING";
                break;
        }


        imgStatus = (ImageView) findViewById(R.id.imageView8);
        switch(status){
            case("P"):
                imgStatus.setImageResource(R.drawable.s1);
                status = "PENDING";
                break;
            case("A"):
                imgStatus.setImageResource(R.drawable.s2);
                status = "AWAITING PICKUP";
                break;
            case("I"):
                imgStatus.setImageResource(R.drawable.s3);
                status = "IN PROGRESS";
                break;
            case("C"):
                imgStatus.setImageResource(R.drawable.s4);
                status = "COMPLETED";
                break;
        }


        final TextView tvAll = (TextView) findViewById(R.id.textViewAll);
        String s = //"#: " + id + "\n" +
                "" + orders + "\n" + "\n" +
                "TOTAL PRICE: N" + total_price + "k\n" + "\n" +
                "TOTAL WEIGHT: " + total_weight + "Kg\n" + "\n" +
                //": " + username + "\n" +
                "PICKUP LOCATION: " + pickup_location + "\n" + "\n" +
                "PICKUP DATE: " + pickup_date + "\n" + "\n" +
                "PICKUP TIME: " + pickup_time + "\n" + "\n" +
                "STATUS: " + status;
        tvAll.setText(s);

    }
}
