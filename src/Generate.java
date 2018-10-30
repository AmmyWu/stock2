
import com.core.generate.GenerateController;
import com.core.generate.GenerateModelVO;
import com.core.generate.GenerateService;
import freemarker.template.TemplateException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Generate {
    public static void main(String[] args) throws IOException, TemplateException, ParserConfigurationException, SAXException {
        String ftlPath = "";
        String controllerftlName = "";
        String fileName = "";
        String modelfilePath = "";
        String modelPackageName = "";

        String modelVOftlName = ""; //modelVO模板文件
        String controllerFilePath = "";
        String controllerPackageName = "";

        String serviceftlName = "";
        String servicefilePath = "";
        String servicepackgeName = "";

        String parentPackageName = "";

        String modelVOFilePath = "";
        String modelVOPackageName = "";

        List<Object> modellist = new ArrayList<Object>();
        //读取配置文件
        File xmlFile = new File(System.getProperty("user.dir"), "/src/GenerateConfStock.xml");
        DocumentBuilderFactory builderFactory =  DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);

        Element rootElement = doc.getDocumentElement();
        Node ftlnode = rootElement.getElementsByTagName("ftl").item(0);
        ftlPath = ((Element)ftlnode).getAttribute("path");
        NodeList params = ftlnode.getChildNodes();
        for(int i = 0; i < params.getLength(); i++){
            Node node = params.item(i);
            if(node.getNodeType() != Node.ELEMENT_NODE) continue;
            Element e = (Element)node;
            if(e.getAttribute("name").trim().equals("controller"))  controllerftlName = node.getFirstChild().getNodeValue();//controller.ftl
            if(e.getAttribute("name").trim().equals("modelVO"))  modelVOftlName = node.getFirstChild().getNodeValue();//modelVO.ftl
            if(e.getAttribute("name").trim().equals("service"))  serviceftlName = node.getFirstChild().getNodeValue();//service.ftl
        }
        //生成service层
        Node servicenode = rootElement.getElementsByTagName("service").item(0);
        servicefilePath = ((Element)servicenode).getAttribute("path");
        NodeList classNodes = servicenode.getChildNodes();
        for(int i =0;i<classNodes.getLength();i++){
            Node node = classNodes.item(i);
            if(node.getNodeType() != Node.ELEMENT_NODE) continue;
            Element e = (Element)node;
            if(e.getNodeName().trim().equals("servicePackageName")){
                servicepackgeName = node.getFirstChild().getNodeValue();
                continue;
            }
            if(e.getNodeName().trim().equals("modelPackageName")){
                modelPackageName = node.getFirstChild().getNodeValue();
                continue;
            }
            if(e.getNodeName().trim().equals("parentPackageName")){
                parentPackageName = node.getFirstChild().getNodeValue();
                continue;
            }
            if(e.getNodeName().trim().equals("class")){
                String className = e.getAttribute("name");
                String primaryKey = e.getAttribute("id");
                //生成一个service
                generateService(ftlPath, serviceftlName, servicefilePath, servicepackgeName, modelPackageName,className,primaryKey,parentPackageName);
            }
        }
        //生成controller层
        Node controllernode = rootElement.getElementsByTagName("controller").item(0);
        controllerFilePath = ((Element)controllernode).getAttribute("path");//\\src\\com\\sliansoft\\controller
        NodeList contollerNodes = controllernode.getChildNodes();
        for(int i =0;i<contollerNodes.getLength();i++){
            Node node = contollerNodes.item(i);
            if(node.getNodeType() != Node.ELEMENT_NODE) continue;
            Element e = (Element)node;
            if(e.getNodeName().trim().equals("controllerPackageName")){
                controllerPackageName = node.getFirstChild().getNodeValue();
                continue;
            }
            if(e.getNodeName().trim().equals("modelPackageName")){
                modelPackageName = node.getFirstChild().getNodeValue();
                continue;
            }
            if(e.getNodeName().trim().equals("parentPackageName")){
                parentPackageName = node.getFirstChild().getNodeValue();
                continue;
            }
            if(e.getNodeName().trim().equals("class")){
                String className = e.getAttribute("name");
                String primaryKey = e.getAttribute("id");
                generateController(ftlPath, controllerftlName, controllerFilePath, controllerPackageName, modelPackageName,className,primaryKey,parentPackageName);
            }
        }


        //生成modelVO
        Node modelVONode = rootElement.getElementsByTagName("modelVO").item(0);
        modelVOFilePath = ((Element)modelVONode).getAttribute("path");//\\src\\com\\sliansoft\\pojo\\vo
        NodeList modelVONodes = modelVONode .getChildNodes();
        for(int i =0;i<modelVONodes.getLength();i++){
            Node node = modelVONodes.item(i);
            if(node.getNodeType() != Node.ELEMENT_NODE) continue;
            Element e = (Element)node;
            if(e.getNodeName().trim().equals("modelVOPackageName")){
                modelVOPackageName = node.getFirstChild().getNodeValue();
                continue;
            }
            if(e.getNodeName().trim().equals("modelPackageName")){
                modelPackageName = node.getFirstChild().getNodeValue();
                continue;
            }
            if(e.getNodeName().trim().equals("class")){
                String className = e.getAttribute("name");
                String primaryKey = e.getAttribute("id");
                //生成一个modelVO
                generateModelVO(ftlPath, modelVOftlName, className, modelVOFilePath, modelVOPackageName, modelPackageName);
            }
        }
        System.out.println("create end");

    }

    private static void generateService(String ftlPath, String serviceftlName, String servicefilePath, String servicepackgeName,  String modelpackgeName,String className,String primaryKey,String parentPackageName) throws IOException, TemplateException {

        GenerateService.Generate(ftlPath, serviceftlName, servicefilePath, servicepackgeName, modelpackgeName,className,primaryKey,parentPackageName);
    }

    private static void generateController(String ftlPath, String ftlName,String filePath, String packageName,
                                           String modelpackageName,String className,String primaryKey,String parentPackageName) throws IOException, TemplateException{

        GenerateController.Generate(ftlPath, ftlName, filePath, packageName,
                modelpackageName, className, primaryKey, parentPackageName);
    }

    private static void generateModelVO(String ftlPath, String ftlName, String className, String filePath, String packageName, String modelPackageName) throws IOException, TemplateException {

        GenerateModelVO.Generate(ftlPath, ftlName, className, filePath, packageName, modelPackageName);
    }
}
