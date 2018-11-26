package org.springframework.beans.factory;

import org.springframework.beans.factory.stereotype.Component;

import java.io.File;
import java.io.IOException;
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
    public void instantiate(String basePackage) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        String path = basePackage.replace('.', '/');

        try {
            Enumeration<URL> resources = classLoader.getResources(path);

            //TODO try to scan embedded directories via recursion
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();

                File file = new File(resource.toURI());
                for (File classFile: file.listFiles()) {
                    String fileName = classFile.getName();
                    if (classFile.isDirectory()) {
                        instantiate(basePackage+"."+fileName);
                    }
                    if (fileName.endsWith(".class")) {
                        String className = fileName.substring(0, fileName.lastIndexOf('.'));
                        Class<?> classObject = Class.forName(basePackage + "." + className);
                        if (classObject.isAnnotationPresent(Component.class)) {
                            System.out.println("Component: " + classObject);
                        }
                    }
                }

            }
        } catch (IOException | URISyntaxException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
