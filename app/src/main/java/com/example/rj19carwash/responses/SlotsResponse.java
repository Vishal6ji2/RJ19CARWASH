package com.example.rj19carwash.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class SlotsResponse implements Serializable
{

    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("errors")
    @Expose
    private Errors errors;
    @SerializedName("data")
    @Expose
    private Data data;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public static class Data implements Serializable
    {

        @SerializedName("slotlist")
        @Expose
        private Slotlist slotlist;

        public Slotlist getSlotlist() {
            return slotlist;
        }

        public void setSlotlist(Slotlist slotlist) {
            this.slotlist = slotlist;
        }

        public static class Slotlist implements Serializable
        {

            @SerializedName("date")
            @Expose
            private ArrayList<Date> date = null;

            public ArrayList<Date> getDate() {
                return date;
            }

            public void setDate(ArrayList<Date> date) {
                this.date = date;
            }

            public static class Date implements Serializable
            {

                @SerializedName("name")
                @Expose
                private String name;
                @SerializedName("time")
                @Expose
                private ArrayList<String> time = null;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public ArrayList<String> getTime() {
                    return time;
                }

                public void setTime(ArrayList<String> time) {
                    this.time = time;
                }

            }

        }

    }

    public static class Errors implements Serializable
    {

        private final static long serialVersionUID = -807485482717328309L;

    }

}





