// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
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
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.annotation.NonNull;

public class LoginActivity extends Activity {

    private EditText mEmail, mPassword;
    private Button mLogin;
    private TextView txtResponse;
    private TextView txtFPassword;

    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    boolean emailVerified = false;
    String resMessage;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        mEmail = (EditText) findViewById(R.id.editText2);
        mPassword = (EditText) findViewById(R.id.editText3);
        mLogin = (Button) findViewById(R.id.button1);
        txtResponse = (TextView) findViewById(R.id.textViewRes);

        txtFPassword = (TextView) findViewById(R.id.textForgotPassword);

        mauth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {

                    emailVerified = user.isEmailVerified();
                    if (emailVerified)
                    {
                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("user_id", user.getUid());
                        bundle.putString("user_email", user.getEmail());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                        return;
                    } else {
                        mEmail.setText(user.getEmail());
                    }
                }
            }
        };


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mLogin.setVisibility(View.INVISIBLE);
                txtResponse.setText("");

                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();

                if ((!email.equals("")) && (!password.equals(""))) {

                    mauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        resMessage = task.getException().getMessage();

                                        txtResponse.setText("u-cycle: " + resMessage);

                                        mLogin.setVisibility(View.VISIBLE);
                                    } else {

                                        emailVerified = mauth.getCurrentUser().isEmailVerified();
                                        if (emailVerified) {
                                            //
                                            user_id = mauth.getCurrentUser().getUid();
                                            DatabaseReference current_user_db = FirebaseDatabase.getInstance()
                                                    .getReference().child("Users").child("Appusers").child(user_id);
                                            current_user_db.setValue(true);

                                            finish();
                                        } else {
                                            Toast.makeText(LoginActivity.this, "u-cycle: Please click the link sent to your mail to verify your account. (you may need to check your spam folder)", Toast.LENGTH_LONG).show();
                                            mLogin.setVisibility(View.VISIBLE);
                                        }

                                    }
                                }
                            });


                } else {
                    resMessage = "u-cycle: Empty email or password.";

                    txtResponse.setText(resMessage);
                    mLogin.setVisibility(View.VISIBLE);
                }

            }
        });


        final Button ibCreateAccount = (Button) findViewById(R.id.button2);
        ibCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
        //

        txtFPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(LoginActivity.this, "coming soon..", Toast.LENGTH_SHORT).show();
            }
        });
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

    //

}
