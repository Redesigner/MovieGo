package stephen.moviego;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> drawableUris;

    public ImageAdapter(Context c, ArrayList<String> ids) {
        mContext = c;
        drawableUris = ids;
    }

    public int getCount() {
        return drawableUris.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        float dpWidth = (displayMetrics.widthPixels)/3-8;
        float dpHeight =  dpWidth * 1.5f; //get a size of 3:2
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setClickable(true);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("Image Adapter",""+position);
                    Intent intent = new Intent(mContext,MovieInformation.class);
                    intent.putExtra("Id",position);
                    mContext.startActivity(intent);
                }
            });
            imageView.setLayoutParams(new GridView.LayoutParams(Math.round(dpWidth),Math.round(dpHeight)));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(3, 3, 3, 3);
        } else {
            imageView = (ImageView) convertView;
        }
        Uri imageLocation = Uri.parse("http://image.tmdb.org/t/p/w185/")
                .buildUpon()
                .appendEncodedPath(drawableUris.get(position))
                .build();
        Log.e("tag",imageLocation.toString());
        Picasso.with(mContext).load(imageLocation).into(imageView);
        return imageView;
    }
}