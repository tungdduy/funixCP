package net.timxekhach.generator.message;

import freemarker.template.Configuration;
import freemarker.template.Template;
import net.timxekhach.generator.GeneratorCenter;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.utility.XeFileUtil;
import net.timxekhach.utility.XeStringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MessageGenerator {
    public static final String
            TEMPLATE_DIR = "SourceCode/TimXeKhachSpring/src/main/java/net/timxekhach/generator/message",
            TEMPLATE_NAME = "api-messages.ts.ftl",
            MESSAGE_PATH = "SourceCode/XeKhachAngular/src/app/business/i18n/api-messages_vi.ts";

    public static void main(String[] args) {
        run();
    }

    private static final Map<String, String> messages = new TreeMap<>();

    public static void run(){
        messages.clear();
        Configuration config = GeneratorCenter.getConfig();
        XeFileUtil.readByLine(MESSAGE_PATH, MessageGenerator::processMessage);
        for(ErrorCode err : ErrorCode.values()) {
            messages.putIfAbsent(err.name(), makeAppMessageContent(err));
        }
        Map<String, Object> input = new HashMap<>();
        input.put("messages", messages.entrySet());
        Template template = GeneratorCenter.prepareTemplate(config, TEMPLATE_DIR, TEMPLATE_NAME);
        GeneratorCenter.writeToFile(input, template, new File(MESSAGE_PATH));
    }

    private static String makeAppMessageContent(ErrorCode err) {
        String content = "";
        String params = "";
        if(err.getParamNames() != null) {
            for(String param : err.getParamNames()) {
                params += "${param." + param + "}";
            }
            content = "(param) => `"+ params +"`";
        }
        if(content.isEmpty()) {
            content = "\"\"";
        }
        return content;
    }

    public static void processMessage(String message) {
        message = message.trim();
        String[] messageMap = message.split(":");
        if(messageMap.length > 1) {
            String key = messageMap[0].trim();
            String content = XeStringUtils.removeLastChar(message.substring(key.length() + 1).trim(), 1);
            messages.put(key, content);
        }
    }
}
