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

    public static String readFileAsString(File file) {
        try {
            return FileUtil.readAsString(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
