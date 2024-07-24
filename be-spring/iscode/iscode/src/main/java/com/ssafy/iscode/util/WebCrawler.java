package com.ssafy.iscode.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class WebCrawler {

    //GET Problem Information of BaekJoon OJS
    public String getProblemInfo(Long pid) throws IOException {
        //Crawl Problem info by pid

        String url = "https://www.acmicpc.net/problem/";
        //String problemName = "21611";
        Document document = Jsoup.connect(url+pid).get();



        Elements elem = document.select("#problem-body");

        // delete button element & hint
        elem.select("button").remove();
        elem.select("#hint").remove();
        // change all a tag to p tag
        for (Element anchor : elem.select("a")) {
            Element p = new Element("span").text(anchor.text());
            anchor.replaceWith(p);
        }
        // return element to HTML
        return elem.html();
    }
    public static void main(String[] args) throws IOException {

        //for Test
        
        String url = "https://www.acmicpc.net/problem/";
        // problem url
        String problemName = "21611";
        // problem name
        Document document = Jsoup.connect(url+problemName).get();

        List<String> inputList = new ArrayList<>();
        List<String> outputList = new ArrayList<>();


        Elements elem = document.select("#problem-body");

        // delete button element & hint
        elem.select("button").remove();
        elem.select("#hint").remove();
        // change all a tag to p tag
        for (Element anchor : elem.select("a")) {
            Element p = new Element("span").text(anchor.text());
            anchor.replaceWith(p);
        }

        //print element
        System.out.println(elem);


    }
}
