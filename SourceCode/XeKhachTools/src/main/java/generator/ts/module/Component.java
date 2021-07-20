package generator.ts.module;

import architect.urls.UrlNode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Component {
    private final String componentName;
    private final String url;

    public Component(UrlNode node) {
        this.componentName = node.getBuilder().buildComponentName();
        this.url = node.getUrl();
    }
}
