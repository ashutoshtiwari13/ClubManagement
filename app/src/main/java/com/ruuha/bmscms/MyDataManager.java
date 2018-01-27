package com.ruuha.bmscms;
import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.core.RushSearch;



public class MyDataManager {

    /*
 - Retrieve SQLite data using the RushSearch().find() method.
 - This returns a list of events.
  */
    public static ArrayList<EventHelper> getEvents() {
        List<EventHelper> events = new RushSearch().find(EventHelper.class);
        return (ArrayList<EventHelper>) events;
    }

    /*
        - Save eventhelper to sqlite database by calling save() method.
         */
    public static boolean add(EventHelper eh) {
        try {
            eh.save();
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /*
    - UPDATE SQLITE DATA GIVEN OLD EVENTHelper ID AND NEW EVENTHELPER
     */
    public static boolean update(String id,EventHelper eventHelper)
    {
        try
        {
            EventHelper eh = new RushSearch().whereId(id).findSingle(EventHelper.class);
            eh.setName(eventHelper.getName());
            eh.setDescription(eventHelper.getDescription());
            eh.save();

            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }
    /*
    - FIND SINGLE eventhelper FROM SQLITE GIVEN ID
     */
    public static EventHelper find(String id)
    {
        try
        {
            EventHelper eh = new RushSearch().whereId(id).findSingle(EventHelper.class);
            return eh;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /*
    - DELETE FROM SQLITE GIVEN THE eventhelper
     */
    public static boolean delete(EventHelper eventHelper )
    {
        try
        {
            EventHelper eh = new RushSearch().whereId(eventHelper.getId()).findSingle(EventHelper.class);
            eh.delete();
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
