package generator;

import net.timxekhach.utility.Xe;
import generator.abstracts.render.AbstractRender;
import generator.abstracts.interfaces.RenderGroup;

public class GeneratorCentral extends Xe {

    private void renderAll(){
        AbstractRender.renderByGroup(RenderGroup.ALL);
    }

    public static void main(String[] args) {
        new GeneratorCentral().renderAll();
    }

}

