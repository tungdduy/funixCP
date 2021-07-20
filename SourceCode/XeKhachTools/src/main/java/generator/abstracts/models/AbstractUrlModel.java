package generator.abstracts.models;

import architect.urls.UrlNode;
import lombok.Getter;
import lombok.Setter;

import javax.xml.ws.Holder;

@Getter @Setter
public abstract class AbstractUrlModel extends AbstractModel {
    private UrlNode urlNode;
    public static Holder<UrlNode> urlNodeHolder = new Holder<>();

    @Override
    protected void init() {
        this.urlNode = urlNodeHolder.value;
        super.init();
    }
}
