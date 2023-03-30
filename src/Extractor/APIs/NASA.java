package Extractor.APIs;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Extractor.Content;
import Extractor.ExtractorInterface;
import toolkit.JsonParser;

public class NASA implements ExtractorInterface{
    public List<Content> Extractor(String json){
        List<Map<String, String>> list = JsonParser.parse(json);

        List<Content> content = new ArrayList<>();

        for (Map<String, String> item : list) {
            content.add(new Content(item.get("title"), item.get("url")));
        }

        return content;
    }
}
