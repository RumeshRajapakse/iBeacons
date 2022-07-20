package com.Creswellcrags;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Creswellcrags.Model.BeaconModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BeaconAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<BeaconModel> OpenOrderList;
    public LayoutInflater inflater;


    public BeaconAdapter(Context context, ArrayList<BeaconModel> OpenOrderList) {
        this.context = context;
        this.OpenOrderList = OpenOrderList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return OpenOrderList.size();
    }

    @Override
    public Object getItem(int i) {
        return OpenOrderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertview, ViewGroup parent) {

        ViewHolder holder;
        if (convertview == null) {
            holder = new ViewHolder();
            convertview = inflater.inflate(R.layout.list_item_device, null);
            holder.url = (TextView) convertview.findViewById(R.id.url);
            holder.name = (TextView) convertview.findViewById(R.id.name);
            holder.mainview = (View) convertview.findViewById(R.id.mainview);
            holder.img = (ImageView) convertview.findViewById(R.id.img);


            convertview.setTag(holder);
        } else {
            holder = (ViewHolder) convertview.getTag();
        }
        try {
            Picasso.get().load(OpenOrderList.get(position).image).into(holder.img);
        } catch (Exception e) {

        }
        holder.name.setText(OpenOrderList.get(position).Title);
        holder.url.setText(OpenOrderList.get(position).desc);
        holder.mainview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!OpenOrderList.get(position).Url.equals("")) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("url", OpenOrderList.get(position).Url);
                    context.startActivity(intent);
                }
            }
        });
        return convertview;
    }

    public class ViewHolder {
        TextView name, url;
        View mainview;
        ImageView img;

    }


}