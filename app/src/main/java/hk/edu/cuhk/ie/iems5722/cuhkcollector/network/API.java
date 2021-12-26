package hk.edu.cuhk.ie.iems5722.cuhkcollector.network;

public class API {
    public static String Head = "http://";

//        public static String ServerHost = "10.0.2.2:8080";
    public static String ServerHost = "192.168.137.1:8080";
//    public static String ServerHost = "42.193.248.26:8080"; //Tencent Cloud Server
//    public static String ServerHost = "47.250.45.252:8080"; //AliCloud Server

    public static String Version = "api/project";

    static public String GET_MAP_EVENTS = contactAPI(ServerHost, Version, "get_map_events");

    static public String SAVE_MAP_EVENT = contactAPI(ServerHost, Version, "save_map_event");

    static public String UPDATE_EVENT_DETAIL = contactAPI(ServerHost, Version, "update_event_detail");

    static public String contactAPI(String server, String version, String func){
        return Head + server +  "/" + version + "/" + func;
    }
}
