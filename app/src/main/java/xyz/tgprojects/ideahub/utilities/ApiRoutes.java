package xyz.tgprojects.ideahub.utilities;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;
import xyz.tgprojects.ideahub.models.Favorite;
import xyz.tgprojects.ideahub.models.Idea;

public interface ApiRoutes {
    @GET("ideas")
    Observable<List<Idea>> getIdeas();

    @GET("ideas/user/{id}")
    Observable<List<Idea>> getUserIdeas(@Path("id") String id);

    @POST("ideas")
    Observable<Idea> createIdea(@Body Idea idea);

    @GET("favorites/{userId}")
    Observable<List<Favorite>> getFavorites(@Path("userId") String userId);

    @POST("favorite")
    Observable<Favorite> addFavorite(@Body Favorite favorite);

}
