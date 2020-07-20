// U-CYCLE SCRAPP https://u-cycle.app
// Developed by rilwan.at@gmail.com


package app.rilobas.ucycle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private final String[] mobileValues;

    public ImageAdapter(Context context, String[] mobileValues) {
        this.context = context;
        this.mobileValues = mobileValues;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);
            gridView = inflater.inflate(R.layout.menu, null);

            TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
            textView.setText(mobileValues[position]);

            ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);

            String menu = mobileValues[position];
            if (menu.equals("Value My Waste")) {
                imageView.setImageResource(R.drawable.umenu1);
            } else if (menu.equals("My Wallet")) {
                imageView.setImageResource(R.drawable.umenu2);
            } else if (menu.equals("Redemption Centres")) {
                imageView.setImageResource(R.drawable.umenu3);
            }else if (menu.equals("Barter Shop")) {
                imageView.setImageResource(R.drawable.umenu11);
            }
            //
            else if (menu.equals("Transaction History")) {
                imageView.setImageResource(R.drawable.umenu10);
            } else if (menu.equals("Transfer to Bank Account")) {
                imageView.setImageResource(R.drawable.umenu4);
            } else if (menu.equals("Convert to Crypto")) {
                imageView.setImageResource(R.drawable.umenu5);
            }
            //
            //
            else if (menu.equals("Value By Selection")) {
                imageView.setImageResource(R.drawable.umenu6);
            } else if (menu.equals("Value By Weight")) {
                imageView.setImageResource(R.drawable.umenu7);
            } else if (menu.equals("My Orders")) {
                imageView.setImageResource(R.drawable.umenu10);
            }
            //
            //
            else if (menu.equals("Fast Moving Consumer Goods")) {
                imageView.setImageResource(R.drawable.uload);
            } else if (menu.equals("Gift Items")) {
                imageView.setImageResource(R.drawable.uload);
            }

            else {
                imageView.setImageResource(R.drawable.uload);
            }


        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return mobileValues.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
