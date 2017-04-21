package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Commercial information' frame is a URL pointing at a webpage<br> &nbsp;&nbsp; with information such
 * as where the album can be bought. There may be<br> &nbsp;&nbsp; more than one &quot;WCOM&quot; frame in a tag, but
 * not with the same content.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyWCOM extends AbstractFrameBodyUrlLink {

    /**
     * Creates a new FrameBodyWCOM object.
     */
    public FrameBodyWCOM() {
        super();
    }

    /**
     * Creates a new FrameBodyWCOM object.
     */
    public FrameBodyWCOM(final String urlLink) {
        super(urlLink);
    }

    /**
     * Creates a new FrameBodyWCOM object.
     */
    public FrameBodyWCOM(final FrameBodyWCOM body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyWCOM object.
     */
    public FrameBodyWCOM(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "WCOM" + ((char) 0) + getUrlLink();
    }
}