package org.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/** */
public abstract class BaseGenJavaUtil {

	
	

	public static Template getVelocityTemplate(String templateFileName) {
		Properties props = new Properties();
		props.put("resource.loader", "class");
		props.put("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		VelocityEngine ve = new VelocityEngine();
		ve.init(props);
		return ve.getTemplate(templateFileName);
	}

	public static VelocityContext getVelocityContext(String courseName,
			Map<String, List<String>> topicMap) {
		VelocityContext context = new VelocityContext();
		if (courseName != null && topicMap!=null) {
			context.put("courseName", courseName);
			context.put("topicMap", topicMap);
		}
		return context;
	}

	public static Document getXMLDocument(String xMLFile) {

		File fXmlFile = new File(xMLFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc=null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return doc;
	}

	
	public static void writeFile(StringWriter writer,String destPath, String destFile, 
			String outputFileExt) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			if(destFile!=null && (destFile.contains("dotNet") || destFile.contains("asp_dotnet"))){
				destPath+="/dotNet";
			}else if(destFile!=null && ( destFile.contains("java") || destFile.contains("web_proj_jsf") )){
				destPath+="/java";
			}else if(destFile!=null && destFile.contains("hibernate")){
				destPath+="/hibernate";
			}else if(destFile!=null && destFile.contains("spring")){
				destPath+="/spring";
			}else{
				destPath+="/webDesign";
			}
			
			File destDIR=new File(destPath);
			if(!destDIR.exists()){
				destDIR.mkdirs();
			}
			File file =new File(destDIR,destFile+outputFileExt);// new File(outputFile + outputFileExt);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(writer.toString());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void readControllerXMLConfig(String templateFileName,String destPath,String xmlFileName,String coursefileName) {
		try {

			File fXmlFile = new File(xmlFileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("course");

			List<String> subTopicList = null;
			Map<String, List<String>> topicMap = null;
			String courseName = null;
			StringWriter writer = new StringWriter();
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					courseName = eElement.getAttribute("name");

					NodeList topicList = doc.getElementsByTagName("topic");

					for (int k = 0; k < topicList.getLength(); k++) {
						Node tNode = topicList.item(k);
						if (tNode.getNodeType() == Node.ELEMENT_NODE) {

							Element tElement = (Element) tNode;

							String topicName = tElement.getAttribute("name");
							NodeList sTList = tElement.getChildNodes();
							topicMap = new ConcurrentHashMap<String, List<String>>();
							subTopicList = new ArrayList<String>();
							for (int l = 0; l < sTList.getLength(); l++) {
								Node sTNode = sTList.item(l);
								if (sTNode.getNodeType() == Node.ELEMENT_NODE) {

									Element sTElement = (Element) sTNode;

									String subTopicName = sTElement
											.getTextContent();
									subTopicList.add(subTopicName);
								}
							}

							topicMap.put(topicName, subTopicList);

						}
						createVelocityWriter(templateFileName,courseName, topicMap,writer);	
						
					}
							
				}
			}
			createPHPFile(destPath,coursefileName,writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract void createPHPFile(String destPath,String destFileName,StringWriter writer) ;

	public void createVelocityWriter(String templateFileName,String courseName,
			Map<String, List<String>> topicMap, StringWriter writer){
		Template t = getVelocityTemplate(templateFileName);//"dotNet.vm");
		VelocityContext context = new VelocityContext();
		context.put("courseName", courseName);
		context.put("topicMap", topicMap);
		t.merge(context, writer);
	}

}
