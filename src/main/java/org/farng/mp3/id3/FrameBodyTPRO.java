package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Produced notice' frame, in which the string must begin with a<br> &nbsp;&nbsp; year and a space
 * character (making five characters), is intended for<br>
 * <p/>
 * &nbsp;&nbsp; the production copyright holder of the original sound, not the audio<br> &nbsp;&nbsp; file itself. The
 * absence of this frame means only that the production<br> &nbsp;&nbsp; copyright information is unavailable or has
 * been removed, and must<br> &nbsp;&nbsp; not be interpreted to mean that the audio is public domain. Every<br>
 * &nbsp;&nbsp; time this field is displayed the field must be preceded with<br>
 * <p/>
 * &nbsp;&nbsp; &quot;Produced &quot; (P) &quot; &quot;, where (P) is one character showing a P in a<br> &nbsp;&nbsp;
 * circle.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTPRO extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTPRO object.
     */
    public FrameBodyTPRO() {
        super();
    }

    /**
     * Creates a new FrameBodyTPRO object.
     */
    public FrameBodyTPRO(final FrameBodyTPRO body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTPRO object.
     */
    public FrameBodyTPRO(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTPRO object.
     */
    public FrameBodyTPRO(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TPRO";
    }
}