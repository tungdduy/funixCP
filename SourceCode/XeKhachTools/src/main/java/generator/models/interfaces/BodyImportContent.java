package generator.models.interfaces;

import util.StringUtil;

import static net.timxekhach.utility.XeFileUtils.readAsString;
import static util.StringUtil.fetchSeparatorContent;

public interface BodyImportContent extends RenderFilePath {
    void setImportContent(String importContent);
    void setBodyContent(String bodyContent);
    String getImportSeparator();
    String getBodySeparator();

    default void updateBodyImportContent() {
        String oldContent = readAsString(this.buildRenderFilePath());
        if (StringUtil.isNotBlank(oldContent)) {
            this.setImportContent(fetchSeparatorContent(this.getImportSeparator(), oldContent));
            this.setBodyContent(fetchSeparatorContent(this.getBodySeparator(), oldContent));
        }
    }
}
