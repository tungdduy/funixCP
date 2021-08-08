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

    @Override
    public void prepareSeparator() {
        this.separator("import").unique(
                String.format("import net.timxekhach.operation.data.entity.%s;", this.entityCapName),
                String.format("import net.timxekhach.operation.data.mapped.%s_MAPPED;", this.entityCapName),
                "import org.springframework.data.jpa.repository.JpaRepository;",
                String.format("import %s;", "org.springframework.data.jpa.repository.JpaRepository"),
                String.format("import %s;", "org.springframework.stereotype.Repository"),
                String.format("import %s;", "java.util.List")
        );

        this.separator("body");
    }

    private List<FindMethod> findByPks = new ArrayList<>();
    private List<CapCamel> entitiesCountMe = new ArrayList<>();

    @Override
    public String buildRenderFilePath() {
        return GeneratorSetup.API_OPERATION_DATA_REPOSITORY_ROOT + this.entityCapName + "Repository.java";
    }
}
