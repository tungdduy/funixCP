package generator.abstracts.interfaces;

import lombok.Getter;
import util.StringUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.FileUtil.readAsString;
import static util.StringUtil.fetchSeparatorContent;

@Getter
public abstract class SeparatorContent implements RenderFilePath {
    public static class Separator {
        private String splitter;
        @Getter
        private String appendContent = "";
        boolean isUnique, isNewContentOnly;
        List<String> uniqueLines = new ArrayList<>();
        Function<String, String> splitterBuilder;
        String separatorName;
        Separator(String name) {
            this.separatorName = name;
        }

        public String getSplitter() {
            return this.splitterBuilder != null ? this.splitterBuilder.apply(this.separatorName) : StringUtil.buildSeparator(this.separatorName);
        }

        public void splitterBuilder(Function<String, String> splitterBuilder) {
            this.splitterBuilder = splitterBuilder;
        }

        public Separator append(String content) {
            isUnique = false;
            this.appendContent = this.appendContent + content;
            return this;
        }

        public Separator newOnly(){
            this.isNewContentOnly = true;
            return this;
        }

        public Separator prepend(String content) {
            isUnique = false;
            this.appendContent = content +  this.appendContent;
            return this;
        }

        public Separator unique() {
            this.isUnique = true;
            return this;
        }

        public Separator append(String... content) {
            isUnique = false;
            if (content != null) {
                this.appendContent += String.join("\n", content);
            }
            return this;
        }

        public Separator unique(String content) {
            unique(Arrays.asList(content.split("\n")));
            return this;
        }

        public Separator unique(String... content) {
            Arrays.stream(content)
                        .forEach(c -> StringUtil.addIfNotContainsTrim(uniqueLines, c));
            isUnique = true;
            return this;
        }

        public Separator unique(List<String> content) {
            content.forEach(c -> StringUtil.addIfNotContainsTrim(uniqueLines, c));
            isUnique = true;
            return this;
        }

        public String getUniqueContent() {
            return uniqueLines.stream()
                    .filter(StringUtil::isNotBlank)
                    .collect(Collectors.joining("\n"));
        }

        public String getAll() {
            String content = isUnique ? getUniqueContent() : appendContent;
            return  Stream.of(this.getSplitter(), top, content, bottom, this.getSplitter())
                    .filter(StringUtil::isNotBlank)
                    .map(StringUtil::trimTopBottomBlankLines)
                    .collect(Collectors.joining("\n"));
        }

        public Separator append(List<String> appendContent) {
            isUnique = false;
            if (appendContent != null) {
                this.appendContent += String.join("\n", appendContent);
            }
            return this;
        }

        private String top = "", bottom = "";
        public Separator top(String... topLines) {
            top = String.join("\n", topLines);
            return this;
        }
        public Separator bottom(String... bottomLines) {
            bottom = String.join("\n", bottomLines);
            return this;
        }

        boolean removeIfCurrentContentNotBlank = false;
        public void removeIfCurrentContentNotBlank() {
            this.removeIfCurrentContentNotBlank = true;
        }
        void clear() {
            this.top = "";
            this.bottom ="";
            this.appendContent = "";
            this.uniqueLines.clear();
        }
    }

    protected Map<String, Separator> separators = new HashMap<>();

    public Separator separator(String separatorName) {
        Separator separator = separators.getOrDefault(separatorName, new Separator(separatorName));
        separators.put(separatorName, separator);
        return separator;
    }

    public void prepareSeparator() {
    }

    public void updateSeparatorContent() {
        prepareSeparator();
        String oldContent = readAsString(this.buildRenderFilePath());
        this.separators.forEach((name, separator) -> {
            if(separator.isNewContentOnly) {
                return;
            }
            String separatorContent = fetchSeparatorContent(separator.getSplitter(), oldContent);
            if (StringUtil.isNotBlank(separatorContent)
                    && separator.removeIfCurrentContentNotBlank) {
                separator.clear();
            }

            if (separator.isUnique) {
                separator.unique(separatorContent);
            } else {
                separator.prepend(separatorContent);
            }
        });
    }
}
