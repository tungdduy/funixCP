package generator;

import util.StringUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneratorCentral {
    static Logger logger = Logger.getLogger(GeneratorCentral.class.getName());
    public static void main(String[] args) {
//        AbstractUrlTemplateRender.buildUrlFiles();
//        AbstractTemplateRender.buildAll();
//        Arrays.stream(Buss.class.getDeclaredFields()).forEach(System.out::println);
        logger.log(Level.INFO, (StringUtil.buildSeparator("IMPORT")));
        logger.log(Level.INFO, (StringUtil.buildSeparator("IMPORT")));
    }
}

