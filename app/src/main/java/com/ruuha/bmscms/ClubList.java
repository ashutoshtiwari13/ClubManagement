package com.ruuha.bmscms;

import android.*;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.ruuha.bmscms.R.id.imageView;
import static com.ruuha.bmscms.R.id.imageView2;

/**
 * Created by ruuha on 9/23/2017.
 */

public class ClubList extends AppCompatActivity {
    GridView gridView;
    ArrayList<CustomClubInflate> list;
    ClubListAdapter adapter=null;
    DBHelperClub dbHelperClub;
    ImageView imageViewClub;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_club);
        dbHelperClub=new DBHelperClub(this, "ClubDB.sqlite" ,null ,2);
        gridView=(GridView)findViewById(R.id.gridview);
        list=new ArrayList<>();
       adapter= new ClubListAdapter(this,R.layout.custom_clubgridview,list);
        gridView.setAdapter(adapter);



        //get data from database
        try {
            Cursor cursor = dbHelperClub.getData("SELECT * FROM CLUB_TABLE");
            list.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String cm = cursor.getString(2);
                String ct = cursor.getString(3);
                byte[] image = cursor.getBlob(4);

                list.add(new CustomClubInflate(id, name, cm,ct,image));


            }
            adapter.notifyDataSetChanged();
        }
        catch(Exception error){

            Log.e("Error In Program :", error.getMessage());
        }

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                CharSequence[] items={"Update","Delete"};
                AlertDialog.Builder Dialog=new AlertDialog.Builder(ClubList.this);

                Dialog.setTitle("choose an Action");
                Dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if(item==0){

                            Cursor c= dbHelperClub.getData("SELECT Id FROM CLUB_TABLE");
                            ArrayList<Integer> arrID=new ArrayList<Integer>();
                            while(c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                           showDialogUpdate(ClubList.this,arrID.get(position));
                        }
                        else{


                            Cursor c= dbHelperClub.getData("SELECT Id FROM CLUB_TABLE");
                            ArrayList<Integer> arrID=new ArrayList<Integer>();
                            while(c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));

                        }
                    }
                });
                Dialog.show();
                return true;
            }
        });


    }

    private void showDialogDelete(final int idclub){
        AlertDialog.Builder dialogDelete=new AlertDialog.Builder(ClubList.this);

        dialogDelete.setTitle("WARNING!!");
        dialogDelete.setMessage("Are You Sure you Want to delete ?");

        dialogDelete.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                  try{

                      dbHelperClub.deleteData(idclub);
                      Toast.makeText(getApplicationContext(), "Delete Successful!!", Toast.LENGTH_SHORT).show();

                  }
                  catch(Exception e){
                      Log.e("Error messgae :",e.getMessage());
                  }
                updateClubList();
            }
        });

    dialogDelete.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
        }
    });
        dialogDelete.show();

    }
    private void updateClubList(){
        try {
            Cursor cursor = dbHelperClub.getData("SELECT * FROM CLUB_TABLE");
            list.clear();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String cm = cursor.getString(2);
                String ct = cursor.getString(3);
                byte[] image = cursor.getBlob(4);

                list.add(new CustomClubInflate(id, name, cm,ct, image));


            }
            adapter.notifyDataSetChanged();
        }
        catch(Exception e){
            Log.e("Error Caught :",e.getMessage());
        }

    }


    private void showDialogUpdate(Activity activity, final int position){
        final Dialog dialog=new Dialog(activity);
        dialog.setContentView(R.layout.update_club_activitycopy);
        dialog.setTitle("Update");

        imageViewClub=(ImageView)dialog.findViewById(R.id.imageView3);
        final EditText editName=(EditText)dialog.findViewById(R.id.editText);
        final EditText editManager=(EditText)dialog.findViewById(R.id.editText2);
        final EditText editContact=(EditText)dialog.findViewById(R.id.editText3);
        Button btnUpdate=(Button)dialog.findViewById(R.id.btnUpdate);

        int width=(int)(activity.getResources().getDisplayMetrics().widthPixels*0.95);
        int height=(int)(activity.getResources().getDisplayMetrics().heightPixels*0.70);

        dialog.getWindow().setLayout(width,height);
        dialog.show();

        imageViewClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ClubList.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });





        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    dbHelperClub.updateData( position,editName.getText().toString().trim(),
                            editManager.getText().toString().trim(),
                            editContact.getText().toString().trim(),
                            ListClubs.imageViewToByte(imageViewClub));
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update Successfull!!", Toast.LENGTH_SHORT).show();


                }
                catch(Exception e){
                    Log.e("Update error :",e.getMessage());
                }
                updateClubList();

            }
            
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==888){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,888);
            }
            else{

                Toast.makeText(getApplicationContext(), "You don't have permission to access File location", Toast.LENGTH_SHORT).show();
            }
            return;

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==888 && resultCode==RESULT_OK && data !=null){

            Uri uri=data.getData();

            try {
                InputStream inputstream = getContentResolver().openInputStream(uri);

                Bitmap bitmap= BitmapFactory.decodeStream(inputstream);
                imageViewClub.setImageBitmap(bitmap);
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}
