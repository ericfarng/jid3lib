package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * <div class=h5>TRDA</div>
 * <p/>
 * <div class=t>The 'Recording dates' frame is a intended to be used as complement to the "<a href="#TYER">TYER</a>",
 * "<a href="#TDAT">TDAT</a>" and "<a href="#TIME">TIME</a>" frames. E.g. "4th-7th June, 12th June" in combination with
 * the "<a href="#TYER">TYER</a>" frame.</div>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTRDA extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTRDA object.
     */
    public FrameBodyTRDA() {
        super();
    }

    /**
     * Creates a new FrameBodyTRDA object.
     */
    public FrameBodyTRDA(final FrameBodyTRDA body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTRDA object.
     */
    public FrameBodyTRDA(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTRDA object.
     */
    public FrameBodyTRDA(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TRDA";
    }
}