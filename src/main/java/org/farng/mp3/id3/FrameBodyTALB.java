package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Album/Movie/Show title' frame is intended for the title of the<br> &nbsp;&nbsp; recording (or
 * source of sound) from which the audio in the file is<br>
 * <p/>
 * &nbsp;&nbsp; taken.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTALB extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTALB object.
     */
    public FrameBodyTALB() {
        super();
    }

    /**
     * Creates a new FrameBodyTALB object.
     */
    public FrameBodyTALB(final FrameBodyTALB body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTALB object.
     */
    public FrameBodyTALB(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTALB object.
     */
    public FrameBodyTALB(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TALB";
    }
}