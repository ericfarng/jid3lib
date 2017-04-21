package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Length' frame contains the length of the audio file in<br>
 * <p/>
 * &nbsp;&nbsp; milliseconds, represented as a numeric string.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTLEN extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTLEN object.
     */
    public FrameBodyTLEN() {
        super();
    }

    /**
     * Creates a new FrameBodyTLEN object.
     */
    public FrameBodyTLEN(final FrameBodyTLEN body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTLEN object.
     */
    public FrameBodyTLEN(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTLEN object.
     */
    public FrameBodyTLEN(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TLEN";
    }
}