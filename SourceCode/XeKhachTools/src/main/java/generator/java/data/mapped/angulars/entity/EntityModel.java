package generator.java.data.mapped.angulars.entity;

import data.entities.abstracts.AbstractEntity;
import generator.java.data.mapped.EntityMappedModel;
import lombok.Getter;
import lombok.Setter;
import util.AppUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class EntityModel extends EntityMappedModel {

    @Override
    public void prepareSeparator() {
        List<AbstractEntity> allPk = new ArrayList<>();
        this.entity.getAllPkEntity(allPk);
        String allPkImports = allPk.stream().map(this::toImport).collect(Collectors.joining("\n"));
        String allMapToImports = this.mapColumns.stream()
                .map(columns -> columns.getMapTo().getEntity())
                .filter(e -> e.getClass() != this.entity.getClass())
                .map(this::toImport)
                .collect(Collectors.joining("\n"));
        separator("tsImport").unique(
                "import {XeEntity} from \"./XeEntity\";",
                "import {EntityIdentifier} from \"../../framework/model/XeFormData\";",
                "import {ObjectUtil} from \"../../framework/util/object.util\";",
                "import {XeTableData} from \"../../framework/model/XeTableData\";"
        ).unique(allPkImports)
         .unique(allMapToImports);

        separator("entityTable");
        separator("underImport");
        separator("body");
    }

    private String toImport(AbstractEntity pk) {
        return String.format("import {%s} from \"./%s\";", pk.getCapName(), pk.getCapName());
    }

    @Override
    public String buildRenderFilePath() {
        return AppUtil.ENTITIES_DIR + this.entityCapName + ".ts";
    }
}
