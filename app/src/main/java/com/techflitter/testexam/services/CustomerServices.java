package com.techflitter.testexam.services;

import com.techflitter.testexam.model.BaseBean;
import com.techflitter.testexam.model.CustomerBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface CustomerServices {

    @GET("quandoo-assessment/customer-list.json")
    Call<List<CustomerBean>> customerList();

    @GET("quandoo-assessment/table-map.json")
    Call<List<Boolean>> tableList();
}
