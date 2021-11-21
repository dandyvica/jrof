package com.github.dandyvica.jrof;

import org.w3c.dom.Element;

import java.io.StringReader;
import java.io.ByteArrayInputStream;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.github.dandyvica.jrof.FieldType;

/**
 * A field is defined within a record by its starting position and its length
 * or by its starting and ending positions. The first position in the record
 * is starting at 1.
 * A field is identified by:
 * <ul>
 * <li>an id: whatever is meaningful to refer to it
 * <li>a name
 * <li>a description
 * <li>a starting position
 * <li>an ending position
 * <li>a length
 * <li>a field type
 * <li>a regex pattern the field value must match
 * <ul>
 */
public class Field extends com.github.dandyvica.jrof.Atom {

    // field length in chars
    private int length;

    // useful to display
    private int displayLength;

    // start and end index within a record
    private int start;
    private int end;

    // a useful ID could be provided in the <field> definition (e.g.: a counter for
    // the field)
    private String id;

    // a regex pattern the field could follow (optional)
    private Pattern pattern;

    // the field type
    private FieldType fieldType;

    /**
     * The value of the field when assigned
     */    
    private String value;

    /**
     * Constructor from an XML fragment.
     * <pre>
     * {@code
     * <field id="1" name="FILL" description="Filler" start="10" length="3" type="AN"/>
     * }
     * </pre>
     * For
     * determining the length and the position of the field within a record, on can
     * specify either: <strong>start</strong> and <strong>length</strong> or <strong>start</strong> and <strong>end</strong> XML attributes.
     * 
     * @param elem the XML <field> dom element with all its attributes
     * @throws NumberFormatException if start, end and length attributes are not integers.
     */
    public Field(Element elem) throws NumberFormatException {
        // create object: name & description must exist
        super(
            elem.getAttribute("name"), 
            elem.getAttribute("description"), 
            elem.getAttribute("alias"), 
            elem.getAttribute("id"),
            elem.getAttribute("type")
        );

        // mandatory attributes
        start = Integer.parseInt(elem.getAttribute("start"));

        // length calculation
        if (elem.hasAttribute("end")) {
            end = Integer.parseInt(elem.getAttribute("end"));
            length = end - start + 1;
        } else {
            length = Integer.parseInt(elem.getAttribute("length"));
            end = length + start - 1;
        }

        // optional pattern
        if (elem.hasAttribute("pattern")) {
            String pattern = elem.getAttribute("pattern");
            this.pattern = Pattern.compile(pattern);
        }

        // calculate display length
        displayLength = Integer.max(length, name.length());
    }

    /**
     * Copy constructor: creates a new object based on the object passed.
     * 
     * @param other the object to clone
     */ 
    public Field(Field other) {
        super(other.name, other.description, other.alias, other.id, other.type);

        // copy other attributes
        start = other.start;
        end = other.end;
        length = other.length;

        // verify pattern is actually created
        if (other.pattern != null) {
            pattern = Pattern.compile(other.pattern.pattern());
        }
    }

    /**
     * Saves the substring corresponding to the start and end position in the string text.
     * 
     * @param text the string
     * @throws IndexOutOfBoundsException if the length of the string is lower than the field length
     */ 
    public void setValue(String text) throws IndexOutOfBoundsException {
        value = text.substring(start - 1, end);
    }

    /**
     * Checks whether the string <tt>text</tt> matches the regex <tt>pattern</tt>.
     * 
     * @param text string to check for matching the regex
     * @return <tt>true</tt> if the field pattern matches the string
     */
    public boolean isMatch(String text) {
        return pattern.matcher(text).matches();
    }

    /**
     * @return the field length.
     */ 
    public int getLength() {
        return length;
    }

    /**
     * @return the field start position.
     */ 
    public int getStart() {
        return start;
    }

    /**
     * @return the field end position.
     */
    public int getEnd() {
        return end;
    }

    /**
     * @return the field type as a string.
     */
    public FieldType getFieldType() {
        return fieldType;
    }

    /**
     * @return the field value.
     */
    public String getValue() {
        return value;
    }

    /**
     * @return the displayLength value.
     */
    public int getDisplayLength() {
        return displayLength;
    }

    // setters
    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }
}
