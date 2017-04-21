package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectByteArraySizeTerminated;
import org.farng.mp3.object.ObjectStringNullTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.27.&nbsp;&nbsp; Private frame</h3>
 * <p/>
 * <p>&nbsp;&nbsp; This frame is used to contain information from a software producer<br> &nbsp;&nbsp; that its program
 * uses and does not fit into the other frames. The<br> &nbsp;&nbsp; frame consists of an 'Owner identifier' string and
 * the binary data.<br> &nbsp;&nbsp; The 'Owner identifier' is a null-terminated string with a URL [URL]<br>
 * &nbsp;&nbsp; containing an email address, or a link to a location where an email<br>
 * <p/>
 * &nbsp;&nbsp; address can be found, that belongs to the organisation responsible<br> &nbsp;&nbsp; for the frame.
 * Questions regarding the frame should be sent to the<br> &nbsp;&nbsp; indicated email address. The tag may contain
 * more than one &quot;PRIV&quot;<br> &nbsp;&nbsp; frame but only with different contents.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Private frame', ID: &quot;PRIV&quot;&gt;<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * Owner identifier&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;text string&gt; $00<br> &nbsp;&nbsp;&nbsp;&nbsp; The private
 * data&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * <p/>
 * &lt;binary data&gt;<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyPRIV extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyPRIV object.
     */
    public FrameBodyPRIV() {
        super();
    }

    /**
     * Creates a new FrameBodyPRIV object.
     */
    public FrameBodyPRIV(final FrameBodyPRIV body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyPRIV object.
     */
    public FrameBodyPRIV(final String owner, final byte[] data) {
        setObject("Owner", owner);
        setObject("Private Data", data);
    }

    /**
     * Creates a new FrameBodyPRIV object.
     */
    public FrameBodyPRIV(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getBriefDescription() {
        return this.getOwner();
    }

    public void setData(final byte[] data) {
        setObject("Private Data", data);
    }

    public byte[] getData() {
        return (byte[]) getObject("Private Data");
    }

    public String getIdentifier() {
        return "PRIV" + ((char) 0) + getOwner() + ((char) 0) + (new String(getData()));
    }

    public void setOwner(final String owner) {
        setObject("Owner", owner);
    }

    public String getOwner() {
        return (String) getObject("Owner");
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectStringNullTerminated("Owner"));
        appendToObjectList(new ObjectByteArraySizeTerminated("Private Data"));
    }
}