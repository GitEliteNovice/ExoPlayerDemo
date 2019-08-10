package request.elgroupinternational.com.exoplayerdemo.AuoPlayVideoRecyclerView;



public class VideoModel  {
private String video_url;
private boolean is_selected;

    public VideoModel(String video_url, boolean is_selected) {
        this.video_url = video_url;
        this.is_selected = is_selected;
    }

    public String getVideo_url() {
        return video_url;
    }

    public boolean isIs_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }


}