package generator;

import generator.abstracts.interfaces.RenderGroup;
import generator.abstracts.render.AbstractRender;

public class MessageGenerator {
    public static void main(String[] args) {
        AbstractRender.renderByGroup(RenderGroup.MESSAGE);
    }
}
