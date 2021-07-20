package net.timxekhach.utility.model;

import lombok.Getter;
import lombok.Setter;
import net.timxekhach.utility.XeFileUtils;
import org.springframework.util.ResourceUtils;

import javax.xml.ws.Holder;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class MailModel {
    String toEmail, subject, templateName, htmlContent;
    Map<String, String> params = new HashMap<>();

    public String getHtmlContent() {
        if (this.htmlContent == null) {
            loadContent();
        }
        return htmlContent;
    }

    private void loadContent() {
        try {
            String content = XeFileUtils.readFileAsString(ResourceUtils.getFile("classpath:email-templates/" + this.templateName + ".html"));
            Holder<String> contentHolder = new Holder<>(content);
            params.forEach((k, v) -> {
                contentHolder.value = contentHolder.value.replace(String.format("${%s}", k), v);
            });
            this.htmlContent = contentHolder.value;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
