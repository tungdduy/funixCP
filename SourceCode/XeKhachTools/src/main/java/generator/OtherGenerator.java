package generator;

import generator.abstracts.interfaces.RenderGroup;
import generator.abstracts.render.AbstractRender;
import net.timxekhach.utility.Xe;

public class OtherGenerator extends Xe {

    private void renderOther(){
        AbstractRender.renderByGroup(RenderGroup.OTHER);
    }

    public static void main(String[] args) {
        new OtherGenerator().renderOther();
    }

}

