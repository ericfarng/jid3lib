package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Official artist/performer webpage' frame is a URL pointing at<br> &nbsp;&nbsp; the artists official
 * webpage. There may be more than one &quot;WOAR&quot; frame<br> &nbsp;&nbsp; in a tag if the audio contains more than
 * one performer, but not with<br> &nbsp;&nbsp; the same content.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyWOAR extends AbstractFrameBodyUrlLink {

    /**
     * Creates a new FrameBodyWOAR object.
     */
    public FrameBodyWOAR() {
        super();
    }

    /**
     * Creates a new FrameBodyWOAR object.
     */
    public FrameBodyWOAR(final String urlLink) {
        super(urlLink);
    }

    /**
     * Creates a new FrameBodyWOAR object.
     */
    public FrameBodyWOAR(final FrameBodyWOAR body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyWOAR object.
     */
    public FrameBodyWOAR(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "WOAR" + ((char) 0) + getUrlLink();
    }
}