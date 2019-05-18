import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class Tweet {

    private static final String twitterWebAddressWithoutSlash = "https://twitter.com";
    private Map<String, String> viewHyperLink;
    private String tweetText;
    private List<String> tweetTextHyperLinks;
    private String tweetDate;
    private int numberOfRetweets, numberOfLikes;

    private Tweet(Element tweetElement) {
        viewHyperLink = new LinkedHashMap<>();
        tweetTextHyperLinks = new ArrayList<>();
        constructTweet(tweetElement);
    }

    static Tweet createTweetObject(Element tweetElement) {
        return new Tweet(tweetElement);
    }

    private void constructTweet(Element tweetElement) {
        Element metadata = tweetElement.select("span.metadata > a").first();
        viewHyperLink.put(CommonUtils.toTitleCase(metadata.text().split(" ")[1]), metadata.attr("href"));
        tweetText = tweetElement.select("div.dir-ltr").text();
        for (Element anchor: tweetElement.select("div.dir-ltr > a")) {
            tweetTextHyperLinks.add(anchor.attr("href"));
        }
        Document detailsPage = CommonUtils.openWebPage(twitterWebAddressWithoutSlash
                + viewHyperLink.get("Details"));
        assert detailsPage != null;
        System.out.println(detailsPage.html());
        /*if (viewHyperLink.keySet().contains("Details")) {
            new Thread(() -> {
                Document detailsPage = CommonUtils.openWebPage(twitterWebAddressWithoutSlash
                        + viewHyperLink.get("Details"));
                assert detailsPage != null: "Cannot GET details page!";
                tweetDate = detailsPage.select("div.metadata > a").text();
                System.out.println(detailsPage.select("stats"));
            }).start();
        }*/
    }
}
