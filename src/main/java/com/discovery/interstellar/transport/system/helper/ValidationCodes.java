package com.discovery.interstellar.transport.system.helper;

/**
 * The Enum ValidationCodes.
 */
public enum ValidationCodes {
    
    /** The route exists. */
    ROUTE_EXISTS(1, "ROUTE EXISTS"),
    
    /** The route to self. */
    ROUTE_TO_SELF(2, "ROUTE TO SELF"),
    
    /** The traffic exists. */
    TRAFFIC_EXISTS(3, "TRAFFIC EXISTS"),
    
    /** The traffic to self. */
    TRAFFIC_TO_SELF(4, "TRAFFIC TO SELF");

    /** The id. */
    //Enum variables
    final int id;
    
    /** The label. */
    final String label;

    /**
     * Creates a new instance of ValidationCodes.
     *
     * @param id the id
     * @param label the label
     */
    ValidationCodes(final int id, final String label) {
        this.id = id;
        this.label = label;
    }

    /**
     * From string.
     *
     * @param str the str
     * @return the validation codes
     */
    public static ValidationCodes fromString(final String str) {
        for (ValidationCodes e : ValidationCodes.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return label;
    }
}

