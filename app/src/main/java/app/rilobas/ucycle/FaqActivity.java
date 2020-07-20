// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

public class FaqActivity extends Activity {

    ImageButton ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_faq);

        ibBack = (ImageButton) findViewById(R.id.imageButton5);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim3 = AnimationUtils.loadAnimation(FaqActivity.this, R.anim.anim3);
                ibBack.startAnimation(anim3);

                finish();

            }
        });

        final TextView tvfaq = (TextView) findViewById(R.id.textView3);
        String faqString = "<b>" + getString(R.string.faq1) + "</b><br>" + getString(R.string.faq1a)
                + "<br>" + "<br>" +
                "<b>" + getString(R.string.faq2) + "</b><br>" + getString(R.string.faq2a)
                + "<br>" + "<br>" +
                "<b>" + getString(R.string.faq3) + "</b><br>" + getString(R.string.faq3a)
                + "<br>" + "<br>" +
                "<b>" + getString(R.string.faq4) + "</b>" +
                "<br>" + getString(R.string.faq4a) + "<br>" +
                "<br>" + getString(R.string.faq4b) + "<br>" +
                "<br>" + getString(R.string.faq4c) + "<br>" +
                "<br>" + getString(R.string.faq4d) + "<br>" +
                "<br>" + getString(R.string.faq4e) + "<br>" +
                "<br>" + getString(R.string.faq4f) + "<br>" +
                "<br>" + getString(R.string.faq4g) + "<br>" + "<br>" +

                "<b>" + getString(R.string.faq5) + "</b><br>" + getString(R.string.faq5a)
                + "<br>" + "<br>" +
                "<b>" + getString(R.string.faq6) + "</b><br>" + getString(R.string.faq6a)
                + "<br>" + "<br>" +
                "<b>" + getString(R.string.faq7) + "</b><br>" + getString(R.string.faq7a)
                + "<br>" + "<br>" +
                "<b>" + getString(R.string.faq8) + "</b><br>" + getString(R.string.faq8a)
                + "<br>" + "<br>" +
                "<b>" + getString(R.string.faq9) + "</b><br>" + getString(R.string.faq9a)
                + "<br>" + "<br>" +
                "<b>" + getString(R.string.faq9_9) + "</b><br>" + getString(R.string.faq9_9a)
                + "<br>" + "<br>" +
                "<b>" + getString(R.string.faq10) + "</b><br>" + getString(R.string.faq10a)
                + "<br>" + "<br>" +
                "<b>" + getString(R.string.faq11) + "</b><br>" + getString(R.string.faq11a)
                + "<br>" + "<br>" +
                "<b>" + getString(R.string.faq12) + "</b><br>" + getString(R.string.faq12a)
                + "<br>" + "<br>" +
                "<b>" + getString(R.string.faq13) + "</b><br>" + getString(R.string.faq13a)
                + "<br>" + "<br>" +
                "<b>" + getString(R.string.faq14) + "</b><br>" + getString(R.string.faq14a)
                + "<br>" + "<br>" +
                "<b>" + getString(R.string.faq15) + "</b><br>" + getString(R.string.faq15a)
                + "<br>" + "<br>" +
                "<b>" + getString(R.string.faq16) + "</b><br>" + getString(R.string.faq16a)
                + "<br>" + "<br>";

        tvfaq.setText(Html.fromHtml(faqString));
        tvfaq.setMovementMethod(new ScrollingMovementMethod());
    }
}
