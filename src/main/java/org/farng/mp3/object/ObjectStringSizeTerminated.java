package org.farng.mp3.object;

/**
 * ID3v2 and Lyrics3v2 tags have individual fields <code>AbstractMP3Fragment</code>s Then each fragment is broken down
 * in to individual <code>AbstractMP3Object</code>s
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class ObjectStringSizeTerminated extends AbstractMP3Object {

    /**
     * Creates a new ObjectStringSizeTerminated object.
     */
    public ObjectStringSizeTerminated(final String identifier) {
        this.identifier = identifier;
    }

    /**
     * Creates a new ObjectStringSizeTerminated object.
     */
    public ObjectStringSizeTerminated(final ObjectStringSizeTerminated object) {
        super(object);
    }

    public int getSize() {
        final String str = writeString();
        if (str != null) {
            return str.length();
        }
        return 0;
    }

    public boolean equals(final Object obj) {
        if (obj instanceof ObjectStringSizeTerminated == false) {
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
        this.value = str.substring(offset);
    }

    public String toString() {
        return writeString();
    }

    public String writeString() {
        return (String) this.value;
    }
}