package generator.models.sub;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class UrlModel {
    private String config;
    private String key;
    private final List<UrlModel> children = new ArrayList<>();
}
