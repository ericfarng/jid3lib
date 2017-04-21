package org.farng.mp3;

import org.farng.mp3.filename.FilenameTag;
import org.farng.mp3.filename.FilenameTagBuilder;
import org.farng.mp3.id3.AbstractID3v2;
import org.farng.mp3.id3.AbstractID3v2Frame;
import org.farng.mp3.id3.ID3v1;
import org.farng.mp3.id3.ID3v1_1;
import org.farng.mp3.id3.ID3v2_2;
import org.farng.mp3.id3.ID3v2_3;
import org.farng.mp3.id3.ID3v2_4;
import org.farng.mp3.lyrics3.AbstractLyrics3;
import org.farng.mp3.lyrics3.Lyrics3v1;
import org.farng.mp3.lyrics3.Lyrics3v2;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <TABLE border=0> <TBODY> <TR> <TD class=h2>How is MP3 built?</TD></TR></TBODY></TABLE> <TABLE border=0> <TBODY> <TR
 * vAlign=top> <TD> <P>Most people with a little knowledge in MP3 files know that the sound is divided into smaller
 * parts and compressed with a psycoacoustic model. This smaller pieces of the audio is then put into something called
 * 'frames', which is a little datablock with a header. I'll focus on that header in this text.</P>
 * <p/>
 * <P>The header is 4 bytes, 32 bits, big and begins with something called sync. This sync is, at least according to the
 * MPEG standard, 12 set bits in a row. Some add-on standards made later uses 11 set bits and one cleared bit. The sync
 * is directly followed by a ID bit, indicating if the file is a MPEG-1 och MPEG-2 file. 0=MPEG-2 and 1=MPEG-1</P>
 * <p/>
 * <P>The layer is defined with the two layers bits. They are oddly defined as</P> <CENTER> <TABLE cellSpacing=0
 * cellPadding=2 border=1> <TBODY> <TR> <TD>0 0</TD> <TD>Not defined</TD></TR> <TR> <TD>0 1</TD> <TD>Layer III</TD></TR>
 * <TR> <TD>1 0</TD> <TD>Layer II</TD></TR> <TR> <TD>1 1</TD> <TD>Layer I</TD></TR></TBODY></TABLE> </CENTER>
 * <p/>
 * <P>With this information and the information in the bitrate field we can determine the bitrate of the audio (in
 * kbit/s) according to this table.</P> <CENTER> <TABLE cellSpacing=0 cellPadding=2 border=1> <TBODY> <TR>
 * <TD>Bitrate<BR>value</TD> <TD>MPEG-1,<BR>layer I</TD> <TD>MPEG-1,<BR>layer II</TD> <TD>MPEG-1,<BR>layer III</TD>
 * <TD>MPEG-2,<BR>layer I</TD> <TD>MPEG-2,<BR>layer II</TD> <TD>MPEG-2,<BR>layer III</TD></TR> <TR> <TD>0 0 0 0</TD>
 * <TD></TD> <TD></TD> <TD></TD> <TD></TD> <TD></TD> <TD></TD></TR> <TR> <TD>0 0 0 1</TD> <TD>32</TD> <TD>32</TD>
 * <TD>32</TD> <TD>32</TD> <TD>32</TD> <TD>8</TD></TR> <TR> <TD>0 0 1 0</TD> <TD>64</TD> <TD>48</TD> <TD>40</TD>
 * <TD>64</TD> <TD>48</TD> <TD>16</TD></TR> <TR> <TD>0 0 1 1</TD> <TD>96</TD> <TD>56</TD> <TD>48</TD> <TD>96</TD>
 * <TD>56</TD> <TD>24</TD></TR> <TR> <TD>0 1 0 0</TD> <TD>128</TD> <TD>64</TD> <TD>56</TD> <TD>128</TD> <TD>64</TD>
 * <TD>32</TD></TR> <TR> <TD>0 1 0 1</TD> <TD>160</TD> <TD>80</TD> <TD>64</TD> <TD>160</TD> <TD>80</TD> <TD>64</TD></TR>
 * <TR> <TD>0 1 1 0</TD> <TD>192</TD> <TD>96</TD> <TD>80</TD> <TD>192</TD> <TD>96</TD> <TD>80</TD></TR> <TR> <TD>0 1 1
 * 1</TD> <TD>224</TD> <TD>112</TD> <TD>96</TD> <TD>224</TD> <TD>112</TD> <TD>56</TD></TR> <TR> <TD>1 0 0 0</TD>
 * <TD>256</TD> <TD>128</TD> <TD>112</TD> <TD>256</TD> <TD>128</TD> <TD>64</TD></TR> <TR> <TD>1 0 0 1</TD> <TD>288</TD>
 * <TD>160</TD> <TD>128</TD> <TD>288</TD> <TD>160</TD> <TD>128</TD></TR> <TR> <TD>1 0 1 0</TD> <TD>320</TD> <TD>192</TD>
 * <TD>160</TD> <TD>320</TD> <TD>192</TD> <TD>160</TD></TR> <TR> <TD>1 0 1 1</TD> <TD>352</TD> <TD>224</TD> <TD>192</TD>
 * <TD>352</TD> <TD>224</TD> <TD>112</TD></TR> <TR> <TD>1 1 0 0</TD> <TD>384</TD> <TD>256</TD> <TD>224</TD> <TD>384</TD>
 * <TD>256</TD> <TD>128</TD></TR> <TR> <TD>1 1 0 1</TD> <TD>416</TD> <TD>320</TD> <TD>256</TD> <TD>416</TD> <TD>320</TD>
 * <TD>256</TD></TR> <TR> <TD>1 1 1 0</TD> <TD>448</TD> <TD>384</TD> <TD>320</TD> <TD>448</TD> <TD>384</TD>
 * <TD>320</TD></TR> <TR> <TD>1 1 1 1</TD> <TD></TD> <TD></TD> <TD></TD> <TD></TD> <TD></TD>
 * <TD></TD></TR></TBODY></TABLE> </CENTER>
 * <p/>
 * <P>The sample rate is described in the frequency field. These values is dependent of which MPEG standard is used
 * according to the following table.</P> <CENTER> <TABLE cellSpacing=0 cellPadding=2 border=1> <TBODY> <TR>
 * <TD>Frequency<BR>value</TD> <TD>MPEG-1</TD> <TD>MPEG-2</TD></TR> <TR> <TD>0 0</TD> <TD>44100 Hz</TD> <TD>22050
 * Hz</TD></TR> <TR> <TD>0 1</TD> <TD>48000 Hz</TD> <TD>24000 Hz</TD></TR> <TR> <TD>1 0</TD> <TD>32000 Hz</TD> <TD>16000
 * Hz</TD></TR> <TR> <TD>1 1</TD> <TD></TD> <TD></TD></TR></TBODY></TABLE> </CENTER>
 * <p/>
 * <P>Three bits is not needed in the decoding process at all. These are the copyright bit, original home bit and the
 * private bit. The copyright has the same meaning as the copyright bit on CDs and DAT tapes, i.e. telling that it is
 * illegal to copy the contents if the bit is set. The original home bit indicates, if set, that the frame is located on
 * its original media. No one seems to know what the privat bit is good for.
 * <p/>
 * <p/>
 * <p/>
 * <P>If the protection bit is NOT set then the frame header is followed by a 16 bit checksum, inserted before the audio
 * data. If the padding bit is set then the frame is padded with an extra byte. Knowing this the size of the complete
 * frame can be calculated with the following formula</P> <CENTER> <P>FrameSize = 144 * BitRate / SampleRate<BR>when the
 * padding bit is cleared and</P>
 * <p/>
 * <P>FrameSize = (144 * BitRate / SampleRate) + 1<BR>when the padding bit is set.
 * <p/>
 * <P></CENTER>
 * <p/>
 * <P>The frameSize is of course an integer. If for an example BitRate=128000, SampleRate=44100 and the padding bit is
 * cleared, then the FrameSize = 144 * 128000 / 44100 = 417
 * <p/>
 * <p/>
 * <p/>
 * <P>The mode field is used to tell which sort of stereo/mono encoding that has been used. The purpose of the mode
 * extension field is different for different layers, but I really don't know exactly what it's for.</P> <CENTER> <TABLE
 * cellSpacing=0 cellPadding=2 border=1> <TBODY> <TR> <TD>Mode value</TD> <TD>mode</TD></TR> <TR> <TD>0 0</TD>
 * <TD>Stereo</TD></TR> <TR> <TD>0 1</TD> <TD>Joint stereo</TD></TR> <TR> <TD>1 0</TD> <TD>Dual channel</TD></TR> <TR>
 * <TD>1 1</TD> <TD>Mono</TD></TR></TBODY></TABLE> </CENTER>
 * <p/>
 * <P>The last field is the emphasis field. It is used to sort of 're-equalize' the sound after a Dolby-like noise
 * supression. This is not very used and will probably never be. The following noise supression model is used</P>
 * <CENTER> <TABLE cellSpacing=0 cellPadding=2 border=1> <TBODY> <TR> <TD>Emphasis value</TD> <TD>Emphasis
 * method</TD></TR> <TR> <TD>0 0</TD> <TD>none</TD></TR> <TR> <TD>0 1</TD> <TD>50/15ms</TD></TR> <TR> <TD>1 0</TD>
 * <TD></TD></TR> <TR> <TD>1 1</TD> <TD>CCITT j.17</TD></TR></TBODY></TABLE> </CENTER></TD> </TR></TBODY></TABLE>
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class MP3File {

    /**
     * the ID3v2 tag that this file contains.
     */
    private AbstractID3v2 id3v2tag;
    /**
     * the Lyrics3 tag that this file contains.
     */
    private AbstractLyrics3 lyrics3tag;
    /**
     * the mp3 file that this instance represents. This value can be null. This value is also used for any methods that
     * are called without a file argument
     */
    private File mp3file;
    /**
     * the ID3v2_4 tag that represents the parsed filename.
     */
    private FilenameTag filenameTag;
    /**
     * the ID3v1 tag that this file contains.
     */
    private ID3v1 id3v1tag;
    /**
     * value read from the MP3 Frame header
     */
    private boolean copyProtected;
    /**
     * value read from the MP3 Frame header
     */
    private boolean home;
    /**
     * value read from the MP3 Frame header
     */
    private boolean padding;
    /**
     * value read from the MP3 Frame header
     */
    private boolean privacy;
    /**
     * value read from the MP3 Frame header
     */
    private boolean protection;
    /**
     * value read from the MP3 Frame header
     */
    private boolean variableBitRate;
    /**
     * value read from the MP3 Frame header
     */
    private byte emphasis;
    /**
     * value read from the MP3 Frame header
     */
    private byte layer;
    /**
     * value read from the MP3 Frame header
     */
    private byte mode;
    /**
     * value read from the MP3 Frame header
     */
    private byte modeExtension;
    /**
     * value read from the MP3 Frame header
     */
    private byte mpegVersion;
    /**
     * frequency determined from MP3 Version and frequency value read from the MP3 Frame header
     */
    private double frequency;
    /**
     * bitrate calculated from the frame MP3 Frame header
     */
    private int bitRate;

    /**
     * Creates a new empty MP3File object that is not associated with a specific file.
     */
    public MP3File() {
        super();
    }

    /**
     * Creates a new MP3File object.
     */
    public MP3File(final MP3File copyObject) {
        super();
        copyProtected = copyObject.copyProtected;
        home = copyObject.home;
        padding = copyObject.padding;
        privacy = copyObject.privacy;
        protection = copyObject.protection;
        variableBitRate = copyObject.variableBitRate;
        emphasis = copyObject.emphasis;
        layer = copyObject.layer;
        mode = copyObject.mode;
        modeExtension = copyObject.modeExtension;
        mpegVersion = copyObject.mpegVersion;
        frequency = copyObject.frequency;
        bitRate = copyObject.bitRate;
        mp3file = new File(copyObject.mp3file.getAbsolutePath());
        filenameTag = new FilenameTag(copyObject.filenameTag);
        id3v2tag = (AbstractID3v2) TagUtility.copyObject(copyObject.id3v2tag);
        lyrics3tag = (AbstractLyrics3) TagUtility.copyObject(copyObject.lyrics3tag);
        id3v1tag = (ID3v1) TagUtility.copyObject(copyObject.id3v1tag);
    }

    /**
     * Creates a new MP3File object and parse the tag from the given filename.
     *
     * @param filename MP3 file
     *
     * @throws IOException  on any I/O error
     * @throws TagException on any exception generated by this library.
     */
    public MP3File(final String filename) throws IOException, TagException {
        this(new File(filename));
    }

    /**
     * Creates a new MP3File object and parse the tag from the given file Object.
     *
     * @param file MP3 file
     *
     * @throws IOException  on any I/O error
     * @throws TagException on any exception generated by this library.
     */
    public MP3File(final File file) throws IOException, TagException {
        this(file, true);
    }

    /**
     * Creates a new MP3File object and parse the tag from the given file Object.
     *
     * @param file      MP3 file
     * @param writeable open in read (false) or read-write (true) mode
     *
     * @throws IOException  on any I/O error
     * @throws TagException on any exception generated by this library.
     */
    public MP3File(final File file, final boolean writeable) throws IOException, TagException {
        super();
        mp3file = file;
        final RandomAccessFile newFile = new RandomAccessFile(file, writeable ? "rw" : "r");
        try {
            id3v1tag = new ID3v1_1(newFile);
        } catch (TagNotFoundException ex) {
            // tag might be different version
        }
        try {
            if (id3v1tag == null) {
                id3v1tag = new ID3v1(newFile);
            }
        } catch (TagNotFoundException ex) {
            // ok if it's null
        }
        try {
            id3v2tag = new ID3v2_4(newFile);
        } catch (TagNotFoundException ex) {
            // maybe different version
        }
        try {
            if (id3v2tag == null) {
                id3v2tag = new ID3v2_3(newFile);
            }
        } catch (TagNotFoundException ex) {
            // maybe a different version
        }
        try {
            if (id3v2tag == null) {
                id3v2tag = new ID3v2_2(newFile);
            }
        } catch (TagNotFoundException ex) {
            // it's ok to be null
        }
        try {
            lyrics3tag = new Lyrics3v2(newFile);
        } catch (TagNotFoundException ex) {
            // maybe a different version
        }
        try {
            if (lyrics3tag == null) {
                lyrics3tag = new Lyrics3v1(newFile);
            }
        } catch (TagNotFoundException ex) {
            //it's ok to be null
        }
        newFile.close();
        try {
            filenameTag = FilenameTagBuilder.createFilenameTagFromMP3File(this);
        } catch (Exception ex) {
            throw new TagException("Unable to create FilenameTag", ex);
        }
    }

    public int getBitRate() {
        return bitRate;
    }

    public boolean isCopyProtected() {
        return copyProtected;
    }

    public byte getEmphasis() {
        return emphasis;
    }

    /**
     * Sets the filename tag for this MP3 File. Refer to <code>TagUtilities.parseFileName</code> and
     * <code>TagUtilities.createID3v2Tag</code> for more information about parsing file names into <code>ID3v2_4</code>
     * objects.
     *
     * @param filenameTag parsed <code>ID3v2_4</code> filename tag
     */
    public void setFilenameTag(final FilenameTag filenameTag) {
        this.filenameTag = filenameTag;
    }

    /**
     * Sets the filename tag for this MP3 File. Refer to <code>TagUtilities.parseFileName</code> and
     * <code>TagUtilities.createID3v2Tag</code> for more information about parsing file names into <code>ID3v2_4</code>
     * objects.
     *
     * @return parsed <code>ID3v2_4</code> filename tag
     */
    public FilenameTag getFilenameTag() {
        return filenameTag;
    }

    /**
     * Sets all four (id3v1, lyrics3, filename, id3v2) tags in this instance to the <code>frame</code> argument if the
     * tag exists. This method does not use the options inside the <code>tagOptions</code> object.
     *
     * @param frame frame to set / replace in all four tags.
     */
    //todo this method is very inefficient.
    public void setFrameAcrossTags(final AbstractID3v2Frame frame) {
        if (id3v1tag != null) {
            final ID3v2_4 id3v1 = new ID3v2_4(id3v1tag);
            id3v1.setFrame(frame);
            id3v1tag.overwrite(id3v1);
        }
        if (id3v2tag != null) {
            id3v2tag.setFrame(frame);
        }
        if (lyrics3tag != null) {
            final ID3v2_4 lyrics3 = new ID3v2_4(lyrics3tag);
            lyrics3.setFrame(frame);
            lyrics3tag = new Lyrics3v2(lyrics3);
        }
        if (filenameTag != null) {
            filenameTag.setFrame(frame);
        }
    }

    /**
     * Gets the frames from all four (id3v1, lyrics3, filename, id3v2) mp3 tags in this instance for each tag that
     * exists. This method does not use the options inside the <code>tagOptions</code> object.
     *
     * @param identifier ID3v2.4 Tag Frame Identifier.
     *
     * @return ArrayList of all instances of the desired frame. Each instance is returned as an
     *         <code>ID3v2_4Frame</code>. The nature of the code returns the array in a specific order, but this order
     *         is not guaranteed for future versions of this library.
     */
    //todo this method is very inefficient.
    public List getFrameAcrossTags(final String identifier) {
        if (identifier != null && identifier.length() > 0) {
            final List list = new ArrayList(32);
            Iterator iterator;
            if (id3v1tag != null) {
                final ID3v2_4 id3v1 = new ID3v2_4(id3v1tag);
                if (id3v1.hasFrameOfType(identifier)) {
                    iterator = id3v1.getFrameOfType(identifier);
                    while (iterator.hasNext()) {
                        list.add(iterator.next());
                    }
                }
            }
            if (id3v2tag != null) {
                if (id3v2tag.hasFrameOfType(identifier)) {
                    iterator = id3v2tag.getFrameOfType(identifier);
                    while (iterator.hasNext()) {
                        list.add(iterator.next());
                    }
                }
            }
            if (lyrics3tag != null) {
                final ID3v2_4 lyrics3 = new ID3v2_4(lyrics3tag);
                if (lyrics3.hasFrameOfType(identifier)) {
                    iterator = lyrics3.getFrameOfType(identifier);
                    while (iterator.hasNext()) {
                        list.add(iterator.next());
                    }
                }
            }
            if (filenameTag != null) {
                if (filenameTag.hasFrameOfType(identifier)) {
                    iterator = filenameTag.getFrameOfType(identifier);
                    while (iterator.hasNext()) {
                        list.add(iterator.next());
                    }
                }
            }
            return list;
        }
        return null;
    }

    public double getFrequency() {
        return frequency;
    }

    public boolean isHome() {
        return home;
    }

    /**
     * Sets the <code>ID3v1</code> tag for this object. A new <code>ID3v1_1</code> object is created from the argument
     * and then used here.
     *
     * @param mp3tag Any MP3Tag object can be used and will be converted into a new ID3v1_1 object.
     */
    public void setID3v1Tag(final AbstractMP3Tag mp3tag) {
        id3v1tag = new ID3v1_1(mp3tag);
    }

    public void setID3v1Tag(final ID3v1 id3v1tag) {
        this.id3v1tag = id3v1tag;
    }

    /**
     * Returns the <code>ID3v1</code> tag for this object.
     *
     * @return the <code>ID3v1</code> tag for this object
     */
    public ID3v1 getID3v1Tag() {
        return id3v1tag;
    }

    /**
     * Sets the <code>ID3v2</code> tag for this object. A new <code>ID3v2_4</code> object is created from the argument
     * and then used here.
     *
     * @param mp3tag Any MP3Tag object can be used and will be converted into a new ID3v2_4 object.
     */
    public void setID3v2Tag(final AbstractMP3Tag mp3tag) {
        id3v2tag = new ID3v2_4(mp3tag);
    }

    public void setID3v2Tag(final AbstractID3v2 id3v2tag) {
        this.id3v2tag = id3v2tag;
    }

    /**
     * Returns the <code>ID3v2</code> tag for this object.
     *
     * @return the <code>ID3v2</code> tag for this object
     */
    public AbstractID3v2 getID3v2Tag() {
        return id3v2tag;
    }

    public byte getLayer() {
        return layer;
    }

    /**
     * Sets the <code>Lyrics3</code> tag for this object. A new <code>Lyrics3v2</code> object is created from the
     * argument and then used here.
     *
     * @param mp3tag Any MP3Tag object can be used and will be converted into a new Lyrics3v2 object.
     */
    public void setLyrics3Tag(final AbstractMP3Tag mp3tag) {
        lyrics3tag = new Lyrics3v2(mp3tag);
    }

    public void setLyrics3Tag(final AbstractLyrics3 lyrics3tag) {
        this.lyrics3tag = lyrics3tag;
    }

    /**
     * Returns the <code>ID3v1</code> tag for this object.
     *
     * @return the <code>ID3v1</code> tag for this object
     */
    public AbstractLyrics3 getLyrics3Tag() {
        return lyrics3tag;
    }

    public byte getMode() {
        return mode;
    }

    public byte getModeExtension() {
        return modeExtension;
    }

    /**
     * Returns the byte position of the first MP3 Frame that this object refers to. This is the first byte of music data
     * and not the ID3 Tag Frame.
     *
     * @return the byte position of the first MP3 Frame
     *
     * @throws IOException           on any I/O error
     * @throws FileNotFoundException if the file exists but is a directory rather than a regular file or cannot be
     *                               opened for any other reason
     */
    public long getMp3StartByte() throws IOException, FileNotFoundException {
        return getMp3StartByte(mp3file);
    }

    /**
     * Returns the byte position of the first MP3 Frame that the <code>file</code> arguement refers to. This is the
     * first byte of music data and not the ID3 Tag Frame.
     *
     * @param file MP3 file to search
     *
     * @return the byte position of the first MP3 Frame
     *
     * @throws IOException           on any I/O error
     * @throws FileNotFoundException if the file exists but is a directory rather than a regular file or cannot be
     *                               opened for any other reason
     */
    public long getMp3StartByte(final File file) throws IOException, FileNotFoundException {
        RandomAccessFile rfile = null;
        long startByte = 0L;
        try {
            rfile = new RandomAccessFile(file, "r");
            seekMP3Frame(rfile);
            startByte = rfile.getFilePointer();
        } finally {
            if (rfile != null) {
                rfile.close();
            }
        }
        return startByte;
    }

    public void setMp3file(final File mp3file) {
        this.mp3file = mp3file;
    }

    public File getMp3file() {
        return mp3file;
    }

    public byte getMpegVersion() {
        return mpegVersion;
    }

    public boolean isPadding() {
        return padding;
    }

    public boolean isPrivacy() {
        return privacy;
    }

    public boolean isProtection() {
        return protection;
    }

    /**
     * Returns true if there are any unsynchronized tags in this object. A fragment is unsynchronized if it exists in
     * two or more tags but is not equal across all of them.
     *
     * @return true of any fragments are unsynchronized.
     */
    //todo there might be a faster way to do this, other than calling
    //getUnsynchronizedFragments()
    public boolean isUnsynchronized() {
        return getUnsynchronizedFragments().size() > 0;
    }

    /**
     * Returns a HashSet of unsynchronized fragments across all tags in this object. A fragment is unsynchronized if it
     * exists in two or more tags but is not equal across all of them.
     *
     * @return a HashSet of unsynchronized fragments
     */
    public Set getUnsynchronizedFragments() {
        final ID3v2_4 total = new ID3v2_4(id3v2tag);
        final Set set = new HashSet(32);
        total.append(id3v1tag);
        total.append(lyrics3tag);
        total.append(filenameTag);
        total.append(id3v2tag);
        final ID3v2_4 id3v1 = new ID3v2_4(id3v1tag);
        final ID3v2_4 lyrics3 = new ID3v2_4(lyrics3tag);
        final ID3v2_4 filename = new ID3v2_4(filenameTag);
        final AbstractID3v2 id3v2 = id3v2tag;
        final Iterator iterator = total.iterator();
        while (iterator.hasNext()) {
            final AbstractID3v2Frame frame = (AbstractID3v2Frame) iterator.next();
            final String identifier = frame.getIdentifier();
            if (id3v2 != null) {
                if (id3v2.hasFrame(identifier)) {
                    if (!id3v2.getFrame(identifier).isSubsetOf(frame)) {
                        set.add(identifier);
                    }
                }
            }
            if (id3v1.hasFrame(identifier)) {
                if (!id3v1.getFrame(identifier).isSubsetOf(frame)) {
                    set.add(identifier);
                }
            }
            if (lyrics3.hasFrame(identifier)) {
                if (!lyrics3.getFrame(identifier).isSubsetOf(frame)) {
                    set.add(identifier);
                }
            }
            if (filename.hasFrame(identifier)) {
                if (!filename.getFrame(identifier).isSubsetOf(frame)) {
                    set.add(identifier);
                }
            }
        }
        return set;
    }

    public void setVariableBitRate(final boolean variableBitRate) {
        this.variableBitRate = variableBitRate;
    }

    public boolean isVariableBitRate() {
        return variableBitRate;
    }

    /**
     * Adjust the lenght of the ID3v2 padding at the beginning of the MP3 file referred to in this object. The ID3v2
     * size will be calculated, then a new file will be created with enough size to fit the <code>ID3v2</code> tag in
     * this object. The old file will be deleted, and the new file renamed. All parameters will be taken from the
     * <code>tagOptions</code> object.
     *
     * @throws FileNotFoundException if the file exists but is a directory rather than a regular file or cannot be
     *                               opened for any other reason
     * @throws IOException           on any I/O error
     * @throws TagException          on any exception generated by this library.
     */
    public boolean adjustID3v2Padding() throws FileNotFoundException, IOException, TagException {
        return adjustID3v2Padding(TagOptionSingleton.getInstance().getId3v2PaddingSize(),
                                  TagOptionSingleton.getInstance().isId3v2PaddingWillShorten(),
                                  TagOptionSingleton.getInstance().isId3v2PaddingCopyTag(),
                                  mp3file);
    }

    /**
     * Adjust the length of the ID3v2 padding at the beginning of the MP3 file this object refers to. The ID3v2 size
     * will be calculated, then a new file will be created with enough size to fit the <code>ID3v2</code> tag. The old
     * file will be deleted, and the new file renamed.
     *
     * @param paddingSize  Initial padding size. This size is doubled until the ID3v2 tag will fit.
     * @param willShorten  if the newly calculated padding size is less than the padding length of the file, then news
     *                     the new shorter padding size if this is true.
     * @param copyID3v2Tag if true, write the <code>ID3v2</code> tag of this object into the file
     *
     * @throws FileNotFoundException if the file exists but is a directory rather than a regular file or cannot be
     *                               opened for any other reason
     * @throws IOException           on any I/O error
     * @throws TagException          on any exception generated by this library.
     */
    public boolean adjustID3v2Padding(final int paddingSize, final boolean willShorten, final boolean copyID3v2Tag)
            throws FileNotFoundException, IOException, TagException {
        return adjustID3v2Padding(paddingSize, willShorten, copyID3v2Tag, mp3file);
    }

    /**
     * Adjust the length of the ID3v2 padding at the beginning of the MP3 file this object refers to. The ID3v2 size
     * will be calculated, then a new file will be created with enough size to fit the <code>ID3v2</code> tag. The old
     * file will be deleted, and the new file renamed.
     *
     * @param paddingSize  Initial padding size. This size is doubled until the ID3v2 tag will fit. A paddingSize of
     *                     zero will create a padding length exactly equal to the tag size.
     * @param willShorten  Shorten the padding size by halves if the ID3v2 tag will fit
     * @param copyID3v2Tag if true, write the <code>ID3v2</code> tag of this object into the file
     * @param file         The file to adjust the padding length of
     *
     * @throws FileNotFoundException if the file exists but is a directory rather than a regular file or cannot be
     *                               opened for any other reason
     * @throws IOException           on any I/O error
     * @throws TagException          on any exception generated by this library.
     */
    public boolean adjustID3v2Padding(final int paddingSize,
                                      final boolean willShorten,
                                      final boolean copyID3v2Tag,
                                      final File file) throws FileNotFoundException, IOException, TagException {
        int id3v2TagSize = 0;
        final long mp3start = getMp3StartByte(file);
        long newPaddingSize = paddingSize;
        FileOutputStream outStream = null;
        FileInputStream inStream = null;
        File backupFile = null;
        File paddedFile = null;
        if (newPaddingSize < 0) {
            throw new TagException("Invalid paddingSize: " + newPaddingSize);
        }
        if (hasID3v2Tag()) {
            id3v2TagSize = getID3v2Tag().getSize();
        }
        if (newPaddingSize != 0) {
            // double padding size until it's large enough
            while (newPaddingSize < id3v2TagSize) {
                newPaddingSize *= TagOptionSingleton.getInstance().getId3v2PaddingMultiplier();
            }
        }
        if (newPaddingSize < mp3start && !willShorten) {
            return false;
        }
        if (newPaddingSize == mp3start) {
            return false;
        }
        try {
            // we first copy everything to a new file, then replace the original
            paddedFile = File.createTempFile("temp", ".mp3", file.getParentFile());
            outStream = new FileOutputStream(paddedFile);
            inStream = new FileInputStream(file);
            byte[] buffer;
            if (copyID3v2Tag == true) {
                // paddingSize < mp3start && willshorten == false
                // was already checked for outside of the try block.
                if ((newPaddingSize < mp3start) && willShorten) {
                    // copy the current tag
                    buffer = new byte[(int) newPaddingSize];
                    inStream.read(buffer, 0, buffer.length);
                    outStream.write(buffer, 0, buffer.length);
                    buffer = new byte[(int) (mp3start - newPaddingSize)];

                    // skip the rest of the tag that didn't fit
                    inStream.read(buffer, 0, buffer.length);

                    // paddingSize > mp3start
                } else {
                    // copy the current tag
                    buffer = new byte[(int) mp3start];
                    inStream.read(buffer, 0, buffer.length);
                    outStream.write(buffer, 0, buffer.length);

                    // add zeros for the rest of the padding
                    if (newPaddingSize - mp3start > 0) {
                        buffer = new byte[(int) (newPaddingSize - mp3start)];
                        outStream.write(buffer, 0, buffer.length);
                    }
                }
            } else {
                buffer = new byte[(int) newPaddingSize];

                // skip the tag
                inStream.skip(mp3start);

                // write zeros for the tag
                outStream.write(buffer, 0, buffer.length);
            }
            buffer = new byte[1024];
            int b = inStream.read(buffer, 0, buffer.length);
            while (b == 1024) {
                outStream.write(buffer, 0, buffer.length);
                b = inStream.read(buffer, 0, buffer.length);
            }
            if (b != -1) {
                outStream.write(buffer, 0, b);
            }
            backupFile = new File(file.getParentFile(), TagUtility.appendBeforeExtension(file.getName(), ".original"));
            TagUtility.copyFile(file, backupFile);
            if (backupFile.exists()) {
                backupFile.setLastModified(file.lastModified());
            } else {
                return false;
            }
            TagUtility.copyFile(paddedFile, file);
            return true;
        } finally {
            if (inStream != null) {
                inStream.getFD().sync();
                inStream.close();
            }
            if (outStream != null) {
                outStream.getFD().sync();
                outStream.close();
            }
            if ((backupFile != null) &&
                (TagOptionSingleton.getInstance().isOriginalSavedAfterAdjustingID3v2Padding() == false)) {
                backupFile.delete();
            }
            if (paddedFile != null) {
                paddedFile.delete();
            }
        }
    }

    public void delete(final AbstractMP3Tag mp3tag) throws FileNotFoundException, IOException {
        mp3tag.delete(new RandomAccessFile(mp3file, "rw"));
    }

    /**
     * Returns true if this object contains an filename pseudo-tag
     *
     * @return true if this object contains an filename pseudo-tag
     */
    public boolean hasFilenameTag() {
        return (filenameTag != null);
    }

    /**
     * Returns true if this object contains an <code>Id3v1</code> tag
     *
     * @return true if this object contains an <code>Id3v1</code> tag
     */
    public boolean hasID3v1Tag() {
        return (id3v1tag != null);
    }

    /**
     * Returns true if this object contains an <code>Id3v2</code> tag
     *
     * @return true if this object contains an <code>Id3v2</code> tag
     */
    public boolean hasID3v2Tag() {
        return (id3v2tag != null);
    }

    /**
     * Returns true if this object contains an <code>Lyrics3</code> tag
     *
     * @return true if this object contains an <code>Lyrics3</code> tag
     */
    public boolean hasLyrics3Tag() {
        return (lyrics3tag != null);
    }

    /**
     * Saves the tags in this object to the file referred to by this object. It will be saved as
     * TagConstants.MP3_FILE_SAVE_WRITE
     *
     * @throws IOException  on any I/O error
     * @throws TagException on any exception generated by this library.
     */
    public void save() throws IOException, TagException {
        save(mp3file, TagOptionSingleton.getInstance().getDefaultSaveMode());
    }

    /**
     * Saves the tags in this object to the file referred to by this object. It will be saved as
     * TagConstants.MP3_FILE_SAVE_WRITE
     *
     * @param saveMode write, overwrite, or append. Defined as <code>TagConstants.MP3_FILE_SAVE_WRITE
     *                 TagConstants.MP3_FILE_SAVE_OVERWRITE TagConstants.MP3_FILE_SAVE_APPEND </code>
     *
     * @throws IOException  on any I/O error
     * @throws TagException on any exception generated by this library.
     */
    public void save(final int saveMode) throws IOException, TagException {
        save(mp3file, saveMode);
    }

    /**
     * Saves the tags in this object to the file argument. It will be saved as TagConstants.MP3_FILE_SAVE_WRITE
     *
     * @param filename file to save the this object's tags to
     *
     * @throws IOException  on any I/O error
     * @throws TagException on any exception generated by this library.
     */
    public void save(final String filename) throws IOException, TagException {
        save(new File(filename), TagOptionSingleton.getInstance().getDefaultSaveMode());
    }

    /**
     * Saves the tags in this object to the file argument. It will be saved as TagConstants.MP3_FILE_SAVE_WRITE
     *
     * @param file file to save the this object's tags to
     *
     * @throws IOException  on any I/O error
     * @throws TagException on any exception generated by this library.
     */
    public void save(final File file) throws IOException, TagException {
        save(file, TagOptionSingleton.getInstance().getDefaultSaveMode());
    }

    /**
     * Saves the tags in this object to the file argument. It will be saved as TagConstants.MP3_FILE_SAVE_WRITE
     *
     * @param filename file to save the this object's tags to
     * @param saveMode write, overwrite, or append. Defined as <code>TagConstants.MP3_FILE_SAVE_WRITE
     *                 TagConstants.MP3_FILE_SAVE_OVERWRITE TagConstants.MP3_FILE_SAVE_APPEND </code>
     *
     * @throws IOException  on any I/O error
     * @throws TagException on any exception generated by this library.
     */
    public void save(final String filename, final int saveMode) throws IOException, TagException {
        save(new File(filename), saveMode);
    }

    /**
     * Saves the tags in this object to the file argument. It will be saved as TagConstants.MP3_FILE_SAVE_WRITE
     *
     * @param file     file to save the this object's tags to
     * @param saveMode write, overwrite, or append. Defined as <code>TagConstants.MP3_FILE_SAVE_WRITE
     *                 TagConstants.MP3_FILE_SAVE_OVERWRITE TagConstants.MP3_FILE_SAVE_APPEND </code>
     *
     * @throws IOException  on any I/O error
     * @throws TagException on any exception generated by this library.
     */
    public void save(final File file, final int saveMode) throws IOException, TagException {
        if ((saveMode < TagConstant.MP3_FILE_SAVE_FIRST) || (saveMode > TagConstant.MP3_FILE_SAVE_LAST)) {
            throw new TagException("Invalid Save Mode");
        }
        RandomAccessFile rfile = null;
        try {
            if (id3v2tag != null) {
                adjustID3v2Padding(TagOptionSingleton.getInstance().getId3v2PaddingSize(),
                                   TagOptionSingleton.getInstance().isId3v2PaddingWillShorten(),
                                   TagOptionSingleton.getInstance().isId3v2PaddingCopyTag(),
                                   file);
            }

            // we can't put these two if's together because
            // adjustid3v2padding needs all handles on the file closed;
            rfile = new RandomAccessFile(file, "rw");
            if (TagOptionSingleton.getInstance().isId3v2Save()) {
                if (id3v2tag == null) {
                    if (saveMode == TagConstant.MP3_FILE_SAVE_OVERWRITE) {
                        (new ID3v2_4()).delete(rfile);
                    }
                } else {
                    if (saveMode == TagConstant.MP3_FILE_SAVE_WRITE) {
                        id3v2tag.write(rfile);
                    } else if (saveMode == TagConstant.MP3_FILE_SAVE_APPEND) {
                        id3v2tag.append(rfile);
                    } else if (saveMode == TagConstant.MP3_FILE_SAVE_OVERWRITE) {
                        id3v2tag.overwrite(rfile);
                    }
                }
            }
            if (TagOptionSingleton.getInstance().isLyrics3Save()) {
                if (lyrics3tag == null) {
                    if (saveMode == TagConstant.MP3_FILE_SAVE_OVERWRITE) {
                        (new Lyrics3v2()).delete(rfile);
                    }
                } else {
                    if (saveMode == TagConstant.MP3_FILE_SAVE_WRITE) {
                        lyrics3tag.write(rfile);
                    } else if (saveMode == TagConstant.MP3_FILE_SAVE_APPEND) {
                        lyrics3tag.append(rfile);
                    } else if (saveMode == TagConstant.MP3_FILE_SAVE_OVERWRITE) {
                        lyrics3tag.overwrite(rfile);
                    }
                }
            }
            if (TagOptionSingleton.getInstance().isId3v1Save()) {
                if (id3v1tag == null) {
                    if (saveMode == TagConstant.MP3_FILE_SAVE_OVERWRITE) {
                        (new ID3v1()).delete(rfile);
                    }
                } else {
                    if (saveMode == TagConstant.MP3_FILE_SAVE_WRITE) {
                        id3v1tag.write(rfile);
                    } else if (saveMode == TagConstant.MP3_FILE_SAVE_APPEND) {
                        id3v1tag.append(rfile);
                    } else if (saveMode == TagConstant.MP3_FILE_SAVE_OVERWRITE) {
                        id3v1tag.overwrite(rfile);
                    }
                }
            }
            if (TagOptionSingleton.getInstance().isFilenameTagSave()) {
                if (filenameTag != null) {
                    if (saveMode == TagConstant.MP3_FILE_SAVE_WRITE) {
                        filenameTag.write(rfile);
                    } else if (saveMode == TagConstant.MP3_FILE_SAVE_APPEND) {
                        filenameTag.append(rfile);
                    } else if (saveMode == TagConstant.MP3_FILE_SAVE_OVERWRITE) {
                        filenameTag.overwrite(rfile);
                    }
                }
            }
        } finally {
            if (rfile != null) {
                rfile.close();
            }
        }
    }

    /**
     * Returns true if the first MP3 frame can be found for the MP3 file that this object refers to. This is the first
     * byte of music data and not the ID3 Tag Frame.
     *
     * @return true if the first MP3 frame can be found
     *
     * @throws IOException on any I/O error
     */
    public boolean seekMP3Frame() throws IOException {
        RandomAccessFile rfile = null;
        boolean found = false;
        try {
            rfile = new RandomAccessFile(mp3file, "r");
            found = seekMP3Frame(rfile);
        } finally {
            if (rfile != null) {
                rfile.close();
            }
        }
        return found;
    }

    /**
     * Returns true if the first MP3 frame can be found for the MP3 file argument. It tries to sync as many frame as
     * defined in <code>TagOptions.getNumberMP3SyncFrame</code> This is the first byte of music data and not the ID3 Tag
     * Frame.
     *
     * @param seekFile MP3 file to seek
     *
     * @return true if the first MP3 frame can be found
     *
     * @throws IOException on any I/O error
     */
    public boolean seekMP3Frame(final RandomAccessFile seekFile) throws IOException {
        boolean syncFound = false;
        byte first;
        byte second;
        long filePointer = 1;
        variableBitRate = false;
        try {
            seekFile.seek(0);
            do {
                first = seekFile.readByte();
                if (first == (byte) 0xFF) {
                    filePointer = seekFile.getFilePointer();
                    second = (byte) (seekFile.readByte() & (byte) 0xE0);
                    if (second == (byte) 0xE0) {
                        seekFile.seek(filePointer - 1);

                        // seek the next frames, recursively
                        syncFound = seekNextMP3Frame(seekFile,
                                                     TagOptionSingleton.getInstance().getNumberMP3SyncFrame());
                    }
                    seekFile.seek(filePointer);
                }
            } while (syncFound == false);
            seekFile.seek(filePointer - 1);
        } catch (EOFException ex) {
            syncFound = false;
        } catch (IOException ex) {
            throw ex;
        }
        return syncFound;
    }

    /**
     * Returns the MP3 frame size for the file this object refers to. It assumes that <code>seekNextMP3Frame</code> has
     * already been called.
     *
     * @return MP3 Frame size in bytes.
     */
    private int getFrameSize() {
        if (frequency == 0) {
            return 0;
        }
        final int size;
        final int paddingByte = padding ? 1 : 0;
        if (layer == 3) { // Layer I
            size = (int) ((((12 * bitRate) / frequency) + paddingByte) * 4);
        } else {
            size = (int) (((144 * bitRate) / frequency) + paddingByte);
        }
        return size;
    }

    /**
     * Reads the mp3 frame header from the current posiiton in the file and sets this object's private variables to what
     * is found. It assumes the <code>RandomAccessFile</code> is already pointing to a valid MP3 Frame.
     *
     * @param file File to read frame header
     *
     * @throws IOException          on any I/O error
     * @throws TagNotFoundException if MP3 Frame sync bites were not immediately found
     * @throws InvalidTagException  if any of the header values are invlaid
     */
    private void readFrameHeader(final RandomAccessFile file)
            throws IOException, TagNotFoundException, InvalidTagException {
        final byte[] buffer = new byte[4];
        file.read(buffer);

        // sync
        if ((buffer[0] != (byte) 0xFF) || ((buffer[1] & (byte) 0xE0) != (byte) 0xE0)) {
            throw new TagNotFoundException("MP3 Frame sync bits not found");
        }
        mpegVersion = (byte) ((buffer[1] & TagConstant.MASK_MP3_VERSION) >> 3);
        layer = (byte) ((buffer[1] & TagConstant.MASK_MP3_LAYER) >> 1);
        protection = (buffer[1] & TagConstant.MASK_MP3_PROTECTION) != 1;
        final int bitRateValue = (buffer[2] & TagConstant.MASK_MP3_BITRATE) |
                                 (buffer[1] & TagConstant.MASK_MP3_ID) |
                                 (buffer[1] & TagConstant.MASK_MP3_LAYER);
        final Long object = (Long) TagConstant.bitrate.get(new Long(bitRateValue));
        if (object != null) {
            if (object.longValue() != bitRate) {
                variableBitRate = true;
            }
            bitRate = object.intValue();
        } else {
            throw new InvalidTagException("Invalid bit rate");
        }
        final int frequencyValue = (buffer[2] & TagConstant.MASK_MP3_FREQUENCY) >>> 2;
        if (mpegVersion == 3) { // Version 1.0
            switch (frequencyValue) {
                case 0:
                    frequency = 44.1;
                    break;
                case 1:
                    frequency = 48.0;
                    break;
                case 2:
                    frequency = 32.0;
                    break;
            }
        } else if (mpegVersion == 2) { // Version 2.0
            switch (frequencyValue) {
                case 0:
                    frequency = 22.05;
                    break;
                case 1:
                    frequency = 24.00;
                    break;
                case 2:
                    frequency = 16.00;
                    break;
            }
        } else if (mpegVersion == 00) { // Version 2.5
            switch (frequencyValue) {
                case 0:
                    frequency = 11.025;
                    break;
                case 1:
                    frequency = 12.00;
                    break;
                case 2:
                    frequency = 8.00;
                    break;
            }
        } else {
            throw new InvalidTagException("Invalid MPEG version");
        }
        padding = (buffer[2] & TagConstant.MASK_MP3_PADDING) != 0;
        privacy = (buffer[2] & TagConstant.MASK_MP3_PRIVACY) != 0;
        mode = (byte) ((buffer[3] & TagConstant.MASK_MP3_MODE) >> 6);
        modeExtension = (byte) ((buffer[3] & TagConstant.MASK_MP3_MODE_EXTENSION) >> 4);
        copyProtected = (buffer[3] & TagConstant.MASK_MP3_COPY) != 0;
        home = (buffer[3] & TagConstant.MASK_MP3_HOME) != 0;
        emphasis = (byte) ((buffer[3] & TagConstant.MASK_MP3_EMPHASIS));
    }

    /**
     * Returns true if the first MP3 frame can be found for the MP3 file argument. It is recursive and called by
     * seekMP3Frame. This is the first byte of music data and not the ID3 Tag Frame.
     *
     * @param file       MP3 file to seek
     * @param iterations recursive counter
     *
     * @return true if the first MP3 frame can be found
     *
     * @throws IOException on any I/O error
     */
    private boolean seekNextMP3Frame(final RandomAccessFile file, final int iterations) throws IOException {
        final boolean syncFound;
        final byte[] buffer;
        final byte first;
        final byte second;
        final long filePointer;
        if (iterations == 0) {
            syncFound = true;
        } else {
            try {
                readFrameHeader(file);
            } catch (TagException ex) {
                return false;
            }
            final int size = getFrameSize();
            if ((size <= 0) || (size > file.length())) {
                return false;
            }
            buffer = new byte[size - 4];
            file.read(buffer);
            filePointer = file.getFilePointer();
            first = file.readByte();
            if (first == (byte) 0xFF) {
                second = (byte) (file.readByte() & (byte) 0xE0);
                if (second == (byte) 0xE0) {
                    file.seek(filePointer);

                    // recursively find the next frames
                    syncFound = seekNextMP3Frame(file, iterations - 1);
                } else {
                    syncFound = false;
                }
            } else {
                syncFound = false;
            }
        }
        return syncFound;
    }
}