package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectByteArraySizeTerminated;
import org.farng.mp3.object.ObjectNumberFixedLength;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.28.&nbsp;&nbsp; Signature frame</h3>
 * <p/>
 * <p>&nbsp;&nbsp; This frame enables a group of frames, grouped with the 'Group<br> &nbsp;&nbsp; identification
 * registration', to be signed. Although signatures can<br>
 * <p/>
 * &nbsp;&nbsp; reside inside the registration frame, it might be desired to store<br> &nbsp;&nbsp; the signature
 * elsewhere, e.g. in watermarks. There may be more than<br> &nbsp;&nbsp; one 'signature frame' in a tag, but no two may
 * be identical.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Signature frame', ID: &quot;SIGN&quot;&gt;<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Group symbol&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * Signature&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;binary data&gt;<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodySIGN extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodySIGN object.
     */
    public FrameBodySIGN() {
        super();
    }

    /**
     * Creates a new FrameBodySIGN object.
     */
    public FrameBodySIGN(final FrameBodySIGN body) {
        super(body);
    }

    /**
     * Creates a new FrameBodySIGN object.
     */
    public FrameBodySIGN(final byte groupSymbol, final byte[] signature) {
        setObject("Group Symbol", new Byte(groupSymbol));
        setObject("Signature", signature);
    }

    /**
     * Creates a new FrameBodySIGN object.
     */
    public FrameBodySIGN(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public void setGroupSymbol(final byte groupSymbol) {
        setObject("Group Symbol", new Byte(groupSymbol));
    }

    public byte getGroupSymbol() {
        if (getObject("Group Symbol") != null) {
            return ((Byte) getObject("Group Symbol")).byteValue();
        }
        return 0;
    }

    public String getIdentifier() {
        return "SIGN" + ((char) 0) + getGroupSymbol() + ((char) 0) + (new String(getSignature()));
    }

    public void setSignature(final byte[] signature) {
        setObject("Signature", signature);
    }

    public byte[] getSignature() {
        return (byte[]) getObject("Signature");
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberFixedLength("Group Symbol", 1));
        appendToObjectList(new ObjectByteArraySizeTerminated("Signature"));
    }
}