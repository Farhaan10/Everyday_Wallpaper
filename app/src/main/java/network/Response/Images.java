package network.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Farhaan on 05-09-2016.
 */
public class Images {

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    @SerializedName("startdate")
    @Expose
    private String startdate;

    @SerializedName("fullstartdate")
    @Expose
    private String fullstartdate;

    @SerializedName("enddate")
    @Expose
    private String enddate;

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }


    @SerializedName("url")
    @Expose
    private String url;

    public String getUrlbase() {
        return urlbase;
    }

    public void setUrlbase(String urlbase) {
        this.urlbase = urlbase;
    }

    @SerializedName("urlbase")
    @Expose
    private String urlbase;

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    @SerializedName("copyright")
    @Expose
    private String copyright;

    @SerializedName("copyrightlink")
    @Expose
    private String copyrightlink;

    @SerializedName("wp")
    @Expose
    private String wp;

    @SerializedName("hsh")
    @Expose
    private String hsh;

    @SerializedName("drk")
    @Expose
    private int drk;

    @SerializedName("top")
    @Expose
    private int top;

    @SerializedName("bot")
    @Expose
    private int bot;

    @SerializedName("hs")
    @Expose
    private List hs;
}
