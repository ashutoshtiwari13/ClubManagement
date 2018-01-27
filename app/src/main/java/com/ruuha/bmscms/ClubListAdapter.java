package com.ruuha.bmscms;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ruuha on 9/23/2017.
 */

public class ClubListAdapter extends BaseAdapter {


    private Context context;
    private int layout;
    private ArrayList<CustomClubInflate> clublist;

    public ClubListAdapter(Context context, int layout, ArrayList<CustomClubInflate> clublist) {
        this.context = context;
        this.layout = layout;
        this.clublist = clublist;
    }


    @Override
    public int getCount() {
        return clublist.size();
    }

    @Override
    public Object getItem(int position) {
        return clublist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class viewHolder{
        ImageView imageview;
        TextView txtname,txtcm,txtcontact;

    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

       View row=view;
        viewHolder holder=new viewHolder();
        if(row==null){
            LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              row=inflator.inflate(layout,null);

            holder.txtname=(TextView)row.findViewById(R.id.namedisplay);
            holder.txtcm=(TextView)row.findViewById(R.id.cmdisplay);
            holder.txtcontact=(TextView)row.findViewById(R.id.contactDisplay);
            holder.imageview=(ImageView)row.findViewById(R.id.imageView2);
            row.setTag(holder);
        }
        else{

            holder=(viewHolder)row.getTag();


        }
         CustomClubInflate customClubInflate=clublist.get(position);
         holder.txtname.setText(customClubInflate.getName());
         holder.txtcm.setText(customClubInflate.getC_manager());
        holder.txtcontact.setText(customClubInflate.getContact());

        byte[] photo=customClubInflate.getImage();
        Bitmap bitmap= BitmapFactory.decodeByteArray(photo,0,photo.length);
        holder.imageview.setImageBitmap(bitmap);
        return row;
    }
}
