package com.ruuha.bmscms;

import co.uk.rushorm.core.RushObject;

/**
 * Created by ruuha on 10/3/2017.
 */

public class EventHelper extends RushObject {

    private String name,description;
    private String date;

    public EventHelper(){}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }


}

