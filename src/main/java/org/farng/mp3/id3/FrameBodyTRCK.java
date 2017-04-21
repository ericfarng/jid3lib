package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Track number/Position in set' frame is a numeric string<br> &nbsp;&nbsp; containing the order
 * number of the audio-file on its original<br>
 * <p/>
 * &nbsp;&nbsp; recording. This MAY be extended with a &quot;/&quot; character and a numeric<br> &nbsp;&nbsp; string
 * containing the total number of tracks/elements on the original<br> &nbsp;&nbsp; recording. E.g. &quot;4/9&quot;.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTRCK extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTRCK object.
     */
    public FrameBodyTRCK() {
        super();
    }

    /**
     * Creates a new FrameBodyTRCK object.
     */
    public FrameBodyTRCK(final FrameBodyTRCK body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTRCK object.
     */
    public FrameBodyTRCK(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTRCK object.
     */
    public FrameBodyTRCK(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TRCK";
    }
}