package generator.java.rest.service;

import generator.abstracts.models.AbstractRestModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static generator.GeneratorSetup.API_OPERATION_REST_SERVICE_ROOT;
import static java.lang.String.join;
import static util.StringUtil.toImportFormat;

@Getter @Setter
public class RestServiceModel extends AbstractRestModel {

    @Override
    public void prepareSeparator() {
        separator("import").unique(toImportFormat(
                RequiredArgsConstructor.class.getName(),
                Transactional.class.getName(),
                Service.class.getName(),
                "net.timxekhach.operation.response.ErrorCode"
        ));
    }

    @Override
    public String buildRenderFilePath() {
        return API_OPERATION_REST_SERVICE_ROOT + this.getUrlNode().getBuilder().buildCapName() + "Service.java";
    }
}