package com.example.rj19carwash.responses;

import java.io.Serializable;
import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrdersResponse implements Serializable
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
    private ArrayList<Datum> data = null;

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

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

    public static class Datum implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("customer_id")
        @Expose
        private CustomerId customerId;
        @SerializedName("service_id")
        @Expose
        private ServiceId serviceId;
        @SerializedName("employee_id")
        @Expose
        private EmployeeId employeeId;
        @SerializedName("slot")
        @Expose
        private String slot;
        @SerializedName("price")
        @Expose
        private String price;
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

        public CustomerId getCustomerId() {
            return customerId;
        }

        public void setCustomerId(CustomerId customerId) {
            this.customerId = customerId;
        }

        public ServiceId getServiceId() {
            return serviceId;
        }

        public void setServiceId(ServiceId serviceId) {
            this.serviceId = serviceId;
        }

        public EmployeeId getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(EmployeeId employeeId) {
            this.employeeId = employeeId;
        }

        public String getSlot() {
            return slot;
        }

        public void setSlot(String slot) {
            this.slot = slot;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public static class ServiceId implements Serializable
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
            private ArrayList<Category> category = null;
            @SerializedName("employee")
            @Expose
            private ArrayList<Employee> employee = null;
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

            public ArrayList<Category> getCategory() {
                return category;
            }

            public void setCategory(ArrayList<Category> category) {
                this.category = category;
            }

            public ArrayList<Employee> getEmployee() {
                return employee;
            }

            public void setEmployee(ArrayList<Employee> employee) {
                this.employee = employee;
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

            public static class Category implements Serializable
            {

                @SerializedName("id")
                @Expose
                private Integer id;
                @SerializedName("parent_id")
                @Expose
                private String parentId;
                @SerializedName("category_name")
                @Expose
                private String categoryName;
                @SerializedName("category_image")
                @Expose
                private String categoryImage;
                @SerializedName("category_description")
                @Expose
                private String categoryDescription;
                @SerializedName("category_status")
                @Expose
                private String categoryStatus;
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

                public String getParentId() {
                    return parentId;
                }

                public void setParentId(String parentId) {
                    this.parentId = parentId;
                }

                public String getCategoryName() {
                    return categoryName;
                }

                public void setCategoryName(String categoryName) {
                    this.categoryName = categoryName;
                }

                public String getCategoryImage() {
                    return categoryImage;
                }

                public void setCategoryImage(String categoryImage) {
                    this.categoryImage = categoryImage;
                }

                public String getCategoryDescription() {
                    return categoryDescription;
                }

                public void setCategoryDescription(String categoryDescription) {
                    this.categoryDescription = categoryDescription;
                }

                public String getCategoryStatus() {
                    return categoryStatus;
                }

                public void setCategoryStatus(String categoryStatus) {
                    this.categoryStatus = categoryStatus;
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

        public static class EmployeeId implements Serializable
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

        public static class CustomerId implements Serializable
        {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("password")
            @Expose
            private String password;
            @SerializedName("phone")
            @Expose
            private String phone;
            @SerializedName("gender")
            @Expose
            private Object gender;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("profile")
            @Expose
            private String profile;
            @SerializedName("customer_status")
            @Expose
            private String customerStatus;
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

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getProfile() {
                return profile;
            }

            public void setProfile(String profile) {
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

        }

    }

    public static class Errors implements Serializable
    {

        private final static long serialVersionUID = -807485482717328309L;

    }

}


