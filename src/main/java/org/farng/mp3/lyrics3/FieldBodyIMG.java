package org.farng.mp3.lyrics3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.TagConstant;
import org.farng.mp3.TagOptionSingleton;
import org.farng.mp3.object.ObjectLyrics3Image;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Link to an image files (BMP or JPG format). Image lines include filename, description and timestamp separated by
 * delimiter - two ASCII chars 124 ("||"). Description and timestamp are optional, but if timestamp is used, and there
 * is no description, two delimiters ("||||") should be used between the filename and the timestamp. Multiple images are
 * allowed by using a [CR][LF] delimiter between each image line. No [CR][LF] is needed after the last image line.
 * Number of images is not limited (except by the field size).<BR><B>Filename</B> can be in one of these formats: <UL>
 * <LI>Filename only - when the image is located in the same path as the MP3 file (preferred, since if you move the mp3
 * file this will still be correct) <LI>Relative Path + Filename - when the image is located in a subdirectory below the
 * MP3 file (i.e. images\cover.jpg) <LI>Full path + Filename - when the image is located in a totally different path or
 * drive. This will not work if the image is moved or drive letters has changed, and so should be avoided if possible
 * (i.e. c:\images\artist.jpg)</LI></UL><B>Description</B> can be up to 250 chars long.<BR><B>Timestamp</B> must be
 * formatted like the lyrics timestamp which is "[mm:ss]". If an image has a timestamp, then the visible image will
 * automatically switch to that image on the timestamp play time, just the same as the selected lyrics line is switched
 * based on timestamps.
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FieldBodyIMG extends AbstractLyrics3v2FieldBody {

    private ArrayList images = new ArrayList();

    /**
     * Creates a new FieldBodyIMG object.
     */
    public FieldBodyIMG() {
        super();
    }

    /**
     * Creates a new FieldBodyIMG object.
     */
    public FieldBodyIMG(final FieldBodyIMG copyObject) {
        super(copyObject);
        ObjectLyrics3Image oldObject;
        for (int i = 0; i < copyObject.images.size(); i++) {
            oldObject = (ObjectLyrics3Image) copyObject.images.get(i);
            this.images.add(new ObjectLyrics3Image(oldObject));
        }
    }

    /**
     * Creates a new FieldBodyIMG object.
     */
    public FieldBodyIMG(final String imageString) {
        readString(imageString);
    }

    /**
     * Creates a new FieldBodyIMG object.
     */
    public FieldBodyIMG(final ObjectLyrics3Image image) {
        this.images.add(image);
    }

    /**
     * Creates a new FieldBodyIMG object.
     */
    public FieldBodyIMG(final RandomAccessFile file) throws InvalidTagException, java.io.IOException {
        this.read(file);
    }

    public String getIdentifier() {
        return "IMG";
    }

    public int getSize() {
        int size = 0;
        ObjectLyrics3Image image;
        for (int i = 0; i < this.images.size(); i++) {
            image = (ObjectLyrics3Image) this.images.get(i);
            size += (image.getSize() + 2); // add CRLF pair
        }
        return size - 2; // cut off trailing crlf pair
    }

    public boolean isSubsetOf(final Object object) {
        if ((object instanceof FieldBodyIMG) == false) {
            return false;
        }
        final ArrayList superset = ((FieldBodyIMG) object).images;
        for (int i = 0; i < this.images.size(); i++) {
            if (superset.contains(this.images.get(i)) == false) {
                return false;
            }
        }
        return super.isSubsetOf(object);
    }

    public void setValue(final String value) {
        readString(value);
    }

    public String getValue() {
        return writeString();
    }

    public void addImage(final ObjectLyrics3Image image) {
        this.images.add(image);
    }

    public boolean equals(final Object obj) {
        if ((obj instanceof FieldBodyIMG) == false) {
            return false;
        }
        final FieldBodyIMG fieldBodyIMG = (FieldBodyIMG) obj;
        if (this.images.equals(fieldBodyIMG.images) == false) {
            return false;
        }
        return super.equals(obj);
    }

    public Iterator iterator() {
        return this.images.iterator();
    }

    protected void setupObjectList() {
//        throw new UnsupportedOperationException();
    }

    public void read(final RandomAccessFile file) throws InvalidTagException, java.io.IOException {
        final String imageString;
        byte[] buffer = new byte[5];

        // read the 5 character size
        file.read(buffer, 0, 5);
        final int size = Integer.parseInt(new String(buffer, 0, 5));
        if ((size == 0) && (TagOptionSingleton.getInstance().isLyrics3KeepEmptyFieldIfRead() == false)) {
            throw new InvalidTagException("Lyircs3v2 Field has size of zero.");
        }
        buffer = new byte[size];

        // read the SIZE length description
        file.read(buffer);
        imageString = new String(buffer);
        readString(imageString);
    }

    public String toString() {
        String str = getIdentifier() + " : ";
        for (int i = 0; i < this.images.size(); i++) {
            str += (this.images.get(i).toString() + " ; ");
        }
        return str;
    }

    public void write(final RandomAccessFile file) throws java.io.IOException {
        final int size;
        int offset = 0;
        byte[] buffer = new byte[5];
        String str;
        size = getSize();
        str = Integer.toString(size);
        for (int i = 0; i < (5 - str.length()); i++) {
            buffer[i] = (byte) '0';
        }
        offset += (5 - str.length());
        for (int i = 0; i < str.length(); i++) {
            buffer[i + offset] = (byte) str.charAt(i);
        }
        offset += str.length();
        file.write(buffer, 0, 5);
        if (size > 0) {
            str = writeString();
            buffer = new byte[str.length()];
            for (int i = 0; i < str.length(); i++) {
                buffer[i] = (byte) str.charAt(i);
            }
            file.write(buffer);
        }
    }

    /**
     * @param imageString
     */
    private void readString(final String imageString) {
        // now read each picture and put in the vector;
        ObjectLyrics3Image image;
        String token;
        int offset = 0;
        int delim = imageString.indexOf(TagConstant.SEPERATOR_LINE);
        this.images = new ArrayList();
        while (delim >= 0) {
            token = imageString.substring(offset, delim);
            image = new ObjectLyrics3Image("Image");
            image.setFilename(token);
            this.images.add(image);
            offset = delim + TagConstant.SEPERATOR_LINE.length();
            delim = imageString.indexOf(TagConstant.SEPERATOR_LINE, offset);
        }
        if (offset < imageString.length()) {
            token = imageString.substring(offset);
            image = new ObjectLyrics3Image("Image");
            image.setFilename(token);
            this.images.add(image);
        }
    }

    private String writeString() {
        String str = "";
        ObjectLyrics3Image image;
        for (int i = 0; i < this.images.size(); i++) {
            image = (ObjectLyrics3Image) this.images.get(i);
            str += (image.writeString() + TagConstant.SEPERATOR_LINE);
        }
        if (str.length() > 2) {
            return str.substring(0, str.length() - 2);
        }
        return str;
    }
}