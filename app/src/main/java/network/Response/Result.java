package network.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Farhaan on 05-09-2016.
 */
public class Result {

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images){
        this.images = images;
    }

    @SerializedName("images")
    @Expose
    private List<Images> images;

    public Tooltips getTooltips() {
        return tooltips;
    }

    public void setTooltips(Tooltips tooltips) {
        this.tooltips = tooltips;
    }

    @SerializedName("tooltips")
    @Expose
    private Tooltips tooltips;
}
