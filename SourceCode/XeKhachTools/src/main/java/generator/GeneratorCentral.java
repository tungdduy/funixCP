package generator;

import generator.models.sub.Join;
import generator.renders.abstracts.AbstractRender;
import net.timxekhach.security.constant.AuthEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GeneratorCentral {
    static Logger logger = LoggerFactory.getLogger(GeneratorCentral.class);
    public static void main(String[] args) {
        //        AbstractRender.renderAll();
//        logger.info(String.valueOf(AuthEnum.ADMIN_WRITE instanceof Enum));
//        List<Join> joins = new ArrayList<>();
//        joins.add(new Join("this", "that"));
//        joins.add(new Join("this2", "that2"));
//        joins.add(new Join("this3", "that3"));
//        joins.add(new Join("this4", "that4"));
//        joins.add(new Join("this5", "that5"));
//        List<Join> newJoins = joins.stream().peek(j -> j.setThisName(j.getThisName().replace("this", "diss"))).collect(Collectors.toList());
//        joins.stream().map(Join::getThisName).forEach(logger::info);
//        logger.info(StringUtil.buildSeparator("NEW"));
//        newJoins.stream().map(Join::getThisName).forEach(logger::info);
//        logger.info(StringUtil.buildSeparator("AFTER_CLEAR"));
//        joins.remove(3);
//        joins.stream().map(Join::getThisName).forEach(logger::info);
//        logger.info(StringUtil.buildSeparator("NEW"));
//        newJoins.stream().map(Join::getThisName).forEach(logger::info);





    }
}

