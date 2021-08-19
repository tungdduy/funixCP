package util.constants;

import util.FileUtil;
import util.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static generator.GeneratorSetup.API_OPERATION_RESPONSE_ROOT;

public class ErrorCode {

    private List<String> paramNames = new ArrayList<>();

    public List<String> getParamNames() {
        return this.paramNames;
    }

    private final String _name;

    public String name() {
        return this._name;
    }

    ErrorCode(String name) {
        this._name = name;
    }

    ErrorCode(String name, List<String> paramNames) {
        this._name = name;
        this.paramNames = paramNames;
    }

    public static List<ErrorCode> values() {
        String fileContent = FileUtil.readFileAsString(new File(API_OPERATION_RESPONSE_ROOT + "ErrorCode.java"));
        String rawErrors = StringUtil.getStringBetween(fileContent, "ErrorCode \\{", ";");
        return Arrays.stream(rawErrors.split("\\n")).filter(StringUtil::isNotBlank).map(ErrorCode::convertFromString).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        ErrorCode.values().forEach(errorCode -> {
            System.out.printf("name: %s - params: %s", errorCode._name, errorCode.paramNames.isEmpty() ? "::Empty::" : String.join(",", errorCode.paramNames));
            System.out.println("=======");
        });
    }

    public static ErrorCode convertFromString(String error) {
        error = error.trim();
        System.out.println(error);
        if (error.contains("(")) {
            String name = error.split("\\(")[0];
            List<String> params = Arrays.stream(StringUtil.getStringBetween(error, "\\(", "\\)")
                    .split(","))
                    .map(String::trim)
                    .map(s -> s.substring(1, s.length() - 1))
                    .collect(Collectors.toList());
            return new ErrorCode(name, params);
        } else {
            return new ErrorCode(error.replace(",", "").trim());
        }
    }


}
