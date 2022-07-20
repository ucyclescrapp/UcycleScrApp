// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SummaryscheduleActivity extends Activity {

    private Button btnProceed;
    String gResponse;

    String wr_orders;
    String wr_total_price;
    String wr_total_weight;
    String wr_username;
    String wr_pickup_location;
    String wr_pickup_date;
    String wr_pickup_time;
    String wr_status;
    String wr_phone;
    String wr_address;
    String wr_landmark;
    String wr_ppm;

    private TextView txtSummary;

    private EditText edtPhone;
    private EditText edtAddress;
    private EditText edtLandmark;

    private TextView txtResponse;

    private Spinner ppmSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_summaryschedule);


        txtResponse = (TextView) findViewById(R.id.textViewRes2);

        ppmSelect = (Spinner) findViewById(R.id.spinner1);
        String[] bsh = new String[]{
                "Preferred Payment",
                "CASH",
                "AIRTIME",
                "CREDIT MY WALLET"
        };
        ArrayAdapter<String> adaptr = new ArrayAdapter<String>(this, R.layout.spinner_item, bsh);
        ppmSelect.setAdapter(adaptr);

        final ImageView imgv = (ImageView) findViewById(R.id.imageView2);

        ppmSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Animation anim3 = AnimationUtils.loadAnimation(SummaryscheduleActivity.this, R.anim.anim3);
                    imgv.startAnimation(anim3);

                    switch (position) {
                        case (1):
                            wr_ppm = "CASH";
                            break;
                        case (2):
                            wr_ppm = "AIRTIME";
                            break;
                        case (3):
                            wr_ppm = "WALLET";
                            break;
                        case (4):
                            wr_ppm = "GIFT";
                            break;
                    }
                } else {
                    wr_ppm = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Bundle bundle = getIntent().getExtras();

        wr_orders = bundle.getString("wr_code");
        wr_orders = removeLastChar(wr_orders);
        wr_total_price = bundle.getString("wr_price");
        wr_total_weight = bundle.getString("wr_weight");
        wr_username = bundle.getString("wr_email");
        wr_pickup_location = bundle.getString("wr_location");
        wr_pickup_date = bundle.getString("wr_pickupdate_serve");
        wr_pickup_time = bundle.getString("wr_pickuptime");
        wr_status = "P";


        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtLandmark = (EditText) findViewById(R.id.edtLandmark);


        txtSummary = (TextView) findViewById(R.id.textView32);
        String summary = //"Cart: " + wr_orders + "\n" +
                "Total Price: N" + wr_total_price + "k\n"
                + "Total Weight: " + wr_total_weight + "kg\n"
                + "Pickup Location: " + wr_pickup_location + "\n"
                + "Pickup Date: " + wr_pickup_date + "\n"
                + "Pickup Time: " + wr_pickup_time + "\n";

        txtSummary.setText(summary);


        btnProceed = (Button) findViewById(R.id.button2);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wr_phone = edtPhone.getText().toString();
                wr_address = edtAddress.getText().toString();
                wr_landmark = edtLandmark.getText().toString();


                wr_total_price = Uri.encode(wr_total_price);
                wr_total_weight = Uri.encode(wr_total_weight);
                wr_pickup_location = Uri.encode(wr_pickup_location);
                wr_pickup_date = Uri.encode(wr_pickup_date);
                wr_pickup_time = Uri.encode(wr_pickup_time);
                wr_status = Uri.encode(wr_status);

                wr_phone = Uri.encode(wr_phone);
                wr_address = Uri.encode(wr_address);
                wr_landmark = Uri.encode(wr_landmark);
                wr_ppm = Uri.encode(wr_ppm);

                if (wr_ppm.equals("")) {
                    txtResponse.setText("u-cycle: Select Preferred Payment Method");
                } else {
                    if (!edtPhone.getText().toString().equals("") &&
                            !edtAddress.getText().toString().equals("")
                    ) {

                        btnProceed.setVisibility(View.INVISIBLE);

                        getJSON(MainActivity.app_url + "/product/createschedule.php?m_or=" + wr_orders
                                + "&m_tp=" + wr_total_price
                                + "&m_tw=" + wr_total_weight
                                + "&m_us=" + wr_username
                                + "&m_pl=" + wr_pickup_location
                                + "&m_pd=" + wr_pickup_date
                                + "&m_pt=" + wr_pickup_time
                                + "&m_st=" + wr_status
                                + "&m_pho=" + wr_phone
                                + "&m_add=" + wr_address
                                + "&m_lan=" + wr_landmark
                                + "&m_ppm=" + wr_ppm
                        );

                    } else {
                        txtResponse.setText("u-cycle: *Pickup Phone and Pickup Address");
                    }
                }
            }
        });
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    {
                        InsertSchedule(s);
                    }
                } catch (JSONException e) {
                }

            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void InsertSchedule(String json) throws JSONException {


        JSONObject obj0 = new JSONObject(json);

        try {
            gResponse = obj0.getString("message");
        } catch (Exception e) {
        }

        //Toast.makeText(getApplicationContext(), gResponse, Toast.LENGTH_SHORT).show();
        //finish();

        ProfileActivity.sListValueBackup = null;
        ProfileActivity.sListWeightBackup = null;

        Intent intent = new Intent(SummaryscheduleActivity.this, SummaryresponseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("wr_email", wr_username);
        bundle.putString("u_response", gResponse);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
