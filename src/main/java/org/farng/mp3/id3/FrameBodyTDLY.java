package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Playlist delay' defines the numbers of milliseconds of silence<br> &nbsp;&nbsp; that should be
 * inserted before this audio. The value zero indicates<br>
 * <p/>
 * &nbsp;&nbsp; that this is a part of a multifile audio track that should be played<br> &nbsp;&nbsp; continuously.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTDLY extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTDLY object.
     */
    public FrameBodyTDLY() {
        super();
    }

    /**
     * Creates a new FrameBodyTDLY object.
     */
    public FrameBodyTDLY(final FrameBodyTDLY body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTDLY object.
     */
    public FrameBodyTDLY(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTDLY object.
     */
    public FrameBodyTDLY(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TDLY";
    }
}