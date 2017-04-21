package org.farng.mp3;

import org.farng.mp3.id3.AbstractID3v2;
import org.farng.mp3.id3.AbstractID3v2Frame;
import org.farng.mp3.id3.ID3v2_4;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

/**
 * <h2>Introduction to tags</h2> There are three types of tags found in an MP3 file found in this order: <ol>
 * <li>ID3v2</li> <li><i>&lt;MP3 Data&gt;</i><mp3 data=""></mp3></li> <li>Lyrics3</li> <li>ID3v1</li> </ol> In addition,
 * there are different versions for each tag: <ol> <li>ID3v2 <ul> <li>ID3v2.2 </li> <li>ID3v2.3 </li> <li>ID3v2.4 </li>
 * </ul> </li> <li>Lyrics3 <ul> <li>Lyrics3v1 </li> <li>Lyrics3v2 </li> </ul> </li> <li>ID3v1 <ul> <li>ID3v1.0 </li>
 * <li>ID3v1.1 </li> </ul> </li> </ol>
 * <p/>
 * <h2>Compiling:</h2> If you have ant, there is a build.xml. Type "ant help" for build options.<BR> If you get from
 * CVS, you can use IntelliJ and read jid3lib.ipr<br>
 * <p/>
 * <h2>Reading:<br> </h2>
 * <pre>
 * File sourceFile;
 * MP3File mp3file = new MP3File(sourceFile);
 * </pre>
 * You can also read specific tags:
 * <pre>
 * ID3v1_1 tag = new ID3v1_1(sourceFile);
 * ID3v1 tag = new ID3v1(sourceFile);
 * ID3v2_4 tag = new ID3v2_4(sourceFile);
 * ID3v2_3 tag = new ID3v2_3(sourceFile);
 * ID3v2_2 tag = new ID3v2_2(sourceFile);
 * Lyrics3v2 tag = new Lyrics3v2(sourceFile);
 * Lyrics3v1 tag = new Lyrics3v1(sourceFile);
 * </pre>
 * <p/>
 * <h2>Creating:</h2>
 * <pre>
 * MP3File mp3file = new MP3File();
 * TagOptionSingleton.getInstance().setDefaultSaveMode(TagConstant.MP3_FILE_SAVE_OVERWRITE);
 * <p/>
 * // setup id3v1
 * id3v1.setAlbum("albumTitle");
 * <p/>
 * // setup id3v2
 * AbstractID3v2Frame frame;
 * AbstractID3v2FrameBody frameBody;
 * frameBody = new FrameBodyTALB((byte) 0, "albumTitle");
 * frame = new ID3v2_4Frame(frameBody);
 * id3v2.setFrame(frame);
 * <p/>
 * // setup lyrics3v2
 * Lyrics3v2Field field;
 * AbstractLyrics3v2FieldBody fieldBody;
 * fieldBody = new FieldBodyEAL("albumTitle");
 * field = new Lyrics3v2Field(fieldBody);
 * lyrics3.setField(field);
 * <p/>
 * // setup filename tag
 * frameBody = new FrameBodyTALB((byte) 0, "albumTitle");
 * frame = new ID3v2_4Frame(frameBody);
 * filenameId3.setFrame(frame);
 * TagOptionSingleton.getInstance().setFilenameTagSave(true);
 * </pre>
 * Things to note: <ul> <li>The default save mode is "write but do not delete." This means each field in the object will
 * be saved, but existing fields in the file on disk will not be deleted. The other two are "only append" or "delete and
 * write from scratch."</li> </ul>
 * <p/>
 * <h2>Editing Part 1:</h2>
 * <p/>
 * There are convience methods defined in AbstractMP3Tag to edit common data fields. Not all tags have all fields listed
 * here.
 * <p/>
 * <pre>
 * public abstract String getSongTitle();
 * public abstract String getLeadArtist();
 * public abstract String getAlbumTitle();
 * public abstract String getYearReleased();
 * public abstract String getSongComment();
 * public abstract String getSongGenre();
 * public abstract String getTrackNumberOnAlbum();
 * public abstract String getSongLyric();
 * public abstract String getAuthorComposer();
 * public abstract void setSongTitle(String songTitle);
 * public abstract void setLeadArtist(String leadArtist);
 * public abstract void setAlbumTitle(String albumTitle);
 * public abstract void setYearReleased(String yearReleased);
 * public abstract void setSongComment(String songComment);
 * public abstract void setSongGenre(String songGenre);
 * public abstract void setTrackNumberOnAlbum(String trackNumberOnAlbum);
 * public abstract void setSongLyric(String songLyrics);
 * public abstract void setAuthorComposer(String authorComposer);
 * </pre>
 * <p/>
 * <h2>Editing Part 2:</h2> If the field you want is not listed above, you can use these methods.
 * <pre>
 * id3v1 = mp3file.getID3v1Tag();
 * id3v2 = mp3file.getID3v2Tag();
 * lyrics3 = mp3file.getLyrics3Tag();
 * </pre>
 * ID3v1 tags have fixed fields and use accessor methods to change it's properties. <p>ID3v2 tags have multiple
 * <i>frames</i>. Use this to set the title of the tag.</p>
 * <pre>
 * frame = id3v2.getFrame("TIT2");
 * ((FrameBodyTIT2) frame.getBody()).setText("New Title");
 * </pre>
 * <p/>
 * <p>Lyrics3 tags have multiple <i>fields</i>. Use this to set the title of the tag.</p>
 * <pre>
 * field = lyrics3.getField("ETT");
 * ((FieldBodyETT) field.getBody()).setTitle("New Title");
 * </pre>
 * <p/>
 * <h2>Writing: </h2>
 * <pre>
 * mp3file.save();
 * </pre>
 * You can also save each individual tag through each tags' save() method.<br> <br>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public abstract class AbstractMP3Tag extends AbstractMP3FileItem {

    /**
     * Creates a new AbstractMP3Tag object.
     */
    protected AbstractMP3Tag() {
        super();
    }

    /**
     * Creates a new AbstractMP3Tag object.
     */
    protected AbstractMP3Tag(final AbstractMP3Tag copyObject) {
        super(copyObject);
    }

    /**
     * Appends this tag to the given file. Append means any information this tag contains will be added to the file's
     * corresponding tag, but it will not replace any fields that the file already has. If the file does not have this
     * specific tag, a new one will be created.
     *
     * @param file MP3 file to append to.
     *
     * @throws IOException  on any I/O error
     * @throws TagException on any exception generated by this library.
     */
    public abstract void append(RandomAccessFile file) throws IOException, TagException;

    /**
     * removes the specific tag the easiest way. <BR> ID3v1 - cuts the length of the tag <BR> lyrics3 -cuts the length
     * of the tag, then writes the id3v1 tag if it existed <BR> id3v2 - just overwrites the ID3 tag indicator at the
     * start of the tag <BR>
     *
     * @param file MP3 file to append to.
     *
     * @throws IOException on any I/O error
     */
    public abstract void delete(RandomAccessFile file) throws IOException;

    /**
     * Overwrites this tag to the given file. Overwrite means any information this tag contains will replace any
     * existing fields in the file's corresponding tag. If the file does not have this specific tag, a new one will be
     * created.
     *
     * @param file MP3 file to overwrite
     *
     * @throws IOException  on any I/O error
     * @throws TagException on any exception generated by this library.
     */
    public abstract void overwrite(RandomAccessFile file) throws IOException, TagException;

    /**
     * Looks for this tag. returns true if found. If found, the file pointer is right after the tag start indicator i.e.
     * "TAG" "LYRICSBEGIN" "ID3" + 2
     *
     * @param file MP3 file to overwrite
     *
     * @return returns true if found, false otherwise.
     *
     * @throws IOException on any I/O error
     */
    public abstract boolean seek(RandomAccessFile file) throws IOException;

    /**
     * Returns true if this tag is a subset of the argument. Both tags are converted into ID3v2_4 tags, and then
     * compared frame by frame.
     *
     * @param abstractMP3Tag superset tag
     *
     * @return true if this tag is a subset of the argument
     */
    public boolean isSubsetOf(final AbstractMP3Tag abstractMP3Tag) {
        final AbstractID3v2 subset = new ID3v2_4(this);
        final AbstractID3v2 superset = new ID3v2_4(abstractMP3Tag);
        final Iterator iterator = subset.iterator();
        while (iterator.hasNext()) {
            final AbstractID3v2Frame subsetFrame = (AbstractID3v2Frame) iterator.next();
            final String identifier = subsetFrame.getIdentifier();
            final AbstractID3v2Frame supersetFrame = superset.getFrame(identifier);
            if (supersetFrame == null) {
                return false;
            }
            if (!subsetFrame.isSubsetOf(supersetFrame)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method does nothing, but is called by subclasses for completeness
     *
     * @param abstractMP3Tag tag to overwrite
     */
    public abstract void append(AbstractMP3Tag abstractMP3Tag);

    /**
     * Determines whether another object is equal to this tag. It just compares if they are the same class, then calls
     * <code>super.equals(object)</code>.
     */
    public boolean equals(final Object obj) {
        return obj instanceof AbstractMP3Tag && super.equals(obj);
    }

    public abstract Iterator iterator();

    /**
     * This method does nothing, but is called by subclasses for completeness
     *
     * @param abstractMP3Tag tag to overwrite
     */
    public abstract void overwrite(AbstractMP3Tag abstractMP3Tag);

    /**
     * This method does nothing, but is called by subclasses for completeness
     *
     * @param abstractMP3Tag tag to write to
     */
    public abstract void write(AbstractMP3Tag abstractMP3Tag);

    public abstract String getSongTitle();

    public abstract String getLeadArtist();

    public abstract String getAlbumTitle();

    public abstract String getYearReleased();

    public abstract String getSongComment();

    public abstract String getSongGenre();

    public abstract String getTrackNumberOnAlbum();

    public abstract String getSongLyric();

    public abstract String getAuthorComposer();

    public abstract void setSongTitle(String songTitle);

    public abstract void setLeadArtist(String leadArtist);

    public abstract void setAlbumTitle(String albumTitle);

    public abstract void setYearReleased(String yearReleased);

    public abstract void setSongComment(String songComment);

    public abstract void setSongGenre(String songGenre);

    public abstract void setTrackNumberOnAlbum(String trackNumberOnAlbum);

    public abstract void setSongLyric(String songLyrics);

    public abstract void setAuthorComposer(String authorComposer);
}