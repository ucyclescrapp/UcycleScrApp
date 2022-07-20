// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HistoryActivity extends Activity {

    String[] i_id;
    String[] i_orders;
    String[] i_total_price;
    String[] i_total_weight;
    String[] i_username;
    String[] i_pickup_location;
    String[] i_pickup_date;
    String[] i_pickup_time;
    String[] i_status;

    ListView lvh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_history);

        Bundle bundle = getIntent().getExtras();
        String user_email = bundle.getString("user_email");

        TextView tvu = findViewById(R.id.textView16);
        tvu.setText(user_email);

        lvh = findViewById(R.id.listView1);
        lvh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent = new Intent(HistoryActivity.this, RecordActivity.class);

                //
                Bundle bundle = new Bundle();
                bundle.putString("_id", i_id[position]);
                bundle.putString("_orders", i_orders[position]);
                bundle.putString("_total_price", i_total_price[position]);
                bundle.putString("_total_weight", i_total_weight[position]);
                bundle.putString("_username", i_username[position]);
                bundle.putString("_pickup_location", i_pickup_location[position]);
                bundle.putString("_pickup_date", i_pickup_date[position]);
                bundle.putString("_pickup_time", i_pickup_time[position]);
                bundle.putString("_status", i_status[position]);
                intent.putExtras(bundle);
                //
                startActivity(intent);
                return;

            }
        });

        getJSON(MainActivity.app_url +  "/product/readhistory.php?m_hu=" + user_email);
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
                    ShowHistory(s);
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

    private void ShowHistory(String json) throws JSONException {


        JSONObject employee = new JSONObject(json);
        JSONArray jsonArray = employee.getJSONArray("RECORDS");

        i_id = new String[jsonArray.length()];
        i_orders = new String[jsonArray.length()];
        i_total_price = new String[jsonArray.length()];
        i_total_weight = new String[jsonArray.length()];
        i_username = new String[jsonArray.length()];
        i_pickup_location = new String[jsonArray.length()];
        i_pickup_date = new String[jsonArray.length()];
        i_pickup_time = new String[jsonArray.length()];
        i_status = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            try {
                i_id[i] = obj.getString("#id");
                i_orders[i] = String.valueOf(i + 1) + ". " + obj.getString("#orders");
                i_total_price[i] = obj.getString("#total_price");
                i_total_weight[i] = obj.getString("#total_weight");
                i_username[i] = obj.getString("#username");
                i_pickup_location[i] = obj.getString("#pickup_location");
                i_pickup_date[i] = obj.getString("#pickup_date");
                i_pickup_time[i] = obj.getString("#pickup_time");
                i_status[i] = obj.getString("#status");

            } catch (Exception e) {
            }
        }

        //Toast.makeText(getApplicationContext(), String.valueOf(i_id.length), Toast.LENGTH_SHORT).show();

        ArrayAdapter<String> adaptr = new ArrayAdapter<String>(this, R.layout.spinner_item, i_orders) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the current view as a TextView
                TextView tv = (TextView) super.getView(position, convertView, parent);

                tv.setGravity(Gravity.LEFT | Gravity.START | Gravity.CENTER_VERTICAL);

                // Return the view
                return tv;
            }
        };

        // Data bind the list view with array adapter items
        lvh.setAdapter(adaptr);

    }
}
