package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectByteArraySizeTerminated;
import org.farng.mp3.object.ObjectNumberHashMap;
import org.farng.mp3.object.ObjectStringNullTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.15. Attached picture</h3>
 * <p/>
 * <p class=t> This frame contains a picture directly related to the audio file. Image format is preferably "<a
 * href="#png">PNG</a>" or "<a href="#jfif">JPG</a>". <i>Since JPG has the best lossy compression and PNG the best
 * lossless compression and both are free I don't see much room for other formats. I didn't want to forbid other formats
 * though, since there might pop up better ones in the future. Some people will probably think it is neat with ICO
 * pictures as well.</i> Description is a short description of the picture, represented as a terminated textstring. The
 * description has a maximum length of 64 characters, but may be empty. There may be several pictures attached to one
 * file, each in their individual "PIC" frame, but only one with the same content descriptor. There may only be one
 * picture with the picture type declared as picture type $01 and $02 respectively. There is a possibility to put only a
 * link to the image file by using the 'image format' "--&gt;" and having a complete URL instead of picture data. The
 * use of linked files should however be used restrictively since there is the risk of separation of files.
 * <p/>
 * </p>
 * <p/>
 * <p><center> <table border=0> <tr><td nowrap>Attached picture</td><td rowspan=7>&nbsp;</td><td
 * width="100%">"PIC"</td></tr> <tr><td>Frame size</td><td>$xx xx xx</td></tr> <tr><td>Text
 * encoding</td><td>$xx</td></tr>
 * <p/>
 * <tr><td>Image format</td><td>$xx xx xx</td></tr> <tr><td>Picture type</td><td>$xx</td></tr>
 * <tr><td>Description</td><td>&lt;textstring&gt; $00 (00)</td></tr> <tr><td>Picture data</td><td>&lt;binary
 * data&gt;</td></tr>
 * <p/>
 * </table> </center>
 * <p/>
 * <p><center> <table border=0> <tr valign=top><td rowspan=21 nowrap>Picture type:&nbsp;</td><td width="100%">$00 &nbsp;
 * Other</td></tr> <tr><td>$01 &nbsp; 32x32 pixels 'file icon' (<a href="#png">PNG</a> only)</td></tr>
 * <p/>
 * <tr><td>$02 &nbsp; Other file icon</td></tr> <tr><td>$03 &nbsp; Cover (front)</td></tr> <tr><td>$04 &nbsp; Cover
 * (back)</td></tr> <tr><td>$05 &nbsp; Leaflet page</td></tr>
 * <p/>
 * <tr><td>$06 &nbsp; Media (e.g. lable side of CD)</td></tr> <tr><td>$07 &nbsp; Lead artist/lead
 * performer/soloist</td></tr> <tr><td>$08 &nbsp; Artist/performer</td></tr> <tr><td>$09 &nbsp; Conductor</td></tr>
 * <p/>
 * <tr><td>$0A &nbsp; Band/Orchestra</td></tr> <tr><td>$0B &nbsp; Composer</td></tr> <tr><td>$0C &nbsp; Lyricist/text
 * writer</td></tr> <tr><td>$0D &nbsp; Recording Location</td></tr>
 * <p/>
 * <tr><td>$0E &nbsp; During recording</td></tr> <tr><td>$0F &nbsp; During performance</td></tr> <tr><td>$10 &nbsp;
 * Movie/video screen capture</td></tr> <tr><td>$11 &nbsp; A bright coloured fish</td></tr>
 * <p/>
 * <tr><td>$12 &nbsp; Illustration</td></tr> <tr><td>$13 &nbsp; Band/artist logotype</td></tr> <tr><td>$14 &nbsp;
 * Publisher/Studio logotype</td></tr> </table>
 * <p/>
 * </center>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyPIC extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyPIC object.
     */
    public FrameBodyPIC() {
        super();
    }

    /**
     * Creates a new FrameBodyPIC object.
     */
    public FrameBodyPIC(final FrameBodyPIC body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyPIC object.
     */
    public FrameBodyPIC(final byte textEncoding,
                        final String imageFormat,
                        final byte pictureType,
                        final String description,
                        final byte[] data) {
        setObject("Text Encoding", new Byte(textEncoding));
        setObject("Image Format", imageFormat);
        setObject("Picture Type", new Byte(pictureType));
        setObject("Description", description);
        setObject("Picture Data", data);
    }

    /**
     * Creates a new FrameBodyPIC object.
     */
    public FrameBodyPIC(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public void setDescription(final String description) {
        setObject("Description", description);
    }

    public String getDescription() {
        return (String) getObject("Description");
    }

    public String getIdentifier() {
        return "PIC" + ((char) 0) + getDescription();
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberHashMap(ObjectNumberHashMap.TEXT_ENCODING, 1));
        appendToObjectList(new ObjectStringNullTerminated("Image Format"));
        appendToObjectList(new ObjectNumberHashMap(ObjectNumberHashMap.PICTURE_TYPE, 3));
        appendToObjectList(new ObjectStringNullTerminated("Description"));
        appendToObjectList(new ObjectByteArraySizeTerminated("Picture Data"));
    }
}