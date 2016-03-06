package org.marek.retrofit2usage;


import org.marek.retrofit2usage.rest.RestClient;
import org.marek.retrofit2usage.rest.response.TransportListModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by M on 2016-03-06.
 */
public class Test {
    public static void main(String [] args) {
        //RETROFIT CALL
        Call<TransportListModel<String>> request = RestClient.getService().getSuppliers("");
        //Response<TransportListModel<String>> response = null;

        //ASYNC CALL
        request.enqueue(new Callback<TransportListModel<String>>() {
            @Override
            public void onResponse(Call<TransportListModel<String>> call, Response<TransportListModel<String>> response) {
                System.out.println("SUCCESS");
                System.out.println(response.body().toString());
            }

            @Override
            public void onFailure(Call<TransportListModel<String>> call, Throwable t) {
                System.out.println("FAILURE:" + t.getMessage());
            }
        });
    }
}
