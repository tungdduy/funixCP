package net.timxekhach.utility;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class XeReflectionUtils extends ReflectionUtils {

    @SuppressWarnings("unchecked")
    public static <E> E getField(Object o, String fieldName, Class<E> clazz) {
        E result = null;
        try {
            Field field = ReflectionUtils.findField(o.getClass(), fieldName);
            if (field != null) {
                field.setAccessible(true);
                result = (E) field.get(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

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
            try {
                Constructor constructor = childClass.getConstructor();
                Object child = constructor.newInstance();
                children.add(child);
            } catch (Exception ignored) {}

        });
        return children;
    }
}
