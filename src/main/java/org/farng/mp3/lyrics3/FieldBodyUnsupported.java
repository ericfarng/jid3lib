package org.farng.mp3.lyrics3;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * This is used if the field identifier is not recognized. the contents of the frame are read as a byte stream and kept
 * so they can be saved when the file is written again
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FieldBodyUnsupported extends AbstractLyrics3v2FieldBody {

    private byte[] value = null;

    /**
     * Creates a new FieldBodyUnsupported object.
     */
    public FieldBodyUnsupported() {
        super();
    }

    /**
     * Creates a new FieldBodyUnsupported object.
     */
    public FieldBodyUnsupported(final FieldBodyUnsupported copyObject) {
        super(copyObject);
        this.value = (byte[]) copyObject.value.clone();
    }

    /**
     * Creates a new FieldBodyUnsupported object.
     */
    public FieldBodyUnsupported(final byte[] value) {
        this.value = value;
    }

    /**
     * Creates a new FieldBodyUnsupported object.
     */
    public FieldBodyUnsupported(final RandomAccessFile file) throws java.io.IOException {
        this.read(file);
    }

    public String getIdentifier() {
        return "ZZZ";
    }

    public boolean isSubsetOf(final Object object) {
        if ((object instanceof FieldBodyUnsupported) == false) {
            return false;
        }
        final FieldBodyUnsupported fieldBodyUnsupported = (FieldBodyUnsupported) object;
        final String subset = new String(this.value);
        final String superset = new String(fieldBodyUnsupported.value);
        if (superset.indexOf(subset) < 0) {
            return false;
        }
        return super.isSubsetOf(object);
    }

    protected void setupObjectList() {
//        throw new UnsupportedOperationException();
    }

    public void read(final RandomAccessFile file) throws IOException {
        final int size;
        final byte[] buffer = new byte[5];

        // read the 5 character size
        file.read(buffer, 0, 5);
        size = Integer.parseInt(new String(buffer, 0, 5));
        this.value = new byte[size];

        // read the SIZE length description
        file.read(this.value);
    }

    public String toString() {
        return getIdentifier() + " : " + (new String(this.value));
    }

    public void write(final RandomAccessFile file) throws IOException {
        int offset = 0;
        final String str;
        final byte[] buffer = new byte[5];
        str = Integer.toString(this.value.length);
        for (int i = 0; i < (5 - str.length()); i++) {
            buffer[i] = (byte) '0';
        }
        offset += (5 - str.length());
        for (int i = 0; i < str.length(); i++) {
            buffer[i + offset] = (byte) str.charAt(i);
        }
        file.write(buffer);
        file.write(this.value);
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

        FieldBodyUnsupported that = (FieldBodyUnsupported) o;

        return Arrays.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(value);
        return result;
    }
}