package generator;

import common.Xe;
import generator.abstracts.render.AbstractRender;
import generator.abstracts.interfaces.RenderGroup;
import net.timxekhach.utility.XeFileUtils;

import java.io.File;
import java.util.function.Function;

import static generator.GeneratorSetup.TOOLS_ROOT;
import static generator.GeneratorSetup.TOOL_GENERATOR_ROOT;
import static generator.abstracts.render.AbstractRender.getChildrenRender;
import static util.ObjectUtil.newInstanceFromClass;

public class GeneratorCentral extends Xe {

    private void renderAll(){
        AbstractRender.renderByGroup(RenderGroup.ALL);
    }

    public static void main(String[] args) {
        new GeneratorCentral().renderAll();
    }


    private void testFile() {
        XeFileUtils.fetchAllPossibleFiles(TOOL_GENERATOR_ROOT,
                getChildrenRender(AbstractRender.class),
                s -> {
                    System.out.println(s);
                    return null;
                });
    }




}

