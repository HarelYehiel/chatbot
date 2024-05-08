package com.handson.chatbot.service;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class  Weather {

    public static final Pattern PRODUCT_PATTERN = Pattern.compile("class=\"Fz\\(3rem\\).*?\\(ba\\)\">([^<]+)</h1>.*?\\(.3s\\)\">([^<]+).*?class=\"Lts\\(1px\\).*?\\(300\\)\">([^<]+)</time>.*?Fz\\(1.12rem\\)\">([^<]+)</p></div>.*?celsius celsius_D\\(b\\)\">([^<]+)");

    public String searchProducts(String keyword) throws IOException {
        return parseProductHtml(getProductHtml(keyword));
    }

    private String parseProductHtml(String html) {
        String res = "";
        Matcher matcher = PRODUCT_PATTERN.matcher(html);
        while (matcher.find()) {
            res += matcher.group(1) + " in " + matcher.group(2) + " at time:" + matcher.group(3) +
                    " weather: " + matcher.group(4) + " " + matcher.group(5) + "°C\n";
        }

        return res;
    }

    private String getProductHtml(String keyword) throws IOException {

        keyword = GoogleSearch.SearchInGoogle(keyword);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(keyword)
                .method("GET", null)
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .addHeader("accept-language", "he-IL,he;q=0.9,en-US;q=0.8,en;q=0.7")
                .addHeader("cache-control", "max-age=0")
                .addHeader("cookie", "GUCS=AU9jGwT6; EuConsent=CP-MScAP-MScAAOACKHEAyEgAAAAAAAAACiQAAAAAAAA; GUC=AQABCAFmOhVmaEIbGQR0&s=AQAAAFQrpBsI&g=ZjjHWw; A1=d=AQABBLZya2UCEHEUE_7dtXYokGyXNzsYGWMFEgABCAEVOmZoZl5Ub2UBAiAAAAcItnJrZTsYGWM&S=AQAAAm7Vdh88-6PWKjNlhB2KRA4; A1S=d=AQABBLZya2UCEHEUE_7dtXYokGyXNzsYGWMFEgABCAEVOmZoZl5Ub2UBAiAAAAcItnJrZTsYGWM&S=AQAAAm7Vdh88-6PWKjNlhB2KRA4; A3=d=AQABBLZya2UCEHEUE_7dtXYokGyXNzsYGWMFEgABCAEVOmZoZl5Ub2UBAiAAAAcItnJrZTsYGWM&S=AQAAAm7Vdh88-6PWKjNlhB2KRA4; cmp=t=1714997077&j=1&u=1---&v=24")
                .addHeader("priority", "u=0, i")
                .addHeader("referer", "https://www.yahoo.com/news/weather/?guccounter=1&guce_referrer=aHR0cHM6Ly93d3cuZ29vZ2xlLmNvbS8&guce_referrer_sig=AQAAAG5iizVGnTT6ch5IBLje8PuAY6KLYqltpyKMf80JA7g2A_-ln-ZK_hvcSWWrEyn8_T5RmRKzdhE3vOnPt5mYRxZ9AxSjr_-hefrO7cj677OFIBamclRusWCkwivu18Pc3KAZaxeNrFRAtnJ6Eax3Su2nBac5_WOxyKnE-dFVZ3c7")
                .addHeader("sec-ch-ua", "\"Chromium\";v=\"124\", \"Google Chrome\";v=\"124\", \"Not-A.Brand\";v=\"99\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
