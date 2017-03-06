package org.test;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class GenerateJavaUtil {

    public static void main(String[] args) {

        //Make your database calls here and fetch the data.
        Map<String, String> constMap = new HashMap<>();
        Map<String, String> propMap = new HashMap<String, String>();
        propMap.put("empName", "String");
        propMap.put("empAge", "Integer");

        constMap.put("EMP_CONST", "123");
        constMap.put("M_CONST1", "456");
        String packageName = "com.test";
        String className = "Employee";
        Properties props = new Properties();
        props.put("resource.loader", "class");
        props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        VelocityEngine ve = new VelocityEngine();
        ve.init(props);
        Template t = ve.getTemplate("java_class.vm");
        VelocityContext context = new VelocityContext();
        context.put("PACKAGE_NAME", packageName);
        context.put("CLASS_NAME", className);
        context.put("constMap", constMap);
        context.put("propMap", propMap);

        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        System.out.println(writer.toString());


    }


}
