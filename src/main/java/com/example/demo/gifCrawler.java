package com.example.chatbot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import java.util.ArrayList;
import java.util.List;

public class gifCrawler {

    List<String> gifCollection = new ArrayList<String>();

    public List<String> gifCrawler(String intent){
        String link;
        String loveLink = "https://gfycat.com/gifs/search/love+you";
        String cuteDogLink = "https://gfycat.com/gifs/search/cute+dog";
        String cuteCatLink = "https://gfycat.com/gifs/search/cute+cat";
        String cuteBabyLink = "https://gfycat.com/gifs/search/cute+baby";
        if(intent.equals("negative"))
            link = loveLink;
        else {
            int i = 3;
            i = (int) (Math.random() * 100) + 1;
            if (i % 3 == 0)
                link = cuteDogLink;
            else if (i % 3 == 1)
                link = cuteCatLink;
            else
                link = cuteBabyLink;
        }
        try {
            Document document = Jsoup.connect(link).get();
            Elements elements = document.getElementsByClass("m-grid-item");
            for(Element element : elements){
                String photoLink = element.getElementsByTag("img").attr("src");
                if(photoLink.contains("https"))
                    gifCollection.add(photoLink);
            }

        }catch(Exception e){
            System.out.println("wrong");
        }
        System.out.println(gifCollection);
        return gifCollection;
    }
}
