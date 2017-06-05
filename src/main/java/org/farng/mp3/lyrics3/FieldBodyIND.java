package org.farng.mp3.lyrics3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectBooleanString;

import java.io.RandomAccessFile;

/**
 * Indications field. This is always two characters big in v2.00, but might be bigger in a future standard. The first
 * byte indicates wether or not a lyrics field is present. "1" for present and "0" for otherwise. The second character
 * indicates if there is a timestamp in the lyrics. Again "1" for yes and "0" for no.
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FieldBodyIND extends AbstractLyrics3v2FieldBody {

    /**
     * Creates a new FieldBodyIND object.
     */
    public FieldBodyIND() {
        super();
    }

    /**
     * Creates a new FieldBodyIND object.
     */
    public FieldBodyIND(final FieldBodyIND body) {
        super(body);
    }

    /**
     * Creates a new FieldBodyIND object.
     */
    public FieldBodyIND(final boolean lyricsPresent, final boolean timeStampPresent) {
        setObject("Lyrics Present", new Boolean(lyricsPresent));
        setObject("Timestamp Present", new Boolean(timeStampPresent));
    }

    /**
     * Creates a new FieldBodyIND object.
     */
    public FieldBodyIND(final RandomAccessFile file) throws InvalidTagException, java.io.IOException {
        this.read(file);
    }

    public void setLyricsPresent(final boolean lyricsPresent) {
        setObject("Lyrics Present", lyricsPresent);
    }
    public boolean getLyricsPresent() {
        return (Boolean) getObject("Lyrics Present");
    }

    public void setTimestampPresent(final String timeStampPresent) {
        setObject("Timestamp Present", timeStampPresent);
    }
    public String getTimestampPresent() {
        return (String) getObject("Timestamp Present");
    }

    public String getIdentifier() {
        return "IND";
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectBooleanString("Lyrics Present"));
        appendToObjectList(new ObjectBooleanString("Timestamp Present"));
    }
}