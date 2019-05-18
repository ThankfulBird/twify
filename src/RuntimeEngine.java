import java.util.Scanner;

public class RuntimeEngine {

    public static void main(String[] args) {
        startRuntimeEngine();
    }

    private static void startRuntimeEngine() {
        System.out.print("Enter userID: ");
        String userID = new Scanner(System.in).nextLine();
        long oldTime = System.currentTimeMillis();
        TweetCollector tweetCollector = TweetCollector.createTweetCollector(userID);
        long newTime = System.currentTimeMillis();
        System.out.println("*********************************************\nRuntime: " + (newTime - oldTime) + " milliseconds\n*********************************************");
    }
}
