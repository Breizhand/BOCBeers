package com.boc.beers;

import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;

/**
 * Created by Cédric on 15/12/2016.
 */
public class Beer {

    String name;
    String id;
    double alcohol;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public  Beer(BasicDBObject dbObject){
        this.id = ((ObjectId) dbObject.get("_id")).toString();
        this.name = dbObject.getString("name");
        this.alcohol = dbObject.getDouble("alcohol");
    }


}
