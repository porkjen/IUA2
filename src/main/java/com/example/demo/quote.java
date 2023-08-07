package com.example.chatbot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class quote {
    List<String> quoteCollection = new ArrayList<String>();

    public List<String> quoteCrawler(){
        String link = null;
        String quoteLink="https://cmykhuang.com/positivequotes/";
        String[] tt;

        link = quoteLink;
        try {
            Document document = Jsoup.connect(link).get();
            Elements elements = document.getElementsByClass("entry-content clear");
            //String quote = element.getElementsByTag("p").text();
            for(Element element : elements){
                String quote = element.getElementsByTag("p").text();
                tt= element.html().split(" ");
                System.out.println("tt->"+tt);
                quoteCollection.add(quote);
            }

        }catch(Exception e){
            System.out.println("wrong");
        }
        //System.out.println(quoteCollection);
        return quoteCollection;
    }
}
