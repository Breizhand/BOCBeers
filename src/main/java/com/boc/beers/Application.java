package com.boc.beers;

import com.mongodb.*;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;

/**
 * Created by Cédric on 15/12/2016.
 */
public class Application {

    public static void main(String[] args) throws Exception {

        port(8080);
        get("/", (req, res) -> "Hello Beers");

        BeerDao dao = new BeerDao(mongo());

        get("/beers", (req, res) -> dao.all(), new JSonTransformer());
        get("/beers/:id", (req, res) -> dao.findById(req.params("id")), new JSonTransformer());
        /*post("/beers", (req, res) -> {
            Beer b = dao.add(req.queryParams("name"), req.queryParams("alcohol"));
            res.status(201);
            return b;
        }, new JSonTransformer());*/

        delete("/beers/:id", (req, res) -> {
            Beer b = dao.delete(req.params("id"));
            if (b == null) {
                res.status(406);
            } else {
                res.status(200);
            }
            return b;
        }, new JSonTransformer());

        put("/beers/:id", (req, res) -> {
            Beer b = dao.update(req.params("id"), Double.parseDouble(req.queryParams("alcohol")), req.queryParams("name"));
            return b;
        }, new JSonTransformer());
    }


    private static DB mongo() throws Exception{
        /*String host = "127.0.0.1";
        if(host == null){
            MongoClient mongoClient = new MongoClient("localhost");
            return mongoClient.getDB("todoapp");
        }
        int port = 27017;
        String dbname = "HelloSpark";
        String username = System.getenv("MONGODB_DB_USERNAME");
        String password = System.getenv("MONGODB_DB_PASSWORD");
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
        MongoClient mongoClient = new MongoClient(new ServerAddress(host, port), mongoClientOptions);
        mongoClient.setWriteConcern(WriteConcern.SAFE);
        DB db = mongoClient.getDB(dbname);
        return db;*/

        String host = System.getenv("MONGODB_ADDON_HOST");
        int port = Integer.parseInt(System.getenv("MONGODB_ADDON_PORT"));
        String dbname = System.getenv("MONGODB_ADDON_DB");
        String username = System.getenv("MONGODB_ADDON_USER");
        String password = System.getenv("MONGODB_ADDON_PASSWORD");
        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
        MongoClient mongoClient = new MongoClient(new ServerAddress(host, port), mongoClientOptions);
        mongoClient.setWriteConcern(WriteConcern.SAFE);
        DB db = mongoClient.getDB(dbname);

        if (db.authenticate(username, password.toCharArray())){
            return db;
        } else {
            throw new RuntimeException("Not able to authenticate with MondoDB");
        }
    }
}
