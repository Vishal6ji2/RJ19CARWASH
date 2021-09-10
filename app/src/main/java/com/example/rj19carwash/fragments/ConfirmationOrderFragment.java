package com.example.rj19carwash.fragments;

import static com.example.rj19carwash.Views.toast;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rj19carwash.R;
import com.example.rj19carwash.databinding.FragmentConfirmationOrderBinding;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentResultListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class ConfirmationOrderFragment extends Fragment implements PaymentResultListener {


    FragmentConfirmationOrderBinding confirmationOrderBinding;

    // razorpay variables

    RazorpayClient razorpayClient;
    Order order;
    Checkout checkout;

    private final String order_receipt_no = "Receipt No. " +  System.currentTimeMillis()/1000;
    private final String order_reference_no = "Reference No. #" +  System.currentTimeMillis()/1000;

    String inrRupees;

    Bundle bundle;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        confirmationOrderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirmation_order, container, false);

        bundle = getArguments();

        if (bundle != null){
            inrRupees = bundle.getString("inrrupees");
        }

        confirmationOrderBinding.confirmationBtnback.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.gobackhome));

        bookService();

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
            orderRequest.put("amount",inrRupees ); // amount in the smallest currency unit
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
            options.put("amount", inrRupees+"00");

            checkout.open(requireActivity(), options);
        } catch(Exception e) {
            Log.e("checkouterror", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        toast(requireContext(), s);
        confirmationOrderBinding.confirmationFailurecard.setVisibility(View.GONE);
        confirmationOrderBinding.confirmationSuccesscard.setVisibility(View.VISIBLE);
//        textView.setText("Payment ID: " + s);
//        textView.append("\nOrder ID: " + order.get("id"));
//        textView.append("\n" + order_reference_no);

    }

    @Override
    public void onPaymentError(int i, String s) {
        toast(requireContext(), "Error: " + s);

        confirmationOrderBinding.confirmationFailurecard.setVisibility(View.VISIBLE);
        confirmationOrderBinding.confirmationSuccesscard.setVisibility(View.GONE);
//        textView.setText("Error: " + s);
    }

}