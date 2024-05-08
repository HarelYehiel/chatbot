package com.handson.chatbot.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class GoogleSearch {

    public static String SearchInGoogle(String keyword) throws IOException {

        try {
            // חפש במנוע החיפוש שאתה מעדיף
            String url = "https://www.google.com/search?q=yahoo+weather+" + keyword;
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]"); // מציאת כל הקישורים בדף

            for (int i = 0; i < links.size(); i++) {
                String href = links.get(i).attr("href");
                if (href.contains("yahoo.com/news/weather/")) {
                    return href;
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return "not found the link";

    }
}
