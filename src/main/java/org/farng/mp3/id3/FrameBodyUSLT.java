package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.object.ObjectLyrics3Line;
import org.farng.mp3.object.ObjectNumberHashMap;
import org.farng.mp3.object.ObjectStringHashMap;
import org.farng.mp3.object.ObjectStringNullTerminated;
import org.farng.mp3.object.ObjectStringSizeTerminated;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <h3>4.8.&nbsp;&nbsp; Unsynchronised lyrics/text transcription</h3>
 * <p/>
 * <p>&nbsp;&nbsp; This frame contains the lyrics of the song or a text transcription of<br> &nbsp;&nbsp; other vocal
 * activities. The head includes an encoding descriptor and<br>
 * <p/>
 * &nbsp;&nbsp; a content descriptor. The body consists of the actual text. The<br> &nbsp;&nbsp; 'Content descriptor' is
 * a terminated string. If no descriptor is<br> &nbsp;&nbsp; entered, 'Content descriptor' is $00 (00) only. Newline
 * characters<br> &nbsp;&nbsp; are allowed in the text. There may be more than one 'Unsynchronised<br> &nbsp;&nbsp;
 * lyrics/text transcription' frame in each tag, but only one with the<br>
 * <p/>
 * &nbsp;&nbsp; same language and content descriptor.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Unsynchronised lyrics/text transcription', ID: &quot;USLT&quot;&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Text encoding&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * <p/>
 * Language&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx xx xx<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Content descriptor&nbsp;&nbsp; &lt;text string according to encoding&gt; $00 (00)<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Lyrics/text&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &lt;full text string
 * according to encoding&gt;<br>
 * <p/>
 * </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FrameBodyUSLT extends AbstractID3v2FrameBody {

    /**
     * Creates a new FrameBodyUSLT object.
     */
    public FrameBodyUSLT() {
        super();
    }

    /**
     * Creates a new FrameBodyUSLT object.
     */
    public FrameBodyUSLT(final FrameBodyUSLT body) {
        super(body);
    }

    /**
     * Creates a new FrameBodyUSLT object.
     */
    public FrameBodyUSLT(final byte textEncoding, final String language, final String description, final String text) {
        setObject("Text Encoding", new Byte(textEncoding));
        setObject("Language", language);
        setObject("Description", description);
        setObject("Lyrics/Text", text);
    }

    /**
     * Creates a new FrameBodyUSLT object.
     */
    public FrameBodyUSLT(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public void setDescription(final String description) {
        setObject("Description", description);
    }

    public String getDescription() {
        return (String) getObject("Description");
    }

    public String getIdentifier() {
        return "USLT" + ((char) 0) + getLanguage() + ((char) 0) + getDescription();
    }

    public void setLanguage(final String language) {
        setObject("Language", language);
    }

    public String getLanguage() {
        return (String) getObject("Language");
    }

    public void setLyric(final String lyric) {
        setObject("Lyrics/Text", lyric);
    }

    public String getLyric() {
        return (String) getObject("Lyrics/Text");
    }

    public void addLyric(final String text) {
        setLyric(getLyric() + text);
    }

    public void addLyric(final ObjectLyrics3Line line) {
        setLyric(getLyric() + line.writeString());
    }

    protected void setupObjectList() {
        appendToObjectList(new ObjectNumberHashMap("Text Encoding", 1));
        appendToObjectList(new ObjectStringHashMap("Language", 3));
        appendToObjectList(new ObjectStringNullTerminated("Description"));
        appendToObjectList(new ObjectStringSizeTerminated("Lyrics/Text"));
    }
}