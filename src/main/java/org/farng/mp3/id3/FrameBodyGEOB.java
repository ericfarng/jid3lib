package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectByteArraySizeTerminated;
import org.farng.mp3.object.ObjectNumberHashMap;
import org.farng.mp3.object.ObjectStringNullTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.15.&nbsp;&nbsp; General encapsulated object</h3>
 * <p/>
 * <p>&nbsp;&nbsp; In this frame any type of file can be encapsulated. After the header,<br> &nbsp;&nbsp; 'Frame size'
 * and 'Encoding' follows 'MIME type' [MIME] represented as<br> &nbsp;&nbsp; as a terminated string encoded with ISO
 * 8859-1 [ISO-8859-1]. The<br> &nbsp;&nbsp; filename is case sensitive and is encoded as 'Encoding'. Then follows<br>
 * <p/>
 * &nbsp;&nbsp; a content description as terminated string, encoded as 'Encoding'.<br> &nbsp;&nbsp; The last thing in
 * the frame is the actual object. The first two<br> &nbsp;&nbsp; strings may be omitted, leaving only their
 * terminations. MIME type is<br> &nbsp;&nbsp; always an ISO-8859-1 text string. There may be more than one
 * &quot;GEOB&quot;<br>
 * <p/>
 * &nbsp;&nbsp; frame in each tag, but only one with the same content descriptor.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'General encapsulated object', ID: &quot;GEOB&quot;&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Text encoding&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; MIME type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * <p/>
 * &lt;text string&gt; $00<br> &nbsp;&nbsp;&nbsp;&nbsp; Filename&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &lt;text string according to encoding&gt; $00 (00)<br> &nbsp;&nbsp;&nbsp;&nbsp; Content description&nbsp;&nbsp;&nbsp;
 * &lt;text string according to encoding&gt; $00 (00)<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Encapsulated object&nbsp;&nbsp;&nbsp; &lt;binary data&gt;<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyGEOB extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyGEOB object.
     */
    public FrameBodyGEOB() {
        super();
    }

    /**
     * Creates a new FrameBodyGEOB object.
     */
    public FrameBodyGEOB(final FrameBodyGEOB body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyGEOB object.
     */
    public FrameBodyGEOB(final byte textEncoding,
                         final String mimeType,
                         final String filename,
                         final String description,
                         final byte[] object) {
        setObject("TextEncoding", new Byte(textEncoding));
        setObject("MIME Type", mimeType);
        setObject("Filename", filename);
        setObject("Description", description);
        setObject("Encapsulated Object", object);
    }

    /**
     * Creates a new FrameBodyGEOB object.
     */
    public FrameBodyGEOB(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public void setDescription(final String description) {
        setObject("Description", description);
    }

    public String getDescription() {
        return (String) getObject("Description");
    }

    public String getIdentifier() {
        return "GEOB" + ((char) 0) + getDescription();
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 1));
        appendToObjectList(new ObjectStringNullTerminated("MIME Type"));
        appendToObjectList(new ObjectStringNullTerminated("Filename"));
        appendToObjectList(new ObjectStringNullTerminated("Description"));
        appendToObjectList(new ObjectByteArraySizeTerminated("Encapsulated Object"));
    }
}