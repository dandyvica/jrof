package com.github.dandyvica.jrof;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.stream.Stream;

import com.github.dandyvica.jrof.Field;

/**
 * A record is a list of fields, which are not necessarly contiguous.
 */
public class Record extends com.github.dandyvica.jrof.Atom implements Iterable<Field> {

    // list of fields in the record
    private ArrayList<Field> fieldList;

    // keep track of fields having the same name
    private Map<String, ArrayList<Field>> mapList;

    /**
     * Constructor from an XML fragment
     * <pre>
     * {@code
     * <record name="REC" description="Record header" alias="RECT">
     * }
     * </pre>
     * For determining the length and the position of the field within a record, on can specify either:
     * start and length
     * or start and end XML attributes.
     * @param elem  the XML <record> tag with all its attributes
     */
    public Record(Element elem) {
        // create object: name & description must exist
        super(
            elem.getAttribute("name"), 
            elem.getAttribute("description"), 
            elem.getAttribute("alias"), 
            elem.getAttribute("id"),
            null
        );

        // initiliaze list & map
        fieldList = new ArrayList<Field>();
        mapList = new HashMap<String, ArrayList<Field>>();
    }

    /**
     * Copy constructor: creates a new object based on the object passed.
     * 
     * @param other the object to clone
     */ 

    public Record(Record other) {
        super(other.name, other.description, other.alias, other.id, other.type);

        // initiliaze list & map
        fieldList = new ArrayList<Field>();
        mapList = new HashMap<String, ArrayList<Field>>();

        // just add copied fields
        for (Field f: other) {
            this.add(new Field(f));
        }
    }

    /**
     * Tests whether the record contains a field matching the name parameter.
     * @param fieldName the field name to be searched for
     */
    public boolean containsField(String fieldName) {
        return mapList.containsKey(fieldName);
    }

    /**
     * @return the number of fields in the record
     */
    public int size() {
        return fieldList.size();
    }

    /**
     * Returns the field object located at index
     * @param index index of the element to return
     * @throws IndexOutOfBoundsException - if the index is out of range (index < 0 || index >= size())
     */
    public Field get(int index) throws IndexOutOfBoundsException {
        return fieldList.get(index);
    }

    /**
     * @param fieldName the field name to retrieve
     * @return an ArrayList of fields matching the field name
     */
    public ArrayList<Field> get(String fieldName) {
        return mapList.get(fieldName);
    }

    /**
     * Adds the field object at the end of the record.
     * @param field field object to add to the record
     */    
    public void add(Field field) {
        // add element to the list
        fieldList.add(field);

        // need to initialize the hashmap for the corresponding name if not yet init
        if (!mapList.containsKey(field.getName())) {
            mapList.put(field.getName(), new ArrayList<Field>());
        } 

        // add element to the hash depending on its name
        mapList.get(field.getName()).add(field);
    }

    /**
     * Allows to iterate through records, yielding fields.
     * @return an iterator on fields
     */      
    public Iterator<Field> iterator() {
        return fieldList.iterator();
    }

    /**
     * Returns the record length which is the highest position in the record
     * @return the record length
     */      
    public int length() {
        // safeguard
        if (fieldList.size() == 0) 
            return 0;
        return fieldList.get(fieldList.size() - 1).getEnd();
    }

    /**
     * Sets the value of all fields composing the record
     */    
    public void setValue(String text) {
        // set value for each individual field
        for (Field f: fieldList) {
            f.setValue(text);
        }
    }

    /**
     * Add the stream collection
     */    
    public Stream<Field> stream() {
        return fieldList.stream();
    }
}
   