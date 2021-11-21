package com.github.dandyvica.jrof;

/**
 * A basic structure only defined by its name, its description, its id, its type
 * and by an optional alias which can be used for a synonym of name.
 */
public class Atom {
    /**
     * The name the atom should be referred to (e.g.: <tt>"Hydrogen"</tt>)
     */
    protected String name;

    /**
     * The atom's description (e.g.:
     * <tt>"Hydrogen is the chemical element with the symbol H and atomic number 1"</tt>)
     */
    protected String description;

    /**
     * The atom's alternative name (e.g.: <tt>"protium"</tt>)
     */
    protected String alias;

    /**
     * The atom's ID (e.g.: <tt>"H"</tt>)
     */
    protected String id;

    /**
     * The atom's type or category (e.g.: <tt>"gas"</tt>)
     */
    protected String type;

    /**
     * Basic constructor.
     */
    public Atom(String name, String description, String alias, String id, String type) {
        this.name = name;
        this.description = description;
        this.alias = alias;
        this.id = id;
        this.type = type;
    }

    /**
     * Copy constructor: creates a new object based on the object passed.
     * 
     * @param other the object to clone
     */
    public Atom(Atom other) {
        name = other.name;
        description = other.description;
        alias = other.alias;
        id = other.id;
        type = other.type;
    }

    /**
     * @return the atom's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the atom's alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @return the atom's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the atom's id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the atom's type
     */
    public String getType() {
        return type;
    }
}