package hk.edu.cuhk.ie.iems5722.cuhkcollector.Utils;

public class ExceptionUtil {

    static public class IllegalChatroomIDException extends Exception{
        public IllegalChatroomIDException(){
            super("The chat room id you searched is not exist!!");
        }
    }

    static public class GetInfoException extends Exception{
        public GetInfoException(){
            super("Failed to get info from server!!");
        }
    }

    static public class LoadedAllMsgException extends Exception{
        public LoadedAllMsgException() { super("Loaded all messages!"); }
    }

    static public class PostInfoException extends Exception {
        public PostInfoException() { super("Failed to send message!");}
    }

}
