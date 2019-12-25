package com.sayann.webcrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Start crawling the given root url and discovers all the interconnected url in the page and nested pages.
 *
 * What it does basically it enters the given root discovers all the neighbouring http|https| urls and starts
 * crawling the neighbours so on and so forth.
 *
 * This is a basic example of Breadth first Search algorithm.
 */

public class SimpleUrlCrawler implements WebCrawler {
  private Queue<String> queue;
  private List<String> discoveredUrls;

  public SimpleUrlCrawler() {
    queue = new LinkedList<>();
    discoveredUrls = new ArrayList<>();
  }

  @Override public void startCrawling(String urlToBeginWith) {
    queue.add(urlToBeginWith);
    discoveredUrls.add(urlToBeginWith);
    String regex = "\\b(http?|https)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    Pattern urlPattern = Pattern.compile(regex);
    while (!queue.isEmpty()) {
      String currentUrl = queue.poll();
      String rawHtml = getRawHtml(currentUrl);
      Matcher urlMatcher = urlPattern.matcher(rawHtml);

      while (urlMatcher.find()) {
        String theUrl = urlMatcher.group();
        if(!discoveredUrls.contains(theUrl)) {
          discoveredUrls.add(theUrl);
          System.out.println("Discovered Url: " + theUrl);
          queue.add(theUrl);
        }
      }
    }
    System.out.println("CRAWLING DONE\nTotal found urls: " + discoveredUrls.size());
  }

  /**
   * Extracts the raw html string from the given url
   *
   * @param urlString the urls
   * @return raw html which is visible when inspect element is performed in a web browser
   */
  private String getRawHtml(String urlString) {
    StringBuilder rawHtml = new StringBuilder();
    try {
      URL actualUrl = new URL(urlString);
      BufferedReader in = new BufferedReader(new InputStreamReader(actualUrl.openStream()));

      String currentLine;
      while ((currentLine = in.readLine()) != null) {
        rawHtml.append(currentLine);
      }
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return rawHtml.toString();
  }
}
