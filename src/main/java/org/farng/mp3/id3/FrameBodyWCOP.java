package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Copyright/Legal information' frame is a URL pointing at a<br> &nbsp;&nbsp; webpage where the terms
 * of use and ownership of the file is<br> &nbsp;&nbsp; described.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyWCOP extends AbstractFrameBodyUrlLink {

    /**
     * Creates a new FrameBodyWCOP object.
     */
    public FrameBodyWCOP() {
        super();
    }

    /**
     * Creates a new FrameBodyWCOP object.
     */
    public FrameBodyWCOP(final String urlLink) {
        super(urlLink);
    }

    /**
     * Creates a new FrameBodyWCOP object.
     */
    public FrameBodyWCOP(final FrameBodyWCOP body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyWCOP object.
     */
    public FrameBodyWCOP(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "WCOP";
    }
}