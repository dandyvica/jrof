package com.github.dandyvica.jrof;

import org.w3c.dom.Element;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.lang.IllegalArgumentException;

import com.github.dandyvica.jrof.FieldClass;

/**
 * Each field within a record-oriented file has a type, which is defined in the
 * XML layout file and associated with each field.
 */
public class FieldType extends com.github.dandyvica.jrof.Atom {
    /**
     * Base class (which is only limited to a set a values)
     */
    private FieldClass fieldClass;

    /**
     * Optional pattern which describes field format
     */
    private Pattern pattern;

    /**
     * Additional formatting option
     */
    private String format;

    /**
     * Constructor from an XML fragment.
     * 
     * <pre>
     * {@code
     * <fieldtype type="AN" class="string" pattern=".*" format="%-*.*s"/>
     * }
     * </pre>
     * 
     * @param elem the XML <field> dom element with all its attributes
     * @throws PatternSyntaxException if the pattern is not a well-formed regex.
     */
    public FieldType(Element elem) throws IllegalArgumentException, PatternSyntaxException {
        // create object
        super(elem.getAttribute("name"), elem.getAttribute("description"), elem.getAttribute("alias"),
                elem.getAttribute("id"), elem.getAttribute("type"));

        // extract attributes
        fieldClass = FieldClass.valueOf(elem.getAttribute("class"));
        format = elem.getAttribute("format");

        // compile regex
        if (elem.hasAttribute("pattern")) {
            String pattern = elem.getAttribute("pattern");
            this.pattern = Pattern.compile(pattern);
        }

    }

    /**
     * Copy constructor: creates a new object based on the object passed.
     * 
     * @param other the object to clone
     */
    public FieldType(FieldType other) {
        super(other.name, other.description, other.alias, other.id, other.type);

        fieldClass = other.fieldClass;
        format = new String(other.format);

    }

    /**
     * @return the fieldtype class.
     */
    public FieldClass getFieldClass() {
        return fieldClass;
    }

    /**
     * @return the fieldtype format.
     */
    public String getFormat() {
        return format;
    }

    /**
     * @return the fieldtype pattern.
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * @param text string to check for matching the regex
     * @return <tt>true</tt> if the field pattern matches the string
     */
    public boolean isMatch(String text) {
        return pattern.matcher(text).matches();
    }    
}
