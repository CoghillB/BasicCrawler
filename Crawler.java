import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Crawler {
    public static void main(String[] args) {
        crawl(""); // enter URL on this line
    }

    public static void crawl(String startingURL) {
        ArrayList<String> listOfPendingURLs = new ArrayList<String>();
        ArrayList<String> listOfVisitedURLs = new ArrayList<String>();
        listOfPendingURLs.add(startingURL);

        while (!listOfPendingURLs.isEmpty() && listOfVisitedURLs.size() < 100) { // adjust this number to change the
                                                                                 // number of pages visited
            String urlString = listOfPendingURLs.remove(0);
            if (!listOfVisitedURLs.contains(urlString)) {
                listOfVisitedURLs.add(urlString);
                System.out.println("Visiting " + urlString);
                for (String s : getSubURLs(urlString)) {
                    if (!listOfVisitedURLs.contains(s)) {
                        listOfPendingURLs.add(s);
                    }
                }
            }

        }
        for (int i = 0; i < listOfPendingURLs.size(); i++) {
            System.out.println(i + " " + listOfPendingURLs.get(i));
        }
    }

    private static ArrayList<String> getSubURLs(String urlString) {
        ArrayList<String> list = new ArrayList<String>();

        try {
            Thread.sleep(500); // adjust this delay to avoid crashing the server
            URI uri = new URI(urlString);
            URL url = uri.toURL();
            Scanner input = new Scanner(url.openStream());
            int current = 0;
            while (input.hasNext()) {
                String line = input.nextLine();
                current = line.indexOf("https:", current);
                while (current > 0) {
                    int endIndex = line.indexOf("\"", current);
                    if (endIndex > 0) {
                        list.add(line.substring(current, endIndex));
                        current = line.indexOf("https:", endIndex);
                    } else {
                        current = -1;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
}