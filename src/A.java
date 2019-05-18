import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class A {

    public static void main(String[] args) throws IOException {
        URL url = new URL("https://mobile.twitter.com/AhnaHendrix/status/1117524992449818624?p=v");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        String htmlLine;
        while ((htmlLine = bufferedReader.readLine()) != null) {
            System.out.println(htmlLine);
        }
        bufferedReader.close();
    }
}
