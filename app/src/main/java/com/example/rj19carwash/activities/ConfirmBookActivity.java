package com.example.rj19carwash.activities;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.ActivityConfirmBookBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.OrderStatusResponse;
import com.example.rj19carwash.sessions.UserSession;
import com.example.rj19carwash.utilities.CustomLoading;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentResultListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmBookActivity extends AppCompatActivity implements PaymentResultListener {

    ActivityConfirmBookBinding confirmBookBinding;

    int order_id;

    Intent bundle;

    CustomLoading customLoading;

    UserSession userSession;

    Checkout checkout;
    RazorpayClient razorpayClient;
    Order order;

    private final String order_receipt_no = "Receipt No. " +  System.currentTimeMillis()/1000;
    private final String order_reference_no = "Reference No. #" +  System.currentTimeMillis()/1000;

    String price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        confirmBookBinding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_book);

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        customLoading = new CustomLoading(this);

        userSession = new UserSession(this);

        bundle = getIntent();
        // get bundle values
        if (bundle != null){
            order_id = bundle.getIntExtra("order_id",-1);
            price = bundle.getStringExtra("price");
            bookService();
        }else {
            toast(this, "Booking error ! try again");
        }

        confirmBookBinding.confirmbookBtnback.setOnClickListener(view -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });

    }

    private void bookService() {

        try {
            razorpayClient = new RazorpayClient(getResources().getString(R.string.razorpay_key_id), getResources().getString(R.string.razorpay_secret_key));
            Checkout.preload(this);
            checkout = new Checkout();
            Log.d("checkout", "first");
        } catch (RazorpayException e) {
            e.printStackTrace();
            Log.d("checkout", e.getMessage());
        }

        HashMap<String, String> headers = new HashMap<>();
        razorpayClient.addHeaders(headers);


        try {
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount",price+"00" ); // amount in the smallest currency unit
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", order_receipt_no);
            orderRequest.put("payment_capture", true);

            order = razorpayClient.Orders.create(orderRequest);
            Log.d("checkout", "second");
            startPayment(order);

        } catch (RazorpayException e) {
            // Handle Exception
            System.out.println(e.getMessage());
            Log.d("checkout1",e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("checkout2",e.getMessage());
        }

    }

    public void startPayment(Order order) {
        checkout.setKeyID(getResources().getString(R.string.razorpay_key_id));
        /*
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        /*
         * Set your logo here
         */
        checkout.setImage(R.mipmap.ic_launcher);

        /*
         * Reference to current activity
         */

        /*
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /*
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", "RJ19 CAR WASH");

            /*
             * Description can be anything
             * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put("description", order_reference_no);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", order.get("id"));
            options.put("currency", "INR");

            /*
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", price+"00");

            checkout.open(this, options);
        } catch(Exception e) {
            Log.e("checkouterror", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {

//        toast(this, s);

        confirmBookBinding.confirmbookFailurecard.setVisibility(View.GONE);
        confirmBookBinding.confirmbookSuccesscard.setVisibility(View.VISIBLE);

//        textView.setText("Payment ID: " + s);
//        textView.append("\nOrder ID: " + order.get("id"));
//        textView.append("\n" + order_reference_no);

    }

    @Override
    public void onPaymentError(int i, String s) {

        cancelOrder(order_id);
//        toast(this, "Error: " + s);

        confirmBookBinding.confirmbookFailurecard.setVisibility(View.VISIBLE);
        confirmBookBinding.confirmbookSuccesscard.setVisibility(View.GONE);


//        textView.setText("Error: " + s);
    }

    private void cancelOrder(int order_id) {

        RetrofitClient.getInstance().getapi().setOrderStatus("Bearer "+userSession.getKeyToken().get(KEY_TOKEN), order_id, "-1")
                .enqueue(new Callback<OrderStatusResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<OrderStatusResponse> call, @NonNull Response<OrderStatusResponse> response) {

                        if (response.isSuccessful()){
                            if (response.body() != null){
                                if (response.body().getResponseCode() == 201){
                                    toast(ConfirmBookActivity.this, response.body().getMessage());
                                }else {
                                    toast(ConfirmBookActivity.this, response.body().getMessage());
                                }
                            }}
                    }

                    @Override
                    public void onFailure(@NonNull Call<OrderStatusResponse> call, @NonNull Throwable t) {

                        Log.d("statuserror", t.getMessage());
                        toast(ConfirmBookActivity.this, "Server error! try again later");
                    }
                });
    }

}