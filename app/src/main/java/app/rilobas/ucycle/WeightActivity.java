// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class WeightActivity extends Activity {

    String[] i_id;
    String[] i_plasticcode;
    String[] i_plasticname;
    String[] i_description;
    String[] i_price;
    String[] i_plasticpicture;

    GridView gView;

    private String u_email;
    private TextView txtResponse;


    public static ArrayList<String> sList;
    public static TextView tvWeight;
    public static Double totalmass;
    public static String totalwastes;
    public static Double totalprice;

    String weight_type;

    ImageButton ibSchedule;
    ImageButton ibClearSchedule;

    boolean cartCleared = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        Bundle bundle = getIntent().getExtras();
        u_email = bundle.getString("user_email");

        txtResponse = (TextView) findViewById(R.id.textViewRes);
        txtResponse.setText("u-cycle: Please wait..");

        gView = (GridView) findViewById(R.id.GridView1);


        sList = new ArrayList<String>();


        tvWeight = (TextView) findViewById(R.id.textView16);
        totalmass = 0.0;
        totalprice = 0.0;

        getJSON(MainActivity.app_url + "/product/readtype.php");

        final ImageView imgv = (ImageView) findViewById(R.id.imageView4);

        gView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Animation anim3 = AnimationUtils.loadAnimation(WeightActivity.this, R.anim.anim3);
                imgv.startAnimation(anim3);

                String[] currentUrl = gView.getItemAtPosition(position).toString().split("/");
                String currentPos = currentUrl[currentUrl.length - 1];

                txtResponse.setText("");

                int cp = -1;

                for (int i = 0; i < i_id.length; i++) {

                    if (currentPos.equals(i_plasticpicture[i] + ".png")) {
                        cp = i;
                        break;
                    }
                }

                if (cp > -1) {

                    switch (cp) {
                        case (0):
                            break;
                        case (1):
                            break;
                        case (2):
                            break;
                        case (3):
                            break;
                    }

                    Intent intent = new Intent(WeightActivity.this, WeightItemActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putString("_id", i_id[cp]);
                    bundle.putString("_plasticcode", i_plasticcode[cp]);
                    bundle.putString("_plasticname", i_plasticname[cp]);
                    bundle.putString("_description", i_description[cp]);
                    bundle.putString("_price", i_price[cp]);
                    bundle.putString("_plasticpicture", i_plasticpicture[cp]);

                    intent.putExtras(bundle);

                    startActivity(intent);

                } else {

                }

            }
        });

        ibSchedule = (ImageButton) findViewById(R.id.imagebuttontop2);
        ibSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation anim3 = AnimationUtils.loadAnimation(WeightActivity.this, R.anim.anim3);
                ibSchedule.startAnimation(anim3);

                //double maxWei = 9.0;

                if (sList.size() != 0) {

                    Intent intent = new Intent(WeightActivity.this, CartConfirm.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("action_flag", "weight");
                    bundle.putString("user_email", u_email);
                    bundle.putString("user_code", totalwastes);
                    bundle.putString("user_weight", String.format("%.2f", totalmass));
                    bundle.putString("user_price", String.format("%.2f", totalprice));
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
                    loadIntoListView(s);

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

        i_plasticcode = new String[jsonArray.length()];
        i_plasticname = new String[jsonArray.length()];
        i_description = new String[jsonArray.length()];
        i_price = new String[jsonArray.length()];
        i_plasticpicture = new String[jsonArray.length()];


        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            try {
                i_id[i] = obj.getString("#id");

                i_plasticcode[i] = obj.getString("#plasticcode");
                i_plasticname[i] = obj.getString("#plasticname");
                i_description[i] = obj.getString("#description");
                i_price[i] = obj.getString("#price");
                i_plasticpicture[i] = obj.getString("#plasticpicture");

            } catch (Exception e) {
            }
        }

        txtResponse.setText("u-cycle: Ready..");

        gView.setAdapter(new GImageAdapter(this, i_plasticname, i_plasticpicture));
    }

    @Override
    protected void onStart() {
        super.onStart();

        Toaster();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public static void Toaster() {
        //Toast.makeText(ConsumerActivity.this, "Taodster", Toast.LENGTH_SHORT).show();

        if (ProfileActivity.sListWeightBackup != null) {
            sList = ProfileActivity.sListWeightBackup;
        }

        totalmass = 0.0;
        totalprice = 0.0;
        totalwastes = "";

        for (int i = 0; i < sList.size(); i++) {
            String[] separated = sList.get(i).toString().split("#");
            totalmass += Double.parseDouble(separated[1]);

            totalwastes += separated[0] + ",";

            totalprice += Double.parseDouble(separated[1]) * Double.parseDouble(separated[2]);

        }

        ProfileActivity.sListWeightBackup = sList;

        //totalprice = totalmass * ;
        //totalwastes = totalwastes.substring(totalwastes.length() - 1, totalwastes.length());
        String d2w = String.format("%.2f", totalmass);
        String d2p = String.format("%.2f", totalprice);
        tvWeight.setText("Net weight: " + d2w + "kg \nN" + d2p + "k");


        /*if (!cartCleared) {
            Animation anim3 = AnimationUtils.loadAnimation(WeightActivity.this, R.anim.anim1);
            ibSchedule.startAnimation(anim3);
        }*/

    }

    public void ProceedConfirm() {

        Intent intent = new Intent(WeightActivity.this, CartConfirm.class);
        Bundle bundle = new Bundle();
        bundle.putString("action_flag", "weight");
        bundle.putString("user_email", u_email);
        bundle.putString("user_code", totalwastes);
        bundle.putString("user_weight", String.format("%.2f", totalmass));
        bundle.putString("user_price", String.format("%.2f", totalprice));
        intent.putExtras(bundle);
        startActivity(intent);

        finish();
        return;
    }
}
