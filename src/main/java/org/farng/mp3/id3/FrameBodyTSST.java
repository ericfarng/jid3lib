package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Set subtitle' frame is intended for the subtitle of the part of<br> &nbsp;&nbsp; a set this track
 * belongs to.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTSST extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTSST object.
     */
    public FrameBodyTSST() {
        super();
    }

    /**
     * Creates a new FrameBodyTSST object.
     */
    public FrameBodyTSST(final FrameBodyTSST body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTSST object.
     */
    public FrameBodyTSST(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTSST object.
     */
    public FrameBodyTSST(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TSST";
    }
}