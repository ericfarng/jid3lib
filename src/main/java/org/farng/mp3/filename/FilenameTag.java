package org.farng.mp3.filename;

import org.farng.mp3.AbstractMP3Tag;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagConstant;
import org.farng.mp3.TagException;
import org.farng.mp3.TagUtility;
import org.farng.mp3.id3.AbstractID3v2Frame;
import org.farng.mp3.id3.FrameBodyCOMM;
import org.farng.mp3.id3.FrameBodySYLT;
import org.farng.mp3.id3.FrameBodyTALB;
import org.farng.mp3.id3.FrameBodyTCOM;
import org.farng.mp3.id3.FrameBodyTCON;
import org.farng.mp3.id3.FrameBodyTDRL;
import org.farng.mp3.id3.FrameBodyTIT2;
import org.farng.mp3.id3.FrameBodyTPE1;
import org.farng.mp3.id3.FrameBodyTRCK;
import org.farng.mp3.id3.FrameBodyUSLT;
import org.farng.mp3.id3.ID3v2_3Frame;
import org.farng.mp3.id3.ID3v2_4;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

/**
 * This class represents the filename. To create it, invoke <code>FilenameTagBuilder.createFilenameTagFromMP3File</code>
 * which returns a complete parsed, evaluated, and matched FilenameTag.
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class FilenameTag extends AbstractMP3Tag {

    /**
     * parsed composite
     */
    private AbstractFilenameComposite composite = null;
    /**
     * id3v2_4 tag created from composite
     */
    private ID3v2_4 id3tag = null;
    /**
     * mp3file used to create this tag
     */
    private MP3File mp3file = null;
    /**
     * file name extension used to create this tag
     */
    private String extension = null;

    /**
     * Creates a new FilenameTag object.
     */
    public FilenameTag(final FilenameTag copyObject) {
        super(copyObject);
        composite = (AbstractFilenameComposite) TagUtility.copyObject(copyObject.composite);
        id3tag = new ID3v2_4(copyObject.id3tag);
        mp3file = new MP3File(copyObject.mp3file);
        extension = copyObject.extension;
    }

    /**
     * Creates a new FilenameTag object.
     */
    protected FilenameTag() {
        super();
    }

    /**
     * Sets the composite that this tag will use.
     *
     * @param composite the composite that this tag will use.
     */
    public void setComposite(final AbstractFilenameComposite composite) {
        this.composite = composite;
    }

    /**
     * Returns the composite that this tag will use.
     *
     * @return the composite that this tag will use.
     */
    public AbstractFilenameComposite getComposite() {
        return composite;
    }

    public void setExtension(final String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    /**
     * Sets the frame of this tag
     *
     * @param frame the frame to set
     */
    public void setFrame(final AbstractID3v2Frame frame) {
        if (frame != null) {
            if (id3tag == null) {
                id3tag = new ID3v2_4();
            }
            id3tag.setFrame(frame);
            if (composite != null) {
                composite.setFrame(frame);
            }
        }
    }

    /**
     * Returns a frame of this tag
     *
     * @param identifier ID3v2_4 ID of frame to get
     *
     * @return a frame of this tag
     */
    public AbstractID3v2Frame getFrame(final String identifier) {
        AbstractID3v2Frame frame = null;
        if (id3tag != null) {
            frame = id3tag.getFrame(identifier);
        }
        return frame;
    }

    public Iterator getFrameOfType(final String identifier) {
        return id3tag.getFrameOfType(identifier);
    }

    /**
     * Sets the ID3v2_4 representation of this tag.
     *
     * @param id3tag the ID3v2_4 representation of this tag
     */
    public void setId3tag(final ID3v2_4 id3tag) {
        this.id3tag = id3tag;
        if (id3tag != null) {
            final Iterator iterator = id3tag.iterator();
            while (iterator.hasNext()) {
                composite.setFrame((AbstractID3v2Frame) iterator.next());
            }
            if (composite != null) {
                composite.matchAgainstTag(id3tag);
            }
        }
    }

    /**
     * Returns the ID3v2_4 representation of this tag
     *
     * @return the ID3v2_4 representation of this tag
     */
    public ID3v2_4 getId3tag() {
        return id3tag;
    }

    public String getIdentifier() {
        return "FilenameTagv1.00";
    }

    public void setMp3file(final MP3File mp3file) {
        this.mp3file = mp3file;
    }

    public MP3File getMp3file() {
        return mp3file;
    }

    public int getSize() {
        return composeFilename().length();
    }

    public void append(final AbstractMP3Tag abstractMP3Tag) {
        //todo Implement this org.farng.mp3.AbstractMP3Tag abstract method
        throw new UnsupportedOperationException("Method append() not yet implemented.");
    }

    public void append(final RandomAccessFile file) {
        //todo Implement this org.farng.mp3.AbstractMP3Tag abstract method
        throw new UnsupportedOperationException("Method append() not yet implemented.");
    }

    public String composeFilename() {
        final StringBuffer filename = new StringBuffer(128);
        if (composite != null) {
            filename.append(composite.composeFilename().trim());
            filename.append('.');
            filename.append(extension);
        }
        return filename.toString();
    }

    public void delete(final RandomAccessFile file) {
        //todo Implement this org.farng.mp3.AbstractMP3Tag abstract method
        throw new UnsupportedOperationException("Method delete() not yet implemented.");
    }

    public boolean hasFrame(final String identifier) {
        if (id3tag != null) {
            return id3tag.hasFrame(identifier);
        }
        return false;
    }

    public boolean hasFrameOfType(final String identifier) {
        if (id3tag != null) {
            return id3tag.hasFrameOfType(identifier);
        }
        return false;
    }

    public Iterator iterator() {
        Iterator iterator = null;
        if (composite != null) {
            iterator = composite.iterator();
        }
        return iterator;
    }

    public void overwrite(final AbstractMP3Tag abstractMP3Tag) {
        //todo Implement this org.farng.mp3.AbstractMP3Tag abstract method
        throw new UnsupportedOperationException("Method overwrite() not yet implemented.");
    }

    public void overwrite(final RandomAccessFile file) throws TagException, IOException {
        write(file);
    }

    public void read(final RandomAccessFile file) {
        //todo Implement this org.farng.mp3.AbstractMP3Tag abstract method
        throw new UnsupportedOperationException("Method read() not yet implemented.");
    }

    public boolean seek(final RandomAccessFile file) {
        //todo Implement this org.farng.mp3.AbstractMP3Tag abstract method
        throw new UnsupportedOperationException("Method seek() not yet implemented.");
    }

    public String toString() {
        final StringBuffer stringBuffer = new StringBuffer(128);
        final Iterator iterator = iterator();
        while (iterator.hasNext()) {
            stringBuffer.append(iterator.next().toString());
            stringBuffer.append(TagConstant.SEPERATOR_LINE);
        }
        return stringBuffer.toString();
    }

    public void write(final AbstractMP3Tag abstractMP3Tag) {
        //todo Implement this org.farng.mp3.AbstractMP3Tag abstract method
        throw new UnsupportedOperationException("Method write() not yet implemented.");
    }

    public void write(final RandomAccessFile file) throws IOException, TagException {
        final File originalFile = getMp3file().getMp3file();
        final File newFile = new File(originalFile.getParentFile(), composeFilename());
        if (!newFile.getName().equals(originalFile.getName())) {
            file.getFD().sync();
            file.getChannel().close();
            file.close();

            // copy, then delete
            TagUtility.copyFile(originalFile, newFile);
            if (!originalFile.delete()) {
                throw new TagException("Unable to delete original file: " + originalFile.getName());
            }
        }
    }

    public String getSongTitle() {
        String text = null;
        AbstractID3v2Frame frame = getFrame("TIT2");
        if (frame != null) {
            FrameBodyTIT2 body = (FrameBodyTIT2) frame.getBody();
            text = body.getText();
        }
        return text.trim();
    }

    public String getLeadArtist() {
        String text = null;
        AbstractID3v2Frame frame = getFrame("TPE1");
        if (frame != null) {
            FrameBodyTPE1 body = (FrameBodyTPE1) frame.getBody();
            text = body.getText();
        }
        return text.trim();
    }

    public String getAlbumTitle() {
        String text = null;
        AbstractID3v2Frame frame = getFrame("TALB");
        if (frame != null) {
            FrameBodyTALB body = (FrameBodyTALB) frame.getBody();
            text = body.getText();
        }
        return text.trim();
    }

    public String getYearReleased() {
        String text = null;
        AbstractID3v2Frame frame = getFrame("TDRL");
        if (frame != null) {
            FrameBodyTDRL body = (FrameBodyTDRL) frame.getBody();
            text = body.getText();
        }
        return text.trim();
    }

    public String getSongComment() {
        String text = null;
        AbstractID3v2Frame frame = getFrame("COMM");
        if (frame != null) {
            FrameBodyCOMM body = (FrameBodyCOMM) frame.getBody();
            text = body.getText();
        }
        return text.trim();
    }

    public String getSongGenre() {
        String text = null;
        AbstractID3v2Frame frame = getFrame("TCON");
        if (frame != null) {
            FrameBodyTCON body = (FrameBodyTCON) frame.getBody();
            text = body.getText();
        }
        return text.trim();
    }

    public String getTrackNumberOnAlbum() {
        String text = null;
        AbstractID3v2Frame frame = getFrame("TRCK");
        if (frame != null) {
            FrameBodyTRCK body = (FrameBodyTRCK) frame.getBody();
            text = body.getText();
        }
        return text.trim();
    }

    public String getSongLyric() {
        String text = null;
        AbstractID3v2Frame frame = getFrame("SYLT");
        if (frame != null) {
            FrameBodySYLT body = (FrameBodySYLT) frame.getBody();
            text = body.getLyric();
        }
        if (text == null) {
            frame = getFrame("USLT");
            if (frame != null) {
                FrameBodyUSLT body = (FrameBodyUSLT) frame.getBody();
                text = body.getLyric();
            }
        }
        return text.trim();
    }

    public String getAuthorComposer() {
        String text = null;
        AbstractID3v2Frame frame = getFrame("TCOM");
        if (frame != null) {
            FrameBodyTCOM body = (FrameBodyTCOM) frame.getBody();
            text = body.getText();
        }
        return text.trim();
    }

    public void setSongTitle(String songTitle) {
        AbstractID3v2Frame field = getFrame("TIT2");
        if (field == null) {
            field = new ID3v2_3Frame(new FrameBodyTIT2((byte) 0, songTitle.trim()));
            setFrame(field);
        } else {
            ((FrameBodyTIT2) field.getBody()).setText(songTitle.trim());
        }
    }

    public void setLeadArtist(String leadArtist) {
        AbstractID3v2Frame field = getFrame("TPE1");
        if (field == null) {
            field = new ID3v2_3Frame(new FrameBodyTPE1((byte) 0, leadArtist.trim()));
            setFrame(field);
        } else {
            ((FrameBodyTPE1) field.getBody()).setText(leadArtist.trim());
        }
    }

    public void setAlbumTitle(String albumTitle) {
        AbstractID3v2Frame field = getFrame("TALB");
        if (field == null) {
            field = new ID3v2_3Frame(new FrameBodyTALB((byte) 0, albumTitle.trim()));
            setFrame(field);
        } else {
            ((FrameBodyTALB) field.getBody()).setText(albumTitle.trim());
        }
    }

    public void setYearReleased(String yearReleased) {
        AbstractID3v2Frame field = getFrame("TDRL");
        if (field == null) {
            field = new ID3v2_3Frame(new FrameBodyTDRL((byte) 0, yearReleased.trim()));
            setFrame(field);
        } else {
            ((FrameBodyTDRL) field.getBody()).setText(yearReleased.trim());
        }
    }

    public void setSongComment(String songComment) {
        AbstractID3v2Frame field = getFrame("COMM");
        if (field == null) {
            field = new ID3v2_3Frame(new FrameBodyCOMM((byte) 0, "ENG", "", songComment.trim()));
            setFrame(field);

        } else {
            ((FrameBodyCOMM) field.getBody()).setText(songComment.trim());
        }
    }

    public void setSongGenre(String songGenre) {
        AbstractID3v2Frame field = getFrame("TCON");
        if (field == null) {
            field = new ID3v2_3Frame(new FrameBodyTCON((byte) 0, songGenre.trim()));
            setFrame(field);
        } else {
            ((FrameBodyTCON) field.getBody()).setText(songGenre.trim());
        }
    }

    public void setTrackNumberOnAlbum(String trackNumberOnAlbum) {
        AbstractID3v2Frame field = getFrame("TRCK");
        if (field == null) {
            field = new ID3v2_3Frame(new FrameBodyTRCK((byte) 0, trackNumberOnAlbum.trim()));
            setFrame(field);
        } else {
            ((FrameBodyTRCK) field.getBody()).setText(trackNumberOnAlbum.trim());
        }
    }

    public void setSongLyric(String songLyrics) {
        AbstractID3v2Frame field = getFrame("SYLT");
        if (field == null) {
            field = new ID3v2_3Frame(new FrameBodyUSLT((byte) 0, "ENG", "", songLyrics.trim()));
            setFrame(field);
        } else {
            ((FrameBodyUSLT) field.getBody()).setLyric(songLyrics.trim());
        }
    }

    public void setAuthorComposer(String authorComposer) {
        AbstractID3v2Frame field = getFrame("TCOM");
        if (field == null) {
            field = new ID3v2_3Frame(new FrameBodyTCOM((byte) 0, authorComposer.trim()));
            setFrame(field);
        } else {
            ((FrameBodyTCOM) field.getBody()).setText(authorComposer.trim());
        }
    }
}