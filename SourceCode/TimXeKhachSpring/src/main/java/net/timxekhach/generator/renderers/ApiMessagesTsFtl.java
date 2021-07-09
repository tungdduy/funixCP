package net.timxekhach.generator.renderers;

import lombok.Getter;
import net.timxekhach.generator.abstracts.AbstractTemplateBuilder;
import net.timxekhach.generator.abstracts.AbstractTemplateSource;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.utility.XeFileUtil;
import net.timxekhach.utility.XeStringUtils;

import java.util.*;
import java.util.function.Consumer;

import static net.timxekhach.utility.XeAppUtils.I18N_DIR;
import static net.timxekhach.utility.XeAppUtils.MESSAGE_PATH;

public class ApiMessagesTsFtl extends AbstractTemplateBuilder<ApiMessagesTsFtl.TemplateSource> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    @Override
    protected List<TemplateSource> prepareRenderFiles() {
        return Collections.singletonList(new TemplateSource());
    }

    @Getter
    public static class TemplateSource extends AbstractTemplateSource {

        //______________ Must be used in template
        private Set<Map.Entry<String, String>> messages;
        //____________________________________

        private final Map<String, String> messagesMap = new TreeMap<>();

        @Override
        protected void init() {
            XeFileUtil.readByLine(MESSAGE_PATH, processMessage);
            for (ErrorCode err : ErrorCode.values()) {
                buildRenderMessage(err);
            }
            messages = messagesMap.entrySet();
        }

        Consumer<String> processMessage = message -> {
            message = message.trim();
            String[] messageMap = message.split(":");
            if(messageMap.length > 1) {
                String key = messageMap[0].trim();
                String content = XeStringUtils.removeLastChar(message.substring(key.length() + 1).trim(), 1);
                messagesMap.put(key, content);
            }
        };

        @Override
        protected String buildRenderFilePath() {
            return I18N_DIR + "api-messages_vi.ts";
        }

        private void buildRenderMessage(ErrorCode err) {
            String content = XeStringUtils.trim(messagesMap.get(err.name()));
            StringBuilder params = new StringBuilder();
            boolean paramChanged = false;

            if(err.getParamNames() != null) {
                for(String paramName : err.getParamNames()) {

                    String param = "${param." + paramName + "}";
                    if(!content.contains(param)){
                        paramChanged = true;
                    }
                    params.append(param);
                }
                content = "(param) => `"+ params +"`";
            }
            if(content.isEmpty()) {
                content = "\"\"";
            }

            if(paramChanged || !messagesMap.containsKey(err.name())) {
                messagesMap.put(err.name(), content);
            }
        }
    }
}
