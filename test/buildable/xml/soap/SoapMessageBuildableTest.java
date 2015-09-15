package buildable.xml.soap;

import org.junit.Test;

import buildable.xml.XmlBuildableException;
import buildable.xml.soap.SoapMessageBuildable;
import buildable.xml.soap.SoapMessageBuildableException;

public class SoapMessageBuildableTest {

	@Test
	public void testBuild() throws SoapMessageBuildableException, XmlBuildableException {
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
}
