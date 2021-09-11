package com.example.rj19carwash.fragments;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.sessions.UserSession.KEY_CUSTOMER_ID;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.rj19carwash.R;
import com.example.rj19carwash.adapters.SlotsAdapter;
import com.example.rj19carwash.databinding.FragmentBookServiceBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.ServicesResponse;
import com.example.rj19carwash.responses.SlotsResponse;
import com.example.rj19carwash.sessions.UserSession;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentResultListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookServiceFragment extends Fragment implements PaymentResultListener {

    FragmentBookServiceBinding bookServiceBinding;

    UserSession userSession;

    Bundle bundle;

    int service_id;

    SlotsAdapter slotsAdapter;

    ArrayList<SlotsResponse.Data.Slotlist.Date> arrSlotsList = new ArrayList<>();

    ArrayList<ServicesResponse.Service.Employee> arrEmployeesList = new ArrayList<>();


    // razorpay variables

    RazorpayClient razorpayClient;
    Order order;
    Checkout checkout;

    private final String order_receipt_no = "Receipt No. " +  System.currentTimeMillis()/1000;
    private final String order_reference_no = "Reference No. #" +  System.currentTimeMillis()/1000;

    String inrRupees;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bookServiceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_service, container, false);

        userSession = new UserSession(requireActivity());


        bundle = getArguments();
        if (bundle != null){
            service_id = bundle.getInt("id");
            Picasso.get().load("https://www.rj19carwash.com/"+bundle.getString("image")).into(bookServiceBinding.bookserviceSerimg);
            bookServiceBinding.bookserviceTxtname.setText(bundle.getString("name"));
            bookServiceBinding.bookserviceTxtdesc.setText(bundle.getString("description"));
            arrEmployeesList = (ArrayList<ServicesResponse.Service.Employee>) bundle.getSerializable("employees");
            inrRupees = bundle.getString("inrrupees");
            setToSpinner(arrEmployeesList);
        }

        bookServiceBinding.bookserviceBtnbook.setOnClickListener(view -> {

            bookService();

        });


        bookServiceBinding.bookserviceSpinemployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int employee_id = arrEmployeesList.get(position).getId();

                Log.d("employee", String.valueOf(employee_id));
                getSlots(employee_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                toast(requireActivity(), "Select any employee");
            }
        });

        return bookServiceBinding.getRoot();
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

    private void setToSpinner(ArrayList<ServicesResponse.Service.Employee> arrEmployeesList) {

        ArrayAdapter<ServicesResponse.Service.Employee> arrayAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, arrEmployeesList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bookServiceBinding.bookserviceSpinemployee.setAdapter(arrayAdapter);
    }

    public void getSlots(int employee_id){

        RetrofitClient.getInstance().getapi().getSlots("Bearer "+userSession.getKeyToken().get(KEY_TOKEN), service_id, employee_id, userSession.getCustomerId().get(KEY_CUSTOMER_ID))
                .enqueue(new Callback<SlotsResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<SlotsResponse> call, @NonNull Response<SlotsResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null){
                                if (response.body().getResponseCode() == 201){
                                    arrSlotsList = response.body().getData().getSlotlist().getDate();

                                    Log.d("slotslist", Arrays.toString(arrSlotsList.toArray()));
                                    setSlotsToRecyclerview(arrSlotsList);
                                }else {
                                    Log.d("slot", response.body().getMessage());
                                    toast(requireActivity(), "Something went wrong! try again later");
                                }
                            }else {
                                Log.d("slotnull", response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SlotsResponse> call, @NonNull Throwable t) {

                        toast(requireActivity(), "Server error! try again later");
                        Log.d("slotserror", t.getMessage());
                    }
                });

    }

    private void setSlotsToRecyclerview(ArrayList<SlotsResponse.Data.Slotlist.Date> arrSlotsList) {

        bookServiceBinding.bookserviceSlotsrecyclerview.setHasFixedSize(true);

        slotsAdapter = new SlotsAdapter(requireActivity(), arrSlotsList);
        bookServiceBinding.bookserviceSlotsrecyclerview.setLayoutManager(new GridLayoutManager(requireContext(), 4));
        bookServiceBinding.bookserviceSlotsrecyclerview.setAdapter(slotsAdapter);

    }

    @Override
    public void onPaymentSuccess(String s) {
        toast(requireContext(), s);

        Bundle bundle = new Bundle();
        bundle.putString("message", s);
        bundle.putInt("result", 1);

        Navigation.findNavController(requireView()).navigate(R.id.toconfirmationorder, bundle);
//        textView.setText("Payment ID: " + s);
//        textView.append("\nOrder ID: " + order.get("id"));
//        textView.append("\n" + order_reference_no);

    }

    @Override
    public void onPaymentError(int i, String s) {
        toast(requireContext(), "Error: " + s);
        Bundle bundle = new Bundle();
        bundle.putString("message", s);
        bundle.putInt("result", 2);

        Navigation.findNavController(requireView()).navigate(R.id.toconfirmationorder, bundle);
//        textView.setText("Error: " + s);
    }
}