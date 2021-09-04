package com.example.rj19carwash.responses;


import java.io.Serializable;
import java.util.ArrayList;

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
    private final static long serialVersionUID = -5415958825866234826L;

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


    public class Data implements Serializable
    {

        @SerializedName("slotlist")
        @Expose
        private ArrayList<Slot> slotArrayList = null;
        private final static long serialVersionUID = -6642134889275428288L;

        public ArrayList<Slot> getSlotArrayList() {
            return slotArrayList;
        }

        public void setSlotArrayList(ArrayList<Slot> slotArrayList) {
            this.slotArrayList = slotArrayList;
        }


        public class Slot implements Serializable
        {

            @SerializedName("2021-09-04")
            @Expose
            private _20210904 _20210904;
            @SerializedName("2021-09-05")
            @Expose
            private _20210905 _20210905;
            @SerializedName("2021-09-06")
            @Expose
            private _20210906 _20210906;
            @SerializedName("2021-09-07")
            @Expose
            private _20210907 _20210907;
            private final static long serialVersionUID = -4716313390661874220L;

            public _20210904 get20210904() {
                return _20210904;
            }

            public void set20210904(_20210904 _20210904) {
                this._20210904 = _20210904;
            }

            public _20210905 get20210905() {
                return _20210905;
            }

            public void set20210905(_20210905 _20210905) {
                this._20210905 = _20210905;
            }

            public _20210906 get20210906() {
                return _20210906;
            }

            public void set20210906(_20210906 _20210906) {
                this._20210906 = _20210906;
            }

            public _20210907 get20210907() {
                return _20210907;
            }

            public void set20210907(_20210907 _20210907) {
                this._20210907 = _20210907;
            }


            public class _20210904 implements Serializable
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
                private final static long serialVersionUID = 4081740722249449465L;

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
            public class _20210905 implements Serializable
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
                private final static long serialVersionUID = 8176480037576567984L;

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
            public class _20210906 implements Serializable
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
                private final static long serialVersionUID = -3258496999039524106L;

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
            public class _20210907 implements Serializable
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
                private final static long serialVersionUID = -8665168910038348696L;

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

    public static class Errors implements Serializable
    {

        private final static long serialVersionUID = -807485482717328309L;

    }
}

