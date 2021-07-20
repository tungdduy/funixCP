package generator.models.interfaces;

import generator.models.abstracts.AbstractModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.StringUtil;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import static net.timxekhach.utility.XeFileUtils.readAsString;
import static util.ReflectionUtil.invokeSet;
import static util.StringUtil.*;

public interface SeparatorContent extends RenderFilePath {
    static Logger logger = LoggerFactory.getLogger(SeparatorContent.class);

    default void updateSeparatorContent() {
        String oldContent = readAsString(this.buildRenderFilePath());
        fetchAllSeparator(null, this.getClass()).forEach(segmentName -> {
            String segmentSeparator = buildSeparator(toUPPER_UNDERLINE(segmentName));
            String segmentContentName = segmentName + "Content";
            invokeSet(this, segmentContentName, fetchSeparatorContent(segmentSeparator, oldContent));
        });
    }

    default Set<String> fetchAllSeparator(Set<String> separatorNames, Class<?> checkingClass) {
        if(separatorNames == null) {
            separatorNames = new HashSet<>();
        }

        for (Field field : checkingClass.getDeclaredFields()) {
            if (field.getName().endsWith("Separator")
                    && field.getType() == String.class) {
                separatorNames.add(StringUtil.removeLastChar(field.getName(), "Separator".length()));
            }
        }

        if (AbstractModel.class.isAssignableFrom(checkingClass.getSuperclass())) {
            fetchAllSeparator(separatorNames, checkingClass.getSuperclass());
        }
        return separatorNames;
    }
}
