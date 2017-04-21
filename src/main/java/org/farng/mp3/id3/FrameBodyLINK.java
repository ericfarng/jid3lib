package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectStringFixedLength;
import org.farng.mp3.object.ObjectStringNullTerminated;
import org.farng.mp3.object.ObjectStringSizeTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.20.&nbsp;&nbsp; Linked information</h3>
 * <p/>
 * <p>&nbsp;&nbsp; To keep information duplication as low as possible this frame may be<br> &nbsp;&nbsp; used to link
 * information from another ID3v2 tag that might reside in<br> &nbsp;&nbsp; another audio file or alone in a binary
 * file. It is RECOMMENDED that<br>
 * <p/>
 * &nbsp;&nbsp; this method is only used when the files are stored on a CD-ROM or<br> &nbsp;&nbsp; other circumstances
 * when the risk of file separation is low. The<br> &nbsp;&nbsp; frame contains a frame identifier, which is the frame
 * that should be<br> &nbsp;&nbsp; linked into this tag, a URL [URL] field, where a reference to the<br> &nbsp;&nbsp;
 * file where the frame is given, and additional ID data, if needed.<br>
 * <p/>
 * &nbsp;&nbsp; Data should be retrieved from the first tag found in the file to<br> &nbsp;&nbsp; which this link
 * points. There may be more than one &quot;LINK&quot; frame in a<br> &nbsp;&nbsp; tag, but only one with the same
 * contents. A linked frame is to be<br> &nbsp;&nbsp; considered as part of the tag and has the same restrictions as if
 * it<br>
 * <p/>
 * &nbsp;&nbsp; was a physical part of the tag (i.e. only one &quot;RVRB&quot; frame allowed,<br> &nbsp;&nbsp; whether
 * it's linked or not).</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Linked information', ID: &quot;LINK&quot;&gt;<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Frame identifier&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx xx xx xx<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; URL&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &lt;text string&gt; $00<br> &nbsp;&nbsp;&nbsp;&nbsp; ID and additional data&nbsp; &lt;text string(s)&gt;</p>
 * <p/>
 * <p>&nbsp;&nbsp; Frames that may be linked and need no additional data are &quot;ASPI&quot;,<br> &nbsp;&nbsp;
 * &quot;ETCO&quot;, &quot;EQU2&quot;, &quot;MCID&quot;, &quot;MLLT&quot;, &quot;OWNE&quot;, &quot;RVA2&quot;,
 * &quot;RVRB&quot;, &quot;SYTC&quot;, the<br>
 * <p/>
 * &nbsp;&nbsp; text information frames and the URL link frames.</p>
 * <p/>
 * <p>&nbsp;&nbsp; The &quot;AENC&quot;, &quot;APIC&quot;, &quot;GEOB&quot; and &quot;TXXX&quot; frames may be linked
 * with<br>
 * <p/>
 * &nbsp;&nbsp; the content descriptor as additional ID data.</p>
 * <p/>
 * <p>&nbsp;&nbsp; The &quot;USER&quot; frame may be linked with the language field as additional<br> &nbsp;&nbsp; ID
 * data.<br> &nbsp;&nbsp; </p>
 * <p/>
 * <p>&nbsp;&nbsp; The &quot;PRIV&quot; frame may be linked with the owner identifier as<br> &nbsp;&nbsp; additional ID
 * data.</p>
 * <p/>
 * <p>&nbsp;&nbsp; The &quot;COMM&quot;, &quot;SYLT&quot; and &quot;USLT&quot;
 * <p/>
 * frames may be linked with three bytes<br> &nbsp;&nbsp; of language descriptor directly followed by a content
 * descriptor as<br> &nbsp;&nbsp; additional ID data.<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyLINK extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyLINK object.
     */
    public FrameBodyLINK() {
        super();
    }

    /**
     * Creates a new FrameBodyLINK object.
     */
    public FrameBodyLINK(final FrameBodyLINK body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyLINK object.
     */
    public FrameBodyLINK(final String frameIdentifier, final String url, final String additionalData) {
        setObject("Frame Identifier", frameIdentifier);
        setObject("URL", url);
        setObject("ID and Additional Data", additionalData);
    }

    /**
     * Creates a new FrameBodyLINK object.
     */
    public FrameBodyLINK(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getAdditionalData() {
        return (String) getObject("ID and Additional Data");
    }

    public void getAdditionalData(final String additionalData) {
        setObject("ID and Additional Data", additionalData);
    }

    public String getFrameIdentifier() {
        return (String) getObject("Frame Identifier");
    }

    public void getFrameIdentifier(final String frameIdentifier) {
        setObject("Frame Identifier", frameIdentifier);
    }

    public String getIdentifier() {
        return "LINK" + ((char) 0) + getFrameIdentifier() + ((char) 0) + getAdditionalData();
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectStringFixedLength("Frame Identifier", 4));
        appendToObjectList(new ObjectStringNullTerminated("URL"));
        appendToObjectList(new ObjectStringSizeTerminated("ID and Additional Data"));
    }
}