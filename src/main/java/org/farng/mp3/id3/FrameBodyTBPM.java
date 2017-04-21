package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'BPM' frame contains the number of beats per minute in the<br>
 * <p/>
 * &nbsp;&nbsp; main part of the audio. The BPM is an integer and represented as a<br> &nbsp;&nbsp; numerical
 * string.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTBPM extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTBPM object.
     */
    public FrameBodyTBPM() {
        super();
    }

    /**
     * Creates a new FrameBodyTBPM object.
     */
    public FrameBodyTBPM(final FrameBodyTBPM body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTBPM object.
     */
    public FrameBodyTBPM(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTBPM object.
     */
    public FrameBodyTBPM(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TBPM";
    }
}