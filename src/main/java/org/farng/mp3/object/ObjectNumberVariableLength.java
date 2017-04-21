package org.farng.mp3.object;

import org.farng.mp3.TagUtility;

/**
 * ID3v2 and Lyrics3v2 tags have individual fields <code>AbstractMP3Fragment</code>s Then each fragment is broken down
 * in to individual <code>AbstractMP3Object</code>s
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class ObjectNumberVariableLength extends AbstractMP3Object {

    int minLength = 1;

    /**
     * Creates a new ObjectNumberVariableLength object.
     */
    public ObjectNumberVariableLength(final String identifier, final int minimumSize) {
        this.identifier = identifier;
        if (minimumSize > 0) {
            this.minLength = minimumSize;
        }
    }

    /**
     * Creates a new ObjectNumberVariableLength object.
     */
    public ObjectNumberVariableLength(final ObjectNumberVariableLength copyObject) {
        super(copyObject);
        this.minLength = copyObject.minLength;
    }

    public int getMaximumLenth() {
        return 8;
    }

    public int getMinimumLength() {
        return this.minLength;
    }

    public void setMinimumSize(final int minimumSize) {
        if (minimumSize > 0) {
            this.minLength = minimumSize;
        }
    }

    public int getSize() {
        if (this.value == null) {
            return 0;
        }
        int current;
        long temp = TagUtility.getWholeNumber(this.value);
        int size = 0;
        for (int i = 1; i <= 8; i++) {
            current = (byte) temp & 0xFF;
            if (current != 0) {
                size = i;
            }
            temp >>= 8;
        }
        return (this.minLength > size) ? this.minLength : size;
    }

    public boolean equals(final Object obj) {
        if ((obj instanceof ObjectNumberVariableLength) == false) {
            return false;
        }
        final ObjectNumberVariableLength objectNumberVariableLength = (ObjectNumberVariableLength) obj;
        if (this.minLength != objectNumberVariableLength.minLength) {
            return false;
        }
        return super.equals(obj);
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
        for (int i = offset; i < arr.length; i++) {
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
        final int size = getSize();
        final byte[] arr;
        if (size == 0) {
            arr = new byte[0];
        } else {
            long temp = TagUtility.getWholeNumber(this.value);
            arr = new byte[size];
            for (int i = size - 1; i >= 0; i--) {
                arr[i] = (byte) (temp & 0xFF);
                temp >>= 8;
            }
        }
        return arr;
    }

    public String writeString() {
        if (this.value == null) {
            return "";
        }
        return this.value.toString();
    }
}