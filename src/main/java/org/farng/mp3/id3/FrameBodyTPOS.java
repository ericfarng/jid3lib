package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;

import java.io.RandomAccessFile;

/**
 * &nbsp;&nbsp; The 'Part of a set' frame is a numeric string that describes which<br> &nbsp;&nbsp; part of a set the
 * audio came from. This frame is used if the source<br> &nbsp;&nbsp; described in the &quot;TALB&quot; frame is divided
 * into several mediums, e.g. a<br>
 * <p/>
 * &nbsp;&nbsp; double CD. The value MAY be extended with a &quot;/&quot; character and a<br> &nbsp;&nbsp; numeric
 * string containing the total number of parts in the set. E.g.<br> &nbsp;&nbsp; &quot;1/2&quot;.</p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyTPOS extends AbstractFrameBodyTextInformation {

    /**
     * Creates a new FrameBodyTPOS object.
     */
    public FrameBodyTPOS() {
        super();
    }

    /**
     * Creates a new FrameBodyTPOS object.
     */
    public FrameBodyTPOS(final FrameBodyTPOS body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyTPOS object.
     */
    public FrameBodyTPOS(final byte textEncoding, final String text) {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTPOS object.
     */
    public FrameBodyTPOS(final RandomAccessFile file) throws java.io.IOException, InvalidTagException {
        super(file);
    }

    public String getIdentifier() {
        return "TPOS";
    }
}