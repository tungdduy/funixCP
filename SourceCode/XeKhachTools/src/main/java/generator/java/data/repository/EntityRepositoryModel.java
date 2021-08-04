package generator.java.data.repository;

import generator.GeneratorSetup;
import generator.abstracts.models.AbstractEntityModel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@SuppressWarnings({"rawtypes"})
@Getter
@Setter
public class EntityRepositoryModel extends AbstractEntityModel {
    private Set<String> importRequireLines;
    @SuppressWarnings({"unused"})
    public Set<String> getImportRequireLines() {
        if(importRequireLines == null) {
            importRequireLines = new HashSet<>(Arrays.asList(
                    String.format("import net.timxekhach.operation.data.entity.%s;", this.entityCapName),
                    String.format("import net.timxekhach.operation.data.mapped.%s_MAPPED;", this.entityCapName),
                    "import org.springframework.data.jpa.repository.JpaRepository;",
                    String.format("import %s;", "org.springframework.data.jpa.repository.JpaRepository"),
                    String.format("import %s;", "org.springframework.stereotype.Repository"),
                    String.format("import %s;", "java.util.List")
            ));
        }
        return importRequireLines;
    }

    private List<FindMethod> byPkMethods = new ArrayList<>();

    @Override
    public String buildRenderFilePath() {
        return GeneratorSetup.API_OPERATION_DATA_REPOSITORY_ROOT + this.entityCapName + "Repository.java";
    }
}
