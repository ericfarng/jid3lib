package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectByteArraySizeTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.4.&nbsp;&nbsp; Music CD identifier</h3>
 * <p/>
 * <p>&nbsp;&nbsp; This frame is intended for music that comes from a CD, so that the CD<br>
 * <p/>
 * &nbsp;&nbsp; can be identified in databases such as the CDDB [CDDB]. The frame<br> &nbsp;&nbsp; consists of a binary
 * dump of the Table Of Contents, TOC, from the CD,<br> &nbsp;&nbsp; which is a header of 4 bytes and then 8 bytes/track
 * on the CD plus 8<br> &nbsp;&nbsp; bytes for the 'lead out', making a maximum of 804 bytes. The offset<br>
 * &nbsp;&nbsp; to the beginning of every track on the CD should be described with a<br>
 * <p/>
 * &nbsp;&nbsp; four bytes absolute CD-frame address per track, and not with absolute<br> &nbsp;&nbsp; time. When this
 * frame is used the presence of a valid &quot;TRCK&quot; frame is<br> &nbsp;&nbsp; REQUIRED, even if the CD's only got
 * one track. It is recommended that<br> &nbsp;&nbsp; this frame is always added to tags originating from CDs. There
 * may<br>
 * <p/>
 * &nbsp;&nbsp; only be one &quot;MCDI&quot; frame in each tag.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Music CD identifier', ID: &quot;MCDI&quot;&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; CD TOC&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &lt;binary data&gt;<br>
 * <p/>
 * </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyMCDI extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyMCDI object.
     */
    public FrameBodyMCDI() {
        super();
    }

    /**
     * Creates a new FrameBodyMCDI object.
     */
    public FrameBodyMCDI(final FrameBodyMCDI body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyMCDI object.
     */
    public FrameBodyMCDI(final byte[] cdTOC) {
        setObject("CD Table of Contents", cdTOC);
    }

    /**
     * Creates a new FrameBodyMCDI object.
     */
    public FrameBodyMCDI(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getIdentifier() {
        return "MCDI";
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectByteArraySizeTerminated("CD Table of Contents"));
    }
}