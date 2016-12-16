package com.boc.beers;

import com.mongodb.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Cédric on 15/12/2016.
 */

public class BeerDao {

    static List<Beer> beers = new LinkedList<>();
    private SecureRandom random = new SecureRandom();
    private final DB db;
    private final DBCollection collection;

    public BeerDao(DB db){
        this.db = db;
        this.collection = db.getCollection("beers");
    }

    public List<Beer> all(){
       /* Beer b = new Beer();
        b.setAlcohol(12);
        b.setName("16b");
        b.setId("MonId");
        beers.add(b);*/
       List<Beer> beers = new ArrayList<>();
       DBCursor dbObjects = collection.find();
       while(dbObjects.hasNext()){
           DBObject dbObject = dbObjects.next();
           beers.add(new Beer((BasicDBObject) dbObject));
       }
        return beers;
    }

    /*public Beer add(String name, String alcohol){
        Beer b = new Beer();
        b.setAlcohol(Double.valueOf(alcohol));
        b.setName(name);
        b.setId(new BigInteger(130, random).toString(32));
        beers.add(b);
        return b;
    }*/


    public Beer add(Beer beer) {
        DBObject doc = new BasicDBObject("name", beer.getName()).append("alcohol", beer.getAlcohol());
        collection.insert(doc);
        beer.setId(doc.get("_id").toString());
        return beer;
    }

    public Beer findById(String id){
        for(Beer b: beers){
            if(b.getId().equalsIgnoreCase(id)){
                return b;
            }
        }
        return null;
    }

    public Beer update(String id, double alcohol, String name){
        for(Beer b: beers){
            if(b.getId().equalsIgnoreCase(id)){
                b.setName(name);
                b.setAlcohol(alcohol);
                return b;
            }
        }
        return null;
    }

    public Beer delete(String id){
        for(Beer b: beers){
            if(b.getId().equalsIgnoreCase(id)){
                beers.remove(b);
                return b;
            }
        }
        return null;
    }

}
