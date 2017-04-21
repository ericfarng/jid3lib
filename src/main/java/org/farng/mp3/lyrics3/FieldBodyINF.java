package org.farng.mp3.lyrics3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectStringSizeTerminated;

import java.io.RandomAccessFile;

/**
 * Additional information multi line text.
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FieldBodyINF extends AbstractLyrics3v2FieldBody {

    /**
     * Creates a new FieldBodyINF object.
     */
    public FieldBodyINF() {
        super();
    }

    /**
     * Creates a new FieldBodyINF object.
     */
    public FieldBodyINF(final FieldBodyINF body) {
        super(body);
    }

    /**
     * Creates a new FieldBodyINF object.
     */
    public FieldBodyINF(final String additionalInformation) {
        setObject("Additional Information", additionalInformation);
    }

    /**
     * Creates a new FieldBodyINF object.
     */
    public FieldBodyINF(final RandomAccessFile file) throws InvalidTagException, java.io.IOException {
        this.read(file);
    }

    public void setAdditionalInformation(final String additionalInformation) {
        setObject("Additional Information", additionalInformation);
    }

    public String getAdditionalInformation() {
        return (String) getObject("Additional Information");
    }

    public String getIdentifier() {
        return "INF";
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectStringSizeTerminated("Additional Information"));
    }
}