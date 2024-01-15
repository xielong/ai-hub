package com.github.xielong.aihub.adapter.xunfei;

import com.github.xielong.aihub.adapter.AIModelInvoker;
import com.github.xielong.aihub.dao.CredentialMapper;
import com.github.xielong.aihub.util.AIProvider;
import okhttp3.HttpUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class XunfeiInvoker implements AIModelInvoker {

    private static final String SECURITY_CREDENTIAL_KEY_APP_ID = "appId";
    private static final String SECURITY_CREDENTIAL_KEY_API_KEY = "apiKey";
    private static final String SECURITY_CREDENTIAL_KEY_API_SECRET = "apiSecret";
    private static final String HOST_URL = "https://spark-api.xf-yun.com/v3.1/chat";
    @Autowired
    private CredentialMapper apiCredentialMapper;

    @Override
    public String invoke(String model, String input) throws Exception {
        String authUrl = generateAuthenticatedUrl();
        String url = authUrl.replace("https://", "wss://");

        String appId = apiCredentialMapper
                .findByProviderAndKey(AIProvider.XUNFEI.getId(), SECURITY_CREDENTIAL_KEY_APP_ID).getValue();

        WebSocketClient webSocketClient = new WebSocketClient(appId);
        return webSocketClient.sendMessageAndWait(url, input);

    }

    private String generateAuthenticatedUrl() throws Exception {
        URL url = new URL(HOST_URL);
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String formattedDate = format.format(new Date());

        String preSignatureString = "host: " + url.getHost() + "\n" +
                "date: " + formattedDate + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";

        String algorithm = "hmacsha256";
        Mac mac = Mac.getInstance(algorithm);
        String apiSecret = apiCredentialMapper
                .findByProviderAndKey(AIProvider.XUNFEI.getId(), SECURITY_CREDENTIAL_KEY_API_SECRET)
                .getValue();
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), algorithm);
        mac.init(spec);

        byte[] rawSignature = mac.doFinal(preSignatureString.getBytes(StandardCharsets.UTF_8));
        String base64Signature = Base64.getEncoder().encodeToString(rawSignature);
        String authorizationHeader = formatAuthorizationHeader(base64Signature);

        return constructAuthenticatedUrl(url, formattedDate, authorizationHeader);
    }

    private String formatAuthorizationHeader(String signature) {
        String apiKey = apiCredentialMapper
                .findByProviderAndKey(AIProvider.XUNFEI.getId(), SECURITY_CREDENTIAL_KEY_API_KEY)
                .getValue();
        return String.format("api_key=\"%s\", algorithm=\"hmac-sha256\", headers=\"host date request-line\", " +
                "signature=\"%s\"", apiKey, signature);
    }

    private String constructAuthenticatedUrl(URL url, String date, String authorizationHeader) {
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath()))
                .newBuilder()
                .addQueryParameter("authorization", Base64.getEncoder().encodeToString(
                        authorizationHeader.getBytes(StandardCharsets.UTF_8)))
                .addQueryParameter("date", date)
                .addQueryParameter("host", url.getHost())
                .build();
        return httpUrl.toString();
    }


}
