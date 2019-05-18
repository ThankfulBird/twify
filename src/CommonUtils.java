import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

class CommonUtils {

    static String toTitleCase(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    static Document openWebPage(String webPageURL) {
        try {

            return Jsoup.connect(webPageURL)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("http://www.google.com")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
