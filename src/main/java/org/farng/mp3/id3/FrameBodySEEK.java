package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectNumberFixedLength;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.29.&nbsp;&nbsp; Seek frame</h3>
 * <p/>
 * <p>&nbsp;&nbsp; This frame indicates where other tags in a file/stream can be found.<br> &nbsp;&nbsp; The 'minimum
 * offset to next tag' is calculated from the end of this<br> &nbsp;&nbsp; tag to the beginning of the next. There may
 * only be one 'seek frame'<br> &nbsp;&nbsp; in a tag.</p>
 * <p/>
 * <p>&nbsp;&nbsp; &lt;Header for 'Seek frame', ID: &quot;SEEK&quot;&gt;<br> &nbsp;&nbsp; Minimum offset to next
 * tag&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx xx xx xx<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodySEEK extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodySEEK object.
     */
    public FrameBodySEEK() {
        super();
    }

    /**
     * Creates a new FrameBodySEEK object.
     */
    public FrameBodySEEK(final int minOffsetToNextTag) {
        setObject("Minimum Offset to Next Tag", new Integer(minOffsetToNextTag));
    }

    /**
     * Creates a new FrameBodySEEK object.
     */
    public FrameBodySEEK(final FrameBodySEEK body) {
        super(body);
    }

    /**
     * Creates a new FrameBodySEEK object.
     */
    public FrameBodySEEK(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getIdentifier() {
        return "SEEK";
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberFixedLength("Minimum Offset to Next Tag", 4));
    }
}