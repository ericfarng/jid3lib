package org.farng.mp3.object;

/**
 * ID3v2 and Lyrics3v2 tags have individual fields <code>AbstractMP3Fragment</code>s Then each fragment is broken down
 * in to individual <code>AbstractMP3Object</code>s
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class ObjectLyrics3Image extends AbstractMP3Object {

    private ObjectLyrics3TimeStamp time = null;
    private String description = "";
    private String filename = "";

    /**
     * Creates a new ObjectLyrics3Image object.
     */
    public ObjectLyrics3Image(final String identifier) {
        this.identifier = identifier;
    }

    /**
     * Creates a new ObjectLyrics3Image object.
     */
    public ObjectLyrics3Image(final ObjectLyrics3Image copyObject) {
        super(copyObject);
        this.time = new ObjectLyrics3TimeStamp(copyObject.time);
        this.description = new String(copyObject.description);
        this.filename = new String(copyObject.filename);
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setFilename(final String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return this.filename;
    }

    public int getSize() {
        int size;
        size = this.filename.length() + 2 + this.description.length() + 2;
        if (this.time != null) {
            size += this.time.getSize();
        }
        return size;
    }

    public void setTimeStamp(final ObjectLyrics3TimeStamp time) {
        this.time = time;
    }

    public ObjectLyrics3TimeStamp getTimeStamp() {
        return this.time;
    }

    public boolean equals(final Object obj) {
        if ((obj instanceof ObjectLyrics3Image) == false) {
            return false;
        }
        final ObjectLyrics3Image objectLyrics3Image = (ObjectLyrics3Image) obj;
        if (this.description.equals(objectLyrics3Image.description) == false) {
            return false;
        }
        if (this.filename.equals(objectLyrics3Image.filename) == false) {
            return false;
        }
        if (this.time == null) {
            if (objectLyrics3Image.time != null) {
                return false;
            }
        } else {
            if (this.time.equals(objectLyrics3Image.time) == false) {
                return false;
            }
        }
        return super.equals(obj);
    }

    public void readString(final String imageString, int offset) {
        if (imageString == null) {
            throw new NullPointerException("Image string is null");
        }
        if ((offset < 0) || (offset >= imageString.length())) {
            throw new IndexOutOfBoundsException("Offset to image string is out of bounds: offset = " +
                                                offset +
                                                ", string.length()" +
                                                imageString.length());
        }
        final String timestamp;
        int delim;
        delim = imageString.indexOf("||", offset);
        this.filename = imageString.substring(offset, delim);
        offset = delim + 2;
        delim = imageString.indexOf("||", offset);
        this.description = imageString.substring(offset, delim);
        offset = delim + 2;
        timestamp = imageString.substring(offset);
        if (timestamp.length() == 7) {
            this.time = new ObjectLyrics3TimeStamp("Time Stamp");
            this.time.readString(timestamp);
        }
    }

    public String toString() {
        String str;
        str = "filename = " + this.filename + ", description = " + this.description;
        if (this.time != null) {
            str += (", timestamp = " + this.time.toString());
        }
        return str + "\n";
    }

    public String writeString() {
        String str;
        if (this.filename == null) {
            str = "||";
        } else {
            str = this.filename + "||";
        }
        if (this.description == null) {
            str += "||";
        } else {
            str += (this.description + "||");
        }
        if (this.time != null) {
            str += this.time.writeString();
        }
        return str;
    }
}