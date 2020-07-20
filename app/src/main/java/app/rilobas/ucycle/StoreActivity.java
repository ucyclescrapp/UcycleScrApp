// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

public class StoreActivity extends Activity {

    ListView listView;

    static final String[] MENU_OS = new String[]{
            "Fast Moving Consumer Goods",
            "Gift Items"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_store);

        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(new ImageAdapter(this, MENU_OS));

        final Intent intent1 = new Intent(StoreActivity.this, ConsumerActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (position == 0) {
                    startActivity(intent1);
                } else if (position == 1) {
                } else if (position == 2) {
                } else if (position == 3) {
                }
            }
        });
    }
}
