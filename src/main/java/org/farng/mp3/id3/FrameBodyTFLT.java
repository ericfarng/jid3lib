package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'File type' frame indicates which type of audio this tag defines.<br> &nbsp;&nbsp; The following
 * types and refinements are defined:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; MIME&nbsp;&nbsp; MIME type follows<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; MPG&nbsp;&nbsp;&nbsp; MPEG Audio<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * /1&nbsp;&nbsp;&nbsp;&nbsp; MPEG 1/2 layer I<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /2&nbsp;&nbsp;&nbsp;&nbsp; MPEG
 * 1/2 layer II<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /3&nbsp;&nbsp;&nbsp;&nbsp; MPEG 1/2 layer III<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; /2.5&nbsp;&nbsp; MPEG 2.5<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * /AAC&nbsp;&nbsp; Advanced audio compression<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; VQF&nbsp;&nbsp;&nbsp; Transform-domain Weighted Interleave Vector Quantisation<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; PCM&nbsp;&nbsp;&nbsp; Pulse Code Modulated audio</p>
 * <p/>
 * <p>&nbsp;&nbsp; but other types may be used, but not for these types though. This is<br> &nbsp;&nbsp; used in a
 * similar way to the predefined types in the &quot;TMED&quot;
 * <p/>
 * frame,<br> &nbsp;&nbsp; but without parentheses. If this frame is not present audio type is<br> &nbsp;&nbsp; assumed
 * to be &quot;MPG&quot;.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTFLT extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTFLT object.
     */
    public FrameBodyTFLT() {
        super();
    }

    /**
     * Creates a new FrameBodyTFLT object.
     */
    public FrameBodyTFLT(final FrameBodyTFLT body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTFLT object.
     */
    public FrameBodyTFLT(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTFLT object.
     */
    public FrameBodyTFLT(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TFLT";
    }
}