package net.timxekhach.generator.renderers;

import net.timxekhach.generator.abstracts.AbstractTemplateBuilder;
import net.timxekhach.generator.sources.ApiMessagesTsSource;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.utility.XeFileUtils;
import net.timxekhach.utility.XeStringUtils;

import java.util.*;

import static net.timxekhach.utility.XeAppUtil.MESSAGE_PATH;

public class ApiMessagesTsFtl extends AbstractTemplateBuilder<ApiMessagesTsSource> {

    @Override
    protected boolean isOverrideExistingFile() {
        return true;
    }

    private Map<String, String> messagesMap;

    public Map<String, String> getMessagesMap() {
        if(this.messagesMap == null) {
            this.messagesMap = new TreeMap<>();
        }
        return messagesMap;
    }

    @Override
    protected void handleSource(ApiMessagesTsSource source) {
        XeFileUtils.readByLine(MESSAGE_PATH, message -> {
            message = message.trim();
            String[] messageMap = message.split(":");
            if(messageMap.length > 1) {
                String key = messageMap[0].trim();
                String content = XeStringUtils.removeLastChar(message.substring(key.length() + 1).trim(), 1);
                getMessagesMap().put(key, content);
            }
        });
        for (ErrorCode err : ErrorCode.values()) {
            buildRenderMessage(err);
        }
        source.setMessages(getMessagesMap().entrySet());
    }


    private void buildRenderMessage(ErrorCode err) {
        String content = XeStringUtils.trimToEmpty(getMessagesMap().get(err.name()));
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

        if(paramChanged || !getMessagesMap().containsKey(err.name())) {
            getMessagesMap().put(err.name(), content);
        }
    }

}
