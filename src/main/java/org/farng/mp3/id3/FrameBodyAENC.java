package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectByteArraySizeTerminated;
import org.farng.mp3.object.ObjectNumberFixedLength;
import org.farng.mp3.object.ObjectStringNullTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.19.&nbsp;&nbsp; Audio encryption</h3>
 * <p/>
 * <p>&nbsp;&nbsp; This frame indicates if the actual audio stream is encrypted, and by<br>
 * <p/>
 * &nbsp;&nbsp; whom. Since standardisation of such encryption scheme is beyond this<br> &nbsp;&nbsp; document, all
 * &quot;AENC&quot; frames begin with a terminated string with a<br> &nbsp;&nbsp; URL containing an email address, or a
 * link to a location where an<br> &nbsp;&nbsp; email address can be found, that belongs to the organisation<br>
 * <p/>
 * &nbsp;&nbsp; responsible for this specific encrypted audio file. Questions<br> &nbsp;&nbsp; regarding the encrypted
 * audio should be sent to the email address<br> &nbsp;&nbsp; specified. If a $00 is found directly after the 'Frame
 * size' and the<br> &nbsp;&nbsp; audio file indeed is encrypted, the whole file may be considered<br> &nbsp;&nbsp;
 * useless.</p>
 * <p/>
 * <p>&nbsp;&nbsp; After the 'Owner identifier', a pointer to an unencrypted part of the<br> &nbsp;&nbsp; audio can be
 * specified. The 'Preview start' and 'Preview length' is<br> &nbsp;&nbsp; described in frames. If no part is
 * unencrypted, these fields should<br> &nbsp;&nbsp; be left zeroed. After the 'preview length' field follows optionally
 * a<br> &nbsp;&nbsp; data block required for decryption of the audio. There may be more<br>
 * <p/>
 * &nbsp;&nbsp; than one &quot;AENC&quot; frames in a tag, but only one with the same 'Owner<br> &nbsp;&nbsp;
 * identifier'.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Audio encryption', ID: &quot;AENC&quot;&gt;<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * Owner identifier&nbsp;&nbsp; &lt;text string&gt; $00<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Preview start&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx xx<br> &nbsp;&nbsp;&nbsp;&nbsp; Preview
 * length&nbsp;&nbsp;&nbsp;&nbsp; $xx xx<br> &nbsp;&nbsp;&nbsp;&nbsp; Encryption info&nbsp;&nbsp;&nbsp; &lt;binary
 * data&gt;<br>
 * <p/>
 * </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyAENC extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyAENC object.
     */
    public FrameBodyAENC() {
        super();
    }

    /**
     * Creates a new FrameBodyAENC object.
     */
    public FrameBodyAENC(final FrameBodyAENC body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyAENC object.
     */
    public FrameBodyAENC(final String owner, final short previewStart, final short previewLength, final byte[] data) {
        super();
        setObject("Owner", owner);
        setObject("Preview Start", new Short(previewStart));
        setObject("Preview Length", new Short(previewLength));
        setObject("Encryption Info", data);
    }

    /**
     * Creates a new FrameBodyAENC object.
     */
    public FrameBodyAENC(final RandomAccessFile file) throws IOException, InvalidTagException {
        super();
        read(file);
    }

    public String getIdentifier() {
        return "AENC" + (char) 0 + getOwner();
    }

    public String getOwner() {
        return (String) getObject("Owner");
    }

    public void getOwner(final String description) {
        setObject("Owner", description);
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectStringNullTerminated("Owner"));
        appendToObjectList(new ObjectNumberFixedLength("Preview Start", 2));
        appendToObjectList(new ObjectNumberFixedLength("Preview Length", 2));
        appendToObjectList(new ObjectByteArraySizeTerminated("Encryption Info"));
    }
}