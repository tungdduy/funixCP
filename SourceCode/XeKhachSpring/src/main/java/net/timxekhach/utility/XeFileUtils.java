package net.timxekhach.utility;

import org.aspectj.util.FileUtil;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

public class XeFileUtils extends FileSystemUtils {

    public static <E> void readByLine(File file, Function<String, E> function)  {
        try(Scanner myReader = new Scanner(file)){
            while (myReader.hasNextLine()){
                function.apply(myReader.nextLine());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void readByLine(String filePath, Consumer<String> consumer) {
        readByLine(new File(filePath), s -> {consumer.accept(s); return null;});
    }
    public static String readAsString(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return "";
        }
        return readFileAsString(file);
    }

    public static String readFileAsString(File file) {
        try {
            return FileUtil.readAsString(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
