package generator.abstracts.interfaces;

import lombok.Getter;
import util.StringUtil;

import java.util.*;
import java.util.stream.Collectors;

import static util.FileUtil.readAsString;
import static util.StringUtil.fetchSeparatorContent;

@Getter
public abstract class SeparatorContent implements RenderFilePath {
    public static class Separator {
        @Getter
        private final String splitter;
        @Getter
        private String appendContent = "";
        boolean isUnique;
        Set<String> uniqueLines = new HashSet<>();

        Separator(String name, String... requireLines) {
            if (requireLines != null) {
                this.isUnique = true;
                this.uniqueLines.addAll(Arrays.asList(requireLines));
            }
            this.splitter = StringUtil.buildSeparator(name);
        }

        public Separator append(String content) {
            isUnique = false;
            this.appendContent = this.appendContent + content;
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
            if (this.isUnique) {
                return String.join("\n", splitter, top, StringUtil.trimTopBottomBlankLines(getUniqueContent()), bottom, splitter);
            } else {
                return String.join("\n\n", splitter, top, StringUtil.trimTopBottomBlankLines(appendContent), bottom, splitter);
            }

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
            isUnique = true;
            top = String.join("\n", topLines);
            return this;
        }
        public Separator bottom(String... bottomLines) {
            isUnique = true;
            bottom = String.join("\n", bottomLines);
            return this;
        }

        boolean emptyOnly = false;
        public void emptyOnly() {
            this.emptyOnly = true;
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
            String separatorContent = fetchSeparatorContent(separator.splitter, oldContent);
            if (StringUtil.isNotBlank(separatorContent)
                    && separator.emptyOnly) {
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
