package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Publishers official webpage' frame is a URL pointing at the<br> &nbsp;&nbsp; official webpage for
 * the publisher.<br>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyWPUB extends AbstractFrameBodyUrlLink {

    /**
     * Creates a new FrameBodyWPUB object.
     */
    public FrameBodyWPUB() {
        super();
    }

    /**
     * Creates a new FrameBodyWPUB object.
     */
    public FrameBodyWPUB(final String urlLink) {
        super(urlLink);
    }

    /**
     * Creates a new FrameBodyWPUB object.
     */
    public FrameBodyWPUB(final FrameBodyWPUB body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyWPUB object.
     */
    public FrameBodyWPUB(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "WPUB";
    }
}