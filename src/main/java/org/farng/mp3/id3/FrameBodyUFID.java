package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectByteArraySizeTerminated;
import org.farng.mp3.object.ObjectStringNullTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.1.&nbsp;&nbsp; Unique file identifier</h3>
 * <p/>
 * <p>&nbsp;&nbsp; This frame's purpose is to be able to identify the audio file in a<br> &nbsp;&nbsp; database, that
 * may provide more information relevant to the content.<br> &nbsp;&nbsp; Since standardisation of such a database is
 * beyond this document, all<br>
 * <p/>
 * &nbsp;&nbsp; UFID frames begin with an 'owner identifier' field. It is a null-<br> &nbsp;&nbsp; terminated string
 * with a URL [URL] containing an email address, or a<br> &nbsp;&nbsp; link to a location where an email address can be
 * found, that belongs<br> &nbsp;&nbsp; to the organisation responsible for this specific database<br> &nbsp;&nbsp;
 * implementation. Questions regarding the database should be sent to<br>
 * <p/>
 * &nbsp;&nbsp; the indicated email address. The URL should not be used for the<br> &nbsp;&nbsp; actual database
 * queries. The string<br> &nbsp;&nbsp; &quot;http://www.id3.org/dummy/ufid.html&quot; should be used for tests. The<br>
 * &nbsp;&nbsp; 'Owner identifier' must be non-empty (more than just a termination).<br>
 * <p/>
 * &nbsp;&nbsp; The 'Owner identifier' is then followed by the actual identifier,<br> &nbsp;&nbsp; which may be up to 64
 * bytes. There may be more than one &quot;UFID&quot; frame<br> &nbsp;&nbsp; in a tag, but only one with the same 'Owner
 * identifier'.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Unique file identifier', ID: &quot;UFID&quot;&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Owner identifier&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;text string&gt; $00<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Identifier&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * <p/>
 * &lt;up to 64 bytes binary data&gt;<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyUFID extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyUFID object.
     */
    public FrameBodyUFID() {
        super();
    }

    /**
     * Creates a new FrameBodyUFID object.
     */
    public FrameBodyUFID(final FrameBodyUFID body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyUFID object.
     */
    public FrameBodyUFID(final String owner, final byte[] identifier) {
        setObject("Owner", owner);
        setObject("Identifier", identifier);
    }

    /**
     * Creates a new FrameBodyUFID object.
     */
    public FrameBodyUFID(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getIdentifier() {
        return "UFID" + ((char) 0) + getOwner();
    }

    public void setOwner(final String owner) {
        setObject("Owner", owner);
    }

    public String getOwner() {
        return (String) getObject("Owner");
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectStringNullTerminated("Owner"));
        appendToObjectList(new ObjectByteArraySizeTerminated("Identifier"));
    }
}