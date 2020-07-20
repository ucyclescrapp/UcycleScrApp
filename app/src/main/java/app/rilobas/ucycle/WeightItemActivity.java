// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class WeightItemActivity extends Activity {

    ImageButton ibAddPlastic;
    double plastic_price;
    double plastic_mass;

    private TextView txtResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weight_item);

        txtResponse = (TextView) findViewById(R.id.textViewRes);

        TextView tv_pn = (TextView) findViewById(R.id.txtPlasticname);
        TextView tv_pd = (TextView) findViewById(R.id.txtPlasticdescription);
        final EditText edt_wqty = (EditText) findViewById(R.id.edtItemquantity);
        final TextView tot_pri = (TextView) findViewById(R.id.txtTotalweight);


        Bundle bundle = getIntent().getExtras();

        final String u_plasticcode = bundle.getString("_plasticcode");
        final String u_plasticname = bundle.getString("_plasticname");
        final String u_description = bundle.getString("_description");
        final String u_price = bundle.getString("_price");
        final String u_plasticpicture = bundle.getString("_plasticpicture");

        final String imgURL = "https://u-cycle.app/15U-CycleWeb/api/images/" + u_plasticpicture + ".png";


        Picasso.with(this)
                .load(imgURL)
                .placeholder(R.drawable.uload)
                .fit()
                .centerCrop().into((ImageView) findViewById(R.id.imageView3));

        tv_pn.setText(u_plasticname);
        tv_pd.setText(u_description);

        edt_wqty.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                txtResponse.setText("");

                if (!edt_wqty.getText().toString().equals("")) {
                    if (!edt_wqty.getText().toString().equals("0")) {
                        double price_vector = Double.parseDouble(u_price);
                        double quantity_vector = Double.parseDouble(edt_wqty.getText().toString());
                        plastic_price = price_vector * quantity_vector;
                        plastic_mass = quantity_vector;

                        tot_pri.setText("N" + String.format("%.2f", plastic_price) + "k");
                    } else {
                        edt_wqty.setText("");
                    }
                } else {
                    tot_pri.setText(String.format("%.2f", 0.0) + "k");
                }


            }
        });

        final ImageView imgv = (ImageView)findViewById(R.id.imageView2);

        ibAddPlastic = (ImageButton) findViewById(R.id.imagebuttontop2);
        ibAddPlastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation anim3 = AnimationUtils.loadAnimation(WeightItemActivity.this, R.anim.anim3);
                ibAddPlastic.startAnimation(anim3);
                imgv.startAnimation(anim3);

                if (edt_wqty.getText().toString().equals("")) {
                    txtResponse.setText("u-cycle: Enter weight(kg)");// + u_itemname.toLowerCase() + " quantity");
                } else {


                    if (WeightActivity.sList.size() < 15) {
                        String itemOrder =
                                u_plasticcode + "x" + edt_wqty.getText().toString() + "#"
                                        + String.valueOf(plastic_mass) + "#"
                                        + String.valueOf(u_price) + "#"
                                        + String.valueOf(u_price) + "#"
                                + String.valueOf(u_plasticname);

                        String itemnameOrder =
                                u_plasticname + " (N" + String.valueOf(u_price) + "/kg)#"
                                        + String.valueOf(plastic_mass) + "#"
                                        + String.valueOf(plastic_price) + "#"
                                        + "0.0";

                        WeightActivity.sList.add(itemOrder);


                        Intent intent = new Intent(WeightItemActivity.this, SummaryaddActivity.class);

                        Bundle bundlep = new Bundle();
                        bundlep.putString("itemnameOrder", itemnameOrder);
                        bundlep.putString("orderType", "W");
                        bundlep.putString("_itempicture", u_plasticpicture);
                        intent.putExtras(bundlep);

                        startActivity(intent);
                        finish();
                        return;

                    } else {
                        txtResponse.setText("u-cycle: " + "Sorry, maximum of 15 weight items in cart.");
                    }
                }
            }
        });

    }

}
