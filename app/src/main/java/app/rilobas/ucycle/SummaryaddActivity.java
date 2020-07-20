// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SummaryaddActivity extends Activity {

    private Button btnProceed;

    private TextView txtSummaryadd;
    String itemnameOrder;

    double itemtotalCost = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_summaryadd);


        Bundle bundle = getIntent().getExtras();
        itemnameOrder = bundle.getString("itemnameOrder");
        final String orderType = bundle.getString("orderType");
        String[] separated = itemnameOrder.split("#");

        itemtotalCost = Double.parseDouble(separated[1]) * Double.parseDouble(separated[3]);
        String s_itemtotalCost = (String.format("%.2f", itemtotalCost));

        separated[3] = (String.format("%.2f", Double.parseDouble(separated[3])));
        separated[2] = (String.format("%.2f", Double.parseDouble(separated[2])));
        separated[1] = (String.format("%.2f", Double.parseDouble(separated[1])));

        final String u_itempicture = bundle.getString("_itempicture");
        final String imgURL = "https://u-cycle.app/15U-CycleWeb/api/images/" + u_itempicture + ".png";
        Picasso.with(this)
                .load(imgURL)
                .placeholder(R.drawable.uload)
                .fit()
                .centerCrop().into((ImageView) findViewById(R.id.imageView2));



        if (orderType.equals("V")) {
            itemnameOrder = "" + separated[0] + "\n" +
                    "Weight per unit: " + separated[2] + "kg\n" +
                    "Total Weight: " + separated[1] + "kg\n\n" +
                    "Total Value: N" + s_itemtotalCost + "k";
        } else if (orderType.equals("W")) {
            itemnameOrder = "" + separated[0] + "\n" +
                    "Weight kg: " + separated[1] + "\n" +
                    "Total Value: N" + separated[2] + "k";
        }


        txtSummaryadd = (TextView) findViewById(R.id.textView32);
        txtSummaryadd.setText(itemnameOrder);

        btnProceed = (Button) findViewById(R.id.button2);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
