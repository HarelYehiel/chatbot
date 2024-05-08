package com.handson.chatbot.service;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AmazonService {
    public static final Pattern PRODUCT_PATTERN = Pattern.compile("<span class=\"a-size-medium a-color-base a-text-normal\">([^<]+)</span>.*<span class=\"a-icon-alt\">([^<]+)</span>.*<span class=\"a-offscreen\">([^<]+)</span>");

    public String searchProducts(String keyword) throws IOException {
        return parseProductHtml(getProductHtml(keyword));


    }

    private String parseProductHtml(String html) {
        String res = "";
        Matcher matcher = PRODUCT_PATTERN.matcher(html);
        while (matcher.find()) {
            res += matcher.group(1) + " - " + matcher.group(2) + ", price:" + matcher.group(3) + "\n\n";
        }

        return res;
    }

    private String getProductHtml(String keyword) throws IOException {


        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        while (true) {
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("https://www.amazon.com/s?k=" + keyword + "&crid=LOLAGKP50MPT&sprefix=ip%2Caps%2C237&ref=nb_sb_noss_2")
                    .method("GET", null)
                    .addHeader("authority", "www.amazon.com")
                    .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                    .addHeader("accept-language", "he-IL,he;q=0.9,en-US;q=0.8,en;q=0.7")
                    .addHeader("cache-control", "max-age=0")
                    .addHeader("cookie", "aws-target-data=%7B%22support%22%3A%221%22%7D; aws-target-visitor-id=1703420169226-51456.47_0; aws-ubid-main=480-5406051-3022621; regStatus=registered; aws-account-alias=995553441267; remember-account=true; awsc-color-theme=light; awsc-uh-opt-in=\"\"; AMCV_7742037254C95E840A4C98A6%40AdobeOrg=1585540135%7CMCIDTS%7C19738%7CMCMID%7C85020539559219489961003909259806840536%7CMCAAMLH-1705906504%7C6%7CMCAAMB-1705906504%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1705308904s%7CNONE%7CMCAID%7CNONE%7CvVersion%7C4.4.0; aws-userInfo=%7B%22arn%22%3A%22arn%3Aaws%3Aiam%3A%3A995553441267%3Auser%2Fharely%22%2C%22alias%22%3A%22995553441267%22%2C%22username%22%3A%22harely%22%2C%22keybase%22%3A%229femu%2FTBRc5FS%2FPGzzlix8Z7dqxl9hlgJmPIvUPrEjU%5Cu003d%22%2C%22issuer%22%3A%22http%3A%2F%2Fsignin.aws.amazon.com%2Fsignin%22%2C%22signinType%22%3A%22PUBLIC%22%7D; aws-userInfo-signed=eyJ0eXAiOiJKV1MiLCJrZXlSZWdpb24iOiJldS1ub3J0aC0xIiwiYWxnIjoiRVMzODQiLCJraWQiOiJlMTRlODcwOS0yOGE3LTRjODYtYWY0Yy0zMjdkNmI2YzMzYTEifQ.eyJzdWIiOiI5OTU1NTM0NDEyNjciLCJzaWduaW5UeXBlIjoiUFVCTElDIiwiaXNzIjoiaHR0cDpcL1wvc2lnbmluLmF3cy5hbWF6b24uY29tXC9zaWduaW4iLCJrZXliYXNlIjoiOWZlbXVcL1RCUmM1RlNcL1BHenpsaXg4WjdkcXhsOWhsZ0ptUEl2VVByRWpVPSIsImFybiI6ImFybjphd3M6aWFtOjo5OTU1NTM0NDEyNjc6dXNlclwvaGFyZWx5IiwidXNlcm5hbWUiOiJoYXJlbHkifQ.550NX4Oc0zLQnV4wg6DYHJglIjRewo_MzOlAljIeawL_pM5wDt8pz1X6hrPQiNaJ3RMt1wGZnHfopaqVMuk7DQfe4sK2F_xfikkCK6Q79OlZ8Swmc5-vIeSUv0uWouvY; noflush_awsccs_sid=18d4278a6a996bcb0190bd1c1e5cdeae3ef0dad58201d7f3f92bc555346234fd; session-id=145-4161383-9268806; ubid-main=130-7739062-5527534; id_pkel=n0; sp-cdn=\"L5Z9:IL\"; x-main=\"ItbmYRPCoIEjhdWou@wJifonY?ORRDnp68jWDZ8HKFDL0fsJzzPQA?vF4hMiQbGI\"; at-main=Atza|IwEBIMggTrCgwBrHsG3DJnYfe_NpHCSa-Ye5o7tm-OjUEBpaYFT_KXeqnuvKe2vH2sbppGiyBkvjRZIawtUiQ61rKxpqzYkXAUWzUd8HCym2EhfebvezLzE8vu_796A2vjMSeMvEy5mYc541sPNCVDY7My-sP3ELPJW9oahMxtlWUQkkALtwDat1sZy233iNCQFi39JhDUR1EhqFuR1VPp2nLTcVLhzETqp4t3JDTSaKfYSShOw-aCrJLjzCoIVgRI0oAmw; sess-at-main=\"0U0d/XIoZBNrmZNS+cT+TwT877cAlJKc2aRnr3g2QBA=\"; sst-main=Sst1|PQF70y5DZzl6lChem7O_p_JpCaxvpD0bAw6G_bkhVLfp1kC9xPKypsxa1sbGRaCgVVazxpBoEmtW6bx2MtYVasi58Rb5nBMMWj5G5-5Qjot47ScouDtZdNbhdPWNhyljc4khEdpGzNUhF6RFqbPzuFQaKxv7VBok2c04FSsiFvczMf5PCzVZg3pMG0WpOwLjlAJbs76loPn7Sm-GrWS6-vyVGE8eVI8Sif7K6sWZfYMg0x7yYLRBuzIxa6OOcueeO1TLh63noKPVVxYwmQ0t-9TXbwMOqvKd91joLjf1GCFkWZ8; lc-main=he_IL; session-id-time=2082787201l; i18n-prefs=USD; session-token=K3hEZ08I3sR9K4cbcOvXWJc5bM+eXkNJgFrQRcBb7rPwzgQ5W90mJ4WcZ9mI46FIoddqLaSiX+g8DD3vuOOybuNqXsRwLbYnXuAzyY03ZGntLPiE0ymA51vKrhEqWDXAvnNq2mCdbXuWXHvxY83Umm4C2Kn5eKR1++g8qRfXRVia/+yBYSxKBn9Q20LOrX95ORaR05ZwNtSoFAymnUn6UeVFazLTzw0Jh8kXB5ltzaiSrDyREhNizTfgfvbgeY2BzhV5FbS3bn4fyXsLqQU/0cdOXxN8VOfnpetp4c7bBIN3uudnYXJl035u4Z2z59GFFzA2dYggVzuRwv7wmflxGB+BpYF7ypXAFexhrvMuElQmYuP7Sg+oh+5c28QpNE7u; csm-hit=tb:3JR40VH0VYD3BYNHZK1K+s-04Z94GHX5NX1YWC9VRXD|1706010162626&t:1706010162626&adb:adblk_no; i18n-prefs=USD; lc-main=en_US; session-id=147-1915200-7420213; session-id-time=2082787201l; session-token=c4ea7lTm7ScfZMx5h2VJ25FH/BZq47wjtFDm9FwZUqlyKkgA+mtmwcln0pcTULJzWvoqJXOf9bFOdAMXKY6GVK2w0HE7yFzLrRel8HbwCfnY/qrjTS9P0qcGO1MHrGdHgeKCsE37f9s3Lm1FNUNtmBAwIScajkpN9ly8JIIHmbTYxLb8t3pV2srwxjFK0o0M8QCJ2zpevtq5MFVS+md7obt0xSiP0c+8s2KMQUDUFK8KrY+UH2CrsBx/y/im//KlZWlGj+GC0AMAwzRE4ORnppUMdVqAOGoOCdEDAIzenu9O7pKTJE6wUQmlESM3+8VtqZu3wEg2Fq6SRE49UEbu8ITUXayCVY25PmQmgy83xUwBqOCsLj4enY9mZKKUuqnN; sp-cdn=\"L5Z9:IL\"; ubid-main=132-0227894-5115643")
                    .addHeader("device-memory", "8")
                    .addHeader("downlink", "6.2")
                    .addHeader("dpr", "1.5")
                    .addHeader("ect", "4g")
                    .addHeader("referer", "https://www.amazon.com/gp/yourstore/home?path=%2Fgp%2Fyourstore%2Fhome&signIn=1&useRedirectOnSuccess=1&action=sign-out&ref_=nav_AccountFlyout_signout")
                    .addHeader("rtt", "0")
                    .addHeader("sec-ch-device-memory", "8")
                    .addHeader("sec-ch-dpr", "1.5")
                    .addHeader("sec-ch-ua", "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\"")
                    .addHeader("sec-ch-ua-mobile", "?0")
                    .addHeader("sec-ch-ua-platform", "\"Windows\"")
                    .addHeader("sec-ch-ua-platform-version", "\"10.0.0\"")
                    .addHeader("sec-ch-viewport-width", "494")
                    .addHeader("sec-fetch-dest", "document")
                    .addHeader("sec-fetch-mode", "navigate")
                    .addHeader("sec-fetch-site", "same-origin")
                    .addHeader("sec-fetch-user", "?1")
                    .addHeader("upgrade-insecure-requests", "1")
                    .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .addHeader("viewport-width", "494")
                    .build();
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            if(!str.contains(keyword)) continue;
            return str;
        }
    }
}
