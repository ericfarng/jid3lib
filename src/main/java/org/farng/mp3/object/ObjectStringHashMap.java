package org.farng.mp3.object;

import org.farng.mp3.TagConstant;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * ID3v2 and Lyrics3v2 tags have individual fields <code>AbstractMP3Fragment</code>s Then each fragment is broken down
 * in to individual <code>AbstractMP3Object</code>s
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class ObjectStringHashMap extends ObjectStringFixedLength implements ObjectHashMapInterface {

    public static final String LANGUAGE = "Language";
    HashMap idToString = null;
    HashMap stringToId = null;
    boolean hasEmptyValue = false;

    /**
     * Creates a new ObjectStringHashMap object.
     */
    public ObjectStringHashMap(final String identifier, final int size) {
        super(identifier, size);
        if (identifier.equals(ObjectStringHashMap.LANGUAGE)) {
            this.stringToId = TagConstant.languageStringToId;
            this.idToString = TagConstant.languageIdToString;
        } else {
            throw new IllegalArgumentException("Hashmap identifier not defined in this class: " + identifier);
        }
    }

    /**
     * Creates a new ObjectStringHashMap object.
     */
    public ObjectStringHashMap(final ObjectStringHashMap copyObject) {
        super(copyObject);
        this.hasEmptyValue = copyObject.hasEmptyValue;
        this.idToString = copyObject.idToString;
        this.stringToId = copyObject.stringToId;
    }

    public HashMap getIdToString() {
        return this.idToString;
    }

    public HashMap getStringToId() {
        return this.stringToId;
    }

    public void setValue(final Object value) {
        if (value instanceof String) {
            this.value = ((String) value).toLowerCase();
        } else {
            this.value = value;
        }
    }

    public boolean equals(final Object obj) {
        if ((obj instanceof ObjectStringHashMap) == false) {
            return false;
        }
        final ObjectStringHashMap objectStringHashMap = (ObjectStringHashMap) obj;
        if (this.hasEmptyValue != objectStringHashMap.hasEmptyValue) {
            return false;
        }
        if (this.idToString == null) {
            if (objectStringHashMap.idToString != null) {
                return false;
            }
        } else {
            if (this.idToString.equals(objectStringHashMap.idToString) == false) {
                return false;
            }
        }
        if (this.idToString == null) {
            if (objectStringHashMap.idToString != null) {
                return false;
            }
        } else {
            if (this.stringToId.equals(objectStringHashMap.stringToId) == false) {
                return false;
            }
        }
        return super.equals(obj);
    }

    public Iterator iterator() {
        if (this.idToString == null) {
            return null;
        }

        // put them in a treeset first to sort them
        final TreeSet treeSet = new TreeSet(this.idToString.values());
        if (this.hasEmptyValue) {
            treeSet.add("");
        }
        return treeSet.iterator();
    }

    public String toString() {
        if (this.value == null) {
            return "";
        } else if (this.idToString.get(this.value) == null) {
            return "";
        } else {
            return this.idToString.get(this.value).toString();
        }
    }
}