package generator.models.interfaces;

import util.ReflectionUtil;
import util.StringUtil;

import java.lang.reflect.Field;

import static net.timxekhach.utility.XeFileUtils.readAsString;
import static util.StringUtil.fetchSeparatorContent;
import static util.StringUtil.toUPPER_UNDERLINE;

public interface SeparatorContent extends RenderFilePath {

    default void updateSeparatorContent() {
        String oldContent = readAsString(this.buildRenderFilePath());
        if (StringUtil.isNotBlank(oldContent)) {
            for (Field field : this.getClass().getDeclaredFields()) {
                if (field.getName().contains("Separator")) {
                    String segmentName = StringUtil.removeLastChar(field.getName(), "Separator".length());
                    String segmentContentName = segmentName + "Content";
                    String segmentSeparator = StringUtil.buildSeparator(toUPPER_UNDERLINE(segmentName));
                    ReflectionUtil.invokeSet(this, segmentContentName, fetchSeparatorContent(segmentSeparator, oldContent));
                }
            }
        }
    }
}
