package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Interpreted, remixed, or otherwise modified by' frame contains<br> &nbsp;&nbsp; more information
 * about the people behind a remix and similar<br> &nbsp;&nbsp; interpretations of another existing piece.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTPE4 extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTPE4 object.
     */
    public FrameBodyTPE4() {
        super();
    }

    /**
     * Creates a new FrameBodyTPE4 object.
     */
    public FrameBodyTPE4(final FrameBodyTPE4 body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTPE4 object.
     */
    public FrameBodyTPE4(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTPE4 object.
     */
    public FrameBodyTPE4(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TPE4";
    }
}