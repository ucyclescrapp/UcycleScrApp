// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConsumerActivity extends Activity {

    String u_email;
    //static Double myBalance;

    String[] i_id;
    String[] i_bartercode;
    String[] i_bartername;
    String[] i_description;
    String[] i_price;
    String[] i_barterpicture;
    String[] i_registrationdate;

    GridView gvItem;

    public static TextView txtResponse;
    public static ImageView imv10;

    public static ArrayList<String> bsList;
    public static TextView tvWeight;
    public static Double btotalqty;
    public static String btotalcodes;
    public static Double btotalprice;

    ImageButton ibSchedule;

    //DatabaseReference reff;

    String option;
    String gResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_consumer);

        Bundle bundle = getIntent().getExtras();
        u_email = bundle.getString("user_email");


        txtResponse = (TextView) findViewById(R.id.textViewRes);
        txtResponse.setText("u-cycle: Please wait..");

        imv10 = (ImageView) findViewById(R.id.imageView10);


        bsList = new ArrayList<String>();

        tvWeight = (TextView) findViewById(R.id.textView16);

        gvItem = (GridView) findViewById(R.id.GridView1);

        option = "check";
        getJSON("https://u-cycle.app/15U-CycleWeb/api/product/userreadstore.php?m_us=" + u_email);


        /*reff = FirebaseDatabase.getInstance().getReference().child("Member");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if (snapshot.child("username").getValue().toString().equals(u_email)) {
                        myBalance = Double.parseDouble(snapshot.child("userbalance").getValue().toString());
                        break;
                    } else {
                        myBalance = 0.0;
                    }
                }
                onStart();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        ibSchedule = (ImageButton) findViewById(R.id.imagebuttontop2);
        ibSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation anim3 = AnimationUtils.loadAnimation(ConsumerActivity.this, R.anim.anim3);
                ibSchedule.startAnimation(anim3);

                if (bsList.size() != 0) {

                    Intent intent = new Intent(ConsumerActivity.this, CartConfirm.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("action_flag", "barter");
                    bundle.putString("user_email", u_email);
                    bundle.putString("user_code", btotalcodes);
                    bundle.putString("user_qty", String.format("%.2f", btotalqty));
                    bundle.putString("user_price", String.format("%.2f", btotalprice));
                    intent.putExtras(bundle);
                    startActivity(intent);

                    finish();
                    return;

                } else {
                    txtResponse.setText("u-cycle: " + "Cart is empty");
                }
            }
        });
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
                    if (option.equals("check")) {
                        loadIntoListViewCheck(s);
                    } else if (option.equals("read")) {
                        loadIntoListView(s);
                    }
                } catch (JSONException e) {
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
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

    private void loadIntoListView(String json) throws JSONException {
        JSONObject employee = new JSONObject(json);
        JSONArray jsonArray = employee.getJSONArray("RECORDS");

        i_id = new String[jsonArray.length()];
        i_bartercode = new String[jsonArray.length()];
        i_bartername = new String[jsonArray.length()];
        i_description = new String[jsonArray.length()];
        i_price = new String[jsonArray.length()];
        i_barterpicture = new String[jsonArray.length()];
        i_registrationdate = new String[jsonArray.length()];

        double rcl_price;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            try {
                i_id[i] = obj.getString("#id");
                i_bartercode[i] = obj.getString("#bartercode");
                i_bartername[i] = obj.getString("#bartername");
                i_description[i] = obj.getString("#description");


                i_price[i] = obj.getString("#price");
                rcl_price = Double.parseDouble(i_price[i]) / 500;
                i_price[i] = String.format("%.2f", rcl_price);

                i_barterpicture[i] = obj.getString("#barterpicture");
                i_registrationdate[i] = obj.getString("#registrationdate");

            } catch (Exception e) {
            }
        }

        txtResponse.setText("u-cycle: Ready..");
        gvItem.setAdapter(new StoreImageAdapter(this, i_bartername, i_description, i_price, i_barterpicture, i_bartercode));
    }

    private void loadIntoListViewCheck(String json) throws JSONException {
        JSONObject obj0 = new JSONObject(json);

        try {
            gResponse = obj0.getString("message");

            if (gResponse.equals("NotFound")) {
                option = "read";
                getJSON("https://u-cycle.app/15U-CycleWeb/api/product/readstore.php");

            } else {
                Toast.makeText(getApplicationContext(), "Previous barter in progress please wait", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
        }
    }

    protected void onStart() {
        super.onStart();
        Toaster();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public static void Toaster() {
        if (ProfileActivity.sListBarterBackup != null) {
            bsList = ProfileActivity.sListBarterBackup;
        }

        btotalqty = 0.0;
        btotalprice = 0.0;
        btotalcodes = "";

        for (int i = 0; i < bsList.size(); i++) {
            String[] separated = bsList.get(i).toString().split("#");
            btotalqty += Double.parseDouble(separated[1]);
            btotalcodes += separated[0] + ",";
            btotalprice += Double.parseDouble(separated[1]) * Double.parseDouble(separated[3]);
        }

        ProfileActivity.sListBarterBackup = bsList;
        String d2p = String.format("%.2f", btotalprice);
        tvWeight.setText("Cart total: " + d2p + " " + ProfileActivity.symbol +
                "\nWallet bal: " + ProfileActivity.uBalance + " " + ProfileActivity.symbol);

    }
}
