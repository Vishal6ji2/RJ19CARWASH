package com.example.rj19carwash.responses;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RegisterResponse implements Serializable
{

    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("data")
    @Expose
    private Data data;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public static class Data implements Serializable
    {

        @SerializedName("submit_customer")
        @Expose
        private SubmitCustomer submitCustomer;

        public SubmitCustomer getSubmitCustomer() {
            return submitCustomer;
        }

        public void setSubmitCustomer(SubmitCustomer submitCustomer) {
            this.submitCustomer = submitCustomer;
        }


        public static class SubmitCustomer implements Serializable
        {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("name")
            @Expose
            private Object name;
            @SerializedName("email")
            @Expose
            private Object email;
            @SerializedName("phone")
            @Expose
            private String phone;
            @SerializedName("gender")
            @Expose
            private Object gender;
            @SerializedName("address")
            @Expose
            private Object address;
            @SerializedName("profile")
            @Expose
            private Object profile;
            @SerializedName("customer_status")
            @Expose
            private String customerStatus;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;
            @SerializedName("token")
            @Expose
            private String token;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
                this.name = name;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Object getGender() {
                return gender;
            }

            public void setGender(Object gender) {
                this.gender = gender;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public Object getProfile() {
                return profile;
            }

            public void setProfile(Object profile) {
                this.profile = profile;
            }

            public String getCustomerStatus() {
                return customerStatus;
            }

            public void setCustomerStatus(String customerStatus) {
                this.customerStatus = customerStatus;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

        }

    }

}

