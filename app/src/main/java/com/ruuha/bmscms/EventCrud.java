package com.ruuha.bmscms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.riddhimanadib.formmaster.helper.FormBuildHelper;
import me.riddhimanadib.formmaster.model.FormElement;
import me.riddhimanadib.formmaster.model.FormHeader;
import me.riddhimanadib.formmaster.model.FormObject;

/**
 * Created by ruuha on 10/3/2017.
 */

public class EventCrud  extends AppCompatActivity {
    RecyclerView mRecyclerView;
    FormBuildHelper mFormBuilder;
    Button saveBtn,deleteBtn;
    EventHelper eventHelper;
    String id=null;
    Boolean isForUpdate = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_event);

        this.receiveData();
        this.initializeViews();

    }

    private void initializeViews() {
        // initialize variables
        mRecyclerView = (RecyclerView) findViewById(R.id.newRecyclerView);
        saveBtn = (Button) findViewById(R.id.newBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);

        deleteBtn.setEnabled(isForUpdate);

        mFormBuilder = new FormBuildHelper(this, mRecyclerView);

        // declare form elements
        FormHeader header = FormHeader.createInstance().setTitle("AVAILABLE EVENTS");
        final FormElement nameEditText = FormElement.createInstance().setType(FormElement.TYPE_EDITTEXT_TEXT_SINGLELINE).setTitle("Event").setHint("Event Name....");
        final FormElement descEditText = FormElement.createInstance().setType(FormElement.TYPE_EDITTEXT_TEXT_MULTILINE).setTitle("Description").setHint("Event Description....");
        final FormElement publishDate = FormElement.createInstance().setType(FormElement.TYPE_PICKER_DATE).setTitle("Event Date.....");

        if (isForUpdate) {
            nameEditText.setValue(eventHelper.getName());
            descEditText.setValue(eventHelper.getDescription());
            publishDate.setValue(eventHelper.getDate());
        }
        // add them in a list
        List<FormObject> formItems = new ArrayList<>();
        formItems.add(header);
        formItems.add(nameEditText);
        formItems.add(descEditText);
        formItems.add(publishDate);

        // build and display the form
        mFormBuilder.addFormElements(formItems);
        mFormBuilder.refreshView();

        //INSERT DATA OR UPDATE EXISTING DATA
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isForUpdate) {
                    //INSERT NEW DATA
                    EventHelper eventHelper = new EventHelper();
                    eventHelper.setName(nameEditText.getValue());
                    eventHelper.setDescription(descEditText.getValue());
                    eventHelper.setDate(publishDate.getValue());
                    if(MyDataManager.add(eventHelper))
                    {
                        nameEditText.setValue("");
                        descEditText.setValue("");
                        publishDate.setValue("");
                        mFormBuilder.refreshView();
                        Toast.makeText(getApplicationContext(), "Successfully Saved Data", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getApplicationContext(), "Unable To Insert data", Toast.LENGTH_SHORT).show();
                    }


                } else {

                    if (eventHelper != null) {
                        //UPDATE EXISTING DATA
                        EventHelper newEvent=new EventHelper();
                        newEvent.setName(nameEditText.getValue());
                        newEvent.setDescription(descEditText.getValue());
                        newEvent.setDate(publishDate.getValue());
                        if(MyDataManager.update(eventHelper.getId(),newEvent))
                        {
                            Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(getApplicationContext(), "Unable To Update", Toast.LENGTH_SHORT).show();

                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Event list is Null.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //DELETE DATA FROMSQLITE DATABASE
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isForUpdate)
                {
                    if (eventHelper != null)
                    {
                        if(MyDataManager.delete(eventHelper))
                        {
                            nameEditText.setValue("");
                            descEditText.setValue("");
                            publishDate.setValue("");
                            mFormBuilder.refreshView();

                            deleteBtn.setEnabled(isForUpdate=false);
                            saveBtn.setEnabled(false);
                            Toast.makeText(getApplicationContext(), "Deleted Successfully.", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Unable To Delete", Toast.LENGTH_SHORT).show();

                        }

                    }
                }
            }
        });
    }

    private void receiveData() {
        try {
            Intent i = this.getIntent();
            id=i.getStringExtra("KEY_EVENT");
            if(id == null)
            {
                isForUpdate=false;
            }else
            {
                eventHelper=MyDataManager.find(id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
