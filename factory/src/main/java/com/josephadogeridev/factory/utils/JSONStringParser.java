package com.josephadogeridev.factory.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSONStringParser {

    private String key;
    private String value;

    public JSONStringParser() {

    }

    public void extract(String jsonString) throws Exception {

        if(jsonString == null) {
            throw new Exception("Only json string as input");
        }

        try {
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

            String key = "message";
            String value = jsonObject.get(key).getAsString();

            System.out.println("Key: " + key);
            System.out.println("Value: " + value);
            setKey(key);
            setValue(value);
        }catch (Exception e){
            throw new Exception("Invalid JSON string : " + jsonString);
        }

    }

    public String getKey() {
        return key;
    }

    private void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }
    private void setValue(String value) {
        this.value = value;
    }

}