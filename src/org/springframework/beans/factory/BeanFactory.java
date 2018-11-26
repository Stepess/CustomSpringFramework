package org.springframework.beans.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.stereotype.Component;
import org.springframework.beans.factory.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class BeanFactory {
    private Map<String, Object> singletons = new HashMap<>();

    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }

    //TODO try to write with reflection example in stackoverflow
    //https://stackoverflow.com/questions/520328/can-you-find-all-classes-in-a-package-using-reflection
    public void instantiate(String basePackage) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        String path = basePackage.replace('.', '/');

        try {
            Enumeration<URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();

                File file = new File(resource.toURI());
                for (File classFile : file.listFiles()) {
                    String fileName = classFile.getName();

                    //recursive scanning
                    if (classFile.isDirectory()) {
                        instantiate(basePackage + "." + fileName);
                    }

                    if (fileName.endsWith(".class")) {
                        String className = fileName.substring(0, fileName.lastIndexOf('.'));
                        Class<?> classObject = Class.forName(basePackage + "." + className);

                        if (classObject.isAnnotationPresent(Component.class)) {
                            System.out.println("Component: " + classObject);
                            addClassToBeanContainer(className, classObject);
                        }

                        if (classObject.isAnnotationPresent(Service.class)) {
                            System.out.println("Service: " + classObject);
                            addClassToBeanContainer(className, classObject);
                        }
                    }
                }

            }
        } catch (IOException | URISyntaxException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

    }

    private void addClassToBeanContainer(String className, Class<?> classObject) throws InstantiationException, IllegalAccessException {
        Object instance = classObject.newInstance();
        String beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
        singletons.put(beanName, instance);
    }

    public void populateProperties() {
        System.out.println("==populateProperties==");

        //TODO think about three cycles
        for (Object object : singletons.values()) {
            for (Field field : object.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    for (Object dependency : singletons.values()) {
                        if (dependency.getClass().equals(field.getType())) {
                            injectDependencyToObjectFieldWithSetter(object, field, dependency);
                        }
                    }
                }
                if (field.isAnnotationPresent(Resource.class)) {
                    Resource annotation = field.getAnnotation(Resource.class);
                    String dependencyBeanName = annotation.name();
                    Object dependency = singletons.get(dependencyBeanName);
                    if (dependency == null) {
                        throw new RuntimeException("NoSuchBeanDefinition: " + dependencyBeanName);
                    }
                    injectDependencyToObjectFieldWithSetter(object, field, dependency);
                }
            }
        }
    }

    private void injectDependencyToObjectFieldWithSetter(Object object, Field field, Object dependency) {
        try {
            String setterName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            System.out.println("Setter name = " + setterName);
            Method setter = object.getClass().getMethod(setterName, dependency.getClass());
            setter.invoke(object, dependency);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void injectBeanNames() {
        for (String name : singletons.keySet()) {
            Object bean = singletons.get(name);
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(name);
            }
        }
    }

    public void injectBeanFactory() {
        for (Object bean: singletons.values()) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
        }
    }

    public void initializeBeans() {
        for (Object bean: singletons.values()) {
            if (bean instanceof InitializingBean) {
                ((InitializingBean) bean).afterPropertiesSet();
            }
        }
    }
}
