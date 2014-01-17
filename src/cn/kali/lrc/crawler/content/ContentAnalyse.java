package cn.kali.lrc.crawler.content;

import cn.kali.lrc.crawler.webConnect.HtmlConnect;
import cn.kali.lrc.util.FileOut;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: yy
 * Date: 14-1-17
 * Time: 下午3:03
 * crawler lrc from web
 */
public class ContentAnalyse {
    private final String singerName;
    private final String  path;
    private final String urlTemplate="http://music.baidu.com/search/lrc?key=SINGER_NAME&start=PAGE_INDEX&size=20";
    private int songCount = 0;
    private HtmlConnect connection;
    private FileOut writer;

    public ContentAnalyse(String singerName,String path) {
        this.singerName = singerName;
        this.path = path;
        connection = new HtmlConnect();
        writer = new FileOut(path);
    }

    private String getUrl(int index){
        String url = urlTemplate.replace("SINGER_NAME",singerName);
        url = url.replace("PAGE_INDEX" , String.valueOf(index));
        return url;
    }

    private void extractLrc(){
        int index = 0;
        int step = 10;
        do{
            try {
                String html = connection.getHtmlByUrl(getUrl(index));
                Document doc = Jsoup.parse(html);
                if(songCount == 0){
                    Element e = doc.getElementsByAttributeValue("class","ui-tab-active   last-tab").first();
                    songCount = Integer.valueOf(e.text().replaceAll("\\D",""));
                }
                Element e = doc.select("#lrc_list ul").first();

                for(Element lrc : e.children()){
                    try{
                        String name = lrc.select(".song-title a").first().text();
                        String lrcContent = lrc.select(".lrc-content p").first().text();
                        writer.write(name,lrcContent);
                    }catch (NullPointerException exception){
                        continue;
                    }
                }
                index += step;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while(index < songCount);
    }

    public static void main(String[] args){
        ContentAnalyse ca = new ContentAnalyse("邓紫棋","./G.E.M/");
        ca.extractLrc();
    }

}
