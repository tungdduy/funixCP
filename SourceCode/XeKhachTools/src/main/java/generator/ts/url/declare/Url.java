package generator.ts.url.declare;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Url {
    private String config;
    private String key;
    private final List<Url> children = new ArrayList<>();
}
