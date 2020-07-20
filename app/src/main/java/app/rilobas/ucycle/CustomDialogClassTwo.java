// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class CustomDialogClassTwo extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    public String action_flag;
    public String[] action_schedule;

    public CustomDialogClassTwo(Activity a, String action_flag, String[] action_schedule) {
        super(a);
        this.c = a;

        this.action_flag = action_flag;
        this.action_schedule = action_schedule;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialogtwo);

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
                        ValueActivity va = new ValueActivity();
                        va.ProceedConfirm();
                        break;
                    case ("weight"):
                        WeightActivity wa = new WeightActivity();
                        wa.ProceedConfirm();
                        break;
                    case ("barter"):
                        CartConfirm ca = new CartConfirm();
                        ca.SendBarterSchedule(action_schedule);
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
