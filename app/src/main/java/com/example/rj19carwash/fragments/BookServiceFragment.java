package com.example.rj19carwash.fragments;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.sessions.UserSession.KEY_ADDRESS;
import static com.example.rj19carwash.sessions.UserSession.KEY_CUSTOMER_ID;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.rj19carwash.R;
import com.example.rj19carwash.activities.ConfirmBookActivity;
import com.example.rj19carwash.adapters.SlotsAdapter;
import com.example.rj19carwash.adapters.TimesAdapter;
import com.example.rj19carwash.databinding.FragmentBookServiceBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.OrderNowResponse;
import com.example.rj19carwash.responses.ServicesResponse;
import com.example.rj19carwash.responses.SlotsResponse;
import com.example.rj19carwash.sessions.UserSession;
import com.example.rj19carwash.utilities.CustomLoading;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookServiceFragment extends Fragment implements SlotsAdapter.SlotsItemClickListener, TimesAdapter.TimesItemClickListener {

    FragmentBookServiceBinding bookServiceBinding;

    UserSession userSession;

    Bundle bundle;

    int service_id, employee_id, order_id;
    String date, time;

    SlotsAdapter slotsAdapter;
    TimesAdapter timesAdapter;

    CustomLoading customLoading;

    ArrayList<SlotsResponse.Data.Slotlist.Date> arrSlotsList = new ArrayList<>();
    ArrayList<String> arrTimesList = new ArrayList<>();

    ArrayList<ServicesResponse.Service.Employee> arrEmployeesList = new ArrayList<>();

    String price;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bookServiceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_service, container, false);

        userSession = new UserSession(requireActivity());

        customLoading = new CustomLoading(requireContext());


        bundle = getArguments();
        if (bundle != null){
            service_id = bundle.getInt("id");

            Picasso.get().load("https://www.rj19carwash.com/"+bundle.getString("image")).placeholder(R.mipmap.ic_launcher_foreground).into(bookServiceBinding.bookserviceSerimg);

            bookServiceBinding.bookserviceTxtname.setText(bundle.getString("name"));
            bookServiceBinding.bookserviceTxtdesc.setText(bundle.getString("description"));

            price = bundle.getString("inrrupees");

            arrEmployeesList = (ArrayList<ServicesResponse.Service.Employee>) bundle.getSerializable("employees");

            setToSpinner(arrEmployeesList);

        }

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Navigation.findNavController(requireView()).popBackStack();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);


        bookServiceBinding.bookserviceBtnbook.setOnClickListener(view -> makeOrderToServer());

        bookServiceBinding.bookserviceSpinemployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                employee_id = arrEmployeesList.get(position).getId();

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

    private void setToSpinner(ArrayList<ServicesResponse.Service.Employee> arrEmployeesList) {

        if (arrEmployeesList != null) {

            bookServiceBinding.bookserviceSpinemployee.setVisibility(View.VISIBLE);
            bookServiceBinding.bookserviceTxtemployee.setVisibility(View.GONE);

            ArrayAdapter<ServicesResponse.Service.Employee> arrayAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, arrEmployeesList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            bookServiceBinding.bookserviceSpinemployee.setAdapter(arrayAdapter);

        }else {
            bookServiceBinding.bookserviceSpinemployee.setVisibility(View.GONE);
            bookServiceBinding.bookserviceTxtemployee.setVisibility(View.VISIBLE);
            bookServiceBinding.bookserviceTimesrecyclerview.setVisibility(View.GONE);
            bookServiceBinding.bookserviceSlotsrecyclerview.setVisibility(View.GONE);

            bookServiceBinding.bookserviceSlotsrecyclerview.setVisibility(View.GONE);
            bookServiceBinding.bookserviceTxtslots.setVisibility(View.VISIBLE);
            bookServiceBinding.bookserviceTimesrecyclerview.setVisibility(View.GONE);
            bookServiceBinding.bookserviceTxttimes.setVisibility(View.GONE);

            bookServiceBinding.bookserviceBtnbook.setEnabled(false);
        }
    }

    public void getSlots(int employee_id){

        RetrofitClient.getInstance().getapi().getSlots("Bearer "+userSession.getKeyToken().get(KEY_TOKEN), service_id, employee_id, userSession.getCustomerId().get(KEY_CUSTOMER_ID))
                .enqueue(new Callback<SlotsResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<SlotsResponse> call, @NonNull Response<SlotsResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null){
                                if (response.body().getResponseCode() == 201){
                                    arrSlotsList.clear();
                                    arrSlotsList = response.body().getData().getSlotlist().getDate();

                                    Log.d("slotslist", Arrays.toString(arrSlotsList.toArray()));
                                    setSlotsToRecyclerview(arrSlotsList);

                                }else {
                                    Log.d("slot", response.body().getMessage());
                                    toast(requireActivity(), "Something went wrong! try again later");
                                }
                            }else {
                                Log.d("slotnull", "Something went wrong! try again later");
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

        if (arrSlotsList != null){
            bookServiceBinding.bookserviceSlotsrecyclerview.setVisibility(View.VISIBLE);
            bookServiceBinding.bookserviceTxtslots.setVisibility(View.GONE);

            slotsAdapter = new SlotsAdapter(requireActivity(), arrSlotsList, this);

            bookServiceBinding.bookserviceSlotsrecyclerview.setLayoutManager(new GridLayoutManager(requireContext(), 4));
            bookServiceBinding.bookserviceSlotsrecyclerview.setAdapter(slotsAdapter);

        }else {
            bookServiceBinding.bookserviceSlotsrecyclerview.setVisibility(View.GONE);
            bookServiceBinding.bookserviceTxtslots.setVisibility(View.VISIBLE);
            bookServiceBinding.bookserviceTimesrecyclerview.setVisibility(View.GONE);
            bookServiceBinding.bookserviceTxttimes.setVisibility(View.GONE);

            bookServiceBinding.bookserviceBtnbook.setEnabled(false);
        }
    }

    private void makeOrderToServer() {

        if (time.isEmpty()){
            toast(requireContext(), "select a time slot");
        }/*else if (userSession.getCustomerData().get(KEY_ADDRESS).isEmpty()) {
            toast(requireContext(), "please complete your address");
        }*/else {

            customLoading.startLoading(getLayoutInflater());
            Log.d("customerid", userSession.getCustomerId().get(KEY_CUSTOMER_ID).toString());
            RetrofitClient.getInstance().getapi().bookOrderNow("Bearer " + userSession.getKeyToken().get(KEY_TOKEN), service_id, employee_id, date + " " + time, price, userSession.getCustomerId().get(KEY_CUSTOMER_ID))
                    .enqueue(new Callback<OrderNowResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<OrderNowResponse> call, @NonNull Response<OrderNowResponse> response) {
                            customLoading.dismissLoading();
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    if (response.body().getResponseCode() == 201) {
                                        toast(requireContext(), response.body().getMessage());
                                        order_id = response.body().getData().getId();
                                        Log.d("customeridsecond", String.valueOf(response.body().getData().getCustomerId().getId()));

                                        Intent bundle = new Intent(requireActivity(), ConfirmBookActivity.class);
                                        bundle.putExtra("order_id", order_id);

                                        bundle.putExtra("price", price);
                                        startActivity(bundle);
                                    } else if (response.body().getResponseCode() == 422) {
                                        toast(requireContext(), response.body().getMessage());
                                    } else {
                                        toast(requireContext(), response.body().getMessage());
                                    }
                                } else {
                                    toast(requireContext(), "Something went wrong! try again later");
                                }
                            } else {
                                toast(requireContext(), "Something went wrong! try again later");
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<OrderNowResponse> call, @NonNull Throwable t) {

                            customLoading.dismissLoading();
                            toast(requireContext(), "Server error! try again later");
                            Log.d("bookerror", t.getMessage());
                        }
                    });

        }
    }


    @Override
    public void onSlotClick(int position) {

        date = arrSlotsList.get(position).getName();

        time = "";
        bookServiceBinding.bookserviceTimesrecyclerview.removeAllViews();
        arrTimesList = arrSlotsList.get(position).getTime();
        Log.d("times", String.valueOf(arrTimesList.size()));

        if (arrTimesList.size() != 0) {
            bookServiceBinding.bookserviceTxttimes.setVisibility(View.GONE);
            bookServiceBinding.bookserviceTimesrecyclerview.setVisibility(View.VISIBLE);

            timesAdapter = new TimesAdapter(requireContext(), arrTimesList, this);
            bookServiceBinding.bookserviceTimesrecyclerview.setLayoutManager(new GridLayoutManager(requireContext(), 4));
            bookServiceBinding.bookserviceTimesrecyclerview.setAdapter(timesAdapter);
        }else {
            bookServiceBinding.bookserviceTimesrecyclerview.setVisibility(View.GONE);
            bookServiceBinding.bookserviceTxttimes.setVisibility(View.VISIBLE);

            bookServiceBinding.bookserviceBtnbook.setEnabled(false);
        }
    }

    @Override
    public void onTimeClick(int position) {

        time = "";
        time = arrTimesList.get(position);
        bookServiceBinding.bookserviceBtnbook.setEnabled(true);

    }

}