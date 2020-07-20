// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.NonNull;

public class RegisterActivity extends Activity {

    private EditText mEmail, mPassword, mPassword2;
    private Button mRegistration;
    private TextView txtResponse;

    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    String resMessage;
    String user_id;
    String gResponse;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);


        txtResponse = (TextView) findViewById(R.id.textViewRes);

        mauth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {

                    getJSON("https://u-cycle.app/15U-CycleWeb/api/product/createregmapping.php?m_us=" + user.getEmail());


                }
            }
        };

        mEmail = (EditText) findViewById(R.id.editText2);
        mPassword = (EditText) findViewById(R.id.editText3);
        mPassword2 = (EditText) findViewById(R.id.editText);

        mRegistration = (Button) findViewById(R.id.button1);

        //
        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                final String password2 = mPassword2.getText().toString();

                mRegistration.setVisibility(View.INVISIBLE);
                txtResponse.setText("");

                if ((!email.equals("")) && (!password.equals(""))) {
                    if (password.equals(password2)) {
                        mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this,
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            resMessage = task.getException().getMessage();

                                            txtResponse.setText("u-cycle: " + resMessage);

                                            mRegistration.setVisibility(View.VISIBLE);

                                        } else {
                                            user_id = mauth.getCurrentUser().getUid();
                                            DatabaseReference current_user_db = FirebaseDatabase.getInstance()
                                                    .getReference().child("Users").child("Appusers").child(user_id);
                                            current_user_db.setValue(true);

                                            user.sendEmailVerification();
                                        }
                                    }
                                });
                    } else {

                        resMessage = "u-cycle: Passwords do not match!";

                        txtResponse.setText(resMessage);
                        mRegistration.setVisibility(View.VISIBLE);
                    }

                } else {
                    resMessage = "u-cycle: Empty email or password.";

                    txtResponse.setText(resMessage);
                    mRegistration.setVisibility(View.VISIBLE);
                }
            }
        });
        //
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
                    CheckResponse(s);
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

    private void CheckResponse(String json) throws JSONException {

        JSONObject obj0 = new JSONObject(json);

        try {
            gResponse = obj0.getString("message");
        } catch (Exception e) {
        }

        if (gResponse.equals("Thank you. Your account is being provisioned.")) {
            Intent intent = new Intent(RegisterActivity.this, SummaryActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("user_id", user.getUid());
            bundle.putString("user_email", user.getEmail());
            bundle.putString("u_response", gResponse);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
            return;
        } else {
            Intent intent = new Intent(RegisterActivity.this, SummaryActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("user_id", user.getUid());
            bundle.putString("user_email", user.getEmail());
            bundle.putString("u_response", user.getEmail() + ": " + gResponse);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
            return;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mauth.removeAuthStateListener(firebaseAuthListener);
    }

}
