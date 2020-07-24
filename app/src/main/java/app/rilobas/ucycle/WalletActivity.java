// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class WalletActivity extends Activity {

    ListView listView;
    TextView tvExchange;

    //DatabaseReference reff;
    //Double myBalance;
    //Double myETHBalance;
    public static TextView tvBal;

    static final String[] MENU_OS = new String[]{
            "Transaction History",
            "Transfer to Bank Account",
            "Convert to Crypto"
    };

    ImageButton ibBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wallet);

        ibBack = (ImageButton) findViewById(R.id.imageButton7);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim3 = AnimationUtils.loadAnimation(WalletActivity.this, R.anim.anim3);
                ibBack.startAnimation(anim3);

                finish();

            }
        });

        Bundle bundle = getIntent().getExtras();
        final String u_email = bundle.getString("user_email");
        String exc_rate = "Exchange Rate: -";

        Bundle bundleu = new Bundle();
        bundleu.putString("user_email", u_email);

        tvBal = (TextView) findViewById(R.id.txtWallBal);
        tvBal.setText("Balance:" + "\n" +
                ProfileActivity.symbol + ": " + ProfileActivity.uBalance
        );

        final Intent intent_bank = new Intent(WalletActivity.this, BankActivity.class);
        intent_bank.putExtras(bundleu);

        final Intent intent_cryp = new Intent(WalletActivity.this, CryptoActivity.class);
        intent_cryp.putExtras(bundleu);

        final Intent intent_hist = new Intent(WalletActivity.this, HistoryActivity.class);
        intent_hist.putExtras(bundleu);

        final ImageView imgv = (ImageView) findViewById(R.id.imageView2);

        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(new ImageAdapter(this, MENU_OS));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Animation anim3 = AnimationUtils.loadAnimation(WalletActivity.this, R.anim.anim3);
                imgv.startAnimation(anim3);
                if (position == 0) {
                    startActivity(intent_hist);
                } else if (position == 1) {
                    startActivity(intent_bank);
                } else if (position == 2) {
                    startActivity(intent_cryp);
                }
            }
        });


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
                        myETHBalance = 0.0;

                    }
                }

                tvBal.setText("Balance:" + "\n" +
                        ProfileActivity.symbol + ": " + ProfileActivity.uBalance
                );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


        tvExchange = (TextView) findViewById(R.id.textViewExc);
        tvExchange.setText(exc_rate);


    }
}
