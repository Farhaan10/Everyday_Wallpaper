package network.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Farhaan on 05-09-2016.
 */
public class Tooltips {

    @SerializedName("loading")
    @Expose
    private String loading;

    @SerializedName("previous")
    @Expose
    private String previous;

    @SerializedName("next")
    @Expose
    private String next;

    @SerializedName("walle")
    @Expose
    private String walle;

    @SerializedName("walls")
    @Expose
    private String walls;
}
