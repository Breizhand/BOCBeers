package com.boc.beers;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by Cédric on 15/12/2016.
 */
public class JSonTransformer implements ResponseTransformer{

    private Gson gson = new Gson();

    @Override
    public String render(Object model) throws Exception{
        return gson.toJson(model);

    }
}
