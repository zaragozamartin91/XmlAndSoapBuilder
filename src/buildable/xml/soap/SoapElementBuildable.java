package buildable.xml.soap;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import buildable.xml.XmlBuildable;
import buildable.xml.XmlBuildableException;

public class SoapElementBuildable implements XmlBuildable {
	private SOAPElement soapElement;
	private XmlBuildable parent;

	public SoapElementBuildable(SOAPElement soapElement, XmlBuildable parent) {
		super();
		this.soapElement = soapElement;
		this.parent = parent;
	}

	@Override
	public XmlBuildable setValue(Object value) throws XmlBuildableException {
		soapElement.setTextContent(value.toString());
		return this;
	}

	@Override
	public XmlBuildable setAttribute(String localName, String prefix, String value) throws XmlBuildableException {
		if (prefix == null) {
			soapElement.setAttribute(localName, value);
		} else {
			soapElement.setAttribute(prefix + ":" + localName, value);
		}
		return this;
	}

	@Override
	public XmlBuildable addChild(String localName, String prefix) throws XmlBuildableException {
		try {
			SOAPElement elem;
			if (prefix == null) {
				elem = soapElement.addChildElement(localName);
			} else {
				elem = soapElement.addChildElement(localName, prefix);
			}
			return new SoapElementBuildable(elem, this);
		} catch (SOAPException e) {
			throw new XmlBuildableException(e);
		}
	}

	@Override
	public XmlBuildable addNamespace(String prefix, String uri) throws XmlBuildableException {
		try {
			this.soapElement.addNamespaceDeclaration(prefix, uri);
			return this;
		} catch (SOAPException e) {
			throw new XmlBuildableException(e);
		}
	}

	@Override
	public XmlBuildable build() throws XmlBuildableException {
		return parent();
	}

	@Override
	public XmlBuildable addChild(String localName) throws XmlBuildableException {
		return addChild(localName, null);
	}

	@Override
	public XmlBuildable setAttribute(String localName, String value) throws XmlBuildableException {
		return this.setAttribute(localName, null, value);
	}

	@Override
	public XmlBuildable parent() {
		return parent;
	}

	@Override
	public XmlBuildable setDefaultNamespace(String namespaceUri) throws XmlBuildableException {
		try {
			soapElement.addNamespaceDeclaration("", namespaceUri);
		} catch (SOAPException e) {
			throw new XmlBuildableException(e);
		}
		return this;
	}
}
