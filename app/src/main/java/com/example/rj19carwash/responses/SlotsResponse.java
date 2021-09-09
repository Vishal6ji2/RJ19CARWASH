package com.example.rj19carwash.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
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

    public static class Errors implements Serializable
    {
    }

    public static class Data implements Serializable
    {

        @SerializedName("slotList")
        @Expose
        private SlotList slotList;

        public SlotList getSlotList() {
            return slotList;
        }

        public void setSlotArrayList(SlotList slotList) {
            this.slotList = slotList;
        }

        public static class SlotList implements Serializable
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

        }

        public static class Date implements Serializable
        {

            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("time")
            @Expose
            private Time time;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Time getTime() {
                return time;
            }

            public void setTime(Time time) {
                this.time = time;
            }

            public static class Time implements Serializable
            {

                @SerializedName("0")
                @Expose
                private String _0;
                @SerializedName("1")
                @Expose
                private String _1;
                @SerializedName("2")
                @Expose
                private String _2;
                @SerializedName("3")
                @Expose
                private String _3;
                @SerializedName("4")
                @Expose
                private String _4;
                @SerializedName("5")
                @Expose
                private String _5;
                @SerializedName("6")
                @Expose
                private String _6;
                @SerializedName("7")
                @Expose
                private String _7;
                @SerializedName("8")
                @Expose
                private String _8;
                @SerializedName("9")
                @Expose
                private String _9;
                private final static long serialVersionUID = -5582182968423411704L;

                public String get0() {
                    return _0;
                }

                public void set0(String _0) {
                    this._0 = _0;
                }

                public String get1() {
                    return _1;
                }

                public void set1(String _1) {
                    this._1 = _1;
                }

                public String get2() {
                    return _2;
                }

                public void set2(String _2) {
                    this._2 = _2;
                }

                public String get3() {
                    return _3;
                }

                public void set3(String _3) {
                    this._3 = _3;
                }

                public String get4() {
                    return _4;
                }

                public void set4(String _4) {
                    this._4 = _4;
                }

                public String get5() {
                    return _5;
                }

                public void set5(String _5) {
                    this._5 = _5;
                }

                public String get6() {
                    return _6;
                }

                public void set6(String _6) {
                    this._6 = _6;
                }

                public String get7() {
                    return _7;
                }

                public void set7(String _7) {
                    this._7 = _7;
                }

                public String get8() {
                    return _8;
                }

                public void set8(String _8) {
                    this._8 = _8;
                }

                public String get9() {
                    return _9;
                }

                public void set9(String _9) {
                    this._9 = _9;
                }

            }

        }
    }
}



