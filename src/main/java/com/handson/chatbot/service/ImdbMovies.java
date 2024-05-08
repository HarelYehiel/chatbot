package com.handson.chatbot.service;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ImdbMovies {

    public static final Pattern MOVIE_PATTERN = Pattern.compile("href=\"/title[^>]+>([^<]+)</a>.*?\"false\">([^<]+)</span>.*?\"false\">([^<]+)");

    public String searchMovie(String keyword) throws IOException {

        keyword = keyword.trim().replaceAll("\\s+", "%20");;
        return parseMovieHtml(getMovieHtml(keyword));
    }

    private String getMovieHtml(String keyword) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://m.imdb.com/find/?s=tt&q=" + keyword + "&ref_=nv_sr_sm")
                .method("GET", null)
                .addHeader("Referer", "https://m.imdb.com/find/?s=tt&q=kung%20fu%20panda%204&ref_=nv_sr_sm")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                .addHeader("sec-ch-ua", "\"Chromium\";v=\"124\", \"Google Chrome\";v=\"124\", \"Not-A.Brand\";v=\"99\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("Cookie", "session-id=144-7246229-3977962; session-id-time=2082787201l; session-token=13GdJKdqkv9iIO6+i/Zkn4eDjWF0AWQASXBpIKISsSPAqyPIXGauEhwl1vgWzRcyv+vwA9L42NnZlovnfMB2zBs/Ulq6++fxOp6sPooSWZWRSPw6Sur7DLUh+V4PPXp4q49nZ+6T3TX5fGo35GEbkOeDo3BO/CkA2fAhtdE5poZFYqlm3NFZXtNEADQRzGHGGohsioVEeJRNoBxmeOu9qM8ocxZYvGeuAypLf4zjx1Ncdk1RDF8s/QCXWsf7/ENgSW1ZAvTxPNx/zANUWq89hFu5pF9WyaySVGQrQSl91iah89APMLW2uEN/yZG/MegBV2UCYvpdhaidSHTuenD1p35FHX+ng0Um")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private String parseMovieHtml(String html) {
        String res = "";
        Matcher matcher = MOVIE_PATTERN.matcher(html);
        while (matcher.find()) {
            res += "Name:" + matcher.group(1) + " ,Year: " + matcher.group(2) + ", Actors: " + matcher.group(3) + "\n";
        }
        return res;
    }
}
