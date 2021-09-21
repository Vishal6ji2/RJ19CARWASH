package com.example.rj19carwash.fragments;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.FragmentConfirmationOrderBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.OrderStatusResponse;
import com.example.rj19carwash.sessions.UserSession;
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


public class ConfirmationOrderFragment extends Fragment implements PaymentResultListener {


    FragmentConfirmationOrderBinding confirmationOrderBinding;

    int order_id;

    Bundle bundle;

    UserSession userSession;

    Checkout checkout;
    RazorpayClient razorpayClient;
    Order order;

    private final String order_receipt_no = "Receipt No. " +  System.currentTimeMillis()/1000;
    private final String order_reference_no = "Reference No. #" +  System.currentTimeMillis()/1000;

    String price;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        confirmationOrderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirmation_order, container, false);


        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        bundle = getArguments();

        userSession = new UserSession(requireContext());

        // get bundle values
        if (bundle != null){
            order_id = bundle.getInt("order_id");
            price = bundle.getString("price");
            bookService();
        }

        confirmationOrderBinding.confirmationBtnback.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.gobackhome));

        return confirmationOrderBinding.getRoot();
    }


    private void bookService() {

        try {
            razorpayClient = new RazorpayClient(getResources().getString(R.string.razorpay_key_id), getResources().getString(R.string.razorpay_secret_key));
            Checkout.preload(requireActivity());
            checkout = new Checkout();
        } catch (RazorpayException e) {
            e.printStackTrace();
        }

        HashMap<String, String> headers = new HashMap<>();
        razorpayClient.addHeaders(headers);


        try {
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount",price ); // amount in the smallest currency unit
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", order_receipt_no);
            orderRequest.put("payment_capture", true);

            order = razorpayClient.Orders.create(orderRequest);

            startPayment(order);


        } catch (RazorpayException e) {
            // Handle Exception
            System.out.println(e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void startPayment(Order order) {
        checkout.setKeyID(getResources().getString(R.string.razorpay_key_id));
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.ic_launcher_foreground);

        /**
         * Reference to current activity
         */

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", "RJ19 CAR WASH");

            /**
             * Description can be anything
             * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put("description", order_reference_no);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", order.get("id"));
            options.put("currency", "INR");

            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            options.put("amount", price+"00");

            checkout.open(requireActivity(), options);
        } catch(Exception e) {
            Log.e("checkouterror", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {

//        toast(requireContext(), s);

        confirmationOrderBinding.confirmationFailurecard.setVisibility(View.GONE);
        confirmationOrderBinding.confirmationSuccesscard.setVisibility(View.VISIBLE);

//        textView.setText("Payment ID: " + s);
//        textView.append("\nOrder ID: " + order.get("id"));
//        textView.append("\n" + order_reference_no);

    }

    @Override
    public void onPaymentError(int i, String s) {

        cancelOrder(order_id);
//        toast(requireContext(), "Error: " + s);

        confirmationOrderBinding.confirmationFailurecard.setVisibility(View.VISIBLE);
        confirmationOrderBinding.confirmationSuccesscard.setVisibility(View.GONE);

    }

    private void cancelOrder(int order_id) {

        RetrofitClient.getInstance().getapi().setOrderStatus("Bearer "+userSession.getKeyToken().get(KEY_TOKEN), order_id, "-1")
                .enqueue(new Callback<OrderStatusResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<OrderStatusResponse> call, @NonNull Response<OrderStatusResponse> response) {

                        if (response.isSuccessful()){
                            if (response.body() != null){
                                if (response.body().getResponseCode() == 201){
                                    toast(requireContext(), response.body().getMessage());
                                    Navigation.findNavController(requireView()).navigate(R.id.gobackbook);
                                }else {
                                    toast(requireContext(), response.body().getMessage());
                                }
                            }else {
                                toast(requireContext(), "Something went wrong! try again");
                            }
                            Navigation.findNavController(requireView()).navigate(R.id.toOrders);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<OrderStatusResponse> call, @NonNull Throwable t) {

                        Log.d("statuserror", t.getMessage());
                        toast(requireContext(), "Server error! try again later");
                    }
                });
    }

}