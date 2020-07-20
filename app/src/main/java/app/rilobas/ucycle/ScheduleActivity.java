// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.PriorityQueue;

import javax.net.ssl.HttpsURLConnection;

public class ScheduleActivity extends Activity {

    Spinner locationSelect;
    String[] locsel;
    ImageButton ibSchedule;

    String option;
    String gResponse;

    String dateString;
    Boolean parsedDate;
    String baseDate;
    Date dt1 = null;
    Date dt2 = null;
    String[] mda;
    String md = "";

    private RadioGroup rg1;
    private RadioGroup rg2;

    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb4;
    private RadioButton rb5;
    private RadioButton rb6;

    //
    String wr_email;
    String wr_code;
    String wr_weight;
    String wr_price;
    String wr_location;
    String wr_pickupdate;
    String wr_pickuptime;
    String[] wr_pickupdate_serve = new String[3];

    private TextView txtResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_schedule);


        Bundle bundle = getIntent().getExtras();
        wr_email = bundle.getString("user_email");
        wr_code = bundle.getString("user_code");
        wr_weight = bundle.getString("user_weight");
        wr_price = bundle.getString("user_price");


        txtResponse = (TextView) findViewById(R.id.textViewRes);
        txtResponse.setText("u-cycle: Please wait..");


        parsedDate = false;
        option = "gDate";
        getJSON("https://u-cycle.app/15U-CycleWeb/api/misc/getst.php");

        rg1 = (RadioGroup) findViewById(R.id.radioGroup1);
        rg2 = (RadioGroup) findViewById(R.id.radioGroup2);

        rb1 = (RadioButton) findViewById(R.id.radio1);
        rb2 = (RadioButton) findViewById(R.id.radio2);

        rb4 = (RadioButton) findViewById(R.id.radio4);
        rb5 = (RadioButton) findViewById(R.id.radio5);
        rb6 = (RadioButton) findViewById(R.id.radio6);


        rg1.setVisibility(View.INVISIBLE);
        rg2.setVisibility(View.INVISIBLE);


        locationSelect = (Spinner) findViewById(R.id.spinnerLoc);
        locationSelect.setEnabled(false);
        locsel = new String[]{
                "Select Location",
                "JABI",
                "UTAKO",
                "WUSE",
                "MAITAMA",
                "MABUSHI",
                "JAHI",
                "KADO",
                "AREA 1",
                "GAMES VILLAGE",
                "DURUMI",
                "GADUWA",
                "GWARIMPA",
                "DAWAKI",
                "ASOKORO",
                "KARU",
                "CENTRAL AREA",
                "KATAMPE",
                "WUYE",
                "LUGBE",
                "KUBWA",
                "KABUSA",
                "SUNNYVALE",
                "SUNCITY",
                "GALADIMA"
        };

        ArrayAdapter<String> adaptr = new ArrayAdapter<String>(this, R.layout.spinner_item, locsel);
        locationSelect.setAdapter(adaptr);

        locationSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                wr_location = String.valueOf(locationSelect.getItemAtPosition(position));

                if (position > 0) {
                    rg1.setVisibility(View.VISIBLE);
                    rg2.setVisibility(View.VISIBLE);
                } else {
                    rg1.setVisibility(View.INVISIBLE);
                    rg2.setVisibility(View.INVISIBLE);
                }

                rg1.clearCheck();
                rg2.clearCheck();


                if (parsedDate) {

                    txtResponse.setText("");

                    mda = baseDate.split("-");
                    md = mda[0] + "-" + mda[1] + "-";

                    String[] schDates = new String[2];

                    switch (wr_location) {
                        case ("Select Location"):
                            rb1.setText("Schedule");
                            rb2.setText("Schedule");
                            break;
                        case ("JABI"):
                            schDates[0] = md + "01";
                            schDates[1] = md + "16";
                            break;
                        case ("UTAKO"):
                            schDates[0] = md + "01";
                            schDates[1] = md + "16";
                            break;
                        case ("WUSE"):
                            schDates[0] = md + "02";
                            schDates[1] = md + "17";
                            break;
                        case ("MAITAMA"):
                            schDates[0] = md + "02";
                            schDates[1] = md + "17";
                            break;
                        case ("MABUSHI"):
                            schDates[0] = md + "03";
                            schDates[1] = md + "18";
                            break;
                        case ("JAHI"):
                            schDates[0] = md + "03";
                            schDates[1] = md + "18";
                            break;
                        case ("KADO"):
                            schDates[0] = md + "03";
                            schDates[1] = md + "18";
                            break;
                        case ("GARKI"):
                            schDates[0] = md + "04";
                            schDates[1] = md + "19";
                            break;
                        case ("APO"):
                            schDates[0] = md + "04";
                            schDates[1] = md + "19";
                            break;
                        case ("AREA 1"):
                            schDates[0] = md + "05";
                            schDates[1] = md + "20";
                            break;
                        case ("GAMESVILLAGE"):
                            schDates[0] = md + "05";
                            schDates[1] = md + "20";
                            break;
                        case ("DURUMI"):
                            schDates[0] = md + "05";
                            schDates[1] = md + "20";
                            break;
                        case ("GADUWA"):
                            schDates[0] = md + "05";
                            schDates[1] = md + "20";
                            break;
                        case ("GWARIMPA"):
                            schDates[0] = md + "06";
                            schDates[1] = md + "22";
                            break;
                        case ("DAWAKI"):
                            schDates[0] = md + "06";
                            schDates[1] = md + "22";
                            break;
                        case ("ASOKORO"):
                            schDates[0] = md + "08";
                            schDates[1] = md + "23";
                            break;
                        case ("KARU"):
                            schDates[0] = md + "08";
                            schDates[1] = md + "23";
                            break;
                        case ("CENTRAL AREA"):
                            schDates[0] = md + "09";
                            schDates[1] = md + "24";
                            break;
                        case ("KATAMPE"):
                            schDates[0] = md + "10";
                            schDates[1] = md + "25";
                            break;
                        case ("WUYE"):
                            schDates[0] = md + "11";
                            schDates[1] = md + "26";
                            break;
                        case ("LUGBE"):
                            schDates[0] = md + "12";
                            schDates[1] = md + "27";
                            break;
                        case ("KUBWA"):
                            schDates[0] = md + "13";
                            schDates[1] = md + "29";
                            break;
                        case ("KABUSA"):
                            schDates[0] = md + "15";
                            schDates[1] = md + "30";
                            break;
                        case ("SUNNYVALE"):
                            schDates[0] = md + "15";
                            schDates[1] = md + "30";
                            break;
                        case ("SUNCITY"):
                            schDates[0] = md + "15";
                            schDates[1] = md + "30";
                            break;
                        case ("GALADIMA"):
                            schDates[0] = md + "15";
                            schDates[1] = md + "30";
                            break;

                    }
                    DoDates(schDates[0], schDates[1]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                rg1.setVisibility(View.INVISIBLE);
                rg2.setVisibility(View.INVISIBLE);

            }
        });

        final ImageView imgv = (ImageView) findViewById(R.id.imageView2);


        ibSchedule = (ImageButton) findViewById(R.id.imagebuttontop);
        ibSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim3 = AnimationUtils.loadAnimation(ScheduleActivity.this, R.anim.anim3);
                ibSchedule.startAnimation(anim3);
                imgv.startAnimation(anim3);

                if (!wr_location.equals("Select Location")) {
                    if (rb1.isChecked() || rb2.isChecked()) {
                        if (rb1.isChecked()) {
                            wr_pickupdate = rb1.getText().toString();
                            wr_pickupdate_serve[2] = wr_pickupdate_serve[0];
                        }
                        if (rb2.isChecked()) {
                            wr_pickupdate = rb2.getText().toString();
                            wr_pickupdate_serve[2] = wr_pickupdate_serve[1];
                        }

                        if (rb4.isChecked() || rb5.isChecked() || rb6.isChecked()) {
                            if (rb4.isChecked()) {
                                wr_pickuptime = rb4.getText().toString();
                            }
                            if (rb5.isChecked()) {
                                wr_pickuptime = rb5.getText().toString();
                            }
                            if (rb6.isChecked()) {
                                wr_pickuptime = rb6.getText().toString();
                            }

                            Intent intent = new Intent(ScheduleActivity.this, SummaryscheduleActivity.class);

                            Bundle bundle = new Bundle();
                            bundle.putString("wr_email", wr_email);
                            bundle.putString("wr_code", wr_code);
                            bundle.putString("wr_weight", wr_weight);
                            bundle.putString("wr_price", wr_price);
                            bundle.putString("wr_location", wr_location);
                            bundle.putString("wr_pickupdate_serve", wr_pickupdate_serve[2]);
                            bundle.putString("wr_pickuptime", wr_pickuptime);
                            intent.putExtras(bundle);

                            startActivity(intent);
                            finish();
                            return;

                        } else {
                            txtResponse.setText("u-cycle: " + "Select pickup time of day.");
                        }

                    } else {
                        txtResponse.setText("u-cycle: " + "Select pickup date.");
                    }
                } else {
                    txtResponse.setText("u-cycle: " + "Select pickup location.");
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
                    if (option.equals("gDate")) {
                        ShowTime(s);
                    }
                } catch (JSONException e) {
                    //Toast.makeText(RegisterActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
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

    private void ShowTime(String json) throws JSONException {

        JSONObject employee = new JSONObject(json);
        JSONArray jsonArray = employee.getJSONArray("STIME");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            try {
                dateString = obj.getString("#Year") +
                        "-" + obj.getString("#Month") +
                        "-" + obj.getString("#Day");
            } catch (Exception e) {
            }
        }

        //Toast.makeText(getApplicationContext(), dateString.toString(), Toast.LENGTH_SHORT).show();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
        }

        baseDate = df.format(date);
        parsedDate = true;

        //Toast.makeText(getApplicationContext(), "u-cycle: " + parseDate, Toast.LENGTH_SHORT).show();

        locationSelect.setEnabled(true);
        ibSchedule.setEnabled(true);

        txtResponse.setText("u-cycle: Schedule ready..");

    }


    private void DoDates(String sdt1, String sdt2) {

        try {
            Calendar calendar;
            SimpleDateFormat dateFormat;
            calendar = Calendar.getInstance();

            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dtn = dateFormat.format(calendar.getTime());
            Date dt3 = new SimpleDateFormat("yyyy-MM-dd").parse(dtn);

            dt1 = new SimpleDateFormat("yyyy-MM-dd").parse(sdt1);
            dt2 = new SimpleDateFormat("yyyy-MM-dd").parse(sdt2);


            //Calendar c1 = Calendar.getInstance();
            //c1.setTime(dateFormat.parse(sdt1));

            //Calendar c2 = Calendar.getInstance();
            //c2.setTime(dateFormat.parse(sdt2));

            //check passed date and add
            if (dt3.compareTo(dt1) > 0) {
                //c1.add(Calendar.MONTH, 1);
                //dt1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateFormat.format(c1));
                rb1.setEnabled(false);
            }
            if (dt3.compareTo(dt2) > 0) {
                //c2.add(Calendar.MONTH, 1);
                //dt2 = new SimpleDateFormat("yyyy-MM-dd").parse(dateFormat.format(c2));
                rb2.setEnabled(false);
            }


            wr_pickupdate_serve[0] = sdt1;
            wr_pickupdate_serve[1] = sdt2;
            //Toast.makeText(getApplicationContext(), "u-cycle: " + "true", Toast.LENGTH_SHORT).show();

            rb1.setEnabled(true);
            rb2.setEnabled(true);

            rb1.setText(dt1.toString().substring(0, 10) + ", " + dt1.toString().substring(dt1.toString().length() - 4, dt1.toString().length()));
            rb2.setText(dt2.toString().substring(0, 10) + ", " + dt2.toString().substring(dt2.toString().length() - 4, dt2.toString().length()));


        } catch (
                Exception e) {
        }

    }


}
