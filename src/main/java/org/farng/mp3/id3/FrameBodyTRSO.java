package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Internet radio station owner' frame contains the name of the<br> &nbsp;&nbsp; owner of the internet
 * radio station from which the audio is<br> &nbsp;&nbsp; streamed.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTRSO extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTRSO object.
     */
    public FrameBodyTRSO() {
        super();
    }

    /**
     * Creates a new FrameBodyTRSO object.
     */
    public FrameBodyTRSO(final FrameBodyTRSO body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTRSO object.
     */
    public FrameBodyTRSO(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTRSO object.
     */
    public FrameBodyTRSO(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TRSO";
    }
}