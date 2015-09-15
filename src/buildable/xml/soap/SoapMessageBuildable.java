package buildable.xml.soap;

import java.io.ByteArrayOutputStream;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import buildable.xml.XmlBuildable;
import buildable.xml.XmlBuildableException;

/**
 * Permite construir mensajes xml soap.<br/>
 * <br/>
 * Ejemplo de uso:<br/><br/>
 * <code>
 * 		SoapMessageBuildable soapMessageBuildable = new SoapMessageBuildable();<br/>
		soapMessageBuildable<br/>
				.getEnvelopeBuildable()<br/>
				.addNamespace("web", "http://webservices.cts.ast/")<br/>
				.addNamespace("wsse",
						"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");<br/>
<br/>
		soapMessageBuildable<br/>
				.getHeaderBuildable()<br/>
				.addChild("Security", "wsse")<br/>
				.addChild("UsernameToken", "wsse")<br/>
				.addChild("Username", "wsse")<br/>
				.setValue("clara")<br/>
				.parent()<br/>
				.addChild("Password", "wsse")<br/>
				.setAttribute("Type", null,
						"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");<br/>
<br/>
		soapMessageBuildable.getBodyBuildable().addChild("validateUsers", "web").addChild("users").addChild("name")<br/>
				.setValue("martin").parent().addChild("pass").setValue("1234");<br/>
<br/>
		soapMessageBuildable.build();<br/>
<br/>
		System.out.println(soapMessageBuildable.toString());<br/>
 * </code>
 * @author martin.zaragoza
 *
 */
public class SoapMessageBuildable implements XmlBuildable {
	private SOAPEnvelope envelope;
	private SOAPMessage soapMessage;

	public SoapMessageBuildable() throws SoapMessageBuildableException {
		try {
			MessageFactory messageFactory = MessageFactory.newInstance();
			soapMessage = messageFactory.createMessage();
			SOAPPart soapPart = soapMessage.getSOAPPart();
			envelope = soapPart.getEnvelope();
		} catch (Exception e) {
			throw new SoapMessageBuildableException(e);
		}
	}

	public SoapMessageBuildable addNamespaceInEnvelope(String prefix, String uri) throws SoapMessageBuildableException {
		try {
			envelope.addNamespaceDeclaration(prefix, uri);
			return this;
		} catch (SOAPException e) {
			throw new SoapMessageBuildableException(e);
		}
	}

	public SoapMessageBuildable addNamespaceInHeader(String prefix, String uri) throws SoapMessageBuildableException {
		try {
			SOAPHeader header = envelope.getHeader();
			header.addNamespaceDeclaration(prefix, uri);
			return this;
		} catch (SOAPException e) {
			throw new SoapMessageBuildableException(e);
		}
	}

	public XmlBuildable getBodyBuildable() throws SoapMessageBuildableException {
		try {
			return new SoapElementBuildable(envelope.getBody(), this);
		} catch (SOAPException e) {
			throw new SoapMessageBuildableException(e);
		}
	}

	public XmlBuildable getHeaderBuildable() throws SoapMessageBuildableException {
		try {
			return new SoapElementBuildable(envelope.getHeader(), this);
		} catch (SOAPException e) {
			throw new SoapMessageBuildableException(e);
		}
	}

	public XmlBuildable getEnvelopeBuildable() throws SoapMessageBuildableException {
		try {
			return new SoapElementBuildable(envelope, this);
		} catch (Exception e) {
			throw new SoapMessageBuildableException(e);
		}
	}

	@Override
	public XmlBuildable setValue(Object value) throws XmlBuildableException {
		try {
			return getBodyBuildable().setValue(value).build();
		} catch (Exception e) {
			throw new XmlBuildableException(e);
		}
	}

	@Override
	public XmlBuildable setAttribute(String localName, String prefix, String value) throws XmlBuildableException {
		try {
			return getEnvelopeBuildable().setAttribute(localName, prefix, value).build();
		} catch (SoapMessageBuildableException e) {
			throw new XmlBuildableException(e);
		}
	}

	@Override
	public XmlBuildable addChild(String localName, String prefix) throws XmlBuildableException {
		return this.addChildIn(localName, prefix, "body");
	}// addChild

	@Override
	public XmlBuildable addNamespace(String prefix, String uri) throws XmlBuildableException {
		try {
			this.addNamespaceInEnvelope(prefix, uri);
		} catch (SoapMessageBuildableException e) {
			throw new XmlBuildableException(e);
		}
		return this;
	}// addNamespace

	@Override
	public XmlBuildable build() throws XmlBuildableException {
		try {
			this.soapMessage.saveChanges();
			return this;
		} catch (SOAPException e) {
			throw new XmlBuildableException(e);
		}
	}// build

	@Override
	public XmlBuildable addChild(String localName) throws XmlBuildableException {
		return addChild(localName, null);
	}

	public XmlBuildable addChildIn(String localName, String prefix, String reference) throws XmlBuildableException {
		try {
			SOAPElement element;

			if (reference.equals("header")) {
				element = envelope.getHeader();
			} else if (reference.equals("body")) {
				element = envelope.getBody();
			} else {
				throw new XmlBuildableException("unknown reference!");
			}

			SOAPElement addChildElement;
			if (prefix == null) {
				addChildElement = element.addChildElement(localName);
			} else {
				addChildElement = element.addChildElement(localName, prefix);
			}
			return new SoapElementBuildable(addChildElement, this);
		} catch (SOAPException e) {
			throw new XmlBuildableException(e);
		}
	}

	public void print() throws SoapMessageBuildableException {
		try {
			System.out.println("Request SOAP Message = ");
			soapMessage.writeTo(System.out);
		} catch (Exception e) {
			throw new SoapMessageBuildableException(e);
		}
	}

	@Override
	public XmlBuildable setAttribute(String localName, String value) throws XmlBuildableException {
		return setAttribute(localName, null, value);
	}

	@Override
	public XmlBuildable parent() {
		return null;
	}

	@Override
	public String toString() {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			soapMessage.writeTo(out);
			String strMsg = new String(out.toByteArray());
			return strMsg;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}// SoapMessageBuilder
