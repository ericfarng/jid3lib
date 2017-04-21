package org.farng.mp3.lyrics3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectStringSizeTerminated;

import java.io.RandomAccessFile;

/**
 * Extended Album name. The extended Album, Artist and Track are an extension to the fields in the ID3v1 tag - which are
 * limited to 30 chars. If these extended fields exist, make sure their first 30 chars are exactly the same as the ones
 * in the ID3v1 tag. If they are the same, display the extended field. If not, display the one from the ID tag. These
 * 'mismatched' extended fields, should be removed when saving the lyrics tag. When saving the extended fields, make
 * sure to copy the first 30 chars of each field to the ID3 tag matching fields. It is recommended NOT to save extended
 * fields at all, if they are not larger then 30 chars.
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FieldBodyEAL extends AbstractLyrics3v2FieldBody {

    /**
     * Creates a new FieldBodyEAL object.
     */
    public FieldBodyEAL() {
        super();
    }

    /**
     * Creates a new FieldBodyEAL object.
     */
    public FieldBodyEAL(final FieldBodyEAL body) {
        super(body);
    }

    /**
     * Creates a new FieldBodyEAL object.
     */
    public FieldBodyEAL(final String album) {
        setObject("Album", album);
    }

    /**
     * Creates a new FieldBodyEAL object.
     */
    public FieldBodyEAL(final RandomAccessFile file) throws InvalidTagException, java.io.IOException {
        this.read(file);
    }

    public void setAlbum(final String album) {
        setObject("Album", album);
    }

    public String getAlbum() {
        return (String) getObject("Album");
    }

    public String getIdentifier() {
        return "EAL";
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectStringSizeTerminated("Album"));
    }
}