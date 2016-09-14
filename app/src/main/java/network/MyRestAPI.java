package network;

import network.Response.Result;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Farhaan on 05-09-2016.
 */
public interface MyRestAPI {

    @GET("/HPImageArchive.aspx?format=js&idx=0&n=8")
    public Observable<Result> getResult();
}
