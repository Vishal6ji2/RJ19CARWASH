package com.example.rj19carwash.responses;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EmployeesResponse implements Serializable
{

    @SerializedName("Employee")
    @Expose
    private ArrayList<Employee> employee = null;

    public ArrayList<Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(ArrayList<Employee> employee) {
        this.employee = employee;
    }


    public static class Employee implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("time_in")
        @Expose
        private String timeIn;
        @SerializedName("time_out")
        @Expose
        private String timeOut;
        @SerializedName("contact_number")
        @Expose
        private String contactNumber;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("profile_image")
        @Expose
        private String profileImage;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        private final static long serialVersionUID = -3593232628697310060L;

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

        public String getTimeIn() {
            return timeIn;
        }

        public void setTimeIn(String timeIn) {
            this.timeIn = timeIn;
        }

        public String getTimeOut() {
            return timeOut;
        }

        public void setTimeOut(String timeOut) {
            this.timeOut = timeOut;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
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


