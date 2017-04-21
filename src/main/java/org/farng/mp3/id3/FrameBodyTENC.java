package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Encoded by' frame contains the name of the person or<br> &nbsp;&nbsp; organisation that encoded the
 * audio file. This field may contain a<br> &nbsp;&nbsp; copyright message, if the audio file also is copyrighted by
 * the<br>
 * <p/>
 * &nbsp;&nbsp; encoder.<br>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTENC extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTENC object.
     */
    public FrameBodyTENC() {
        super();
    }

    /**
     * Creates a new FrameBodyTENC object.
     */
    public FrameBodyTENC(final FrameBodyTENC body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTENC object.
     */
    public FrameBodyTENC(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTENC object.
     */
    public FrameBodyTENC(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TENC";
    }
}