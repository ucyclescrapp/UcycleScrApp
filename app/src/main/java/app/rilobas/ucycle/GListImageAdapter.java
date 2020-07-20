// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GListImageAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] itemName;
    private final String[] itemDescription;
    private final String[] itemPrice;
    private final String[] itemPicture;
    private final String[] itemCode;

    ArrayList<String> ThumbIds = new ArrayList<String>();

    int imageTotal;
    public String[] mThumbIds;

    public GListImageAdapter(Context c, String[] itemName, String[] itemDescription, String[] itemPrice, String[] itemPicture, String[] itemCode) {
        mContext = c;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemPicture = itemPicture;
        this.itemCode = itemCode;

        imageTotal = itemPicture.length;
        for (int i = 0; i < imageTotal; i++) {

            ThumbIds.add("https://u-cycle.app/15U-CycleWeb/api/images/" + itemPicture[i] + ".png");
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

    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView = new View(mContext);
        gridView = inflater.inflate(R.layout.storeproduct2, null);

        ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);

        String url = getItem(position);
        Picasso.with(mContext)
                .load(url)
                .placeholder(R.drawable.uload)
                .fit()
                .centerCrop().into(imageView);


        final TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label1);
        textView.setText(itemName[position]);

        TextView textView2 = (TextView) gridView.findViewById(R.id.grid_item_label2);
        textView2.setText(itemDescription[position]);

        final TextView textView3 = (TextView) gridView.findViewById(R.id.grid_item_label3);
        textView3.setText(itemCode[position]);

        double uclPrice = Double.parseDouble(itemPrice[position]);
        TextView textView4 = (TextView) gridView.findViewById(R.id.grid_item_label4);
        textView4.setText("Total Price: " + String.format("%.2f", uclPrice) + " " + ProfileActivity.symbol);


        return gridView;
    }
}