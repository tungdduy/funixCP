package net.timxekhach.utility;

import org.aspectj.util.FileUtil;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public static <E> List<E> fetchAllDir(String dirPath, Function<File, E> converter, List<E> result) {
        if (result == null) result = new ArrayList<>();
        for (File dir : Objects.requireNonNull(new File(dirPath).listFiles(File::isDirectory))) {
            E value = converter.apply(dir);
            if(value != null) {
                result.add(value);
            }
            fetchAllDir(dir.getPath(), converter, result);
        }
        return result;
    }

    @SuppressWarnings("rawtypes")
    public static <E> List<E> fetchAllPossibleFiles(String dirPath, Function... converterChain) {
        return fetchFilesRecursive(dirPath, converterChain, new ArrayList<>());
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static <E> List<E> fetchFilesRecursive(String dirPath, Function[] converterChain, List<E> result) {
        for (File fileOrDir : Objects.requireNonNull(new File(dirPath).listFiles())) {
            if (fileOrDir.isFile()){
                Object chainResult = fileOrDir;
                for (Function function : converterChain) {
                    chainResult = function.apply(chainResult);
                    if (chainResult == null) {
                        break;
                    }
                }
                if(chainResult != null) {
                    result.add((E) chainResult);
                }
            } else if (fileOrDir.isDirectory()) {
                fetchFilesRecursive(fileOrDir.getPath(), converterChain, result);
            }
        }
        return result;
    }
}
