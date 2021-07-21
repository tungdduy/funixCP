package util;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ReflectionUtil extends ReflectionUtils {

    static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    public static <E, T extends E> Set<Class<? extends E>> findExtendsClassesInPackage(Class<E> clazz, String packageName) {

        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName))));

        return reflections.getSubTypesOf(clazz);
    }

    @SuppressWarnings({"unchecked","rawtypes"})
    public static <E> List<? extends E> newInstancesOfAllChildren(Class<E> clazz, String packageName) {
        List children = new ArrayList<>();
        findExtendsClassesInPackage(clazz, packageName).forEach(childClass -> {
            logger.debug("new instance for " + childClass.getName());
            if (Modifier.isAbstract(childClass.getModifiers())) {
                return;
            }
            Object child = newInstance(childClass);
            children.add(child);
        });
        return children;
    }

    public static void eachField(Object obj, Consumer<Field> consumer) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                consumer.accept(field);
            } catch (Exception e) {
                logger.error(String.format("cannot get field value of %s", obj.getClass().getName()));
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <E> List<E> findFieldsByType(Object obj, Class<E> targetClass, BiConsumer<Field, E> consumer) {
        List<E> result = new ArrayList<>();
        eachField(obj, field -> {
            if (field.getDeclaringClass().isAssignableFrom(targetClass)) {
                try {
                    E e = (E) field.get(obj);
                    consumer.accept(field, e);
                    result.add(e);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        return result;
    }

    public static Object newInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
//            logger.error("cannot create instance for " + clazz.getName());
        }
        return null;
    }

    @SuppressWarnings("all")
    public static void invokeSet(Object obj, String fieldName, Object value) {
        try {
            Method setMethod = obj.getClass().getMethod("set" + StringUtil.toCapitalizeEachWord(fieldName), value.getClass());
            Method getMethod = obj.getClass().getMethod("get" + StringUtil.toCapitalizeEachWord(fieldName));
            Class<?> fieldType = setMethod.getParameters()[0].getType();
            if (fieldType.isAssignableFrom(value.getClass()) && value.getClass().isAssignableFrom(fieldType)) {
                setMethod.invoke(obj, value.getClass().cast(value));
//                logger.info(String.format("%s updated %s: %s", obj.getClass().getSimpleName(), fieldName, getMethod.invoke(obj)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
