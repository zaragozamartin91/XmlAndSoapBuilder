package buildable.xml;

/**
 * Define una estructura encadenada de Xml construible.
 * 
 * @author martin.zaragoza
 *
 */
public interface XmlBuildable {
	/**
	 * Establece el valor del elemento. Retorna this.
	 * 
	 * @param value
	 *            Valor a establecer.
	 * @return this.
	 */
	public XmlBuildable setValue(Object value) throws XmlBuildableException;

	/**
	 * Agrega un atributo a la entidad. Retorna this.
	 * 
	 * @param localName
	 *            - Nombre del atributo.
	 * @param prefix
	 *            - Prefijo del atributo, null o vacio en caso de no
	 *            corresponder.
	 * @param value
	 *            - Valor de atributo.
	 * @return this.
	 */
	public XmlBuildable setAttribute(String localName, String prefix, String value) throws XmlBuildableException;

	/**
	 * Agrega un atributo a la entidad. Retorna this.
	 * 
	 * @param localName
	 *            - Nombre del atributo.
	 * @param value
	 *            - Valor de atributo.
	 * @return this.
	 */
	public XmlBuildable setAttribute(String localName, String value) throws XmlBuildableException;

	/**
	 * Agrega un elemento hijo. Retorna una referencia al elemento hijo.
	 * 
	 * @param localName
	 *            - Nombre del elemento hijo nuevo.
	 * @param prefix
	 *            - Prefijo del nombre, null o vacio en caso de no corresponder.
	 * @return referencia al elemento hijo.
	 */
	public XmlBuildable addChild(String localName, String prefix) throws XmlBuildableException;

	/**
	 * Agrega un elemento hijo. Retorna una referencia al elemento hijo.
	 * 
	 * @param localName
	 *            - Nombre del elemento hijo nuevo.
	 * @return referencia al elemento hijo.
	 */
	public XmlBuildable addChild(String localName) throws XmlBuildableException;

	/**
	 * Agrega un namespace sobre el elemento actual o entidad. Retorna this.
	 * 
	 * @param prefix
	 *            - Prefijo o nombre de namespace.
	 * @param uri
	 *            - Url o Uri de namespace.
	 * @return this.
	 */
	public XmlBuildable addNamespace(String prefix, String uri) throws XmlBuildableException;

	/**
	 * Establece el namespace por defecto de un elemento
	 * 'xmlns="http://webservices.cts.ast/"', por ejemplo.
	 * 
	 * @param namespaceUri
	 *            - Uri de namespace a establecer.
	 * @return this.
	 * @throws XmlBuildableException
	 */
	public XmlBuildable setDefaultNamespace(String namespaceUri) throws XmlBuildableException;

	/**
	 * Guarda cambios sobre la entidad y retorna referencia a padre si existe.
	 * De no existir referencia a padre, retorna referencia a this.
	 * 
	 * @return entidad padre si existe o this.
	 */
	public XmlBuildable build() throws XmlBuildableException;

	/**
	 * Retorna una referencia al elemento padre, null si no existe.
	 * 
	 * @return referencia al elemento padre.
	 */
	public XmlBuildable parent();
}
