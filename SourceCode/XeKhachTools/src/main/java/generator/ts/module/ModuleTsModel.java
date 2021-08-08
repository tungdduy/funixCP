package generator.ts.module;

import generator.abstracts.models.AbstractUrlModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static util.AppUtil.PAGES_DIR;
@Getter
@Setter
public class ModuleTsModel extends AbstractUrlModel {

    private List<Component> children = new ArrayList<>();
    private String capName, url;

    @Override
    public void prepareSeparator() {
        separator("headerImport").unique(
                "import {NgModule} from '@angular/core';",
                "import {FormsModule as ngFormsModule} from \"@angular/forms\";",
                "import {RouterModule} from \"@angular/router\";"
        ).emptyOnly();

        separator("moduleImport").unique(
                this.capName + "RoutingModule,",
                "ngFormsModule,",
                "RouterModule,"
                )
                .top("moduleImport", "imports: [")
                .bottom("moduleImport\", \"],");
    }

    @Override
    public String buildRenderFilePath() {
        return PAGES_DIR + this.getUrlNode().getBuilder().buildUrlChain() + "/" + this.getUrlNode().getUrl() + ".module.ts";
    }

}
