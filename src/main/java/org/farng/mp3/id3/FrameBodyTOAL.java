package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Original album/movie/show title' frame is intended for the title<br> &nbsp;&nbsp; of the original
 * recording (or source of sound), if for example the<br>
 * <p/>
 * &nbsp;&nbsp; music in the file should be a cover of a previously released song.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTOAL extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTOAL object.
     */
    public FrameBodyTOAL() {
        super();
    }

    /**
     * Creates a new FrameBodyTOAL object.
     */
    public FrameBodyTOAL(final FrameBodyTOAL body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTOAL object.
     */
    public FrameBodyTOAL(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTOAL object.
     */
    public FrameBodyTOAL(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TOAL";
    }
}