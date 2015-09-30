package util.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;

public class XmlHelper {
	private static XmlHelper instance = new XmlHelper();

	protected XmlHelper() {
	}

	public static XmlHelper instance() {
		return instance;
	}

	public Document stringToW3cDocument(String str) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();

		InputStream stream = new ByteArrayInputStream(str.getBytes("UTF-8"));
		Document document = builder.parse(stream);

		return document;
	}

	public String mapToXmlString(Map<String, String> map) {
		XStream magicApi = new XStream();
		// magicApi.registerConverter( );
		magicApi.alias("root", Map.class);

		String xml = magicApi.toXML(map);
		return xml;
	}
}
