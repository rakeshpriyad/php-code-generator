package org.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public class PHPCourseGenDotNetUtil extends BaseGenJavaUtil {
	String destPath = "C:\\wamp\\www\\intellizone";

	public static void main(String[] args) {
		PHPCourseGenDotNetUtil util = new PHPCourseGenDotNetUtil();
		util.createCourses();
		util.createCSS();
	}

	public void createCSS() {
		copyDIR("js");
		copyDIR("css");
		copyDIR("images");
		copyDIR("header");
		copyDIR("menu");
		copyDIR("footer");
		copyDIR("android");

	}

	public void copyDIR(String destDIRToCopy) {
		try {
			File destDIR = new File(destPath + File.separator + destDIRToCopy);
			if (!destDIR.exists()) {
				destDIR.mkdirs();
			}

			File dIRToCopy = new File(destDIRToCopy);
			for (File f : dIRToCopy.listFiles()) {
				File destFile = new File(destPath + File.separator
						+ destDIRToCopy + File.separator + f.getName());
				Files.copy(f.toPath(), destFile.toPath(),
						java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}

	public void createPHPFile(String destPath, String destFileName,
			StringWriter writer) {
		writeFile(writer, destPath, destFileName, ".php");
	}

	public void createCourseParentPHPFile(String destPath, String destFileName,
			StringWriter writer, String courseName) {
		String templateFile="courseParent.vm";
		createFile(destPath, destFileName, writer, courseName,templateFile,null);
	}

	private void createFile(String destPath, String destFileName,
			StringWriter writer, String courseName,String templateFile,String learnMore) {
		Template t = getVelocityTemplate(templateFile);
		VelocityContext context = new VelocityContext();
		context.put("courseName", courseName);
		context.put("learnMore", learnMore);
		t.merge(context, writer);
		writeFile(writer, destPath, destFileName, ".php");
	}
	
	public void createLearnMorePHPFile(String destPath, String destFileName,
			StringWriter writer, String courseName,String learnMore) {
		String templateFile="learnMoreTemplate.vm";
		createFile(destPath, destFileName, writer, courseName,templateFile,learnMore);
	}
	public void createLearnMoreCoursePHPFile(String destPath, String destFileName,
			StringWriter writer, String courseName,String learnMore) {
		String templateFile="learnMoreCourseTemplate.vm";
		createFile(destPath, destFileName, writer, courseName,templateFile,learnMore);
	}
	public void createCoursePanelPHPFile(String destPath, String destFileName,
			StringWriter writer, String courseName,String learnMore) {
		String templateFile="CoursePanelTemplate.vm";
		createFile(destPath, destFileName, writer, courseName,templateFile,learnMore);
	}

	public void createCourses() {
		String templateFileName = "courseTemplate.vm";
		String coursefileNames[] = { "dotNet", "asp_dotnet", "java",
				"web_app_with_java", "spring", "hibernate",
				"standalone_proj_java","web_proj_jsf","web_proj_java","web_dev_fundamental","web_design"};
		String xmlFileNames[] = { "dotNet.xml", "ASPDotNetMVC.xml", "java.xml",
				"web_app_with_java.xml", "spring.xml", "hibernate.xml",
				"standalone_proj_java.xml","web_proj_jsf.xml","web_proj_java.xml","web_dev_fundamental.xml","web_design.xml"};
		String learnJava = "We provide Java training with industry experienced training professionals. In our java training program, you will learn Java programming, java Language, Basic Java programming, OOPS in Java, Java Classes, Exception Handling, Packages, Swing etc. Classes for Java is conducted only in weekends (Saturday and Sunday).";
		String learnDotNet="We provide Dot Net training with industry experienced training professionals. In our Microsoft dot net training program, you will learn basic of .net, .net framework, MVC framework, asp .net, c# language basics, ADO.net. Classes for Dot net is conducted only in weekends (Saturday and Sunday).";
		String learnWebDesign="We provide Web designing training with industry experienced training professionals. In our Web designing training program, you will learn web designing and development, working with Photoshop, HTML, DHTML, Java Script and CSS etc. Classes for Web designing is conducted only in weekends (Saturday and Sunday).";

		for (int i = 0; i < xmlFileNames.length; i++) {
			readControllerXMLConfig(templateFileName, destPath, "src/"
					+ xmlFileNames[i], coursefileNames[i]);
			StringWriter writer = new StringWriter();
			createCourseParentPHPFile(destPath, coursefileNames[i] + "_course",
					writer, coursefileNames[i]);
			writer = new StringWriter();
			String learnMore=learnDotNet;
			if(coursefileNames[i].contains("java")){
				learnMore=learnJava;
			}else if(coursefileNames[i].contains("dotnet")){
				learnMore=learnDotNet;
			}else{
				learnMore=learnWebDesign;
			}
			createLearnMorePHPFile(destPath, "learn_more_"+coursefileNames[i],
					writer, coursefileNames[i],learnMore);
			writer = new StringWriter();
			createLearnMoreCoursePHPFile(destPath, "learn_more_"+coursefileNames[i]+ "_course",
					writer, coursefileNames[i],learnMore);
			writer = new StringWriter();
			createCoursePanelPHPFile(destPath, coursefileNames[i]+ "_panel",
					writer, coursefileNames[i],learnMore);
		}
	}
}
