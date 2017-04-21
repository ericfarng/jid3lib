package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectNumberHashMap;
import org.farng.mp3.object.ObjectStringNullTerminated;
import org.farng.mp3.object.ObjectStringSizeTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <p>&nbsp;&nbsp; This frame is intended for URL [URL] links concerning the audio file<br> &nbsp;&nbsp; in a similar
 * way to the other &quot;W&quot;-frames. The frame body consists<br>
 * <p/>
 * &nbsp;&nbsp; of a description of the string, represented as a terminated string,<br> &nbsp;&nbsp; followed by the
 * actual URL. The URL is always encoded with ISO-8859-1<br> &nbsp;&nbsp; [ISO-8859-1]. There may be more than one
 * &quot;WXXX&quot; frame in each tag,<br> &nbsp;&nbsp; but only one with the same description.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'User defined URL link frame', ID: &quot;WXXX&quot;&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Text encoding&nbsp;&nbsp;&nbsp;&nbsp; $xx<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;text string according to encoding&gt; $00 (00)<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; URL&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &lt;text string&gt;<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyWXXX extends AbstractID3v2FrameBody {

    String description = "";
    String urlLink = "";
    byte textEncoding = 0;

    /**
     * Creates a new FrameBodyWXXX object.
     */
    public FrameBodyWXXX() {
        super();
    }

    /**
     * Creates a new FrameBodyWXXX object.
     */
    public FrameBodyWXXX(final FrameBodyWXXX body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyWXXX object.
     */
    public FrameBodyWXXX(final byte textEncoding, final String description, final String urlLink) {
        setObject("Text Encoding", new Byte(textEncoding));
        setObject("Description", description);
        setObject("URL", urlLink);
    }

    /**
     * Creates a new FrameBodyWXXX object.
     */
    public FrameBodyWXXX(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getBriefDescription() {
        return this.getUrlLink();
    }

    public String getIdentifier() {
        return "WXXX" + ((char) 0) + this.description;
    }

    public void setUrlLink(final String urlLink) {
        setObject("URL", urlLink);
    }

    public String getUrlLink() {
        return (String) getObject("URL");
    }

    public boolean equals(final Object obj) {
        if ((obj instanceof FrameBodyWXXX) == false) {
            return false;
        }
        final FrameBodyWXXX frameBodyWXXX = (FrameBodyWXXX) obj;
        if (this.description.equals(frameBodyWXXX.description) == false) {
            return false;
        }
        if (this.textEncoding != frameBodyWXXX.textEncoding) {
            return false;
        }
        if (this.urlLink.equals(frameBodyWXXX.urlLink) == false) {
            return false;
        }
        return super.equals(obj);
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberHashMap("Text Encoding", 1));
        appendToObjectList(new ObjectStringNullTerminated("Description"));
        appendToObjectList(new ObjectStringSizeTerminated("URL"));
    }
}