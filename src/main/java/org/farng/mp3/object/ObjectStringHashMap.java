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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        ObjectStringHashMap that = (ObjectStringHashMap) o;

        if (hasEmptyValue != that.hasEmptyValue) {
            return false;
        }
        if (idToString != null ? !idToString.equals(that.idToString) : that.idToString != null) {
            return false;
        }
        return stringToId != null ? stringToId.equals(that.stringToId) : that.stringToId == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (idToString != null ? idToString.hashCode() : 0);
        result = 31 * result + (stringToId != null ? stringToId.hashCode() : 0);
        result = 31 * result + (hasEmptyValue ? 1 : 0);
        return result;
    }
}