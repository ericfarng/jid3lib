package org.farng.mp3.object;

/**
 * ID3v2 and Lyrics3v2 tags have individual fields <code>AbstractMP3Fragment</code>s Then each fragment is broken down
 * in to individual <code>AbstractMP3Object</code>s
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class ObjectStringNullTerminated extends AbstractMP3Object {

    /**
     * Creates a new ObjectStringNullTerminated object.
     */
    public ObjectStringNullTerminated(final String identifier) {
        this.identifier = identifier;
    }

    /**
     * Creates a new ObjectStringNullTerminated object.
     */
    public ObjectStringNullTerminated(final ObjectStringNullTerminated object) {
        super(object);
    }

    public int getSize() {
        int len = 0;
        if (this.value != null) {
            len = ((String) this.value).length() + 1;
        }
        return len;
    }

    public boolean equals(final Object obj) {
        if (obj instanceof ObjectStringNullTerminated == false) {
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
        final int delim = str.indexOf(0, offset);
        if (delim >= 0) {
            this.value = str.substring(offset, delim);
        } else {
            this.value = str.substring(offset);
        }
    }

    public String toString() {
        return (String) this.value;
    }

    public String writeString() {
        String string = "";
        if (this.value != null) {
            string = this.value.toString() + (char) 0;
        }
        return string;
    }
}