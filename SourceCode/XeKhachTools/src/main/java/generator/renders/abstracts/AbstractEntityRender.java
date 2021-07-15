package generator.renders.abstracts;

import generator.models.abstracts.AbstractTemplateModel;

public abstract class AbstractEntityRender<E extends AbstractTemplateModel> extends AbstractTemplateRender<E> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

}
