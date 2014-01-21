package cn.kali.lrc.crawler.content;

/**
 * Created with IntelliJ IDEA.
 * User: yy
 * Date: 14-1-20
 * Time: 下午7:42
 * To change this template use File | Settings | File Templates.
 */
public class AblumEntity  implements  Entity{
    private String uri;
    private String singer;
    private String date;

    public URIEntity(String singer , String date , String uri){
        this.uri = uri;
        this.singer = singer;
        this.date = date;
    }

    public int type(){
        return  Entity.Albunm;
    }


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
