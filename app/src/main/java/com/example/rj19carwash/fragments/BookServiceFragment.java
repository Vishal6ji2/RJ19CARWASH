package com.example.rj19carwash.fragments;

import static com.example.rj19carwash.Views.toast;
import static com.example.rj19carwash.sessions.UserSession.KEY_CUSTOMER_ID;
import static com.example.rj19carwash.sessions.UserSession.KEY_TOKEN;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.rj19carwash.R;
import com.example.rj19carwash.adapters.SlotsAdapter;
import com.example.rj19carwash.databinding.FragmentBookServiceBinding;
import com.example.rj19carwash.networks.RetrofitClient;
import com.example.rj19carwash.responses.EmployeesResponse;
import com.example.rj19carwash.responses.SlotsResponse;
import com.example.rj19carwash.sessions.UserSession;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookServiceFragment extends Fragment {

    FragmentBookServiceBinding bookServiceBinding;

    UserSession userSession;

    Bundle bundle;

    int service_id;

    SlotsAdapter slotsAdapter;

    ArrayList<SlotsResponse.Data.Slot> arrSlotsList = new ArrayList<>();

    ArrayList<EmployeesResponse.Employee> arrEmployeesList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bookServiceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_service, container, false);

        initViews();

        bundle = getArguments();
        if (bundle != null){
            service_id = bundle.getInt("id");
            Picasso.get().load("https://www.rj19carwash.com/"+bundle.getString("image")).into(bookServiceBinding.bookserviceSerimg);
            bookServiceBinding.bookserviceTxtname.setText(bundle.getString("name"));
            bookServiceBinding.bookserviceTxtdesc.setText(bundle.getString("description"));
            bundle.getString("employees");

        }

        getEmployees();

        bookServiceBinding.bookserviceSpinemployee.setOnItemClickListener((adapterView, view, position, l) -> {

            int employee_id = arrEmployeesList.get(position).getId();

            getSlots(employee_id);
        });

        return bookServiceBinding.getRoot();
    }

    private void initViews() {

        userSession = new UserSession(requireActivity());

    }

    public void getEmployees(){

        RetrofitClient.getInstance().getapi().getEmployees(userSession.getKeyToken().get(KEY_TOKEN))
                .enqueue(new Callback<EmployeesResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<EmployeesResponse> call, @NonNull Response<EmployeesResponse> response) {
                        if (response.isSuccessful() && response.body() != null){

                            arrEmployeesList = response.body().getEmployee();
                            setToSpinner(arrEmployeesList);

                        }else {
                        arrEmployeesList = null;
                        toast(requireActivity(), "Server error! try again later");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<EmployeesResponse> call, @NonNull Throwable t) {
                        arrEmployeesList = null;
                        Log.d("employeeerror", t.getMessage());
                        toast(requireActivity(), "Something went wrong! try again later");
                    }
                });
    }

    private void setToSpinner(ArrayList<EmployeesResponse.Employee> arrEmployeesList) {

        ArrayAdapter<EmployeesResponse.Employee> arrayAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, arrEmployeesList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bookServiceBinding.bookserviceSpinemployee.setAdapter(arrayAdapter);
    }

    public void getSlots(int employee_id){

        RetrofitClient.getInstance().getapi().getSlots(userSession.getKeyToken().get(KEY_TOKEN), service_id, employee_id, userSession.getCustomerId().get(KEY_CUSTOMER_ID))
                .enqueue(new Callback<SlotsResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<SlotsResponse> call, @NonNull Response<SlotsResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null){
                                if (response.body().getResponseCode() == 201){
                                    arrSlotsList = response.body().getData().getSlotArrayList();

                                    setSlotsToRecyclerview(arrSlotsList);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SlotsResponse> call, @NonNull Throwable t) {

                    }
                });

    }

    private void setSlotsToRecyclerview(ArrayList<SlotsResponse.Data.Slot> arrSlotsList) {

        bookServiceBinding.bookserviceSlotsrecyclerview.setHasFixedSize(true);

        slotsAdapter = new SlotsAdapter(requireActivity(), arrSlotsList);
        bookServiceBinding.bookserviceSlotsrecyclerview.setAdapter(slotsAdapter);

    }
}