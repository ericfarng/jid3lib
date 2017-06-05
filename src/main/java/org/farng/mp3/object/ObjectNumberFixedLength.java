package org.farng.mp3.object;

import org.farng.mp3.TagUtility;

/**
 * ID3v2 and Lyrics3v2 tags have individual fields <code>AbstractMP3Fragment</code>s Then each fragment is broken down
 * in to individual <code>AbstractMP3Object</code>s
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class ObjectNumberFixedLength extends AbstractMP3Object {

    int length = 0;

    /**
     * Creates a new ObjectNumberFixedLength object.
     */
    public ObjectNumberFixedLength(final String identifier, final int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Length is less than zero: " + this.length);
        }
        this.length = size;
        this.identifier = identifier;
    }

    /**
     * Creates a new ObjectNumberFixedLength object.
     */
    public ObjectNumberFixedLength(final ObjectNumberFixedLength copyObject) {
        super(copyObject);
        this.length = copyObject.length;
    }

    public int getLength() {
        return this.length;
    }

    public void setSize(final int length) {
        if (length > 0) {
            this.length = length;
        }
    }

    public int getSize() {
        return this.length;
    }

    public void readByteArray(final byte[] arr, final int offset) {
        if (arr == null) {
            throw new NullPointerException("Byte array is null");
        }
        if ((offset < 0) || (offset >= arr.length)) {
            throw new IndexOutOfBoundsException("Offset to byte array is out of bounds: offset = " +
                                                offset +
                                                ", array.length = " +
                                                arr
                                                        .length);
        }
        long lvalue = 0;
        for (int i = offset; i < (offset + this.length); i++) {
            lvalue <<= 8;
            lvalue += arr[i];
        }
        this.value = new Long(lvalue);
    }

    public void readString(final String str, final int offset) {
        if (str == null) {
            throw new NullPointerException("Number string is null");
        }
        if ((offset < 0) || (offset >= str.length())) {
            throw new IndexOutOfBoundsException("Offset to number string is out of bounds: offset = " +
                                                offset +
                                                ", string.length()" +
                                                str.length());
        }
        this.value = Long.getLong(str.substring(offset));
    }

    public String toString() {
        if (this.value == null) {
            return "";
        }
        return this.value.toString();
    }

    public byte[] writeByteArray() {
        final byte[] arr;
        arr = new byte[this.length];
        if (this.value != null) {
            long temp = TagUtility.getWholeNumber(this.value);
            for (int i = this.length - 1; i >= 0; i--) {
                arr[i] = (byte) (temp & 0xFF);
                temp >>= 8;
            }
        }
        return arr;
    }

    public String writeString() {
        if (this.value == null) {
            return String.valueOf(new char[this.length]);
        }
        return this.value.toString();
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

        ObjectNumberFixedLength that = (ObjectNumberFixedLength) o;

        return length == that.length;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + length;
        return result;
    }
}