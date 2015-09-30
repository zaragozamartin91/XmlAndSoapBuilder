package buildable.xml.soap;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;
import org.w3c.dom.Document;

import buildable.xml.XmlBuildableException;

public class SoapMessageBuildableTest {

	@Test
	public void testBuild() throws SoapMessageBuildableException, XmlBuildableException {
		System.out
				.println("testBuild----------------------------------------------------------------------------------------------------------------------------------------");

		SoapMessageBuildable soapMessageBuildable = new SoapMessageBuildable();
		soapMessageBuildable
				.getEnvelopeBuildable()
				.addNamespace("web", "http://webservices.cts.ast/")
				.addNamespace("wsse",
						"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");

		soapMessageBuildable
				.getHeaderBuildable()
				.addChild("Security", "wsse")
				.addChild("UsernameToken", "wsse")
				.addChild("Username", "wsse")
				.setValue("clara")
				.parent()
				.addChild("Password", "wsse")
				.setAttribute("Type", null,
						"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");

		soapMessageBuildable.getBodyBuildable().addChild("validateUsers", "web").addChild("users").addChild("name")
				.setValue("martin").parent().addChild("pass").setValue("1234");

		soapMessageBuildable.build();

		System.out.println(soapMessageBuildable.toString());
	}

	@Test
	public void testBuildSetValueAsStringXml() throws SoapMessageBuildableException, XmlBuildableException {
		System.out
				.println("testBuildSetValueAsStringXml----------------------------------------------------------------------------------------------------------------------------------------");
		SoapMessageBuildable soapMessageBuildable = new SoapMessageBuildable();
		soapMessageBuildable
				.getEnvelopeBuildable()
				.addNamespace("web", "http://webservices.cts.ast/")
				.addNamespace("wsse",
						"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");

		soapMessageBuildable
				.getHeaderBuildable()
				.addChild("Security", "wsse")
				.addChild("UsernameToken", "wsse")
				.addChild("Username", "wsse")
				.setValue("clara")
				.parent()
				.addChild("Password", "wsse")
				.setAttribute("Type", null,
						"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");

		// <SOAP-ENV:Envelope
		// xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
		// xmlns:web="http://webservices.cts.ast/"
		// xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"><SOAP-ENV:Header><wsse:Security><wsse:UsernameToken><wsse:Username>clara</wsse:Username><wsse:Password
		// Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText"/></wsse:UsernameToken></wsse:Security></SOAP-ENV:Header><SOAP-ENV:Body><web:validateUsers><users><name>martin</name><pass>1234</pass></users></web:validateUsers></SOAP-ENV:Body></SOAP-ENV:Envelope>

		soapMessageBuildable.getBodyBuildable().addChild("validateUsers", "web")
				.setValue("&lt;users&gt;&lt;name&gt;martin&lt;/name&gt;&lt;pass&gt;1234&lt;/pass&gt;&lt;/users&gt;");

		soapMessageBuildable.build();

		System.out.println(soapMessageBuildable.toString());
	}

	@Test
	public void testJsonToXml() {
		System.out
				.println("testJsonToXml----------------------------------------------------------------------------------------------------------------------------------------");

		String str = "{name:'martin'}";
		JSONObject json = new JSONObject(str);
		String s_xml = XML.toString(json);

		System.out.println(s_xml);
	}

	@Test
	public void testBuildXmlFromString() throws Exception {
		System.out
				.println("testBuildXmlFromString----------------------------------------------------------------------------------------------------------------------------------------");

		SoapMessageBuildable soapMessageBuildable = new SoapMessageBuildable();
		soapMessageBuildable
				.getEnvelopeBuildable()
				.addNamespace("web", "http://webservices.cts.ast/")
				.addNamespace("wsse",
						"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");

		soapMessageBuildable.getBodyBuildable().setDefaultNamespace("http://webservices.cts.ast/");

		String exampleXML = "<validateUsers ><person/></validateUsers>";

		soapMessageBuildable.addDocumentToBody(exampleXML);
		System.out.println(soapMessageBuildable.toString());
	}

	@Test
	public void testBuildUsingDefaultNamespace() throws SoapMessageBuildableException, XmlBuildableException {
		System.out
				.println("testBuildUsingDefaultNamespace----------------------------------------------------------------------------------------------------------------------------------------");

		SoapMessageBuildable soapMessageBuildable = new SoapMessageBuildable();
		soapMessageBuildable
				.getEnvelopeBuildable()
				.addNamespace("wsse",
						"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")
				.addNamespace("web", "http://webservices.cts.ast/");

		soapMessageBuildable
				.getHeaderBuildable()
				.addChild("Security", "wsse")
				.addChild("UsernameToken", "wsse")
				.addChild("Username", "wsse")
				.setValue("clara")
				.parent()
				.addChild("Password", "wsse")
				.setAttribute("Type", null,
						"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");

		soapMessageBuildable.getBodyBuildable().addChild("validateUsers","web").setDefaultNamespace("asdasd")
				.addChild("users").addChild("name").setValue("martin").parent().addChild("pass").setValue("1234");

		soapMessageBuildable.build();

		System.out.println(soapMessageBuildable.toString());
	}
}
