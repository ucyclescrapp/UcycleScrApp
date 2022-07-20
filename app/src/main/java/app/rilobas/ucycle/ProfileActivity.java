// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import android.app.Activity;
import android.app.Application;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class ProfileActivity extends Activity {

    public static ArrayList<String> sListValueBackup;
    public static ArrayList<String> sListWeightBackup;
    public static ArrayList<String> sListBarterBackup;

    ListView listView;
    ImageButton ibLogout;

    ImageButton ibSupport;
    ImageButton ibHelp;

    TextView tvCarousel;
    Integer inf;
    String tInfo;
    String tMessage;
    Integer tLShow = 50;
    String[] i_notification;

    static final String[] MENU_OS = new String[]{
            "Value My Waste",
            "My Wallet",
            "Redemption Centres",
            "Barter Shop"
    };

    String u_email;
    String pad;

    String option;
    Boolean mapStatus = false;

    String[] i_id;
    String[] i_username;
    String[] i_allocated;
    String[] i_walladd;

    String usernameWalletAddress;

    public static double uBalance;
    public static String symbol = "UCL";

    int c = 0;
    ImageView ivb;

    TextView tve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_profile);


        tve = (TextView) findViewById(R.id.textView5);

        TextView txtUsermail = (TextView) findViewById(R.id.txtPagename);
        tvCarousel = (TextView) findViewById(R.id.txtCarousel);
        final ImageView imgv = (ImageView) findViewById(R.id.imageView2);

        listView = (ListView) findViewById(R.id.listView1);
        listView.setVisibility(View.INVISIBLE);

        ibLogout = (ImageButton) findViewById(R.id.imagebuttontop1);
        ibSupport = (ImageButton) findViewById(R.id.imagebuttontop2);
        ibHelp = (ImageButton) findViewById(R.id.imagebuttontop3);


        Bundle bundle = getIntent().getExtras();
        u_email = bundle.getString("user_email");
        txtUsermail.setText(u_email);

        //
        pad = "";
        for (int i = 0; i < tLShow; i++) {
            pad += " ";
        }
        option = "readNotes";

        getJSON(MainActivity.app_url + "/product/readnotes.php");
        //


        final Intent intent1 = new Intent(ProfileActivity.this, ValueSelectActivity.class);
        Bundle bundlep = new Bundle();
        bundlep.putString("user_email", u_email);
        intent1.putExtras(bundlep);

        final Intent intent2 = new Intent(ProfileActivity.this, WalletActivity.class);
        intent2.putExtras(bundlep);

        final Intent intent3 = new Intent(ProfileActivity.this, RedemptionActivity.class);
        intent3.putExtras(bundlep);

        final Intent intent4 = new Intent(ProfileActivity.this, ConsumerActivity.class);
        intent4.putExtras(bundlep);

        inf = 0;


        listView.setAdapter(new ImageAdapter(this, MENU_OS));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Animation anim3 = AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.anim3);
                imgv.startAnimation(anim3);

                if (position == 0) {
                    startActivity(intent1);
                } else if (position == 1) {
                    startActivity(intent2);
                } else if (position == 2) {
                    startActivity(intent3);
                } else if (position == 3) {
                    startActivity(intent4);
                }
            }
        });


        ivb = (ImageView) findViewById(R.id.imageView);
        ibLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim3 = AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.anim3);
                ibLogout.startAnimation(anim3);

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });


        ibSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim3 = AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.anim3);
                ibSupport.startAnimation(anim3);

                Intent intent = new Intent(ProfileActivity.this, PhoneActivity.class);
                startActivity(intent);
                return;

            }
        });


        ibHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim3 = AnimationUtils.loadAnimation(ProfileActivity.this, R.anim.anim3);
                ibHelp.startAnimation(anim3);

                Intent intent = new Intent(ProfileActivity.this, FaqActivity.class);
                startActivity(intent);
                return;

            }
        });

    }

    final Thread timerThread = new Thread() {
        public void run() {

            while (true) {
                try {

                    sleep(150);
                } catch (InterruptedException e) {
                } finally {

                    ProfileActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            int cnt = tMessage.length() + tLShow;
                            if (inf > cnt) {
                                inf = 0;
                            }

                            tvCarousel.setText(String.valueOf(tInfo.substring(inf, tLShow + inf)));
                            inf++;
                        }
                    });

                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
                    if (option == "readNotes") {
                        ShowNotes(s);
                    } else if (option == "readMap") {
                        ReadMap(s);
                    } else if (option == "readEtherscanBalance") {
                        ReadEthBal(s);
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

    private void ShowNotes(String json) throws JSONException {


        JSONObject employee = new JSONObject(json);
        JSONArray jsonArray = employee.getJSONArray("RECORDS");

        i_notification = new String[jsonArray.length()];

        tMessage = "";
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            try {
                i_notification[i] = obj.getString("#notification");
            } catch (Exception e) {
            }
        }

        List<String> strList = Arrays.asList(i_notification);
        String welcome = strList.get(0);
        Collections.shuffle(strList);
        String[] n_shuffled = strList.toArray(new String[strList.size()]);

        tMessage += welcome + "    > > >    "; // Welcome
        for (int i = 0; i < strList.size(); i++) {
            tMessage += n_shuffled[i] + "    > > >    ";
        }

        tInfo = pad + tMessage + pad;

        timerThread.start();


        String ue_email = Uri.encode(u_email);
        option = "readMap";
        getJSON(MainActivity.app_url +  "/product/checkmap.php?m_us=" + ue_email);
    }

    private void ReadMap(String json) throws JSONException {

        JSONObject employee = new JSONObject(json);
        JSONArray jsonArray = employee.getJSONArray("RECORDS");


        if (jsonArray.length() == 1) {
            i_id = new String[jsonArray.length()];
            i_username = new String[jsonArray.length()];
            i_allocated = new String[jsonArray.length()];
            i_walladd = new String[jsonArray.length()];


            JSONObject obj = jsonArray.getJSONObject(0);
            {
                i_id[0] = obj.getString("#id");
                i_username[0] = obj.getString("#username");
                i_allocated[0] = obj.getString("#allocated");
                i_walladd[0] = obj.getString("#walladd");

            }


            if (i_username[0].equals(u_email) && i_allocated[0].equals("Y")) {
                mapStatus = true;
            } else {
                mapStatus = false;
            }
        }

        usernameWalletAddress = i_walladd[0];
        if (mapStatus) {
            listView.setVisibility(View.VISIBLE);
            Toast.makeText(ProfileActivity.this, u_email + " provisioned\n(address: " + i_walladd[0] + ")", Toast.LENGTH_SHORT).show();

            tve.setVisibility(View.INVISIBLE);


            usernameWalletAddress = i_walladd[0];
            String contractAddress = "0x6342bB3a47Aa9e5eA62D27Fb979Aa6A7c5dEa5B9";
            option = "readEtherscanBalance";
            String apikey = "RYWJSJIXVPWXWXFA64442R2VR363RHCKES";
            getJSON("https://api.etherscan.io/api?module=account&action=tokenbalance&contractaddress=" + contractAddress + "&address=" + usernameWalletAddress + "&tag=latest&apikey=" + apikey + "");

        } else {
            listView.setVisibility(View.INVISIBLE);
            Toast.makeText(ProfileActivity.this, "Email verified!", Toast.LENGTH_LONG).show();

            tve.setText("Your account is currently being provisioned, try again in a few minutes. \n\n If you still cannot access the app services, send us a message here: \nhelp@u-cycle.app");
            tve.setVisibility(View.VISIBLE);
        }
    }

    private void ReadEthBal(String json) throws JSONException {

        JSONObject employee = new JSONObject(json);
        String ethValue = "10,000";
        String e_status, e_message, e_result;
        {
            e_status = employee.getString("status");
            e_message = employee.getString("message");
            e_result = employee.getString("result");
        }

        uBalance = Double.parseDouble(e_result) / Double.parseDouble(ethValue.replace(",", ""));

        Toast.makeText(ProfileActivity.this, String.valueOf(uBalance) + " " + ProfileActivity.symbol, Toast.LENGTH_SHORT).show();

    }
}
