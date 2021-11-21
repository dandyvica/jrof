package com.github.dandyvica.jrof;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.github.dandyvica.jrof.FieldType;
import com.github.dandyvica.jrof.Field;
import com.github.dandyvica.jrof.Record;

/**
 * A layout is the description of the structure of a record-oriented file. It describes such a file by its records and fields.
 */
public class Layout {
    private String version;
    private int recLength;
    private String description;

    private HashMap<String, Record> recordMap;
    private HashMap<String, FieldType> fieldTypeMap;

    public Layout(String xmlFile)
            throws NumberFormatException, ParserConfigurationException, SAXException, IOException {
        // Instantiate the Factory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // parse XML file
        DocumentBuilder db = factory.newDocumentBuilder();
        Document document = db.parse(new File(xmlFile));

        // init map
        recordMap = new HashMap<String, Record>();
        fieldTypeMap = new HashMap<String, FieldType>();

        // Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

        // Here comes the root node
        Element root = document.getDocumentElement();

        // get children under the topmost tag
        var nodes = root.getChildNodes();

        // lopp through nodes
        for (int i = 0; i < nodes.getLength(); i++) {
            // get an individual node
            var node = nodes.item(i);

            // text nodes are meaningless
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                // create a new element
                Element elem = (Element) node;

                // now create appropriate objects
                switch (elem.getNodeName()) {
                // ex: <meta version="April 2016" description="ATPCO ISR file format"
                // reclength="400"/>
                case "meta":
                    version = elem.getAttribute("version");
                    description = elem.getAttribute("description");
                    recLength = Integer.parseInt(elem.getAttribute("reclength"));
                    break;

                // ex: <fieldtype name="N" type="decimal" pattern="^[\d\.]+$" format="%0*.*g"/>
                case "fieldtype":
                    var fieldType = new FieldType(elem);
                    fieldTypeMap.put(fieldType.getName(), fieldType);
                    break;

                // ex: <record name="IS" alias="HEADER" description="ISR header">
                case "record":
                    // create record
                    var record = new Record(elem);
                    recordMap.put(record.getName(), record);

                    // and now add all fields
                    var fields = elem.getElementsByTagName("field");
                    for (int j = 0; j < fields.getLength(); j++) {
                        var fieldNode = fields.item(j);

                        if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {
                            // create field object from element
                            // ex: <field name="APID" description="Application ID" length="15" type="A"/>
                            Element f = (Element) fieldNode;
                            var field = new Field(f);

                            // assign field type object
                            field.setFieldType(fieldTypeMap.get(field.getType()));

                            // finally, add it to the record
                            record.add(field);
                        }
                    }
                    break;

                default:
                    break;

                }
            }

        }
    }

    /**
     * Returns the version of the layout file, corresponding to the <strong>version</strong> attribute of the <strong>layout</strong>
     * in the layout XML file.
     * 
     * @return the version of the layout file
     */
    public String getVersion() {
        return version;
    }

    /**
     * Returns the description of the layout file, corresponding to the <strong>description</strong> attribute of the <strong>layout</strong>
     * in the layout XML file.
     * 
     * @return the description of the layout file
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the record length of the layout file, corresponding to the <strong>reclength</strong> attribute of the <strong>layout</strong>
     * in the layout XML file.
     * 
     * @return the record length of the records described in the layout file
     */
    public int getRecLength() {
        return recLength;
    }

    /**
     * Returns the number of records in the layout file.
     * 
     * @return the number of records in the layout file
     */
    public int size() {
        return recordMap.size();
    }

    /**
     * Returns true if the layout contains a record matching the record name.
     * 
     * @param recordName        the record name to look for
     * 
     * @return true or false
     */    
    public boolean containsRecord(String recordName) {
        return recordMap.containsKey(recordName);
    }

    /**
     * Returns true if the layout contains a field matching the field name, for any record in the layout.
     * 
     * @param fieldName        the field name to look for
     * 
     * @return true or false
     */    
    public boolean containsField(String fieldName) {
        return recordMap.values().stream().anyMatch(r -> r.containsField(fieldName));
    }

    /**
     * Returns a record object, matching the record name.
     * 
     * @param recordName        the record name to retrieve
     * 
     * @return a record object, matching the record name.
     */    
    public Record get(String recordName) {
        return recordMap.get(recordName);
    }

    public Set<Entry<String, Record>> entrySet() {
        return recordMap.entrySet();
    }
}