package com.example.rj19carwash.responses;


import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


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
/*
        @SerializedName("slotlist")
        @Expose
        private Slotlist slotlist;

        public Slotlist getSlotlist() {
            return slotlist;
        }

        public void setSlotlist(Slotlist slotlist) {
            this.slotlist = slotlist;
        }*/


/*
        public static class Slotlist implements Serializable
        {

            @SerializedName("2021-08-30")
            @Expose
            private com.example._20210830 _20210830;
            @SerializedName("2021-08-31")
            @Expose
            private com.example._20210831 _20210831;
            @SerializedName("2021-09-01")
            @Expose
            private com.example._20210901 _20210901;
            @SerializedName("2021-09-02")
            @Expose
            private com.example._20210902 _20210902;

            public com.example._20210830 get20210830() {
                return _20210830;
            }

            public void set20210830(com.example._20210830 _20210830) {
                this._20210830 = _20210830;
            }

            public com.example._20210831 get20210831() {
                return _20210831;
            }

            public void set20210831(com.example._20210831 _20210831) {
                this._20210831 = _20210831;
            }

            public com.example._20210901 get20210901() {
                return _20210901;
            }

            public void set20210901(com.example._20210901 _20210901) {
                this._20210901 = _20210901;
            }

            public com.example._20210902 get20210902() {
                return _20210902;
            }

            public void set20210902(com.example._20210902 _20210902) {
                this._20210902 = _20210902;
            }

        }
*/

    }

    public static class Errors implements Serializable
    {

        private final static long serialVersionUID = -807485482717328309L;

    }
}



