package net.timxekhach.utility;

import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class XeFileUtil extends FileSystemUtils {

    public static void readByLine(File file, Consumer<String> consumer)  {
        try(Scanner myReader = new Scanner(file);){
            while (myReader.hasNextLine()){
                consumer.accept(myReader.nextLine());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void readByLine(String filePath, Consumer<String> consumer) {
        readByLine(new File(filePath), consumer);
    }

    public static List<String> readAllLines(String filePath) {
        File file = new File(filePath);
        try {
            return Files.readAllLines(file.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void createFile(String path) {
        File file = new File(path);
        if(!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
