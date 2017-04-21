package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

/**
 * &nbsp;&nbsp; The 'Content type', which ID3v1 was stored as a one byte numeric<br>
 * <p/>
 * &nbsp;&nbsp; value only, is now a string. You may use one or several of the ID3v1<br> &nbsp;&nbsp; types as numerical
 * strings, or, since the category list would be<br> &nbsp;&nbsp; impossible to maintain with accurate and up to date
 * categories,<br> &nbsp;&nbsp; define your own. Example: &quot;21&quot; $00 &quot;Eurodisco&quot;
 * <p/>
 * $00</p>
 * <p/>
 * <p>&nbsp;&nbsp; You may also use any of the following keywords:<br> &nbsp;&nbsp; </p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; RX&nbsp; Remix<br> &nbsp;&nbsp;&nbsp;&nbsp; CR&nbsp; Cover</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTCON extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTCON object.
     */
    public FrameBodyTCON() {
        super();
    }

    /**
     * Creates a new FrameBodyTCON object.
     */
    public FrameBodyTCON(final FrameBodyTCON body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTCON object.
     */
    public FrameBodyTCON(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTCON object.
     */
    public FrameBodyTCON(final java.io.RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TCON";
    }
}