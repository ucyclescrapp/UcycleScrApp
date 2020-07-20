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

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ItemActivity extends Activity {

    ImageButton ibAddItem;
    double item_mass;

    private TextView txtResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_item);

        txtResponse = (TextView) findViewById(R.id.textViewRes);

        TextView tv_in = (TextView) findViewById(R.id.txtItemname);
        TextView tv_id = (TextView) findViewById(R.id.txtItemdescription);

        final EditText edt_qty = (EditText) findViewById(R.id.edtItemquantity);
        final TextView tot_wei = (TextView) findViewById(R.id.txtTotalweight);

        Bundle bundle = getIntent().getExtras();

        final String u_id = bundle.getString("_id");
        final String u_itemcode = bundle.getString("_itemcode");
        final String u_itemname = bundle.getString("_itemname");
        final String u_description = bundle.getString("_description");
        final String u_price = bundle.getString("_price");
        final String u_itempicture = bundle.getString("_itempicture");
        final String u_registrationdate = bundle.getString("_registrationdate");
        final String u_category = bundle.getString("_category");
        final String u_color = bundle.getString("_color");
        final String u_quantity = bundle.getString("_quantity");
        final String u_weight = bundle.getString("_weight");
        final String u_plastictytpe = bundle.getString("_plastictype");


        final String imgURL = "https://u-cycle.app/15U-CycleWeb/api/images/" + u_itempicture + ".png";


        Picasso.with(this)
                .load(imgURL)
                .placeholder(R.drawable.uload)
                .fit()
                .centerCrop().into((ImageView) findViewById(R.id.imageView3));

        tv_in.setText(u_itemname);
        tv_id.setText(u_description);

        edt_qty.addTextChangedListener(new TextWatcher() {

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
                //
                if (!edt_qty.getText().toString().equals("")) {
                    if (!edt_qty.getText().toString().equals("0")) {
                        double weight_vector = Double.parseDouble(u_weight);
                        double quantity_vector = Double.parseDouble(edt_qty.getText().toString());
                        item_mass = weight_vector * quantity_vector;

                        tot_wei.setText(String.format("%.2f", item_mass) + "kg");
                    } else {
                        edt_qty.setText("");
                    }
                } else {
                    tot_wei.setText(String.format("%.2f", 0.0) + "kg");
                }


            }
        });

        final ImageView imgv = (ImageView)findViewById(R.id.imageView2);

        ibAddItem = (ImageButton) findViewById(R.id.imagebuttontop2);
        ibAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation anim3 = AnimationUtils.loadAnimation(ItemActivity.this, R.anim.anim3);
                ibAddItem.startAnimation(anim3);
                imgv.startAnimation(anim3);

                if (edt_qty.getText().toString().equals("")) {
                    txtResponse.setText("u-cycle: Enter quantity");// + u_itemname.toLowerCase() + " quantity");
                } else {


                    if (ValueActivity.sList.size() < 15) {
                        String itemOrder =
                                u_itemcode + "x" + edt_qty.getText().toString() + "#"
                                        + String.valueOf(item_mass) + "#"
                                        + String.valueOf(u_weight) + "#"
                                        + String.valueOf(u_price) + "#"
                                        + String.valueOf(u_itemname);


                        String itemnameOrder =
                                u_itemname + " (x" + edt_qty.getText().toString() + ")#"
                                        + String.valueOf(item_mass) + "#"
                                        + String.valueOf(u_weight) + "#"
                                        + String.valueOf(u_price);

                        ValueActivity.sList.add(itemOrder);

                        Intent intent = new Intent(ItemActivity.this, SummaryaddActivity.class);

                        Bundle bundlep = new Bundle();
                        bundlep.putString("itemnameOrder", itemnameOrder);
                        bundlep.putString("orderType", "V");
                        bundlep.putString("_itempicture", u_itempicture);
                        intent.putExtras(bundlep);

                        startActivity(intent);
                        finish();
                        return;

                    } else {
                        txtResponse.setText("u-cycle: " + "Sorry, maximum of 15 items in cart.");
                    }
                }
            }
        });
    }
}
