// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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

public class ValueActivity extends Activity {

    String[] i_id;
    String[] i_itemcode;
    String[] i_itemname;
    String[] i_description;
    String[] i_price;
    String[] i_itempicture;
    String[] i_registrationdate;
    String[] i_category;
    String[] i_color;
    String[] i_quantity;
    String[] i_weight;
    String[] i_plastictype;

    Spinner plasticSelect;
    String[] bsh;

    GridView gView;

    public static ArrayList<String> sList;
    public static TextView tvWeight;
    public static Double totalmass;
    public static String totalwastes;
    public static Double totalprice;

    ImageButton ibSchedule;
    ImageButton ibClearSchedule;

    boolean cartCleared = false;

    private TextView txtResponse;

    private String user_email;
    String sort_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_value);


        Bundle bundle = getIntent().getExtras();
        user_email = bundle.getString("user_email");


        txtResponse = (TextView) findViewById(R.id.textViewRes);
        txtResponse.setText("u-cycle: Please wait..");

        sList = new ArrayList<String>();

        tvWeight = (TextView) findViewById(R.id.textView16);
        gView = (GridView) findViewById(R.id.GridView1);
        totalmass = 0.0;
        totalprice = 0.0;


        plasticSelect = (Spinner) findViewById(R.id.spinnerPl);
        bsh = new String[]{
                "Select waste type..",
                "PLASTIC",
                "METAL CAN",
                "ALUMINIUM CAN",
                "PAPER"
        };

        getJSON("https://u-cycle.app/15U-CycleWeb/api/product/read.php");


        final ImageView imgv = (ImageView) findViewById(R.id.imageView4);

        gView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Animation anim3 = AnimationUtils.loadAnimation(ValueActivity.this, R.anim.anim3);
                imgv.startAnimation(anim3);

                String[] currentUrl = gView.getItemAtPosition(position).toString().split("/");
                String currentPos = currentUrl[currentUrl.length - 1];

                txtResponse.setText("");

                int cp = -1;

                for (int i = 0; i < i_id.length; i++) {

                    if (currentPos.equals(i_itempicture[i] + ".png")) {
                        cp = i;
                        break;
                    }
                }

                if (cp > -1) {

                    Intent intent = new Intent(ValueActivity.this, ItemActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putString("_id", i_id[cp]);
                    bundle.putString("_itemcode", i_itemcode[cp]);
                    bundle.putString("_itemname", i_itemname[cp]);
                    bundle.putString("_description", i_description[cp]);
                    bundle.putString("_price", i_price[cp]);
                    bundle.putString("_itempicture", i_itempicture[cp]);
                    bundle.putString("_registrationdate", i_registrationdate[cp]);
                    bundle.putString("_category", i_category[cp]);
                    bundle.putString("_color", i_color[cp]);
                    bundle.putString("_quantity", i_quantity[cp]);
                    bundle.putString("_weight", i_weight[cp]);
                    bundle.putString("_plastictype", i_plastictype[cp]);

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

                Animation anim3 = AnimationUtils.loadAnimation(ValueActivity.this, R.anim.anim3);
                ibSchedule.startAnimation(anim3);

                if (sList.size() != 0) {

                    Intent intent = new Intent(ValueActivity.this, CartConfirm.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("action_flag", "value");
                    bundle.putString("user_email", user_email);
                    bundle.putString("user_code", totalwastes);
                    bundle.putString("user_weight", String.format("%.2f", totalmass));
                    bundle.putString("user_price", String.format("%.2f", totalprice));
                    intent.putExtras(bundle);
                    startActivity(intent);

                    finish();
                    return;


                } else {
                    Toast.makeText(getApplicationContext(), "u-cycle: Add waste items first", Toast.LENGTH_SHORT).show();

                    txtResponse.setText("u-cycle: " + "Cart is empty");
                }
            }
        });


        plasticSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sort_type = String.valueOf(plasticSelect.getItemAtPosition(position));

                if (position != 0) {

                    Animation anim3 = AnimationUtils.loadAnimation(ValueActivity.this, R.anim.anim3);
                    imgv.startAnimation(anim3);

                    switch (position) {
                        case (1):
                            break;
                        case (2):
                            break;
                        case (3):
                            break;
                        case (4):
                            break;
                    }

                    SortItems(sort_type);
                } else {
                    gView.setAdapter(null);
                    txtResponse.setText("u-cycle: Ready..");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void SortItems(String q) {

        int count = 0;
        for (int i = 0; i < i_id.length; i++) {
            if (i_plastictype[i].equals(q)) {
                count++;
            }
        }

        String[] ni_id = new String[count];
        String[] ni_itemcode = new String[count];
        String[] ni_itemname = new String[count];
        String[] ni_description = new String[count];
        String[] ni_price = new String[count];
        String[] ni_itempicture = new String[count];
        String[] ni_registrationdate = new String[count];
        String[] ni_category = new String[count];
        String[] ni_color = new String[count];
        String[] ni_quantity = new String[count];
        String[] ni_weight = new String[count];
        String[] ni_plastictype = new String[count];

        txtResponse.setText("u-cycle: " + String.valueOf(count) + " " + sort_type.toLowerCase() + " items.");

        int ni = 0;
        for (int i = 0; i < i_id.length; i++) {
            if (i_plastictype[i].equals(q)) {

                ni_id[ni] = i_id[i];
                ni_itemcode[ni] = i_itemcode[i];
                ni_itemname[ni] = i_itemname[i];
                ni_description[ni] = i_description[i];
                ni_price[ni] = i_price[i];
                ni_itempicture[ni] = i_itempicture[i];
                ni_registrationdate[ni] = i_registrationdate[i];
                ni_category[ni] = i_category[i];
                ni_color[ni] = i_color[i];
                ni_quantity[ni] = i_quantity[i];
                ni_weight[ni] = i_weight[i];
                ni_plastictype[ni] = i_plastictype[i];

                ni++;
            }
        }


        gView.setAdapter(new GImageAdapter(this, ni_itemname, ni_itempicture));
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

        //JSONObject obj0 = new JSONObject(json);
        JSONObject employee = new JSONObject(json);
        JSONArray jsonArray = employee.getJSONArray("RECORDS");

        i_id = new String[jsonArray.length()];

        i_itemcode = new String[jsonArray.length()];
        i_itemname = new String[jsonArray.length()];
        i_description = new String[jsonArray.length()];
        i_price = new String[jsonArray.length()];
        i_itempicture = new String[jsonArray.length()];
        i_registrationdate = new String[jsonArray.length()];
        i_category = new String[jsonArray.length()];
        i_color = new String[jsonArray.length()];
        i_quantity = new String[jsonArray.length()];
        i_weight = new String[jsonArray.length()];
        i_plastictype = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            try {
                i_id[i] = obj.getString("#id");

                i_itemcode[i] = obj.getString("#itemcode");
                i_itemname[i] = obj.getString("#itemname");
                i_description[i] = obj.getString("#description");
                i_price[i] = obj.getString("#price");
                i_itempicture[i] = obj.getString("#itempicture");
                i_registrationdate[i] = obj.getString("#registrationdate");
                i_category[i] = obj.getString("#category");
                i_color[i] = obj.getString("#color");
                i_quantity[i] = obj.getString("#quantity");
                i_weight[i] = obj.getString("#weight");
                i_plastictype[i] = obj.getString("#plastictype");

            } catch (Exception e) {
            }
        }

        //Toast.makeText(getApplicationContext(), String.valueOf(i_id.length), Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), String.valueOf("u-cycle: ready"), Toast.LENGTH_SHORT).show();

        ArrayAdapter<String> adaptr = new ArrayAdapter<String>(this, R.layout.spinner_item, bsh);
        plasticSelect.setAdapter(adaptr);

        //txtResponse.setText("u-cycle: Ready..");
        //lvList.setAdapter(new ItemsImageAdapter(this, i_itemname, i_itempicture));

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

        if (ProfileActivity.sListValueBackup != null) {
            sList = ProfileActivity.sListValueBackup;
        }

        totalmass = 0.0;
        totalprice = 0.0;
        totalwastes = "";


        for (int i = 0; i < sList.size(); i++) {
            String[] separated = sList.get(i).toString().split("#");

            totalmass += Double.parseDouble(separated[1]);

            totalwastes += separated[0] + ",";

            totalprice += Double.parseDouble(separated[1]) * Double.parseDouble(separated[3]);

        }


        ProfileActivity.sListValueBackup = sList;

        String d2w = String.format("%.2f", totalmass);
        String d2p = String.format("%.2f", totalprice);
        tvWeight.setText("Net weight: " + d2w + "kg \nN" + d2p + "k");

    }

    public void ProceedConfirm() {

        Intent intent = new Intent(ValueActivity.this, CartConfirm.class);
        Bundle bundle = new Bundle();
        bundle.putString("action_flag", "value");
        bundle.putString("user_email", user_email);
        bundle.putString("user_code", totalwastes);
        bundle.putString("user_weight", String.format("%.2f", totalmass));
        bundle.putString("user_price", String.format("%.2f", totalprice));
        intent.putExtras(bundle);
        startActivity(intent);

        finish();
        return;
    }
}
