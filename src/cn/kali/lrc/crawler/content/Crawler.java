package cn.kali.lrc.crawler.content;

import cn.kali.lrc.util.FileOut;
import cn.kali.lrc.util.JedisPoolHandler;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: yy
 * Date: 14-1-21
 * Time: 下午1:51
 * To change this template use File | Settings | File Templates.
 */
public class Crawler {
    private final String dir = "../lrc/";           //base directory to store lrc files
    Jedis jedis = JedisPoolHandler.getJedisConnection();

    private List<AblumEntity> crawler(String singer) throws IOException {
        AblumCrawler ablumCrawler = new AblumCrawler();
        SongListCrawler songListCrawler = new SongListCrawler();
        LrcCrawler lrcCrawler = new LrcCrawler();
        FileOut out = new FileOut(dir + singer);

        List<AblumEntity> ablums = new ArrayList<AblumEntity>();
        ablums = ablumCrawler.crawleAblum(singer.trim());
        for(AblumEntity ablum : ablums){
            List<SongEntity> songs = new ArrayList<SongEntity>();
            out.mkdir(ablum.getAblum());
            songs = songListCrawler.crawleSong(ablum);
            for(SongEntity song : songs){
                String lrc = lrcCrawler.crawleLrc(song);
                out.write(ablum.getAblum() + "/" + song.getSongName() ,lrc);
            }
        }
        return  ablums;
    }

    public void crawleLrc() throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader(
                        new File("./conf/singerList")));
        String singer ;
        while( (singer = reader.readLine() ) != null){
            List<AblumEntity> ablums =  crawler(singer);
            jedis.select(JedisSelection.SINGER.index());
            for(AblumEntity ablum : ablums){
               jedis.sadd(singer,ablum.getAblum());
            }
        }
    }
    public static void  main(String[] args) throws IOException {
        Crawler crawler = new Crawler();
        crawler.crawleLrc();

    }

}
