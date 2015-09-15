package saaj;

/**
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

/**
 * SOAP Client Implementation using SAAJ Api.
 */
public class SaaJSoapClient {
	/**
	 * Method used to create the SOAP Request
	 */
	private static SOAPMessage createSOAPRequest() throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		/*	EL MENSAJE A CREAR ES AQUEL A CONTINUACION...
		 * 
		 * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://webservices.cts.ast/" xmlns:xsi="xsi">
   <soapenv:Header>
      <wsse:Security soapenv:mustUnderstand="1" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken>
            <wsse:Username>clara</wsse:Username>
            <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText"/>
         </wsse:UsernameToken>
      </wsse:Security>
   </soapenv:Header>
   <soapenv:Body>
      <web:execute>
         <requestConnection>
            <user>clara</user>
            <password/>
            <applicationID>1</applicationID>
            <sessionID xsi:nil="true"/>
         </requestConnection>
         <filtro>
            <i_login>12</i_login>
         </filtro>
      </web:execute>
   </soapenv:Body>
</soapenv:Envelope>
		 * */

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("web", "http://webservices.cts.ast/");
		envelope.addNamespaceDeclaration("xsi", "xsi");
		
		//SOAP Header
		SOAPHeader header = envelope.getHeader();
		header.addNamespaceDeclaration("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
		SOAPElement security = header.addChildElement("Security", "wsse");
		SOAPElement usernameToken = security.addChildElement("UsernameToken", "wsse");
		usernameToken.addChildElement("Username", "wsse").setTextContent("clara");
		SOAPElement password = usernameToken.addChildElement("Password","wsse");
		password.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement execute = soapBody.addChildElement("execute", "web");
		SOAPElement requestConnection = execute.addChildElement("requestConnection");
		requestConnection.addChildElement("user").setTextContent("clara");
		requestConnection.addChildElement("password");
		requestConnection.addChildElement("applicationID").setTextContent("1");
		requestConnection.addChildElement("sessionID").setAttribute("xsi:nil", "true");
		SOAPElement filtro = execute.addChildElement("filtro");
		filtro.addChildElement("i_login").setTextContent("12");

		//se persisten los cambios
		soapMessage.saveChanges();

		// Check the input
		System.out.println("Request SOAP Message = ");
		soapMessage.writeTo(System.out);
		System.out.println();
		return soapMessage;
	}

	/**
	 * Method used to print the SOAP Response
	 */
	private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Source sourceContent = soapResponse.getSOAPPart().getContent();
		System.out.println("\nResponse SOAP Message = ");
		StreamResult result = new StreamResult(System.out);
		transformer.transform(sourceContent, result);
	}

	/**
	 * Starting point for the SAAJ - SOAP Client Testing
	 */
	public static void main(String args[]) {
		testRunWs();
	}
	
	private static void test_2(){
		try {
			createSOAPRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void test_1() {
		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			String url = "http://localhost:8080/axis2/services/Student?wsdl";
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);

			// Process the SOAP Response
			printSOAPResponse(soapResponse);

			soapConnection.close();
		} catch (Exception e) {
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
		}
	}//test_1
	
	
	private static void testRunWs() {
		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			String url = "http://localhost:9082/AST-WS-CTS-IDM-DESBLOQUEAR_USUARIOS/DesbloquearUsuariosService?wsdl";
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);

			// Process the SOAP Response
			printSOAPResponse(soapResponse);

			soapConnection.close();
		} catch (Exception e) {
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
		}
	}//test_3
	
}