package generator;

import generator.abstracts.render.AbstractRender;
import generator.abstracts.interfaces.RenderGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratorCentral {
    static Logger logger = LoggerFactory.getLogger(GeneratorCentral.class);
    public static void main(String[] args) {
         AbstractRender.renderByGroup(RenderGroup.ALL);
//        System.out.println(StringUtil.buildSeparator("IMPORT_CHILD_COMPONENT"));
    }

}

