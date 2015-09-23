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
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

/**
 * SOAP Client Implementation using SAAJ Api.
 */
public class SaaJSoapClientTest {
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
//		SOAPElement execute = soapBody.addChildElement("execute");
		SOAPElement execute = soapBody.addChildElement("execute", "web");
		
		execute.addNamespaceDeclaration("", "http://webservices.cts.ast/");
		
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
	 * Starting point for the SAAJ - SOAP Client Testing
	 */
	public static void main(String args[]) {
		testRunWs();
	}
	
	
	
	private static void testRunWs() {
		try {
			// Send SOAP Message to SOAP Server
			SOAPMessage soapMessage = createSOAPRequest();

		} catch (Exception e) {
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
		}
	}//test_3
	
}