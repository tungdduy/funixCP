package generator.java.data.repository;

import generator.GeneratorSetup;
import generator.abstracts.models.AbstractEntityModel;
import lombok.Getter;
import lombok.Setter;
import net.timxekhach.operation.response.ErrorCode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
                    String.format("import %s;", org.springframework.data.jpa.repository.JpaRepository.class.getName()),
                    String.format("import %s;", org.springframework.stereotype.Repository.class.getName()),
                    String.format("import %s;", ErrorCode.class.getName()),
                    String.format("import %s;", java.util.Map.class.getName())
            ));
        }
        return importRequireLines;
    }


    @Override
    public String buildRenderFilePath() {
        return GeneratorSetup.API_OPERATION_DATA_REPOSITORY_ROOT + this.entityClassName + "Repository.java";
    }
}
