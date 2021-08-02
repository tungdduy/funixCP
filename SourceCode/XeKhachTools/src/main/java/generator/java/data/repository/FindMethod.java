package generator.java.data.repository;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static util.StringUtil.toCapitalizeEachWord;

@Getter @Setter
public class FindMethod {
    public FindMethod(String name) {
        this.name = toCapitalizeEachWord(name);
    }
    String name;
    List<String> params = new ArrayList<>();

    public void addParam(String param) {
        this.params.add(param);
    }

    public FindMethod appendThenNew(String name) {
        FindMethod findMethod = new FindMethod(this.name + "And" + toCapitalizeEachWord(name));
        findMethod.params = new ArrayList<>(this.params);
        findMethod.params.add(name);
        return findMethod;
    }
}
