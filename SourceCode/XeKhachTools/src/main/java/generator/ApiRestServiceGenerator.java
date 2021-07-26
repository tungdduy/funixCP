package generator;

import generator.abstracts.interfaces.RenderGroup;
import generator.abstracts.render.AbstractRender;

public class
ApiRestServiceGenerator {
    public static void main(String[] args) {
        AbstractRender.renderByGroup(RenderGroup.API_REST_SERVICE);
    }
}
