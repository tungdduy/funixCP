package generator.ts.xe.role;

import generator.abstracts.models.AbstractModel;
import lombok.Getter;
import util.constants.RoleEnum;

import static util.AppUtil.BUSINESS_DIR;

@Getter
public class XeRoleTsModel extends AbstractModel {

    private final RoleEnum[] roles = RoleEnum.values();

    @Override
    public String buildRenderFilePath() {
        return BUSINESS_DIR + "xe.role.ts";
    }
}
