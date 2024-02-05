package com.handson.chatbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JokesService {
    OkHttpClient client = new OkHttpClient().newBuilder().build();
    @Autowired
    ObjectMapper om;
    public String getValueById(String id) throws IOException {
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.chucknorris.io/jokes/" + id)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        JokesObject res = om.readValue(response.body().string(), JokesObject.class);
        return res.getValue();

    }

    private static class JokesObject {
        String id;
        String value;
        List<String> categories;
        String created_at;
        String icon_url;
        String updated_at;
        String url;
        public String getId() {
            return id;
        }

        public String getValue() {
            return value;
        }

        public List<String> getCategories() {
            return categories;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getUrl() {
            return url;
        }
    }
}
