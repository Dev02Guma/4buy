package com.example.capadatos;

//import okhttp3.OkHttpClient;



public class ManagerURI {




    private static String SERVER = "10.0.3.2:8080";


    private static String URL_Base= "http://"+ SERVER + "/gmv_rest/index.php/";
    private static String DIR_DB = "/data/data/com.example.alderhernandez.a4buy/";
    public static String getURL_Base() {
        return URL_Base;
    }
    public static String getDirDb() {
        return DIR_DB;
    }
    /*public static OkHttpClient ConfigTimeOut(){
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build();
        return okHttpClient;
    }*/

    /*public static boolean isOnlinea(Context cxt){
        ConnectivityManager cm = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }*/

}
