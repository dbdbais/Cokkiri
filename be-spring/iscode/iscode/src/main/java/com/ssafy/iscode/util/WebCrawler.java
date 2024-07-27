package com.ssafy.iscode.util;

import com.ssafy.iscode.problem.model.dto.Problem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class WebCrawler {

    //GET Problem Information of BaekJoon OJS
    public Problem crawlProblem(Long pid) throws IOException {


        String url = "https://www.acmicpc.net/problem/";
        // problem url
        //String problemName = "21611";

        Problem prob = new Problem();

        // problem name
        Document document = Jsoup.connect(url+pid).get();

        double percent = 0.0;

        Elements elem = document.select("#problem-body");

        // delete button element & hint
        elem.select("button").remove();
        elem.select("#hint").remove();
        // change all a tag to p tag
        for (Element anchor : elem.select("a")) {
            Element p = new Element("span").text(anchor.text());
            anchor.replaceWith(p);
        }

        // Select the #problem-info element
        Element problemInfo = document.selectFirst("#problem-info");

        if (problemInfo != null) {
            // Select the first tr within tbody
            Element firstRow = problemInfo.selectFirst("tbody tr");

            if (firstRow != null) {
                // Select the last td in the first tr
                Element lastTd = firstRow.select("td").last();

                if (lastTd != null) {
                    // Extract the text from the last td and convert it to a Double
                    String percentText = lastTd.text();
                    percent = Double.parseDouble(percentText.replace("%", "").trim());
                }
            }
        }
        System.out.println("Percent: "+percent);

        Map<Integer,String> inputList = new HashMap<>();
        Map<Integer,String> outputList = new HashMap<>();

        int iidx = 1;
        int oidx = 1;

        // extract sample inputs
        Elements sampleInputs = document.select("[id^=sample-input]");
        for (Element input : sampleInputs) {
            inputList.put(iidx++,input.text());
        }

        // extract sample outputs
        Elements sampleOutputs = document.select("[id^=sample-output]");
        for (Element output : sampleOutputs) {
            outputList.put(oidx++,output.text());
        }

        prob.setNo(pid);
        prob.setAlgoInput(inputList);
        prob.setAlgoOutput(outputList);
        prob.setAlgoPercent(percent);
        //set HTML
        prob.setInfo(elem.html());
        return prob;
    }
    public static void main(String[] args) throws IOException {

        //for Test
        
        String url = "https://www.acmicpc.net/problem/";
        // problem url
        String problemName = "21611";
        // problem name
        Document document = Jsoup.connect(url+problemName).get();

        Double percent = 0.0;

        // Select the #problem-info element
        Element problemInfo = document.selectFirst("#problem-info");

        if (problemInfo != null) {
            // Select the first tr within tbody
            Element firstRow = problemInfo.selectFirst("tbody tr");

            if (firstRow != null) {
                // Select the last td in the first tr
                Element lastTd = firstRow.select("td").last();

                if (lastTd != null) {
                    // Extract the text from the last td and convert it to a Double
                    String percentText = lastTd.text();
                    percent = Double.parseDouble(percentText.replace("%", "").trim());
                }
            }
        }
        System.out.println("Percent: "+percent);

        HashMap<Integer,String> inputList = new HashMap<>();
        HashMap<Integer,String> outputList = new HashMap<>();

        Elements elem = document.select("#problem-body");

        // delete button element & hint
        elem.select("button").remove();
        elem.select("#hint").remove();
        // change all a tag to p tag
        for (Element anchor : elem.select("a")) {
            Element p = new Element("span").text(anchor.text());
            anchor.replaceWith(p);
        }


        int iidx = 1;
        int oidx = 1;

        // extract sample inputs
        Elements sampleInputs = document.select("[id^=sample-input]");
        for (Element input : sampleInputs) {
            inputList.put(iidx++,input.text());
        }

        // extract sample outputs
        Elements sampleOutputs = document.select("[id^=sample-output]");
        for (Element output : sampleOutputs) {
            outputList.put(oidx++,output.text());
        }

        // print extracted inputs and outputs
        System.out.println("Sample Inputs: " + inputList);
        System.out.println("Sample Outputs: " + outputList);

        //print element
        System.out.println(elem);


    }
}
