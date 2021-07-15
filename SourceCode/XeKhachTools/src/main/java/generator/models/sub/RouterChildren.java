package generator.models.sub;

import architect.urls.UrlNode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RouterChildren {
    private String key, keyChain;
    public RouterChildren(UrlNode urlNode) {
        this.key = urlNode.getBuilder().buildKey();
        this.keyChain = urlNode.getBuilder().buildKeyChain();
    }
}
