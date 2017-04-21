package org.farng.mp3.id3;

import org.farng.mp3.InvalidTagException;
import org.farng.mp3.TagUtility;
import org.farng.mp3.object.ObjectID3v2LyricLine;
import org.farng.mp3.object.ObjectLyrics3Line;
import org.farng.mp3.object.ObjectLyrics3TimeStamp;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * <h3>4.9.&nbsp;&nbsp; Synchronised lyrics/text</h3>
 * <p/>
 * <p>&nbsp;&nbsp; This is another way of incorporating the words, said or sung lyrics,<br> &nbsp;&nbsp; in the audio
 * file as text, this time, however, in sync with the<br> &nbsp;&nbsp; audio. It might also be used to describing events
 * e.g. occurring on a<br>
 * <p/>
 * &nbsp;&nbsp; stage or on the screen in sync with the audio. The header includes a<br> &nbsp;&nbsp; content
 * descriptor, represented with as terminated text string. If no<br> &nbsp;&nbsp; descriptor is entered, 'Content
 * descriptor' is $00 (00) only.</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &lt;Header for 'Synchronised lyrics/text', ID: &quot;SYLT&quot;&gt;<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Text encoding&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br> &nbsp;&nbsp;&nbsp;&nbsp;
 * Language&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx xx xx<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Time stamp format&nbsp;&nbsp;&nbsp; $xx<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; Content type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $xx<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Content descriptor&nbsp;&nbsp; &lt;text string according to encoding&gt; $00 (00)</p>
 * <p/>
 * <p>&nbsp;&nbsp; Content type:&nbsp;&nbsp; $00 is other<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $01 is
 * lyrics<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $02 is text transcription<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $03 is movement/part name (e.g. &quot;Adagio&quot;)<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $04 is
 * events (e.g. &quot;Don Quijote enters the stage&quot;)<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $05 is chord (e.g. &quot;Bb F Fsus&quot;)<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $06 is trivia/'pop up' information<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; $07 is
 * URLs to webpages<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $08 is URLs to images</p>
 * <p/>
 * <p>&nbsp;&nbsp; Time stamp format:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; $01&nbsp; Absolute time, 32 bit sized, using MPEG [MPEG] frames as unit<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; $02&nbsp; Absolute time, 32 bit sized, using milliseconds as unit</p>
 * <p/>
 * <p>&nbsp;&nbsp; Absolute time means that every stamp contains the time from the<br> &nbsp;&nbsp; beginning of the
 * file.</p>
 * <p/>
 * <p>&nbsp;&nbsp; The text that follows the frame header differs from that of the<br>
 * <p/>
 * &nbsp;&nbsp; unsynchronised lyrics/text transcription in one major way. Each<br> &nbsp;&nbsp; syllable (or whatever
 * size of text is considered to be convenient by<br> &nbsp;&nbsp; the encoder) is a null terminated string followed by
 * a time stamp<br> &nbsp;&nbsp; denoting where in the sound file it belongs. Each sync thus has the<br> &nbsp;&nbsp;
 * following structure:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; Terminated text to be synced (typically a syllable)<br> &nbsp;&nbsp;&nbsp;&nbsp; Sync
 * identifier (terminator to above string)&nbsp;&nbsp; $00 (00)<br> &nbsp;&nbsp;&nbsp;&nbsp; Time
 * stamp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * $xx (xx ...)</p>
 * <p/>
 * <p>&nbsp;&nbsp; The 'time stamp' is set to zero or the whole sync is omitted if<br> &nbsp;&nbsp; located directly at
 * the beginning of the sound. All time stamps<br> &nbsp;&nbsp; should be sorted in chronological order. The sync can be
 * considered<br> &nbsp;&nbsp; as a validator of the subsequent string.</p>
 * <p/>
 * <p>&nbsp;&nbsp; Newline characters are allowed in all &quot;SYLT&quot; frames and MUST be used<br> &nbsp;&nbsp; after
 * every entry (name, event etc.) in a frame with the content type<br> &nbsp;&nbsp; $03 - $04.</p>
 * <p/>
 * <p>&nbsp;&nbsp; A few considerations regarding whitespace characters: Whitespace<br>
 * <p/>
 * &nbsp;&nbsp; separating words should mark the beginning of a new word, thus<br> &nbsp;&nbsp; occurring in front of
 * the first syllable of a new word. This is also<br> &nbsp;&nbsp; valid for new line characters. A syllable followed by
 * a comma should<br> &nbsp;&nbsp; not be broken apart with a sync (both the syllable and the comma<br> &nbsp;&nbsp;
 * should be before the sync).</p>
 * <p/>
 * <p>&nbsp;&nbsp; An example: The &quot;USLT&quot; passage</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &quot;Strangers in the night&quot; $0A &quot;Exchanging glances&quot;</p>
 * <p/>
 * <p>&nbsp;&nbsp; would be &quot;SYLT&quot; encoded as:</p>
 * <p/>
 * <p>&nbsp;&nbsp;&nbsp;&nbsp; &quot;Strang&quot; $00 xx xx &quot;ers&quot; $00 xx xx &quot; in&quot; $00 xx xx &quot;
 * the&quot; $00 xx xx<br>
 * <p/>
 * &nbsp;&nbsp;&nbsp;&nbsp; &quot; night&quot; $00 xx xx 0A &quot;Ex&quot; $00 xx xx &quot;chang&quot; $00 xx xx
 * &quot;ing&quot; $00 xx<br> &nbsp;&nbsp;&nbsp;&nbsp; xx &quot;glan&quot; $00 xx xx &quot;ces&quot; $00 xx xx</p>
 * <p/>
 * <p>&nbsp;&nbsp; There may be more than one &quot;SYLT&quot; frame in each tag, but only one<br> &nbsp;&nbsp; with the
 * same language and content descriptor.<br> </p>
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class FrameBodySYLT extends AbstractID3v2FrameBody {

    LinkedList lines = new LinkedList();
    String description = "";
    String language = "";
    byte contentType = 0;
    byte textEncoding = 0;
    byte timeStampFormat = 0;

    /**
     * Creates a new FrameBodySYLT object.
     */
    public FrameBodySYLT() {
        super();
    }

    /**
     * Creates a new FrameBodySYLT object.
     */
    public FrameBodySYLT(final FrameBodySYLT copyObject) {
        super(copyObject);
        this.description = new String(copyObject.description);
        this.language = new String(copyObject.language);
        this.contentType = copyObject.contentType;
        this.textEncoding = copyObject.textEncoding;
        this.timeStampFormat = copyObject.timeStampFormat;
        ObjectID3v2LyricLine newLine;
        for (int i = 0; i < copyObject.lines.size(); i++) {
            newLine = new ObjectID3v2LyricLine((ObjectID3v2LyricLine) copyObject.lines.get(i));
            this.lines.add(newLine);
        }
    }

    /**
     * Creates a new FrameBodySYLT object.
     */
    public FrameBodySYLT(final byte textEncoding,
                         final String language,
                         final byte timeStampFormat,
                         final byte contentType,
                         final String description) {
        this.textEncoding = textEncoding;
        this.language = language;
        this.timeStampFormat = timeStampFormat;
        this.contentType = contentType;
        this.description = description;
    }

    /**
     * Creates a new FrameBodySYLT object.
     */
    public FrameBodySYLT(final RandomAccessFile file) throws IOException, InvalidTagException {
        this.read(file);
    }

    public byte getContentType() {
        return this.contentType;
    }

    public String getDescription() {
        return this.description;
    }

    public String getIdentifier() {
        return "SYLT";
    }

    public String getLanguage() {
        return this.language;
    }

    public String getLyric() {
        String lyrics = "";
        for (int i = 0; i < this.lines.size(); i++) {
            lyrics += this.lines.get(i);
        }
        return lyrics;
    }

    public int getSize() {
        int size;
        size = 1 + 3 + 1 + 1 + this.description.length();
        for (int i = 0; i < this.lines.size(); i++) {
            size += ((ObjectID3v2LyricLine) this.lines.get(i)).getSize();
        }
        return size;
    }

    public byte getTextEncoding() {
        return this.textEncoding;
    }

    public byte getTimeStampFormat() {
        return this.timeStampFormat;
    }

    public void addLyric(final int timeStamp, final String text) {
        final ObjectID3v2LyricLine line = new ObjectID3v2LyricLine("Lyric Line");
        line.setTimeStamp(timeStamp);
        line.setText(text);
        this.lines.add(line);
    }

    public void addLyric(final ObjectLyrics3Line line) {
        final Iterator iterator = line.getTimeStamp();
        ObjectLyrics3TimeStamp timeStamp;
        final String lyric = line.getLyric();
        long time;
        final ObjectID3v2LyricLine id3Line;
        id3Line = new ObjectID3v2LyricLine("Lyric Line");
        if (iterator.hasNext() == false) {
            // no time stamp, give it 0
            time = 0;
            id3Line.setTimeStamp(time);
            id3Line.setText(lyric);
            this.lines.add(id3Line);
        } else {
            while (iterator.hasNext()) {
                timeStamp = (ObjectLyrics3TimeStamp) iterator.next();
                time = (timeStamp.getMinute() * 60) + timeStamp.getSecond(); // seconds
                time *= 1000; // milliseconds
                id3Line.setTimeStamp(time);
                id3Line.setText(lyric);
                this.lines.add(id3Line);
            }
        }
    }

    /**
     * This method is not yet supported.
     *
     * @throws java.lang.UnsupportedOperationException
     *          This method is not yet supported
     */
    public void equals() {
        // todo Implement this java.lang.Object method
        throw new java.lang.UnsupportedOperationException("Method equals() not yet implemented.");
    }

    public Iterator iterator() {
        return this.lines.iterator();
    }

    protected void setupObjectList() {
//        throw new UnsupportedOperationException();
    }

    public void read(final RandomAccessFile file) throws IOException, InvalidTagException {
        final int size;
        final int delim;
        int offset = 0;
        final byte[] buffer;
        final String str;
        size = readHeader(file);
        buffer = new byte[size];
        file.read(buffer);
        str = new String(buffer);
        this.textEncoding = buffer[offset++];
        this.language = str.substring(offset, offset + 3);
        offset += 3;
        this.timeStampFormat = buffer[offset++];
        this.contentType = buffer[offset++];
        delim = str.indexOf(0, offset);
        this.description = str.substring(offset, delim);
        offset = delim + 1;
        final byte[] data = new byte[size - offset];
        System.arraycopy(buffer, offset, data, 0, size - offset);
        readByteArray(data);
    }

    public void readByteArray(final byte[] arr) {
        int offset = 0;
        int delim;
        byte[] line;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                delim = i;
                line = new byte[offset - delim + 4];
                System.arraycopy(arr, offset, line, 0, offset - delim + 4);
                this.lines.add(new ObjectID3v2LyricLine("Lyric Line"));
                i += 4;
                offset += 4;
            }
        }
    }

    public String toString() {
        String str;
        str = getIdentifier() + " " + this
                .textEncoding + " " + this
                .language + " " + this
                .timeStampFormat + " " + this
                .contentType + " " + this
                .description;
        for (int i = 0; i < this.lines.size(); i++) {
            str += (this.lines.get(i)).toString();
        }
        return str;
    }

    public void write(final RandomAccessFile file) throws IOException {
        final byte[] buffer;
        int offset = 0;
        writeHeader(file, this.getSize());
        buffer = new byte[this.getSize()];
        buffer[offset++] = this.textEncoding; // text encoding;
        this.language = TagUtility.truncate(this.language, 3);
        for (int i = 0; i < this.language.length(); i++) {
            buffer[i + offset] = (byte) this.language.charAt(i);
        }
        offset += this.language.length();
        buffer[offset++] = this.timeStampFormat;
        buffer[offset++] = this.contentType;
        for (int i = 0; i < this.description.length(); i++) {
            buffer[i + offset] = (byte) this.description.charAt(i);
        }
        offset += this.description.length();
        buffer[offset++] = 0; // null character
        System.arraycopy(writeByteArray(), 0, buffer, offset, buffer.length - offset);
        file.write(buffer);
    }

    public byte[] writeByteArray() {
        final byte[] arr;
        ObjectID3v2LyricLine line = null;
        int offset = 0;
        int size = 0;
        for (int i = 0; i < this.lines.size(); i++) {
            line = (ObjectID3v2LyricLine) this.lines.get(i);
            size += line.getSize();
        }
        arr = new byte[size];
        for (int i = 0; i < this.lines.size(); i++) {
            line = (ObjectID3v2LyricLine) this.lines.get(i);
        }
        if (line != null) {
            System.arraycopy(line.writeByteArray(), 0, arr, offset, line.getSize());
            offset += line.getSize();
        }
        return arr;
    }
}