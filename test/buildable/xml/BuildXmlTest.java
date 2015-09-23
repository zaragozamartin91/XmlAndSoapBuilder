package buildable.xml;

import static org.junit.Assert.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.junit.Test;

public class BuildXmlTest {

	@Test
	public void test() {
		Document doc = new Document();

		Namespace sNS = Namespace.getNamespace("someNS", "someNamespace");
		Element element = new Element("SomeElement", sNS);
		element.setAttribute("someKey", "someValue", Namespace.getNamespace("someONS", "someOtherNamespace"));
		Element element2 = new Element("SomeElement", Namespace.getNamespace("someNS", "someNamespace"));
		element2.setAttribute("someKey", "someValue", sNS);
		element.addContent(element2);
		doc.addContent(element);

		System.out.println(doc.toString());
	}

}
