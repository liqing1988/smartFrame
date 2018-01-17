package utils;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;  
/**
 * XML文件操作类
 * 用于读取模板文件，并根据配置信息生成新配置文件
 * @author admin
 *
 */
public class XMLOperator {
	private static String xmlPath = "./src/main/resources/xy-mybatis-generator.xml";
	private static InputStream fileStream = ClassLoader.getSystemResourceAsStream("generator.xml");   
	private static SAXReader saxReader = new SAXReader();  
	private static Document document ;
	
	public static void initBasicInfo(String url, String user, String password, String javaPath, String mapPath, String daoPath){
		try {
			document = saxReader.read(fileStream);
		} catch (DocumentException e) {
			e.printStackTrace();
		}  
		
		//修改数据库URL、user、password
		modifyNodeAttr("/generatorConfiguration/context/jdbcConnection", "connectionURL", url);
		modifyNodeAttr("/generatorConfiguration/context/jdbcConnection", "userId", user);
		modifyNodeAttr("/generatorConfiguration/context/jdbcConnection", "password", password);
		
		//修改Java path
		modifyNodeAttr("/generatorConfiguration/context/javaModelGenerator", "targetPackage", javaPath);
		//修改mapping path
		modifyNodeAttr("/generatorConfiguration/context/sqlMapGenerator", "targetPackage", mapPath);
		//修改dao path
		modifyNodeAttr("/generatorConfiguration/context/javaClientGenerator", "targetPackage", daoPath);
		
	}
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/ymxt";
		String user = "root";
		String password = "iamtop";
		String javaPath = "com.test.model";
		String mapPath = "com.test.mapper";
		String daoPath = "com.test.dao";
		
		String tableName = "sys_parameter";
		String domainName = "Parameter";
		
		initBasicInfo(url, user, password, javaPath, mapPath, daoPath);
		
		modifyTableNodeAttr(tableName, domainName);
	}

	//修改table： table、 domain
	public static void modifyTableNodeAttr(String tableName, String domainName){
		modifyNodeAttr("/generatorConfiguration/context/table", "tableName", tableName);
		modifyNodeAttr("/generatorConfiguration/context/table", "domainObjectName", domainName);
	}
	
	
	public static void modifyNodeAttr(String path, String attr, String value){
		 /** 先用xpath查找对象 */  
        List<Node> list = document.selectNodes(path+"/@"+attr);  
        Iterator<Node> iter = list.iterator();  
        while (iter.hasNext()) {  
            Attribute attribute = (Attribute) iter.next();  
            attribute.setValue(value);
        }  
	}
	
	public static void saveXMLFile(){
		try {  
            /** 将document中的内容写入文件中 */  
			/** 格式化输出,类型IE浏览一样 */  
            OutputFormat format = OutputFormat.createPrettyPrint();  
            /** 指定XML编码 */  
            format.setEncoding("UTF-8");  
			
            File outFile = new File(xmlPath);
            if(!outFile.exists()){
            	File parentFolder = new File(outFile.getParent());
            	parentFolder.mkdirs();
            	outFile.createNewFile();
            }
            
            XMLWriter writer = new XMLWriter(new FileWriter(outFile), format);  
            writer.write(document);  
            writer.close();  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
	}
	
}
