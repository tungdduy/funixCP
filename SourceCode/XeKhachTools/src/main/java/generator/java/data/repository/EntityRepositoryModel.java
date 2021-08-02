package generator.java.data.repository;

import generator.GeneratorSetup;
import generator.abstracts.models.AbstractEntityModel;
import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.response.ErrorCode;

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
                    String.format("import net.timxekhach.operation.data.entity.%s;", this.entityClassName),
                    String.format("import net.timxekhach.operation.data.mapped.%s_MAPPED;", this.entityClassName),
                    "import org.springframework.data.jpa.repository.JpaRepository;",
                    String.format("import %s;", "org.springframework.data.jpa.repository.JpaRepository"),
                    String.format("import %s;", "org.springframework.stereotype.Repository"),
                    String.format("import %s;", "java.util.List"),
                    String.format("import %s;", "org.springframework.data.jpa.repository.Query"),
                    String.format("import %s;", "org.springframework.data.jpa.repository.Modifying")
            ));
        }
        return importRequireLines;
    }

    private List<FindMethod> byPkMethods = new ArrayList<>();
    private List<String> pkNames = new ArrayList<>();

    @Override
    public String buildRenderFilePath() {
        return GeneratorSetup.API_OPERATION_DATA_REPOSITORY_ROOT + this.entityClassName + "Repository.java";
    }
}
