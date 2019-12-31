package com.example.blue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class list_Adapter extends BaseAdapter {

    Context context;
    ArrayList <products> arrayList;
    String base_url = "https://andrioddata.s3.amazonaws.com/";

    public list_Adapter(Context context, ArrayList<products> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView ==  null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.items, parent, false);
        }
        TextView name,price,discount;
        ImageView image;
        name = convertView.findViewById(R.id.name);
        price = convertView.findViewById(R.id.price);
        image = convertView.findViewById(R.id.image);
        discount = convertView.findViewById(R.id.discount);

        if (arrayList.get(position).getImage() != null){
            try {
                String src = base_url+arrayList.get(position).getImage();
                GetBitmapFromURLAsync async = new GetBitmapFromURLAsync();
                async.execute(image,src);


            } catch (Exception e) {
                System.out.println(e);
            }
        }
        name.setText(arrayList.get(position).getName());
        price.setText(arrayList.get(position).getPrice() + "$");
        discount.setText("discount: "+arrayList.get(position).getDiscount() + "%");

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.layout_animation_fall_down);
        convertView.startAnimation(animation);


        final View finalConvertView = convertView;

        return convertView;
    }

    private class GetBitmapFromURLAsync extends AsyncTask<Object, Object, Bitmap> {
        private ImageView iv;
        @Override
        protected Bitmap doInBackground(Object... params) {
            iv = (ImageView) params[0];
            return getBitmapFromURL(params[1].toString());
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null){
                iv.setImageBitmap(bitmap);
            }
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            if (src.contains("null")){
                return null;
            }
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
