package generator;

import generator.abstracts.interfaces.RenderGroup;
import generator.abstracts.render.AbstractRender;
import util.Xe;

public class GeneratorCentral extends Xe {

    private void renderAll(){
        AbstractRender.renderByGroup(RenderGroup.ALL);
    }

    public static void main(String[] args) {
        new GeneratorCentral().renderAll();
    }

}

