// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class CartConfirm extends Activity {

    String _flag;
    String _email;
    String _code;
    String _qty;
    Double _weight;
    Double _price;

    ImageButton ibClearCart;
    ImageButton ibAddCart;
    ImageButton ibContinue;

    public static ArrayList<String> sList;

    String gResponse;

    //String wr_email;
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

    ListView lvItems;
    String[] itL;

    String[] _schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cart_confirm);

        _schedule = new String[12];

        sList = new ArrayList<String>();
        Bundle bundle = getIntent().getExtras();
        _flag = bundle.getString("action_flag");
        if (_flag.equals("barter")) {
            _email = bundle.getString("user_email");
            _code = bundle.getString("user_code");
            _qty = bundle.getString("user_qty");
            _price = Double.parseDouble(bundle.getString("user_price"));
        } else {
            _email = bundle.getString("user_email");
            _code = bundle.getString("user_code");
            _weight = Double.parseDouble(bundle.getString("user_weight"));
            _price = Double.parseDouble(bundle.getString("user_price"));
        }


        switch (_flag) {
            case ("value"):
                if (ProfileActivity.sListValueBackup != null) {
                    sList = ProfileActivity.sListValueBackup;
                }
                break;
            case ("weight"):
                if (ProfileActivity.sListWeightBackup != null) {
                    sList = ProfileActivity.sListWeightBackup;
                }
                break;
            case ("barter"):
                if (ProfileActivity.sListBarterBackup != null) {
                    sList = ProfileActivity.sListBarterBackup;
                }
                break;

        }


        //Toast.makeText(CartConfirm.this, String.valueOf(sList.get(0)), Toast.LENGTH_SHORT).show();


        final String[] ni_itemname = new String[sList.size()];
        final String[] ni_itemdescription = new String[sList.size()];
        final String[] ni_itemprice = new String[sList.size()];
        final String[] ni_itempicture = new String[sList.size()];
        final String[] ni_itemcode = new String[sList.size()];

        for (int j = 0; j < sList.size(); j++) {
            String[] separated1 = sList.get(j).toString().split("#");
            String[] separated2 = separated1[0].split("x");

            ni_itemname[j] = separated1[4];
            ni_itemdescription[j] = "x" + separated2[1];

            if (_flag == "barter") {
                ni_itemprice[j] = String.valueOf(Double.parseDouble(separated1[3]) * Double.parseDouble(separated2[1]));
            } else {
                ni_itemprice[j] = String.valueOf(Double.parseDouble(separated1[3]) * Double.parseDouble(separated1[1]));
            }

            ni_itempicture[j] = separated2[0];
            ni_itemcode[j] = "";
        }

        lvItems = (ListView) findViewById(R.id.listView1);
        lvItems.setAdapter(new GListImageAdapter(this, ni_itemname, ni_itemdescription, ni_itemprice, ni_itempicture, ni_itemcode));


        ibClearCart = (ImageButton) findViewById(R.id.imagebuttontop10); //clear
        ibAddCart = (ImageButton) findViewById(R.id.imagebuttontop12); //addmore
        ibContinue = (ImageButton) findViewById(R.id.imagebuttontop11); //continue

        ibClearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Clear", Toast.LENGTH_SHORT).show();
                Animation anim3 = AnimationUtils.loadAnimation(CartConfirm.this, R.anim.anim3);
                ibClearCart.startAnimation(anim3);

                CustomDialogClass cdd = new CustomDialogClass(CartConfirm.this, _flag);
                cdd.show();
            }
        });

        ibAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                switch (_flag) {
                    case ("value"):
                        intent = new Intent(CartConfirm.this, ValueActivity.class);
                        break;
                    case ("weight"):
                        intent = new Intent(CartConfirm.this, WeightActivity.class);
                        break;
                    case ("barter"):
                        intent = new Intent(CartConfirm.this, ConsumerActivity.class);
                        break;
                }

                Bundle bundle = new Bundle();
                bundle.putString("user_email", _email);
                intent.putExtras(bundle);
                startActivity(intent);

                finish();
                return;
            }
        });

        ibContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (_flag.equals("barter")) {

                    Date todayDate = Calendar.getInstance().getTime();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String todayString = formatter.format(todayDate);

                    _schedule[0] = wr_orders = Uri.encode(_code);
                    _schedule[1] = wr_total_price = Uri.encode(String.valueOf(_price));
                    _schedule[2] = wr_total_weight = Uri.encode(_qty);
                    _schedule[3] = wr_username = Uri.encode(_email);
                    _schedule[4] = wr_pickup_location = Uri.encode(wr_pickup_location);
                    _schedule[5] = wr_pickup_date = Uri.encode(todayString);
                    _schedule[6] = wr_pickup_time = Uri.encode("#");
                    _schedule[7] = wr_status = Uri.encode("n");
                    _schedule[8] = wr_phone = Uri.encode(wr_phone);
                    _schedule[9] = wr_address = Uri.encode(wr_address);
                    _schedule[10] = wr_landmark = Uri.encode(wr_landmark);
                    _schedule[11] = wr_ppm = Uri.encode(wr_ppm);


                    CustomDialogClassTwo cdd = new CustomDialogClassTwo(CartConfirm.this, _flag, _schedule);
                    cdd.show();

                } else {

                    double totalmass = 0.0;
                    double maxWei = 8.0;
                    for (int i = 0; i < sList.size(); i++) {
                        String[] separated = sList.get(i).toString().split("#");
                        totalmass += Double.parseDouble(separated[1]);
                    }

                    if (totalmass > maxWei) {
                        Intent intent = new Intent();
                        intent = new Intent(CartConfirm.this, ScheduleActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("action_flag", _flag);
                        bundle.putString("user_email", _email);
                        bundle.putString("user_code", _code);
                        bundle.putString("user_weight", String.format("%.2f", _weight));
                        bundle.putString("user_price", String.format("%.2f", _price));
                        intent.putExtras(bundle);
                        startActivity(intent);

                        finish();
                        return;
                    } else {
                        Toast.makeText(getApplicationContext(), "u-cycle: " + "Net weight must be greater than " + String.valueOf(maxWei) + "kg", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void SendBarterSchedule(String[] action_schedule) {
        getJSON("https://u-cycle.app/15U-CycleWeb/api/product/createbarterschedule.php?m_or=" + action_schedule[0]
                + "&m_tp=" + action_schedule[1]
                + "&m_tw=" + action_schedule[2]
                + "&m_us=" + action_schedule[3]
                + "&m_pl=" + action_schedule[4]
                + "&m_pd=" + action_schedule[5]
                + "&m_pt=" + action_schedule[6]
                + "&m_st=" + action_schedule[7]
                + "&m_pho=" + action_schedule[8]
                + "&m_add=" + action_schedule[9]
                + "&m_lan=" + action_schedule[10]
                + "&m_ppm=" + action_schedule[11]
        );
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
                    InsertBarterSchedule(s);
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

    private void InsertBarterSchedule(String json) throws JSONException {

        JSONObject obj0 = new JSONObject(json);

        try {
            gResponse = obj0.getString("message");
        } catch (Exception e) {
        }


        ProfileActivity.sListBarterBackup = null;
        finish();
    }

}
