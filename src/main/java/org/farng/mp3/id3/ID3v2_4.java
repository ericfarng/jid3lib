package org.farng.mp3.id3;

import org.farng.mp3.AbstractMP3Tag;
import org.farng.mp3.InvalidTagException;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagConstant;
import org.farng.mp3.TagException;
import org.farng.mp3.TagNotFoundException;
import org.farng.mp3.filename.FilenameTag;
import org.farng.mp3.lyrics3.AbstractLyrics3;
import org.farng.mp3.lyrics3.Lyrics3v2;
import org.farng.mp3.lyrics3.Lyrics3v2Field;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

/**
 * <p>&nbsp;&nbsp; ID3v2 is a general tagging format for audio, which makes it possible<br> &nbsp;&nbsp; to store meta
 * data about the audio inside the audio file itself. The<br> &nbsp;&nbsp; ID3 tag described in this document is mainly
 * targeted at files<br> &nbsp;&nbsp; encoded with MPEG-1/2 layer I, MPEG-1/2 layer II, MPEG-1/2 layer III<br>
 * &nbsp;&nbsp; and MPEG-2.5, but may work with other types of encoded audio or as a<br> &nbsp;&nbsp; stand alone format
 * for audio meta data.</p>
 * <p/>
 * <p>&nbsp;&nbsp; ID3v2 is designed to be as flexible and expandable as possible to<br> &nbsp;&nbsp; meet new meta
 * information needs that might arise. To achieve that<br> &nbsp;&nbsp; ID3v2 is constructed as a container for several
 * information blocks,<br> &nbsp;&nbsp; called frames, whose format need not be known to the software that<br>
 * &nbsp;&nbsp; encounters them. At the start of every frame is an unique and<br> &nbsp;&nbsp; predefined identifier, a
 * size descriptor that allows software to skip<br> &nbsp;&nbsp; unknown frames and a flags field. The flags describes
 * encoding<br> &nbsp;&nbsp; details and if the frame should remain in the tag, should it be<br> &nbsp;&nbsp; unknown to
 * the software, if the file is altered.</p>
 * <p/>
 * <p>&nbsp;&nbsp; The bitorder in ID3v2 is most significant bit first (MSB). The<br> &nbsp;&nbsp; byteorder in
 * multibyte numbers is most significant byte first (e.g.<br> &nbsp;&nbsp; $12345678 would be encoded $12 34 56 78),
 * also known as big endian<br> &nbsp;&nbsp; and network byte order.</p>
 * <p/>
 * <p>&nbsp;&nbsp; Overall tag structure:</p> <table border="1"> <tr> <td width="100%" align="center"> <p
 * align="center">Header (10 bytes)</p> </td> </tr> <tr> <td width="100%" align="center">Extended Header (variable
 * length, OPTIONAL)</td> </tr> <tr> <td width="100%" align="center">Frames (variable length)</td> </tr> <tr> <td
 * width="100%" align="center">Padding (variable length, OPTIONAL)</td> </tr> <tr> <td width="100%"
 * align="center">Footer (10 bytes, OPTIONAL)</td> </tr> </table> <p>&nbsp;&nbsp; In general, padding and footer are
 * mutually exclusive. See details in<br> &nbsp;&nbsp; sections 3.3, 3.4 and 5.<br> </p> <a name="sec3.1"></a>
 * <p/>
 * <h3>3.1.&nbsp;&nbsp; ID3v2 header</h3>
 * <p/>
 * <p>&nbsp;&nbsp; The first part of the ID3v2 tag is the 10 byte tag header, laid out<br> &nbsp;&nbsp; as follows:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; ID3v2/file identifier&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &quot;ID3&quot;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; ID3v2 version&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $04 00<br> &nbsp;&nbsp;&nbsp;&nbsp; ID3v2 flags&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * %abcd0000<br> &nbsp;&nbsp;&nbsp;&nbsp; ID3v2 size&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * 4 * %0xxxxxxx</p>
 * <p/>
 * <p>&nbsp;&nbsp; The first three bytes of the tag are always &quot;ID3&quot;, to indicate that<br> &nbsp;&nbsp; this
 * is an ID3v2 tag, directly followed by the two version bytes. The<br> &nbsp;&nbsp; first byte of ID3v2 version is its
 * major version, while the second<br> &nbsp;&nbsp; byte is its revision number. In this case this is ID3v2.4.0. All<br>
 * &nbsp;&nbsp; revisions are backwards compatible while major versions are not. If<br> &nbsp;&nbsp; software with
 * ID3v2.4.0 and below support should encounter version<br> &nbsp;&nbsp; five or higher it should simply ignore the
 * whole tag. Version or<br> &nbsp;&nbsp; revision will never be $FF.</p>
 * <p/>
 * <p>&nbsp;&nbsp; The version is followed by the ID3v2 flags field, of which currently<br> &nbsp;&nbsp; four flags are
 * used.<br> </p>
 * <p/>
 * <p>&nbsp;&nbsp; a - Unsynchronisation</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; Bit 7 in the 'ID3v2 flags' indicates whether or not<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * unsynchronisation is applied on all frames (see section 6.1 for<br> &nbsp;&nbsp;&nbsp;&nbsp; details); a set bit
 * indicates usage.<br> </p>
 * <p/>
 * <p>&nbsp;&nbsp; b - Extended header</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; The second bit (bit 6) indicates whether or not the header is<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; followed by an extended header. The extended header is described in<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; section 3.2. A set bit indicates the presence of an extended<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * header.<br> </p>
 * <p/>
 * <p>&nbsp;&nbsp; c - Experimental indicator</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; The third bit (bit 5) is used as an 'experimental indicator'. This<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; flag SHALL always be set when the tag is in an experimental stage.<br> </p>
 * <p/>
 * <p>&nbsp;&nbsp; d - Footer present</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; Bit 4 indicates that a footer (section 3.4) is present at the very<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; end of the tag. A set bit indicates the presence of a footer.<br> </p>
 * <p/>
 * <p>&nbsp;&nbsp; All the other flags MUST be cleared. If one of these undefined flags<br> &nbsp;&nbsp; are set, the
 * tag might not be readable for a parser that does not<br> &nbsp;&nbsp; know the flags function.</p>
 * <p/>
 * <p>&nbsp;&nbsp; The ID3v2 tag size is stored as a 32 bit synchsafe integer (section<br> &nbsp;&nbsp; 6.2), making a
 * total of 28 effective bits (representing up to 256MB).</p>
 * <p/>
 * <p>&nbsp;&nbsp; The ID3v2 tag size is the sum of the byte length of the extended<br> &nbsp;&nbsp; header, the padding
 * and the frames after unsynchronisation. If a<br> &nbsp;&nbsp; footer is present this equals to ('total size' - 20)
 * bytes, otherwise<br> &nbsp;&nbsp; ('total size' - 10) bytes.</p>
 * <p/>
 * <p>&nbsp;&nbsp; An ID3v2 tag can be detected with the following pattern:<br> &nbsp;&nbsp;&nbsp;&nbsp; $49 44 33 yy yy
 * xx zz zz zz zz<br> &nbsp;&nbsp; Where yy is less than $FF, xx is the 'flags' byte and zz is less than<br>
 * &nbsp;&nbsp; $80.<br> </p> <a name="sec3.2"></a>
 * <p/>
 * <h3>3.2. Extended header</h3>
 * <p/>
 * <p>&nbsp;&nbsp; The extended header contains information that can provide further<br> &nbsp;&nbsp; insight in the
 * structure of the tag, but is not vital to the correct<br> &nbsp;&nbsp; parsing of the tag information; hence the
 * extended header is<br> &nbsp;&nbsp; optional.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; Extended header size&nbsp;&nbsp; 4 * %0xxxxxxx<br> &nbsp;&nbsp;&nbsp;&nbsp; Number of
 * flag bytes&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $01<br> &nbsp;&nbsp;&nbsp;&nbsp; Extended
 * Flags&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx</p>
 * <p/>
 * <p>&nbsp;&nbsp; Where the 'Extended header size' is the size of the whole extended<br> &nbsp;&nbsp; header, stored as
 * a 32 bit synchsafe integer. An extended header can<br> &nbsp;&nbsp; thus never have a size of fewer than six
 * bytes.</p>
 * <p/>
 * <p>&nbsp;&nbsp; The extended flags field, with its size described by 'number of flag<br> &nbsp;&nbsp; bytes', is
 * defined as:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; %0bcd0000</p>
 * <p/>
 * <p>&nbsp;&nbsp; Each flag that is set in the extended header has data attached, which<br> &nbsp;&nbsp; comes in the
 * order in which the flags are encountered (i.e. the data<br> &nbsp;&nbsp; for flag 'b' comes before the data for flag
 * 'c'). Unset flags cannot<br> &nbsp;&nbsp; have any attached data. All unknown flags MUST be unset and their<br>
 * &nbsp;&nbsp; corresponding data removed when a tag is modified.</p>
 * <p/>
 * <p>&nbsp;&nbsp; Every set flag's data starts with a length byte, which contains a<br> &nbsp;&nbsp; value between 0
 * and 127 ($00 - $7f), followed by data that has the<br> &nbsp;&nbsp; field length indicated by the length byte. If a
 * flag has no attached<br> &nbsp;&nbsp; data, the value $00 is used as length byte.<br> </p>
 * <p/>
 * <p>&nbsp;&nbsp; b - Tag is an update</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; If this flag is set, the present tag is an update of a tag found<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; earlier in the present file or stream. If frames defined as unique<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; are found in the present tag, they are to override any<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * corresponding ones found in the earlier tag. This flag has no<br> &nbsp;&nbsp;&nbsp;&nbsp; corresponding data.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Flag data length&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $00</p>
 * <p/>
 * <p>&nbsp;&nbsp; c - CRC data present</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; If this flag is set, a CRC-32 [ISO-3309] data is included in the<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; extended header. The CRC is calculated on all the data between the<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; header and footer as indicated by the header's tag length field,<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; minus the extended header. Note that this includes the padding (if<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; there is any), but excludes the footer. The CRC-32 is stored as an<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; 35 bit synchsafe integer, leaving the upper four bits always<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * zeroed.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Flag data length&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $05<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Total frame CRC&nbsp;&nbsp;&nbsp; 5 * %0xxxxxxx</p>
 * <p/>
 * <p>&nbsp;&nbsp; d - Tag restrictions</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; For some applications it might be desired to restrict a tag in more<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; ways than imposed by the ID3v2 specification. Note that the<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * presence of these restrictions does not affect how the tag is<br> &nbsp;&nbsp;&nbsp;&nbsp; decoded, merely how it was
 * restricted before encoding. If this flag<br> &nbsp;&nbsp;&nbsp;&nbsp; is set the tag is restricted as follows:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Flag data length&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $01<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Restrictions&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * %ppqrrstt</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; p - Tag size restrictions</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00&nbsp;&nbsp; No more than 128 frames and 1 MB total tag size.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 01&nbsp;&nbsp; No more than 64 frames and 128 KB total tag size.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 10&nbsp;&nbsp; No more than 32 frames and 40 KB total tag size.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 11&nbsp;&nbsp; No more than 32 frames and 4 KB total tag size.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; q - Text encoding restrictions</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 0&nbsp;&nbsp;&nbsp; No restrictions<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * 1&nbsp;&nbsp;&nbsp; Strings are only encoded with ISO-8859-1 [ISO-8859-1] or<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; UTF-8 [UTF-8].</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; r - Text fields size restrictions</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00&nbsp;&nbsp; No restrictions<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * 01&nbsp;&nbsp; No string is longer than 1024 characters.<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 10&nbsp;&nbsp; No
 * string is longer than 128 characters.<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 11&nbsp;&nbsp; No string is longer
 * than 30 characters.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Note that nothing is said about how many bytes is used to<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; represent those characters, since it is encoding dependent. If a<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; text frame consists of more than one string, the sum of the<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; strungs is restricted as stated.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; s - Image encoding restrictions</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 0&nbsp;&nbsp; No restrictions<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * 1&nbsp;&nbsp; Images are encoded only with PNG [PNG] or JPEG [JFIF].</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; t - Image size restrictions</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 00&nbsp; No restrictions<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 01&nbsp;
 * All images are 256x256 pixels or smaller.<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 10&nbsp; All images are 64x64
 * pixels or smaller.<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 11&nbsp; All images are exactly 64x64 pixels, unless
 * required<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; otherwise.<br> </p> <a name="sec3.3"></a>
 * <p/>
 * <h3>3.3.&nbsp;&nbsp; Padding</h3>
 * <p/>
 * <p>&nbsp;&nbsp; It is OPTIONAL to include padding after the final frame (at the end<br> &nbsp;&nbsp; of the ID3 tag),
 * making the size of all the frames together smaller<br> &nbsp;&nbsp; than the size given in the tag header. A possible
 * purpose of this<br> &nbsp;&nbsp; padding is to allow for adding a few additional frames or enlarge<br> &nbsp;&nbsp;
 * existing frames within the tag without having to rewrite the entire<br> &nbsp;&nbsp; file. The value of the padding
 * bytes must be $00. A tag MUST NOT have<br> &nbsp;&nbsp; any padding between the frames or between the tag header and
 * the<br> &nbsp;&nbsp; frames. Furthermore it MUST NOT have any padding when a tag footer is<br> &nbsp;&nbsp; added to
 * the tag.<br> </p> <a name="sec3.4"></a>
 * <p/>
 * <h3>3.4.&nbsp;&nbsp; ID3v2 footer</h3>
 * <p/>
 * <p>&nbsp;&nbsp; To speed up the process of locating an ID3v2 tag when searching from<br> &nbsp;&nbsp; the end of a
 * file, a footer can be added to the tag. It is REQUIRED<br> &nbsp;&nbsp; to add a footer to an appended tag, i.e. a
 * tag located after all<br> &nbsp;&nbsp; audio data. The footer is a copy of the header, but with a different<br>
 * &nbsp;&nbsp; identifier.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; ID3v2 identifier&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * &quot;3DI&quot;<br> &nbsp;&nbsp;&nbsp;&nbsp; ID3v2 version&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $04 00<br> &nbsp;&nbsp;&nbsp;&nbsp; ID3v2 flags&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * %abcd0000<br> &nbsp;&nbsp;&nbsp;&nbsp; ID3v2 size&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * 4 * %0xxxxxxx<br> </p>
 * <p/>
 * <p>&nbsp;&nbsp; The default location of an ID3v2 tag is prepended to the audio so<br> &nbsp;&nbsp; that players can
 * benefit from the information when the data is<br> &nbsp;&nbsp; streamed. It is however possible to append the tag, or
 * make a<br> &nbsp;&nbsp; prepend/append combination. When deciding upon where an unembedded<br> &nbsp;&nbsp; tag
 * should be located, the following order of preference SHOULD be<br> &nbsp;&nbsp; considered.<br> &nbsp;&nbsp; </p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 1. Prepend the tag.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 2. Prepend a tag with all vital information and add a second tag at <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; the end of the file, before tags from other tagging systems. The<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; first tag is required to have a SEEK frame.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3. Add a tag at the end of the file, before tags from other tagging<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; systems.<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </p>
 * <p/>
 * <p>&nbsp;&nbsp; In case 2 and 3 the tag can simply be appended if no other known tags<br> &nbsp;&nbsp; are present.
 * The suggested method to find ID3v2 tags are:<br> &nbsp;&nbsp; </p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 1. Look for a prepended tag using the pattern found in section 3.1.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 2. If a SEEK frame was found, use its values to guide further<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; searching.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; 3. Look for a tag footer, scanning from the back of the file.</p>
 * <p/>
 * <p>&nbsp;&nbsp; For every new tag that is found, the old tag should be discarded<br> &nbsp;&nbsp; unless the update
 * flag in the extended header (section 3.2) is set.<br> &nbsp;&nbsp; <br> </p> <a name="sec6"></a>
 * <p/>
 * <h3>6.&nbsp;&nbsp; Unsynchronisation</h3>
 * <p/>
 * <p>&nbsp;&nbsp; The only purpose of unsynchronisation is to make the ID3v2 tag as<br> &nbsp;&nbsp; compatible as
 * possible with existing software and hardware. There is<br> &nbsp;&nbsp; no use in 'unsynchronising' tags if the file
 * is only to be processed<br> &nbsp;&nbsp; only by ID3v2 aware software and hardware. Unsynchronisation is only<br>
 * &nbsp;&nbsp; useful with tags in MPEG 1/2 layer I, II and III, MPEG 2.5 and AAC<br> &nbsp;&nbsp; files.<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.6 $
 */
public class ID3v2_4 extends ID3v2_3 {

    protected boolean footer = false;
    protected boolean tagRestriction = false;
    protected boolean updateTag = false;
    protected byte imageEncodingRestriction = 0;
    protected byte imageSizeRestriction = 0;
    protected byte tagSizeRestriction = 0;
    protected byte textEncodingRestriction = 0;
    protected byte textFieldSizeRestriction = 0;

    /**
     * Creates a new ID3v2_4 object.
     */
    public ID3v2_4() {
        setMajorVersion((byte) 2);
        setRevision((byte) 4);
    }

//    /**
//     * Creates a new ID3v2_4 object.
//     */
//    public ID3v2_4(final ID3v2_4 copyObject) {
//        super(copyObject);
//        this.footer = copyObject.footer;
//        this.tagRestriction = copyObject.tagRestriction;
//        this.updateTag = copyObject.updateTag;
//        this.imageEncodingRestriction = copyObject.imageEncodingRestriction;
//        this.imageSizeRestriction = copyObject.imageSizeRestriction;
//        this.tagSizeRestriction = copyObject.tagSizeRestriction;
//        this.textEncodingRestriction = copyObject.textEncodingRestriction;
//        this.textFieldSizeRestriction = copyObject.textFieldSizeRestriction;
//    }

    /**
     * Creates a new ID3v2_4 object.
     */
    public ID3v2_4(final AbstractMP3Tag mp3tag) {
        if (mp3tag != null) {
            // if we get a tag, we want to convert to id3v2_4
            // both id3v1 and lyrics3 convert to this type
            // id3v1 needs to convert to id3v2_4 before converting to lyrics3
            if (mp3tag instanceof AbstractID3v2) {
                copyFromID3v2Tag((AbstractID3v2) mp3tag);
            } else if (mp3tag instanceof ID3v1) {
                // convert id3v1 tags.
                final ID3v1 id3tag = (ID3v1) mp3tag;
                ID3v2_4Frame newFrame;
                AbstractID3v2FrameBody newBody;
                if (id3tag.title.length() > 0) {
                    newBody = new FrameBodyTIT2((byte) 0, id3tag.title);
                    newFrame = new ID3v2_4Frame(false, false, false, false, false, false, newBody);
                    this.setFrame(newFrame);
                }
                if (id3tag.artist.length() > 0) {
                    newBody = new FrameBodyTPE1((byte) 0, id3tag.artist);
                    newFrame = new ID3v2_4Frame(false, false, false, false, false, false, newBody);
                    this.setFrame(newFrame);
                }
                if (id3tag.album.length() > 0) {
                    newBody = new FrameBodyTALB((byte) 0, id3tag.album);
                    newFrame = new ID3v2_4Frame(false, false, false, false, false, false, newBody);
                    this.setFrame(newFrame);
                }
                if (id3tag.year.length() > 0) {
                    newBody = new FrameBodyTDRL((byte) 0, id3tag.year);
                    newFrame = new ID3v2_4Frame(false, false, false, false, false, false, newBody);
                    this.setFrame(newFrame);
                }
                if (id3tag.comment.length() > 0) {
                    newBody = new FrameBodyCOMM((byte) 0, "ENG", "", id3tag.comment);
                    newFrame = new ID3v2_4Frame(false, false, false, false, false, false, newBody);
                    this.setFrame(newFrame);
                }
                if (id3tag.genre >= 0) {
                    final String genre = "(" +
                                         Byte.toString(id3tag.genre) +
                                         ") " +
                                         TagConstant.genreIdToString.get(new Long(id3tag.genre));
                    newBody = new FrameBodyTCON((byte) 0, genre);
                    newFrame = new ID3v2_4Frame(false, false, false, false, false, false, newBody);
                    this.setFrame(newFrame);
                }
                if (mp3tag instanceof ID3v1_1) {
                    final ID3v1_1 id3tag2 = (ID3v1_1) mp3tag;
                    if (id3tag2.track > 0) {
                        newBody = new FrameBodyTRCK((byte) 0, Byte.toString(id3tag2.track));
                        newFrame = new ID3v2_4Frame(false, false, false, false, false, false, newBody);
                        this.setFrame(newFrame);
                    }
                }
            } else if (mp3tag instanceof AbstractLyrics3) {
                // put the conversion stuff in the individual frame code.
                final Lyrics3v2 lyric;
                if (mp3tag instanceof Lyrics3v2) {
                    lyric = new Lyrics3v2((Lyrics3v2) mp3tag);
                } else {
                    lyric = new Lyrics3v2(mp3tag);
                }
                final Iterator iterator = lyric.iterator();
                Lyrics3v2Field field;
                ID3v2_4Frame newFrame;
                while (iterator.hasNext()) {
                    try {
                        field = (Lyrics3v2Field) iterator.next();
                        newFrame = new ID3v2_4Frame(field);
                        this.setFrame(newFrame);
                    } catch (InvalidTagException ex) {
                    }
                }
            } else if (mp3tag instanceof FilenameTag) {
                copyFromID3v2Tag(((FilenameTag) mp3tag).getId3tag());
            }
        }
    }

    /**
     * Creates a new ID3v2_4 object.
     */
    public ID3v2_4(final RandomAccessFile file) throws TagException, IOException {
        this.read(file);
    }

    public String getIdentifier() {
        return "ID3v2.40";
    }

    public int getSize() {
        int size = 3 + 2 + 1 + 4;
        if (this.extended) {
            size += (4 + 1 + 1);
            if (this.updateTag) {
                size++;
            }
            if (this.crcDataFlag) {
                size += 5;
            }
            if (this.tagRestriction) {
                size += 2;
            }
        }
        final Iterator iterator = this.getFrameIterator();
        AbstractID3v2Frame frame;
        while (iterator.hasNext()) {
            frame = (AbstractID3v2Frame) iterator.next();
            size += frame.getSize();
        }
        return size;
    }

    public void append(final AbstractMP3Tag tag) {
        if (tag instanceof ID3v2_4) {
            this.updateTag = ((ID3v2_4) tag).updateTag;
            this.footer = ((ID3v2_4) tag).footer;
            this.tagRestriction = ((ID3v2_4) tag).tagRestriction;
            this.tagSizeRestriction = ((ID3v2_4) tag).tagSizeRestriction;
            this.textEncodingRestriction = ((ID3v2_4) tag).textEncodingRestriction;
            this.textFieldSizeRestriction = ((ID3v2_4) tag).textFieldSizeRestriction;
            this.imageEncodingRestriction = ((ID3v2_4) tag).imageEncodingRestriction;
            this.imageSizeRestriction = ((ID3v2_4) tag).imageSizeRestriction;
        }
        super.append(tag);
    }

    public boolean equals(final Object obj) {
        if ((obj instanceof ID3v2_4) == false) {
            return false;
        }
        final ID3v2_4 id3v2_4 = (ID3v2_4) obj;
        if (this.footer != id3v2_4.footer) {
            return false;
        }
        if (this.imageEncodingRestriction != id3v2_4.imageEncodingRestriction) {
            return false;
        }
        if (this.imageSizeRestriction != id3v2_4.imageSizeRestriction) {
            return false;
        }
        if (this.tagRestriction != id3v2_4.tagRestriction) {
            return false;
        }
        if (this.tagSizeRestriction != id3v2_4.tagSizeRestriction) {
            return false;
        }
        if (this.textEncodingRestriction != id3v2_4.textEncodingRestriction) {
            return false;
        }
        if (this.textFieldSizeRestriction != id3v2_4.textFieldSizeRestriction) {
            return false;
        }
        if (this.updateTag != id3v2_4.updateTag) {
            return false;
        }
        return super.equals(obj);
    }

    public void overwrite(final AbstractMP3Tag tag) {
        if (tag instanceof ID3v2_4) {
            this.updateTag = ((ID3v2_4) tag).updateTag;
            this.footer = ((ID3v2_4) tag).footer;
            this.tagRestriction = ((ID3v2_4) tag).tagRestriction;
            this.tagSizeRestriction = ((ID3v2_4) tag).tagSizeRestriction;
            this.textEncodingRestriction = ((ID3v2_4) tag).textEncodingRestriction;
            this.textFieldSizeRestriction = ((ID3v2_4) tag).textFieldSizeRestriction;
            this.imageEncodingRestriction = ((ID3v2_4) tag).imageEncodingRestriction;
            this.imageSizeRestriction = ((ID3v2_4) tag).imageSizeRestriction;
        }
        super.overwrite(tag);
    }

    public void read(final RandomAccessFile file) throws TagException, IOException {
        final int size;
        byte[] buffer = new byte[4];
        file.seek(0);
        if (seek(file) == false) {
            throw new TagNotFoundException(getIdentifier() + " tag not found");
        }

        // read the major and minor @version bytes & flag bytes
        file.read(buffer, 0, 3);
        if ((buffer[0] != 4) || (buffer[1] != 0)) {
            throw new TagNotFoundException(getIdentifier() + " tag not found");
        }
        setMajorVersion(buffer[0]);
        setRevision(buffer[1]);
        this.unsynchronization = (buffer[2] & TagConstant.MASK_V24_UNSYNCHRONIZATION) != 0;
        this.extended = (buffer[2] & TagConstant.MASK_V24_EXTENDED_HEADER) != 0;
        this.experimental = (buffer[2] & TagConstant.MASK_V24_EXPERIMENTAL) != 0;
        this.footer = (buffer[2] & TagConstant.MASK_V24_FOOTER_PRESENT) != 0;

        // read the size
        file.read(buffer, 0, 4);
        size = byteArrayToSize(buffer);
        final long filePointer = file.getFilePointer();
        if (this.extended) {
            // int is 4 bytes.
            final int extendedHeaderSize = file.readInt();

            // the extended header must be atleast 6 bytes
            if (extendedHeaderSize <= 6) {
                throw new InvalidTagException("Invalid Extended Header Size.");
            }
            final byte numberOfFlagBytes = file.readByte();

            // read the flag bytes
            file.read(buffer, 0, numberOfFlagBytes);
            this.updateTag = (buffer[0] & TagConstant.MASK_V24_TAG_UPDATE) != 0;
            this.crcDataFlag = (buffer[0] & TagConstant.MASK_V24_CRC_DATA_PRESENT) != 0;
            this.tagRestriction = (buffer[0] & TagConstant.MASK_V24_TAG_RESTRICTIONS) != 0;

            // read the length byte if the flag is set
            // this tag should always be zero but just in case
            // read this information.
            if (this.updateTag) {
                final int len = file.readByte();
                buffer = new byte[len];
                file.read(buffer, 0, len);
            }
            if (this.crcDataFlag) {
                // the CRC has a variable length
                final int len = file.readByte();
                buffer = new byte[len];
                file.read(buffer, 0, len);
                this.crcData = 0;
                for (int i = 0; i < len; i++) {
                    this.crcData <<= 8;
                    this.crcData += buffer[i];
                }
            }
            if (this.tagRestriction) {
                final int len = file.readByte();
                buffer = new byte[len];
                file.read(buffer, 0, len);
                this.tagSizeRestriction = (byte) ((buffer[0] & TagConstant.MASK_V24_TAG_SIZE_RESTRICTIONS) >> 6);
                this.textEncodingRestriction = (byte) ((buffer[0] & TagConstant.MASK_V24_TEXT_ENCODING_RESTRICTIONS) >>
                                                       5);
                this.textFieldSizeRestriction = (byte) ((buffer[0] & TagConstant
                        .MASK_V24_TEXT_FIELD_SIZE_RESTRICTIONS) >> 3);
                this.imageEncodingRestriction = (byte) ((buffer[0] & TagConstant.MASK_V24_IMAGE_ENCODING) >> 2);
                this.imageSizeRestriction = (byte) (buffer[0] & TagConstant.MASK_V24_IMAGE_SIZE_RESTRICTIONS);
            }
        }
        ID3v2_4Frame next;
        this.clearFrameMap();

        // read the frames
        this.setFileReadBytes(size);
        resetPaddingCounter();
        while ((file.getFilePointer() - filePointer) <= size) {
            try {
                next = new ID3v2_4Frame(file);
                final String id = next.getIdentifier();
                if (this.hasFrame(id)) {
                    this.appendDuplicateFrameId(id + "; ");
                    this.incrementDuplicateBytes(this.getFrame(id).getSize());
                }
                this.setFrame(next);
            } catch (InvalidTagException ex) {
                if (ex.getMessage().equals("Found empty frame")) {
                    this.incrementEmptyFrameBytes(10);
                } else {
                    this.incrementInvalidFrameBytes();
                }
            }
        }
        this.setPaddingSize(getPaddingCounter());

        /**
         * int newSize = this.getSize(); if ((this.padding + newSize - 10) !=
         * size) { System.out.println("WARNING: Tag sizes don't add up");
         * System.out.println("ID3v2.40 tag size : " + newSize);
         * System.out.println("ID3v2.40 padding : " + this.padding);
         * System.out.println("ID3v2.40 total : " + (this.padding + newSize));
         * System.out.println("ID3v2.40 file size: " + size); }
         */
    }

    public boolean seek(final RandomAccessFile file) throws IOException {
        final byte[] buffer = new byte[3];
        file.seek(0);

        // read the tag if it exists
        file.read(buffer, 0, 3);
        final String tag = new String(buffer, 0, 3);
        if (tag.equals("ID3") == false) {
            return false;
        }

        // read the major and minor @version number
        file.read(buffer, 0, 2);

        // read back the @version bytes so we can read and save them later
        file.seek(file.getFilePointer() - 2);
        return ((buffer[0] == 4) && (buffer[1] == 0));
    }

    public String toString() {
        final Iterator iterator = this.getFrameIterator();
        AbstractID3v2Frame frame;
        String str = getIdentifier() + " " + this.getSize() + "\n";
        str += ("compression              = " + this.compression + "\n");
        str += ("unsynchronization        = " + this.unsynchronization + "\n");
        str += ("crcData                  = " + this.crcData + "\n");
        str += ("crcDataFlag              = " + this.crcDataFlag + "\n");
        str += ("experimental             = " + this.experimental + "\n");
        str += ("extended                 = " + this.extended + "\n");
        str += ("paddingSize              = " + this.paddingSize + "\n");
        str += ("footer                   = " + this.footer + "\n");
        str += ("imageEncodingRestriction = " + this.imageEncodingRestriction + "\n");
        str += ("imageSizeRestriction     = " + this.imageSizeRestriction + "\n");
        str += ("tagRestriction           = " + this.tagRestriction + "\n");
        str += ("tagSizeRestriction       = " + this.tagSizeRestriction + "\n");
        str += ("textEncodingRestriction  = " + this.textEncodingRestriction + "\n");
        str += ("textFieldSizeRestriction = " + this.textFieldSizeRestriction + "\n");
        str += ("updateTag                = " + this.updateTag + "\n");
        while (iterator.hasNext()) {
            frame = (ID3v2_4Frame) iterator.next();
            str += (frame.toString() + "\n");
        }
        return str + "\n";
    }

    public void write(final AbstractMP3Tag tag) {
        if (tag instanceof ID3v2_4) {
            this.updateTag = ((ID3v2_4) tag).updateTag;
            this.footer = ((ID3v2_4) tag).footer;
            this.tagRestriction = ((ID3v2_4) tag).tagRestriction;
            this.tagSizeRestriction = ((ID3v2_4) tag).tagSizeRestriction;
            this.textEncodingRestriction = ((ID3v2_4) tag).textEncodingRestriction;
            this.textFieldSizeRestriction = ((ID3v2_4) tag).textFieldSizeRestriction;
            this.imageEncodingRestriction = ((ID3v2_4) tag).imageEncodingRestriction;
            this.imageSizeRestriction = ((ID3v2_4) tag).imageSizeRestriction;
        }
        super.write(tag);
    }

    public void write(final RandomAccessFile file) throws IOException {
        int size;
        final String str;
        final Iterator iterator;
        ID3v2_4Frame frame;
        final byte[] buffer = new byte[6];
        final MP3File mp3 = new MP3File();
        mp3.seekMP3Frame(file);
        final long mp3start = file.getFilePointer();
        file.seek(0);
        str = "ID3";
        for (int i = 0; i < str.length(); i++) {
            buffer[i] = (byte) str.charAt(i);
        }
        buffer[3] = 4;
        buffer[4] = 0;
        if (this.unsynchronization) {
            buffer[5] |= TagConstant.MASK_V24_UNSYNCHRONIZATION;
        }
        if (this.extended) {
            buffer[5] |= TagConstant.MASK_V24_EXTENDED_HEADER;
        }
        if (this.experimental) {
            buffer[5] |= TagConstant.MASK_V24_EXPERIMENTAL;
        }
        if (this.footer) {
            buffer[5] |= TagConstant.MASK_V24_FOOTER_PRESENT;
        }
        file.write(buffer);

        // write size
        file.write(sizeToByteArray((int) mp3start - 10));
        if (this.extended) {
            size = 6;
            if (this.updateTag) {
                size++;
            }
            if (this.crcDataFlag) {
                size += 5;
            }
            if (this.tagRestriction) {
                size += 2;
            }
            file.writeInt(size);
            file.writeByte(1); // always 1 byte of flags in this tag
            buffer[0] = 0;
            if (this.updateTag) {
                buffer[0] |= TagConstant.MASK_V24_TAG_UPDATE;
            }
            if (this.crcDataFlag) {
                buffer[0] |= TagConstant.MASK_V24_CRC_DATA_PRESENT;
            }
            if (this.tagRestriction) {
                buffer[0] |= TagConstant.MASK_V24_TAG_RESTRICTIONS;
            }
            file.writeByte(buffer[0]);
            if (this.updateTag) {
                file.writeByte(0);
            }

            // this can be variable length, but this is easier
            if (this.crcDataFlag) {
                file.writeByte(4);
                file.writeInt(this.crcData);
            }
            if (this.tagRestriction) {
                // todo we need to finish this
                file.writeByte(1);
                buffer[0] = (byte) 0;
                if (this.tagRestriction) {
                    buffer[0] |= TagConstant.MASK_V24_TAG_SIZE_RESTRICTIONS;
                }
                file.writeByte(this.tagSizeRestriction);
                file.writeByte(this.textEncodingRestriction);
                file.writeByte(this.textFieldSizeRestriction);
                file.writeByte(this.imageEncodingRestriction);
                file.writeByte(this.imageSizeRestriction);
                file.writeByte(buffer[0]);
            }
        }

        // write all frames
        iterator = this.getFrameIterator();
        while (iterator.hasNext()) {
            frame = (ID3v2_4Frame) iterator.next();
            frame.write(file);
        }
    }

    private void copyFromID3v2Tag(final AbstractID3v2 mp3tag) {
        // if the tag is id3v2_4
        if (mp3tag instanceof ID3v2_4) {
            final ID3v2_4 tag = (ID3v2_4) mp3tag;
            this.footer = tag.footer;
            this.tagRestriction = tag.tagRestriction;
            this.updateTag = tag.updateTag;
            this.imageEncodingRestriction = tag.imageEncodingRestriction;
            this.imageSizeRestriction = tag.imageSizeRestriction;
            this.tagSizeRestriction = tag.tagSizeRestriction;
            this.textEncodingRestriction = tag.textEncodingRestriction;
            this.textFieldSizeRestriction = tag.textFieldSizeRestriction;
        }
        if (mp3tag instanceof ID3v2_3) {
            // and id3v2_4 tag is an instance of id3v2_3 also ...
            final ID3v2_3 id3tag = (ID3v2_3) mp3tag;
            this.extended = id3tag.extended;
            this.experimental = id3tag.experimental;
            this.crcDataFlag = id3tag.crcDataFlag;
            this.crcData = id3tag.crcData;
            this.paddingSize = id3tag.paddingSize;
        }
        if (mp3tag instanceof ID3v2_2) {
            final ID3v2_2 id3tag = (ID3v2_2) mp3tag;
            this.compression = id3tag.compression;
            this.unsynchronization = id3tag.unsynchronization;
        }
        final AbstractID3v2 id3tag = mp3tag;
        final Iterator iterator = id3tag.getFrameIterator();
        AbstractID3v2Frame frame;
        ID3v2_4Frame newFrame;
        while (iterator.hasNext()) {
            frame = (AbstractID3v2Frame) iterator.next();
            newFrame = new ID3v2_4Frame(frame);
            this.setFrame(newFrame);
        }
    }

    public String getYearReleased() {
        String text = "";
        AbstractID3v2Frame frame = getFrame("TDRL");
        if (frame != null) {
            FrameBodyTDRL body = (FrameBodyTDRL) frame.getBody();
            text = body.getText();
        }
        return text.trim();
    }

    public void setYearReleased(String yearReleased) {
        AbstractID3v2Frame field = getFrame("TDRL");
        if (field == null) {
            field = new ID3v2_4Frame(new FrameBodyTDRL((byte) 0, yearReleased));
            setFrame(field);
        } else {
            ((FrameBodyTDRL) field.getBody()).setText(yearReleased);
        }
    }


    protected AbstractID3v2Frame createEmptyFrame() {
        return new ID3v2_4Frame();
    }

}