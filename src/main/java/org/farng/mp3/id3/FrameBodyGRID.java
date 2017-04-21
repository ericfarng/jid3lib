package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectByteArraySizeTerminated;
import org.farng.mp3.object.ObjectNumberFixedLength;
import org.farng.mp3.object.ObjectStringNullTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.26.&nbsp;&nbsp; Group identification registration</h3>
 * <p/>
 * <p>&nbsp;&nbsp; This frame enables grouping of otherwise unrelated frames. This can<br>
 * <p/>
 * &nbsp;&nbsp; be used when some frames are to be signed. To identify which frames<br> &nbsp;&nbsp; belongs to a set of
 * frames a group identifier must be registered in<br> &nbsp;&nbsp; the tag with this frame. The 'Owner identifier' is a
 * null-terminated<br> &nbsp;&nbsp; string with a URL [URL] containing an email address, or a link to a<br> &nbsp;&nbsp;
 * location where an email address can be found, that belongs to the<br>
 * <p/>
 * &nbsp;&nbsp; organisation responsible for this grouping. Questions regarding the<br> &nbsp;&nbsp; grouping should be
 * sent to the indicated email address. The 'Group<br> &nbsp;&nbsp; symbol' contains a value that associates the frame
 * with this group<br> &nbsp;&nbsp; throughout the whole tag, in the range $80-F0. All other values are<br> &nbsp;&nbsp;
 * reserved. The 'Group symbol' may optionally be followed by some group<br>
 * <p/>
 * &nbsp;&nbsp; specific data, e.g. a digital signature. There may be several &quot;GRID&quot;<br> &nbsp;&nbsp; frames
 * in a tag but only one containing the same symbol and only one<br> &nbsp;&nbsp; containing the same owner identifier.
 * The group symbol must be used<br> &nbsp;&nbsp; somewhere in the tag. See the description of the frame grouping
 * flag<br>
 * <p/>
 * &nbsp;&nbsp; in the ID3v2 structure document [ID3v2-strct] for more information.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Group ID registration', ID: &quot;GRID&quot;&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Owner identifier&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;text string&gt; $00<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Group symbol&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Group dependent data&nbsp; &lt;binary data&gt;<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyGRID extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyGRID object.
     */
    public FrameBodyGRID() {
        super();
    }

    /**
     * Creates a new FrameBodyGRID object.
     */
    public FrameBodyGRID(final FrameBodyGRID body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyGRID object.
     */
    public FrameBodyGRID(final String owner, final byte groupSymbol, final byte[] data) {
        setObject("Owner", owner);
        setObject("Group Symbol", new Byte(groupSymbol));
        setObject("Group Dependent Data", data);
    }

    /**
     * Creates a new FrameBodyGRID object.
     */
    public FrameBodyGRID(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public void setGroupSymbol(final byte textEncoding) {
        setObject("Group Symbol", new Byte(textEncoding));
    }

    public byte getGroupSymbol() {
        if (getObject("Group Symbol") != null) {
            return ((Byte) getObject("Group Symbol")).byteValue();
        }
        return 0;
    }

    public String getIdentifier() {
        return "GRID" + ((char) 0) + getOwner() + ((char) 0) + getGroupSymbol();
    }

    public void setOwner(final String owner) {
        setObject("Owner", owner);
    }

    public String getOwner() {
        return (String) getObject("Owner");
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectStringNullTerminated("Owner"));
        appendToObjectList(new ObjectNumberFixedLength("Group Symbol", 1));
        appendToObjectList(new ObjectByteArraySizeTerminated("Group Dependent Data"));
    }
}