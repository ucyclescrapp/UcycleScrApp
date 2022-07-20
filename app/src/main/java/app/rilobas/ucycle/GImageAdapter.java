// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GImageAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] mobileValues;
    private final String[] itemPicture;

    ArrayList<String> ThumbIds = new ArrayList<String>();

    int imageTotal;
    public String[] mThumbIds;

    public GImageAdapter(Context c, String[] mobileValues, String[] itemPicture) {
        mContext = c;
        this.mobileValues = mobileValues;
        this.itemPicture = itemPicture;

        imageTotal = itemPicture.length;
        for (int i = 0; i < imageTotal; i++) {

            ThumbIds.add(MainActivity.app_url + "/images/" + itemPicture[i] + ".png");
        }

        mThumbIds = ThumbIds.toArray(new String[0]);
    }

    public int getCount() {
        return imageTotal;
    }

    @Override
    public String getItem(int position) {
        return mThumbIds[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(8, 8, 8, 8);
            //imageView.setImageResource(thumbImages[position]);

        } else {
            imageView = (ImageView) convertView;
        }
        String url = getItem(position);
        Picasso.with(mContext)
                .load(url)
                .placeholder(R.drawable.uload)
                .fit()
                .centerCrop().into(imageView);
        return imageView;
    }
}
