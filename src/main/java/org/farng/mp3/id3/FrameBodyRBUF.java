package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectBooleanByte;
import org.farng.mp3.object.ObjectNumberFixedLength;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.18.&nbsp;&nbsp; Recommended buffer size</h3>
 * <p/>
 * <p>&nbsp;&nbsp; Sometimes the server from which an audio file is streamed is aware of<br> &nbsp;&nbsp; transmission
 * or coding problems resulting in interruptions in the<br> &nbsp;&nbsp; audio stream. In these cases, the size of the
 * buffer can be<br> &nbsp;&nbsp; recommended by the server using this frame. If the 'embedded info<br>
 * <p/>
 * &nbsp;&nbsp; flag' is true (1) then this indicates that an ID3 tag with the<br> &nbsp;&nbsp; maximum size described
 * in 'Buffer size' may occur in the audio<br> &nbsp;&nbsp; stream. In such case the tag should reside between two MPEG
 * [MPEG]<br> &nbsp;&nbsp; frames, if the audio is MPEG encoded. If the position of the next tag<br> &nbsp;&nbsp; is
 * known, 'offset to next tag' may be used. The offset is calculated<br>
 * <p/>
 * &nbsp;&nbsp; from the end of tag in which this frame resides to the first byte of<br> &nbsp;&nbsp; the header in the
 * next. This field may be omitted. Embedded tags are<br> &nbsp;&nbsp; generally not recommended since this could render
 * unpredictable<br> &nbsp;&nbsp; behaviour from present software/hardware.</p>
 * <p/>
 * <p>&nbsp;&nbsp; For applications like streaming audio it might be an idea to embed<br> &nbsp;&nbsp; tags into the
 * audio stream though. If the clients connects to<br> &nbsp;&nbsp; individual connections like HTTP and there is a
 * possibility to begin<br> &nbsp;&nbsp; every transmission with a tag, then this tag should include a<br> &nbsp;&nbsp;
 * 'recommended buffer size' frame. If the client is connected to a<br>
 * <p/>
 * &nbsp;&nbsp; arbitrary point in the stream, such as radio or multicast, then the<br> &nbsp;&nbsp; 'recommended buffer
 * size' frame SHOULD be included in every tag.</p>
 * <p/>
 * <p>&nbsp;&nbsp; The 'Buffer size' should be kept to a minimum. There may only be one<br> &nbsp;&nbsp;
 * &quot;RBUF&quot; frame in each tag.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Recommended buffer size', ID: &quot;RBUF&quot;&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Buffer size&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $xx xx xx<br> &nbsp;&nbsp;&nbsp;&nbsp; Embedded info flag&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; %0000000x<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Offset to next tag&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx xx xx xx<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyRBUF extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyRBUF object.
     */
    public FrameBodyRBUF() {
        super();
    }

    /**
     * Creates a new FrameBodyRBUF object.
     */
    public FrameBodyRBUF(final FrameBodyRBUF body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyRBUF object.
     */
    public FrameBodyRBUF(final byte bufferSize, final boolean embeddedInfoFlag, final byte offsetToNextTag) {
        setObject("Buffer Size", new Byte(bufferSize));
        setObject("Embedded Info Flag", new Boolean(embeddedInfoFlag));
        setObject("Offset to Next Flag", new Byte(offsetToNextTag));
    }

    /**
     * Creates a new FrameBodyRBUF object.
     */
    public FrameBodyRBUF(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public String getIdentifier() {
        return "RBUF";
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberFixedLength("Buffer Size", 3));
        appendToObjectList(new ObjectBooleanByte("Embedded Info Flag", (byte) 1));
        appendToObjectList(new ObjectNumberFixedLength("Offset to Next Tag", 4));
    }
}