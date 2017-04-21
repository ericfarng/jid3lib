package org.farng.mp3.object;

import org.farng.mp3.TagUtility;

/**
 * ID3v2 and Lyrics3v2 tags have individual fields <code>AbstractMP3Fragment</code>s Then each fragment is broken down
 * in to individual <code>AbstractMP3Object</code>s
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class ObjectStringFixedLength extends AbstractMP3Object {

    int length = 0;

    /**
     * Creates a new ObjectStringFixedLength object.
     */
    public ObjectStringFixedLength(final String identifier, final int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length is less than zero: " + length);
        }
        this.identifier = identifier;
        this.length = length;
    }

    /**
     * Creates a new ObjectStringFixedLength object.
     */
    public ObjectStringFixedLength(final ObjectStringFixedLength copyObject) {
        super(copyObject);
        this.length = copyObject.length;
    }

    public void setLength(final int size) {
        this.length = size;
    }

    public int getLength() {
        return this.length;
    }

    public int getSize() {
        return this.length;
    }

    public boolean equals(final Object obj) {
        if ((obj instanceof ObjectStringFixedLength) == false) {
            return false;
        }
        final ObjectStringFixedLength objectStringFixedLength = (ObjectStringFixedLength) obj;
        if (this.length != objectStringFixedLength.length) {
            return false;
        }
        return super.equals(obj);
    }

    public void readString(final String str, final int offset) {
        if (str == null) {
            throw new NullPointerException("String is null");
        }
        if ((offset < 0) || (offset >= str.length())) {
            throw new IndexOutOfBoundsException("Offset to String is out of bounds: offset = " +
                                                offset +
                                                ", string.length()" +
                                                str.length());
        }
        this.value = str.substring(offset, this.length + offset);
    }

    public String toString() {
        return writeString();
    }

    public String writeString() {
        final String str;
        if (this.value == null) {
            str = String.valueOf(new char[this.length]);
        } else {
            final int vlength = ((String) this.value).length();
            if (vlength > this.length) {
                str = ((String) this.value).substring(0, this.length);
            } else if (vlength == this.length) {
                str = (String) this.value;
            } else {
                str = TagUtility.padString((String) this.value, this.length, ' ', false);
            }
        }
        return str;
    }
}