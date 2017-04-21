package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectByteArraySizeTerminated;
import org.farng.mp3.object.ObjectNumberFixedLength;
import org.farng.mp3.object.ObjectStringNullTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.25.&nbsp;&nbsp; Encryption method registration</h3>
 * <p/>
 * <p>&nbsp;&nbsp; To identify with which method a frame has been encrypted the<br>
 * <p/>
 * &nbsp;&nbsp; encryption method must be registered in the tag with this frame. The<br> &nbsp;&nbsp; 'Owner identifier'
 * is a null-terminated string with a URL [URL]<br> &nbsp;&nbsp; containing an email address, or a link to a location
 * where an email<br> &nbsp;&nbsp; address can be found, that belongs to the organisation responsible<br> &nbsp;&nbsp;
 * for this specific encryption method. Questions regarding the<br>
 * <p/>
 * &nbsp;&nbsp; encryption method should be sent to the indicated email address. The<br> &nbsp;&nbsp; 'Method symbol'
 * contains a value that is associated with this method<br> &nbsp;&nbsp; throughout the whole tag, in the range $80-F0.
 * All other values are<br> &nbsp;&nbsp; reserved. The 'Method symbol' may optionally be followed by<br> &nbsp;&nbsp;
 * encryption specific data. There may be several &quot;ENCR&quot;
 * <p/>
 * frames in a tag<br> &nbsp;&nbsp; but only one containing the same symbol and only one containing the<br> &nbsp;&nbsp;
 * same owner identifier. The method must be used somewhere in the tag.<br> &nbsp;&nbsp; See the description of the
 * frame encryption flag in the ID3v2<br> &nbsp;&nbsp; structure document [ID3v2-strct] for more information.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Encryption method registration', ID: &quot;ENCR&quot;&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Owner identifier&nbsp;&nbsp;&nbsp; &lt;text string&gt; $00<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * Method symbol&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Encryption data&nbsp;&nbsp;&nbsp;&nbsp; &lt;binary data&gt;<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyENCR extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyENCR object.
     */
    public FrameBodyENCR() {
        super();
    }

    /**
     * Creates a new FrameBodyENCR object.
     */
    public FrameBodyENCR(final FrameBodyENCR body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyENCR object.
     */
    public FrameBodyENCR(final String owner, final byte methodSymbol, final byte[] data) {
        setObject("Owner", owner);
        setObject("Method Symbol", new Byte(methodSymbol));
        setObject("Encryption Data", data);
    }

    /**
     * Creates a new FrameBodyENCR object.
     */
    public FrameBodyENCR(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getIdentifier() {
        return "ENCR" + ((char) 0) + getOwner();
    }

    public void setOwner(final String owner) {
        setObject("Owner", owner);
    }

    public String getOwner() {
        return (String) getObject("Owner");
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectStringNullTerminated("Owner"));
        appendToObjectList(new ObjectNumberFixedLength("Method Symbol", 1));
        appendToObjectList(new ObjectByteArraySizeTerminated("Encryption Data"));
    }
}