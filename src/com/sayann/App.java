package com.sayann;

import com.sayann.webcrawler.WebCrawler;
import com.sayann.webcrawler.SimpleUrlCrawler;

public class App {

    public static void main(String[] args) {
        WebCrawler webCrawler = new SimpleUrlCrawler();
        webCrawler.startCrawling("https://timesofindia.indiatimes.com/");
    }
}
