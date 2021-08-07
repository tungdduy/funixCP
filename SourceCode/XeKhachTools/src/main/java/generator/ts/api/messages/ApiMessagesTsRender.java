package generator.ts.api.messages;

import generator.abstracts.render.AbstractRender;
import util.constants.ErrorCode;
import util.StringUtil;

import java.util.Map;
import java.util.TreeMap;

import static util.AppUtil.MESSAGE_PATH;
import static util.FileUtil.readByLine;

public class ApiMessagesTsRender extends AbstractRender<ApiMessagesTsModel> {

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
    protected void handleModel(ApiMessagesTsModel model) {
        readByLine(MESSAGE_PATH, message -> {
            message = message.trim();
            String[] messageMap = message.split(":");
            if(messageMap.length > 1) {
                String key = messageMap[0].trim();
                String content = StringUtil.removeLastChar(message.substring(key.length() + 1).trim(), 1);
                getMessagesMap().put(key, content);
            }
        });
        for (ErrorCode err : ErrorCode.values()) {
            buildRenderMessage(err);
        }
        model.setMessages(getMessagesMap().entrySet());
    }


    private void buildRenderMessage(ErrorCode err) {
        String content = StringUtil.trimToEmpty(getMessagesMap().get(err.name()));
        StringBuilder params = new StringBuilder();
        boolean paramChanged = false;

        if(err.getParamNames() != null) {
            for(String paramName : err.getParamNames()) {

                String param = String.format("${param.%s}", paramName);
                if(!content.contains(param)){
                    paramChanged = true;
                }
                params.append(param);
            }
            content = String.format("(param) => `%s`", params);
        }
        if(content.isEmpty()) {
            content = "\"\"";
        }

        if(paramChanged || !getMessagesMap().containsKey(err.name())) {
            getMessagesMap().put(err.name(), content);
        }
    }

}
