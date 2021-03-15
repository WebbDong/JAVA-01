package com.webbdong.sharding;

import com.google.gson.GsonBuilder;
import com.webbdong.sharding.model.scaling4_1_0.DataSourceParameter;
import com.webbdong.sharding.model.scaling4_1_0.JobConfiguration;
import com.webbdong.sharding.model.scaling4_1_0.RuleConfiguration;
import com.webbdong.sharding.model.scaling4_1_0.ScalingStartRequest;
import lombok.SneakyThrows;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author Webb Dong
 * @date 2021-03-16 1:38 AM
 */
public class ScalingUtils {

    private ScalingUtils() {}

    @SneakyThrows
    public static void startScaling(String scalingServerUrl, InputStream configIn, DataSourceParameter target) {
        StringBuilder sourceSB = new StringBuilder();
        StringBuilder sourceRulesSB = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(configIn))) {
            String line;
            boolean isRule = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || "".equals(line)) {
                    continue;
                }

                if (line.startsWith(" ")) {
                    if (isRule) {
                        sourceRulesSB.append(line).append("\n");
                    } else {
                        sourceSB.append(line).append("\n");
                    }
                } else if ("tables:".equals(line)) {
                    isRule = true;
                    sourceRulesSB.append(line).append("\n");
                } else {
                    isRule = false;
                    sourceSB.append(line).append("\n");
                }
            }
        }

        startScaling(scalingServerUrl,
                createScalingStartRequest(sourceSB.toString(), sourceRulesSB.toString(), target, 16));
    }

    @SneakyThrows
    private static void startScaling(String scalingServerUrl, ScalingStartRequest scalingStartRequest) {
        // 禁止将特殊字符例如 & 进行转义
        String requestJson = new GsonBuilder().disableHtmlEscaping().create().toJson(scalingStartRequest);
        System.out.println(requestJson);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(500, TimeUnit.MILLISECONDS)
                .readTimeout(1, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(scalingServerUrl)
                .header("Content-Type", "application/json")
                .post(RequestBody.create(requestJson.getBytes(StandardCharsets.UTF_8)))
                .build();
        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            if (response.code() == 200) {
                ResponseBody body = response.body();
                System.out.println(body.string());
            } else {
                System.out.println("responseCode = " + response.code()
                        + " " + response.message() + " " + response.body().string());
            }
        }
    }

    private static ScalingStartRequest createScalingStartRequest(String sourceDataSource,
                                                                 String sourceRules,
                                                                 DataSourceParameter target,
                                                                 int concurrencyCount) {
        return ScalingStartRequest
                .builder()
                .ruleConfiguration(RuleConfiguration
                        .builder()
                        .sourceDatasource(sourceDataSource)
                        .sourceRule(sourceRules)
                        .destinationDataSources(target)
                        .build())
                .jobConfiguration(JobConfiguration
                        .builder()
                        .concurrency(concurrencyCount)
                        .build())
                .build();
    }

}
