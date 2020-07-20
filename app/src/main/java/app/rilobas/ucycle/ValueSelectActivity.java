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

public class ValueSelectActivity extends Activity {

    String u_email;

    ListView listView;
    ImageButton ibLogout;

    static final String[] MENU_OS_VMW = new String[]{
            "Value By Selection",
            "Value By Weight",
            "My Orders",
    };

    ImageButton ibBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_value_select);

        ibBack = (ImageButton) findViewById(R.id.imageButton6);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim3 = AnimationUtils.loadAnimation(ValueSelectActivity.this, R.anim.anim3);
                ibBack.startAnimation(anim3);

                finish();

            }
        });


        TextView txtUsermail = (TextView) findViewById(R.id.txtPagename);

        Bundle bundle = getIntent().getExtras();
        u_email = bundle.getString("user_email");
        txtUsermail.setText(u_email);


        final Intent intentV = new Intent(ValueSelectActivity.this, ValueActivity.class);
        final Intent intentW = new Intent(ValueSelectActivity.this, WeightActivity.class);
        final Intent intentH = new Intent(ValueSelectActivity.this, HistoryActivity.class);

        final Bundle bundlep = new Bundle();
        bundlep.putString("user_email", u_email);

        final ImageView imgv = (ImageView) findViewById(R.id.imageView2);

        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(new ImageAdapter(this, MENU_OS_VMW));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Animation anim3 = AnimationUtils.loadAnimation(ValueSelectActivity.this, R.anim.anim3);
                imgv.startAnimation(anim3);

                if (position == 0) {
                    intentV.putExtras(bundlep);
                    startActivity(intentV);
                    finish();
                    return;
                } else if (position == 1) {
                    intentW.putExtras(bundlep);
                    startActivity(intentW);
                    finish();
                    return;
                } else if (position == 2) {
                    intentH.putExtras(bundlep);
                    startActivity(intentH);
                    finish();
                    return;
                }
            }
        });



    }
}
