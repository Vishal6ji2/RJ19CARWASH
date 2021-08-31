package com.example.rj19carwash.responses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ServicesResponse implements Serializable
{

    @SerializedName("Services")
    @Expose
    private ArrayList<Service> services = null;

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }


    public static class Service implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("short_description")
        @Expose
        private String shortDescription;
        @SerializedName("long_description")
        @Expose
        private String longDescription;
        @SerializedName("service_image")
        @Expose
        private String serviceImage;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("emplyee")
        @Expose
        private String emplyee;
        @SerializedName("discountprice")
        @Expose
        private String discountprice;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public String getLongDescription() {
            return longDescription;
        }

        public void setLongDescription(String longDescription) {
            this.longDescription = longDescription;
        }

        public String getServiceImage() {
            return serviceImage;
        }

        public void setServiceImage(String serviceImage) {
            this.serviceImage = serviceImage;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getEmplyee() {
            return emplyee;
        }

        public void setEmplyee(String emplyee) {
            this.emplyee = emplyee;
        }

        public String getDiscountprice() {
            return discountprice;
        }

        public void setDiscountprice(String discountprice) {
            this.discountprice = discountprice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

    }

}



