package generator;

import generator.app.renderers.abstracts.AbstractTemplateRender;
import generator.app.renderers.abstracts.AbstractUrlTemplateRender;

public class GeneratorCentral {
    public static void main(String[] args) {
        AbstractUrlTemplateRender.buildUrlFiles();
        AbstractTemplateRender.buildAll();
    }
}

