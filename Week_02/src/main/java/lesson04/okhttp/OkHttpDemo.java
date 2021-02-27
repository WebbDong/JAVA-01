package lesson04.okhttp;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Webb Dong
 * @description: OkHttpDemo
 * @date 2021-01-21 23:33
 */
public class OkHttpDemo {

    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(500, TimeUnit.MILLISECONDS)
                .readTimeout(1, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .get()
                .header("Connection", "close")
                .url("http://192.168.1.100:8083")
                .build();
        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            if (response.code() == 200) {
                ResponseBody body = response.body();
                System.out.println(body.string());
            }
        }
    }

}
