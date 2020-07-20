// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StoreImageAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] itemName;
    private final String[] itemDescription;
    private final String[] itemPrice;
    private final String[] itemPicture;
    private final String[] itemCode;

    ArrayList<String> ThumbIds = new ArrayList<String>();

    int imageTotal;
    public String[] mThumbIds;

    public StoreImageAdapter(Context c, String[] itemName, String[] itemDescription, String[] itemPrice, String[] itemPicture, String[] itemCode ) {
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
        gridView = inflater.inflate(R.layout.storeproduct, null);

        ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);


        String url = getItem(position);
        Picasso.with(mContext)
                .load(url)
                .placeholder(R.drawable.uload)
                .fit()
                .centerCrop().into(imageView);

        ImageView imageViewSep = (ImageView) gridView.findViewById(R.id.grid_item_sep);
        imageViewSep.setImageResource(R.drawable.separator);

        final TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label1);
        textView.setText(itemName[position]);

        //
        TextView textView2 = (TextView) gridView.findViewById(R.id.grid_item_label2);
        textView2.setText(itemDescription[position]);

        // barter item code
        final TextView textView3 = (TextView) gridView.findViewById(R.id.grid_item_label3);
        textView3.setText(itemCode[position]);

        TextView textView4 = (TextView) gridView.findViewById(R.id.grid_item_label4);
        textView4.setText("Price: " + itemPrice[position] + " " + ProfileActivity.symbol);

        TextView textView5 = (TextView) gridView.findViewById(R.id.grid_item_label5);
        //textView5.setText(itemPrice[position]);

        final Spinner spinner = (Spinner) gridView.findViewById(R.id.grid_item_spinner);


        final ImageButton bt = (ImageButton) gridView.findViewById(R.id.grid_item_button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation anim3 = AnimationUtils.loadAnimation(mContext, R.anim.anim3);
                ConsumerActivity.imv10.startAnimation(anim3);
                bt.startAnimation(anim3);

                if (spinner.getSelectedItemId() != 0) {

                    double totalItemPrice = Double.parseDouble(itemPrice[position])
                            * Double.parseDouble(String.valueOf(spinner.getSelectedItemId()));
                    if ((ConsumerActivity.btotalprice + totalItemPrice) <= ConsumerActivity.myBalance) {

                        Toast.makeText(mContext, textView.getText() + "\n" +
                                        "(" + spinner.getSelectedItem().toString() + " x " + itemPrice[position] + " " + ProfileActivity.symbol + ")" +
                                        "\n" + "Total: " + String.valueOf(totalItemPrice) + " " + ProfileActivity.symbol
                                , Toast.LENGTH_SHORT).show();


                        String itemOrder =
                                textView3.getText() + "x" + spinner.getSelectedItemId() + "#"
                                        + spinner.getSelectedItem().toString() + "#" //qty
                                        + "w" + "#"
                                        + itemPrice[position] + "#" +
                                        textView.getText();


                        String itemnameOrder =
                                textView.getText() + " (x" + spinner.getSelectedItemId() + ")#"
                                        + spinner.getSelectedItem().toString() + "#" //qty
                                        + "w" + "#"
                                        + itemPrice[position];

                        ConsumerActivity.bsList.add(itemOrder);
                        ConsumerActivity.Toaster();


                        spinner.setSelection(0);

                        ConsumerActivity.txtResponse.setText("");
                    } else {
                        spinner.setSelection(0);
                        //Toast.makeText(mContext, "u-cycle: " + "not added! selection exceeds wallet balance", Toast.LENGTH_SHORT).show();
                        ConsumerActivity.txtResponse.setText("u-cycle: " + "item not added! selection exceeds wallet balance");
                    }
                } else {
                    //Toast.makeText(mContext, "u-cycle: " + "Select valid qty first", Toast.LENGTH_SHORT).show();
                    ConsumerActivity.txtResponse.setText("u-cycle: " + "select valid Qty first");
                }
            }
        });

        return gridView;
    }
}
