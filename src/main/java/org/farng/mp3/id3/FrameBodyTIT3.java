package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Subtitle/Description refinement' frame is used for information<br> &nbsp;&nbsp; directly related to
 * the contents title (e.g. &quot;Op. 16&quot; or &quot;Performed<br>
 * <p/>
 * &nbsp;&nbsp; live at Wembley&quot;).</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTIT3 extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTIT3 object.
     */
    public FrameBodyTIT3() {
        super();
    }

    /**
     * Creates a new FrameBodyTIT3 object.
     */
    public FrameBodyTIT3(final FrameBodyTIT3 body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTIT3 object.
     */
    public FrameBodyTIT3(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTIT3 object.
     */
    public FrameBodyTIT3(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TIT3";
    }
}