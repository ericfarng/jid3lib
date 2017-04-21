package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Content group description' frame is used if the sound belongs to<br> &nbsp;&nbsp; a larger category
 * of sounds/music. For example, classical music is<br> &nbsp;&nbsp; often sorted in different musical sections (e.g.
 * &quot;Piano Concerto&quot;,<br>
 * <p/>
 * &nbsp;&nbsp; &quot;Weather - Hurricane&quot;).</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTIT1 extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTIT1 object.
     */
    public FrameBodyTIT1() {
        super();
    }

    /**
     * Creates a new FrameBodyTIT1 object.
     */
    public FrameBodyTIT1(final FrameBodyTIT1 body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTIT1 object.
     */
    public FrameBodyTIT1(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTIT1 object.
     */
    public FrameBodyTIT1(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TIT1";
    }
}