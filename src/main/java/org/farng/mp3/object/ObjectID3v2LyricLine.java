package org.farng.mp3.object;

/**
 * ID3v2 and Lyrics3v2 tags have individual fields <code>AbstractMP3Fragment</code>s Then each fragment is broken down
 * in to individual <code>AbstractMP3Object</code>s
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class ObjectID3v2LyricLine extends AbstractMP3Object {

    String text = "";
    long timeStamp = 0;

    /**
     * Creates a new ObjectID3v2LyricLine object.
     */
    public ObjectID3v2LyricLine(final String identifier) {
        this.identifier = identifier;
    }

    /**
     * Creates a new ObjectID3v2LyricLine object.
     */
    public ObjectID3v2LyricLine(final ObjectID3v2LyricLine copyObject) {
        super(copyObject);
        this.text = new String(copyObject.text);
        this.timeStamp = copyObject.timeStamp;
    }

    public int getSize() {
        return this.text.length() + 1 + 4;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setTimeStamp(final long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public boolean equals(final Object obj) {
        if ((obj instanceof ObjectID3v2LyricLine) == false) {
            return false;
        }
        final ObjectID3v2LyricLine objectID3v2LyricLine = (ObjectID3v2LyricLine) obj;
        if (this.text.equals(objectID3v2LyricLine.text) == false) {
            return false;
        }
        if (this.timeStamp != objectID3v2LyricLine.timeStamp) {
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
        this.text = new String(arr, offset, arr.length - offset - 4);
        this.timeStamp = 0;
        for (int i = arr.length - 4; i < arr.length; i++) {
            this.timeStamp <<= 8;
            this.timeStamp += arr[i];
        }
    }

    public String toString() {
        return this.timeStamp + " " + this.text;
    }

    public byte[] writeByteArray() {
        int i;
        final byte[] arr = new byte[getSize()];
        for (i = 0; i < this.text.length(); i++) {
            arr[i] = (byte) this.text.charAt(i);
        }
        arr[i++] = 0;
        arr[i++] = (byte) ((this.timeStamp & 0xFF000000) >> 24);
        arr[i++] = (byte) ((this.timeStamp & 0x00FF0000) >> 16);
        arr[i++] = (byte) ((this.timeStamp & 0x0000FF00) >> 8);
        arr[i] = (byte) (this.timeStamp & 0x000000FF);
        return arr;
    }
}