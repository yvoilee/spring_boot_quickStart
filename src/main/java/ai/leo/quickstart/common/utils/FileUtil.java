package ai.leo.quickstart.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * @Author: yvoilee
 * @Email: yvoilee@gmail.com
 * @Date: 2018-01-25  9:29
 */
public class FileUtil {
    public static boolean FileWriter(File file, String content){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(content);
            bufferedWriter.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
