package xyz.tgprojects.ideahub.utilities;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private static final String URL = "http://iwtwaa.herokuapp.com/";
    private static ApiRoutes apiRoutes;

    public static ApiRoutes getApi() {
        if (apiRoutes == null){
            apiRoutes = setUpApiRoutes();
        }
        return apiRoutes;
    }

    private static ApiRoutes setUpApiRoutes() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build();
        return retrofit.create(ApiRoutes.class);

    }

}
