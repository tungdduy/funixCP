package generator.java.rest.api;

import generator.abstracts.models.AbstractRestModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import static generator.GeneratorSetup.API_OPERATION_REST_API_ROOT;
import static java.lang.String.join;
import static util.StringUtil.toImportFormat;

@Getter
@Setter
public class RestApiModel extends AbstractRestModel {

    @Override
    public void prepareSeparator() {
        this.separator("body");
        this.separator("import").unique(toImportFormat(
                RequiredArgsConstructor.class.getName(),
                "org.springframework.web.bind.annotation.*",
                this.getUrlNode().getBuilder().buildFullServiceClassName(),
                "static net.timxekhach.utility.XeResponseUtils.success",
                ResponseEntity.class.getName()
        ));
    }

    private String
            url,
            camelName;

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_REST_API_ROOT + this.getUrlNode().getBuilder().buildCapName() + "Api.java";
    }

}
