package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectByteArraySizeTerminated;
import org.farng.mp3.object.ObjectNumberHashMap;
import org.farng.mp3.object.ObjectStringNullTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.14.&nbsp;&nbsp; Attached picture</h3>
 * <p/>
 * <p>&nbsp;&nbsp; This frame contains a picture directly related to the audio file.<br> &nbsp;&nbsp; Image format is
 * the MIME type and subtype [MIME] for the image. In<br> &nbsp;&nbsp; the event that the MIME media type name is
 * omitted, &quot;image/&quot; will be<br>
 * <p/>
 * &nbsp;&nbsp; implied. The &quot;image/png&quot; [PNG] or &quot;image/jpeg&quot; [JFIF] picture format<br>
 * &nbsp;&nbsp; should be used when interoperability is wanted. Description is a<br> &nbsp;&nbsp; short description of
 * the picture, represented as a terminated<br>
 * <p/>
 * &nbsp;&nbsp; text string. There may be several pictures attached to one file, each<br> &nbsp;&nbsp; in their
 * individual &quot;APIC&quot; frame, but only one with the same content<br> &nbsp;&nbsp; descriptor. There may only be
 * one picture with the picture type<br> &nbsp;&nbsp; declared as picture type $01 and $02 respectively. There is
 * the<br>
 * <p/>
 * &nbsp;&nbsp; possibility to put only a link to the image file by using the 'MIME<br> &nbsp;&nbsp; type'
 * &quot;--&gt;&quot; and having a complete URL [URL] instead of picture data.<br> &nbsp;&nbsp; The use of linked files
 * should however be used sparingly since there<br> &nbsp;&nbsp; is the risk of separation of files.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Attached picture', ID: &quot;APIC&quot;&gt;<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * Text encoding&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br> &nbsp;&nbsp;&nbsp;&nbsp; MIME
 * type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;text string&gt;
 * <p/>
 * $00<br> &nbsp;&nbsp;&nbsp;&nbsp; Picture type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;text string according to encoding&gt; $00 (00)<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Picture data&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;binary data&gt;<br> </p>
 * <p/>
 * <p>&nbsp;&nbsp; Picture type:&nbsp; $00&nbsp; Other<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * <p/>
 * $01&nbsp; 32x32 pixels 'file icon' (PNG only)<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $02&nbsp; Other file icon<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $03&nbsp; Cover (front)<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $04&nbsp;
 * Cover (back)<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $05&nbsp; Leaflet page<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $06&nbsp; Media (e.g. label side of CD)<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $07&nbsp; Lead
 * artist/lead performer/soloist<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $08&nbsp; Artist/performer<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $09&nbsp; Conductor<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $0A&nbsp;
 * Band/Orchestra<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $0B&nbsp; Composer<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $0C&nbsp; Lyricist/text writer<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $0D&nbsp;
 * Recording Location<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $0E&nbsp; During recording<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $0F&nbsp; During performance<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $10&nbsp;
 * Movie/video screen capture<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $11&nbsp; A bright coloured fish<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $12&nbsp; Illustration<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $13&nbsp;
 * Band/artist logotype<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $14&nbsp; Publisher/Studio logotype<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyAPIC extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyAPIC object.
     */
    public FrameBodyAPIC() {
        super();
    }

    /**
     * Creates a new FrameBodyAPIC object.
     */
    public FrameBodyAPIC(final FrameBodyAPIC body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyAPIC object.
     */
    public FrameBodyAPIC(final byte textEncoding,
                         final String mimeType,
                         final byte pictureType,
                         final String description,
                         final byte[] data) {
        super();
        setObject("Text Encoding", new Byte(textEncoding));
        setObject("MIME Type", mimeType);
        setObject("Picture Type", new Byte(pictureType));
        setObject("Description", description);
        setObject("Picture Data", data);
    }

    /**
     * Creates a new FrameBodyAPIC object.
     */
    public FrameBodyAPIC(final RandomAccessFile file) throws IOException, InvalidTagException {
        super();
        read(file);
    }

    public void setDescription(final String description) {
        setObject("Description", description);
    }

    public String getDescription() {
        return (String) getObject("Description");
    }

    public String getIdentifier() {
        return "APIC" + (char) 0 + getDescription();
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberHashMap("Text Encoding", 1));
        appendToObjectList(new ObjectStringNullTerminated("MIME Type"));
        appendToObjectList(new ObjectStringNullTerminated("Description"));
        appendToObjectList(new ObjectByteArraySizeTerminated("Picture Data"));
    }
}