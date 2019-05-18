import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class TweetCollector {

    private static final String twitterWebAddressWithSlash = "https://twitter.com/";
    private static final String twitterWebAddressWithoutSlash = "https://twitter.com";
    private static Document currentWebPage;
    private static int constructedTweetCounter;
    private final int TWEET_LIMIT_PER_ITERATION = 300;
    private final int TWEET_PER_PAGE = 30;
    private List<Tweet> tweetList;

    private TweetCollector(String userID) {
        if (isUserAvailable(userID)) {
            if (currentWebPage == null) {
                System.out.println("Web page created.");
                currentWebPage = CommonUtils.openWebPage(twitterWebAddressWithSlash + userID);
                tweetList = new ArrayList<>();
            } else {
                System.out.println("Web page modified.");
            }
            iterateOverTweetElements();
        } else {
            System.out.println("Web page is not created.");
            currentWebPage = null;
        }
    }

    private void iterateOverTweetElements() {
        int iterationCounter = 0;
        while (iterationCounter < TWEET_LIMIT_PER_ITERATION) {
            if (iterationCounter != 0 && iterationCounter % TWEET_PER_PAGE == 0) {
                assert currentWebPage != null : "Current page is null.";
                currentWebPage = CommonUtils.openWebPage(twitterWebAddressWithoutSlash
                        + currentWebPage.select("div.w-button-more > a").attr("href"));
            }
            assert currentWebPage != null : "Current page is null.";
            for (Element tweetElement: currentWebPage.getElementsByClass("tweet  ")) {
                tweetList.add(Tweet.createTweetObject(tweetElement));
                ++iterationCounter;
            }
        }
    }

    static TweetCollector createTweetCollector(String userID) {
        return new TweetCollector(userID);
    }

    private boolean isUserAvailable(String userID) {
        try {
            Jsoup.connect(twitterWebAddressWithSlash + userID)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
                    .referrer("http://www.google.com")
                    .get();
        } catch (HttpStatusException e) {
            System.out.println("User is not found in Twitter database.");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
