package hk.edu.cuhk.ie.iems5722.cuhkcollector.Entity;


import com.alibaba.fastjson.annotation.JSONField;

public class MyEvent {

    @JSONField(name = "latitude")
    public double latitude;

    @JSONField(name = "longitude")
    public double longitude;

    public int colorIndex = 200;

    @JSONField(name = "eventTitle")
    public String title;

    @JSONField(name = "eventSnippet")
    public String snippet;

    @JSONField(name = "mapEventDetail")
    public MapEventDetail mapEventDetail;

    public MyEvent(double latitude, double longitude, int colorIndex, String title, String snippet){
        this.latitude = latitude;
        this.longitude = longitude;
        this.colorIndex = colorIndex;
        this.title = title;
        this.snippet = snippet;
        this.mapEventDetail = new MapEventDetail();
    }
    public MyEvent(){

    }
}
