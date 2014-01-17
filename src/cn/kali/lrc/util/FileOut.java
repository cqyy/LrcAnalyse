/*
 * */
package cn.kali.lrc.util;

import java.io.*;
import java.nio.file.Path;

public class FileOut {

    private String baseDir;
    private FileWriter writer;

    public FileOut(String dir){
        this.baseDir = dir.endsWith("/") ? dir : dir + "/";
        File file = new File(baseDir);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    public void write(String name , String content) throws IOException {
        String outdir = baseDir + name;
        writer = new FileWriter(new File(outdir));
        writer.write(content);
        writer.flush();
        writer.close();
    }
}
