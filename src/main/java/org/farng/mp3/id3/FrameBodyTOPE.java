package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Original artist/performer' frame is intended for the performer<br> &nbsp;&nbsp; of the original
 * recording, if for example the music in the file<br> &nbsp;&nbsp; should be a cover of a previously released
 * song.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTOPE extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTOPE object.
     */
    public FrameBodyTOPE() {
        super();
    }

    /**
     * Creates a new FrameBodyTOPE object.
     */
    public FrameBodyTOPE(final FrameBodyTOPE body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTOPE object.
     */
    public FrameBodyTOPE(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTOPE object.
     */
    public FrameBodyTOPE(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TOPE";
    }
}