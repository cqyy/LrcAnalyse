package cn.kali.lrc.crawler.content;

import cn.kali.lrc.crawler.webConnect.HtmlConnect;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yy
 * Date: 14-1-20
 * Time: 下午7:40
 * To change this template use File | Settings | File Templates.
 */
public class AblumCrawler {
    private static String albumUrlTemplemate = "http://music.sina.com.cn/yueku/search_new.php?" +
            "type=album&key=SINGER&nocacheset=1&page=PAGE_INDEX";

    public List<AblumEntity> crawleAblum(String singer) throws IOException {
        int indexCount = 0;
        int index = 1;
        List<AblumEntity> result = new ArrayList<AblumEntity>();
        do {
            String uri = albumUrlTemplemate.replace("SINGER", singer);
            uri = uri.replace("PAGE_INDEX", String.valueOf(index));
            HtmlConnect connect = new HtmlConnect();
            String html = connect.getHtmlByUrl(uri);
            Document doc = Jsoup.parse(html);

            if( indexCount == 0 ){
                Element e = doc.select("#pageBelow").first();
                if( e == null){
                    indexCount = 1;
                }else {
                    indexCount = e.children().size() - 2;
                }
            }
            Elements ablums = doc.select(".list_K2_con");
            for(Element ablum : ablums){
                AblumEntity entity = new AblumEntity();
                Element info = ablum.select("dd").first();
                entity.setDate(info.children().get(2).text().substring(0,10));
                entity.setSinger(singer);
                entity.setUri(info.select(".list_K2_infoA a").first().attr("href"));
                entity.setAblum(info.select(".list_K2_infoA a b").first().attr("title"));
                result.add(entity);
            }
            index++;
        } while (index < indexCount);

        return result;
    }

}
