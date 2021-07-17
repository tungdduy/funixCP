package generator;

import generator.renders.abstracts.AbstractRender;
import generator.renders.abstracts.RenderGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratorCentral {
    static Logger logger = LoggerFactory.getLogger(GeneratorCentral.class);
    public static void main(String[] args) {
        AbstractRender.renderByGroup(RenderGroup.ENTITY);
    }
}

