package org.farng.mp3.object;

/**
 * ID3v2 and Lyrics3v2 tags have individual fields <code>AbstractMP3Fragment</code>s Then each fragment is broken down
 * in to individual <code>AbstractMP3Object</code>s
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class ObjectBooleanByte extends AbstractMP3Object {

    int bitPosition = -1;

    /**
     * Creates a new ObjectBooleanByte object.
     */
    public ObjectBooleanByte(final String identifier, final int bitPosition) {
        if ((bitPosition < 0) || (bitPosition > 7)) {
            throw new IndexOutOfBoundsException("Bit position needs to be from 0 - 7 : " + bitPosition);
        }
        this.bitPosition = bitPosition;
        this.identifier = identifier;
    }

    /**
     * Creates a new ObjectBooleanByte object.
     */
    public ObjectBooleanByte(final ObjectBooleanByte copyObject) {
        super(copyObject);
        this.bitPosition = copyObject.bitPosition;
    }

    public int getBitPosition() {
        return this.bitPosition;
    }

    public int getSize() {
        return 1;
    }

    public boolean equals(final Object obj) {
        if ((obj instanceof ObjectBooleanByte) == false) {
            return false;
        }
        final ObjectBooleanByte objectBooleanByte = (ObjectBooleanByte) obj;
        if (this.bitPosition != objectBooleanByte.bitPosition) {
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
        byte newValue = arr[offset];
        newValue >>= this.bitPosition;
        newValue &= 0x1;
        this.value = new Boolean(newValue == 1);
    }

    public String toString() {
        return "" + this.value;
    }

    public byte[] writeByteArray() {
        final byte[] retValue;
        retValue = new byte[1];
        if (this.value != null) {
            retValue[0] = (byte) (((Boolean) this.value).booleanValue() ? 1 : 0);
            retValue[0] <<= this.bitPosition;
        }
        return retValue;
    }
}