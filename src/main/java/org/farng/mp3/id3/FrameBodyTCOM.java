package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Composer' frame is intended for the name of the composer.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTCOM extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTCOM object.
     */
    public FrameBodyTCOM() {
        super();
    }

    /**
     * Creates a new FrameBodyTCOM object.
     */
    public FrameBodyTCOM(final FrameBodyTCOM body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTCOM object.
     */
    public FrameBodyTCOM(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTCOM object.
     */
    public FrameBodyTCOM(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TCOM";
    }
}