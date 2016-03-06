package org.marek.retrofit2usage.rest;

import org.marek.retrofit2usage.rest.response.TransportListModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by M on 2016-03-06.
 */
public interface ClientService {

    @GET("rest/mobile/technician/getSuppliers")
    Call<TransportListModel<String>> getSuppliers(
            @Query("filter") String filter
    );
}
