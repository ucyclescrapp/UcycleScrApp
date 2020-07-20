// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    public String action_flag;

    public CustomDialogClass(Activity a, String action_flag) {
        super(a);
        this.c = a;

        this.action_flag = action_flag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:

                switch (action_flag) {
                    case ("value"):
                        ProfileActivity.sListValueBackup = null;
                        ValueActivity.sList = null;
                        break;
                    case ("weight"):
                        ProfileActivity.sListWeightBackup = null;
                        WeightActivity.sList = null;
                        break;
                    case ("barter"):
                        ProfileActivity.sListBarterBackup = null;
                        ConsumerActivity.bsList = null;
                        break;
                }

                c.finish();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
