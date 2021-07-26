package generator.abstracts.interfaces;

import generator.abstracts.models.AbstractModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.StringUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.timxekhach.utility.XeFileUtils.readAsString;
import static util.ReflectionUtil.*;
import static util.StringUtil.*;

public interface SeparatorContent extends RenderFilePath {

    default void updateSeparatorContent() {
        String oldContent = readAsString(this.buildRenderFilePath());
        fetchAllSeparator(null, this.getClass()).forEach(segmentName -> {
            String segmentSeparator = buildSeparator(toUPPER_UNDERLINE(segmentName));

            String segmentContentName = segmentName + "Content";
            String segmentContent = fetchSeparatorContent(segmentSeparator, oldContent);

            String segmentRequireLinesName =  segmentName + "RequireLines";
            Set<String> segmentRequireLines = invokeGet(this, segmentRequireLinesName);

            if (segmentRequireLines != null) {
                segmentRequireLines.addAll(Arrays.asList(StringUtil.split("\n", segmentContent)));
                segmentContent = String.join("\n", segmentRequireLines);
            }

            invokeSet(this, segmentContentName, segmentContent);
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
