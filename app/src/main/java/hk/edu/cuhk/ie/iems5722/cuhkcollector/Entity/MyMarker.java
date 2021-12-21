package hk.edu.cuhk.ie.iems5722.cuhkcollector.Entity;


public class MyMarker {
    public double latitude;
    public double longitude;
    public int colorIndex;
    public String title;
    public String snippet;

    public MyMarker(double latitude, double longitude, int colorIndex, String title, String snippet){
        this.latitude = latitude;
        this.longitude = longitude;
        this.colorIndex = colorIndex;
        this.title = title;
        this.snippet = snippet;
    }
}
