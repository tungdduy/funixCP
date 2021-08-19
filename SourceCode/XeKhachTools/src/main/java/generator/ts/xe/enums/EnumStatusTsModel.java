package generator.ts.xe.enums;

import generator.abstracts.models.AbstractModel;
import lombok.Getter;
import util.constants.RoleEnum;

import java.util.ArrayList;
import java.util.List;

import static util.AppUtil.*;

@Getter
public class EnumStatusTsModel extends AbstractModel {

    List<TsEnum> enums = new ArrayList<>();

    @Override
    public void prepareSeparator() {
        separator("import").unique();
    }

    @Override
    public String buildRenderFilePath() {
        return FRAMEWORK_MODEL_DIR + "EnumStatus.ts";
    }
}
