/**
 * 
 */
package ldapclient;

import java.io.File;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author dropspot
 *
 */
public class LDAPConfiguration {
	
	public Exception exception;
	public boolean errorStatus;
	
	
	/* gets configuration from pointed XML file
	 *
	 * XML file syntax:
	 *
	 * <ldapclientconf>
	 *   <connection>
	 *     <ssl></ssl>
	 *     <protocol></protocol>
	 *     <ip></ip>
	 *     <port></port>
	 *     <dn></dn>
	 *   </connection>
	 *   <ts>
	 *     <tspath></tspath>
	 *     <tspass></tspass>
	 *   </ts>
	 *   <credentials>
	 *     <ldaplogin></ldaplogin>
	 *     <ldappass></ldappass>
	 *   </credentials>
	 * </ldapclientconf>
	 *
	 * Note that ssl element content should be 'yes' or 'no'.
	 *
	 * */
	
	public Hashtable<String,String> load(String path) {

		Hashtable<String,String> ht = new Hashtable<String,String>();
		
		try {
			  File file = new File(path);
			  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			  DocumentBuilder db = dbf.newDocumentBuilder();
			  Document doc = db.parse(file);
			  doc.getDocumentElement().normalize();

			  NodeList nodeLst = doc.getElementsByTagName("connection");
			  Node node1 = nodeLst.item(0);
			  Element element = (Element) node1;

			  if (node1.getNodeType() == Node.ELEMENT_NODE) {
				  NodeList typeElmntLst = element.getElementsByTagName("ssl");
				  Element typeElmnt = (Element) typeElmntLst.item(0);
				  NodeList type = typeElmnt.getChildNodes();
				  String choice = ((Node)type.item(0)).getNodeValue().toString();
				  if(choice.compareTo("yes")==0) { ht.put("ssl", "yes"); }
				  if(choice.compareTo("no")==0) { ht.put("ssl", "no"); }

				  NodeList protocolElmntLst = element.getElementsByTagName("protocol");
				  Element protocolElmnt = (Element) protocolElmntLst.item(0);
				  NodeList protocol = protocolElmnt.getChildNodes();
				  ht.put("protocol", (((Node)protocol.item(0)).getNodeValue()) );

				  NodeList ipElmntLst = element.getElementsByTagName("ip");
				  Element ipElmnt = (Element) ipElmntLst.item(0);
				  NodeList ip = ipElmnt.getChildNodes();
				  ht.put("ip", (((Node)ip.item(0)).getNodeValue()) );

				  NodeList portElmntLst = element.getElementsByTagName("port");
				  Element portElmnt = (Element) portElmntLst.item(0);
				  NodeList port = portElmnt.getChildNodes();
				  ht.put("port", (((Node)port.item(0)).getNodeValue()) );

				  NodeList dnElmntLst = element.getElementsByTagName("dn");
				  Element dnElmnt = (Element) dnElmntLst.item(0);
				  NodeList dn = dnElmnt.getChildNodes();
				  ht.put("dn", (((Node)dn.item(0)).getNodeValue()) );
			  }

			  nodeLst = doc.getElementsByTagName("ts");
			  Node node2 = nodeLst.item(0);
			  element = (Element) node2;

			  if (node2.getNodeType() == Node.ELEMENT_NODE) {
				  NodeList tspathElmntLst = element.getElementsByTagName("tspath");
				  Element tspathElmnt = (Element) tspathElmntLst.item(0);
				  NodeList tspath = tspathElmnt.getChildNodes();
				  ht.put("tspath", (((Node)tspath.item(0)).getNodeValue()) );

				  NodeList tspassElmntLst = element.getElementsByTagName("tspass");
				  Element tspassElmnt = (Element) tspassElmntLst.item(0);
				  NodeList tspass = tspassElmnt.getChildNodes();
				  ht.put("tspass", (((Node)tspass.item(0)).getNodeValue()) );
			  }

			  nodeLst = doc.getElementsByTagName("credentials");
			  Node node3 = nodeLst.item(0);
			  element = (Element) node3;

			  if (node3.getNodeType() == Node.ELEMENT_NODE) {
				  NodeList ldaploginElmntLst = element.getElementsByTagName("ldaplogin");
				  Element ldaploginElmnt = (Element) ldaploginElmntLst.item(0);
				  NodeList ldaplogin = ldaploginElmnt.getChildNodes();
				  ht.put("ldaplogin", (((Node)ldaplogin.item(0)).getNodeValue()) );

				  NodeList ldappassElmntLst = element.getElementsByTagName("ldappass");
				  Element ldappassElmnt = (Element) ldappassElmntLst.item(0);
				  NodeList ldappass = ldappassElmnt.getChildNodes();
				  ht.put("ldappass", (((Node)ldappass.item(0)).getNodeValue()) );
			  }
		} catch (Exception e) { this.errorStatus=true; this.exception=e; }

		return ht;
	}
	
	public boolean isError() {
		return this.errorStatus;
	}

	public Exception getException() {
		return this.exception;
	}
}
