package cn.kali.lrc.wordcount;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: yy
 * Date: 14-1-20
 * Time: 下午2:52
 */
public class IKAWordCounter {

    private static IKAWordCounter wordCounter = null;
    private static IKSegmenter iks;

    private IKAWordCounter(){
        iks = new IKSegmenter(null,true);
    };

    public static IKAWordCounter getInstance(){
        if(wordCounter == null ){
            wordCounter = new IKAWordCounter();
        }
        return  wordCounter;
    }

    /**
     * <p>Split sentence into words</p>
     * @return Set<String>
     * @throws NullPointerException
     */
    public Set<String> words(String text) throws IOException {
        if(text == null){
            throw new NullPointerException("text is null");
        }
        Reader reader = new InputStreamReader(
                new ByteArrayInputStream(
                        text.getBytes()));
        iks.reset(reader);
        Set<String> result = new HashSet<String>();
        Lexeme lexeme ;
        while ( (lexeme = iks.next() ) != null ){
            result.add(lexeme.getLexemeText());
        }

        return  result;
    }
}
