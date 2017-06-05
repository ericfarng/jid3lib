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
    private String description = null;
    private String filename = null;

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
        if (copyObject.time != null) this.time = new ObjectLyrics3TimeStamp(copyObject.time);
        if (copyObject.description != null) this.description = new String(copyObject.description);
        if (copyObject.filename != null) this.filename = new String(copyObject.filename);
    }

    public void setDescription(final String description) {
        if (description == null || description.trim().length() == 0) {
            this.description = null;
        } else {
            this.description = description.trim();
        }
    }

    public String getDescription() {
        return this.description;
    }

    public void setFilename(final String filename) {
        if (filename == null || filename.trim().length() == 0) {
            this.filename = null;
        } else {
            this.filename = filename.trim();
        }
    }

    public String getFilename() {
        return this.filename;
    }

    public int getSize() {
        int size = 0;
        size += this.filename == null ? 0 : this.filename.length();
        size += 2;
        size += this.description == null ? 0 : this.description.length();
        size += 2;
        size += this.time == null ? 0 : this.time.getSize();
        return size;
    }

    public void setTimeStamp(final ObjectLyrics3TimeStamp time) {
        this.time = time;
    }

    public ObjectLyrics3TimeStamp getTimeStamp() {
        return this.time;
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
        if (offset < delim) {
            this.description = imageString.substring(offset, delim);
        }
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

        ObjectLyrics3Image that = (ObjectLyrics3Image) o;

        if (time != null ? !time.equals(that.time) : that.time != null) {
            return false;
        }
        if (description != null ? !description.equals(that.description)
                                : that.description != null) {
            return false;
        }
        return filename != null ? filename.equals(that.filename) : that.filename == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        return result;
    }
}