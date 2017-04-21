package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectByteArraySizeTerminated;
import org.farng.mp3.object.ObjectStringNullTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.20. Encrypted meta frame</h3>
 * <p/>
 * <p class=t> This frame contains one or more encrypted frames. This enables protection of copyrighted information such
 * as pictures and text, that people might want to pay extra for. Since standardisation of such an encryption scheme is
 * beyond this document, all "CRM" frames begin with a terminated string with a <a href="#url">URL</a> containing an
 * email address, or a link to a location where an email adress can be found, that belongs to the organisation
 * responsible for this specific encrypted meta frame. </p>
 * <p/>
 * <p class=t> Questions regarding the encrypted frame should be sent to the indicated email address. If a $00 is found
 * directly after the 'Frame size', the whole frame should be ignored, and preferably be removed. The 'Owner identifier'
 * is then followed by a short content description and explanation as to why it's encrypted. After the
 * 'content/explanation' description, the actual encrypted block follows. </p>
 * <p/>
 * <p class=t>
 * <p/>
 * When an ID3v2 decoder encounters a "CRM" frame, it should send the datablock to the 'plugin' with the corresponding
 * 'owner identifier' and expect to receive either a datablock with one or several ID3v2 frames after each other or an
 * error. There may be more than one "CRM" frames in a tag, but only one with the same 'owner identifier'. </p>
 * <p/>
 * <p><center> <table border=0> <tr><td nowrap>Encrypted meta frame</td><td rowspan=5>&nbsp;</td><td
 * width="100%">"CRM"</td></tr> <tr><td>Frame size</td><td>$xx xx xx</td></tr> <tr><td>Owner
 * identifier</td><td>&lt;textstring&gt; $00 (00)</td></tr>
 * <p/>
 * <tr><td>Content/explanation</td><td>&lt;textstring&gt; $00 (00)</td></tr> <tr><td>Encrypted
 * datablock</td><td>&lt;binary data&gt;</td></tr> </table> </center>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyCRM extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyCRM object.
     */
    public FrameBodyCRM() {
        super();
    }

    /**
     * Creates a new FrameBodyCRM object.
     */
    public FrameBodyCRM(final FrameBodyCRM body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyCRM object.
     */
    public FrameBodyCRM(final String owner, final String description, final byte[] data) {
        setObject("Owner", owner);
        setObject("Description", description);
        setObject("Encrypted datablock", data);
    }

    /**
     * Creates a new FrameBodyCRM object.
     */
    public FrameBodyCRM(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getIdentifier() {
        return "CRM" + ((char) 0) + getOwner();
    }

    public String getOwner() {
        return (String) getObject("Owner");
    }

    public void getOwner(final String description) {
        setObject("Owner", description);
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectStringNullTerminated("Owner"));
        appendToObjectList(new ObjectStringNullTerminated("Description"));
        appendToObjectList(new ObjectByteArraySizeTerminated("Encrypted datablock"));
    }
}