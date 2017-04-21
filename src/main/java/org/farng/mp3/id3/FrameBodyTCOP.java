package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Copyright message' frame, in which the string must begin with a<br> &nbsp;&nbsp; year and a space
 * character (making five characters), is intended for<br>
 * <p/>
 * &nbsp;&nbsp; the copyright holder of the original sound, not the audio file<br> &nbsp;&nbsp; itself. The absence of
 * this frame means only that the copyright<br> &nbsp;&nbsp; information is unavailable or has been removed, and must
 * not be<br> &nbsp;&nbsp; interpreted to mean that the audio is public domain. Every time this<br> &nbsp;&nbsp; field
 * is displayed the field must be preceded with &quot;Copyright &quot; (C) &quot;<br>
 * <p/>
 * &nbsp;&nbsp; &quot;, where (C) is one character showing a C in a circle.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTCOP extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTCOP object.
     */
    public FrameBodyTCOP() {
        super();
    }

    /**
     * Creates a new FrameBodyTCOP object.
     */
    public FrameBodyTCOP(final FrameBodyTCOP body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTCOP object.
     */
    public FrameBodyTCOP(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTCOP object.
     */
    public FrameBodyTCOP(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TCOP";
    }
}