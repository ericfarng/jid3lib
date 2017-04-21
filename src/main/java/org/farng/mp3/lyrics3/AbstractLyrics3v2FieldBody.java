package org.farng.mp3.lyrics3;

import org.farng.mp3.AbstractMP3FragmentBody;
import org.farng.mp3.InvalidTagException;
import org.farng.mp3.TagOptionSingleton;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Contains the actual text strings for a Lyrics3v2 field.
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public abstract class AbstractLyrics3v2FieldBody extends AbstractMP3FragmentBody {

    /**
     * Creates a new AbstractLyrics3v2FieldBody object.
     */
    public AbstractLyrics3v2FieldBody() {
        super();
    }

    /**
     * Creates a new AbstractLyrics3v2FieldBody object.
     */
    public AbstractLyrics3v2FieldBody(final AbstractLyrics3v2FieldBody copyObject) {
        super(copyObject);
    }

    protected int readHeader(final RandomAccessFile file) throws InvalidTagException, IOException {
        final int size;
        final byte[] buffer = new byte[5];

        // read the 5 character size
        file.read(buffer, 0, 5);
        size = Integer.parseInt(new String(buffer, 0, 5));
        if ((size == 0) && (TagOptionSingleton.getInstance().isLyrics3KeepEmptyFieldIfRead() == false)) {
            throw new InvalidTagException("Lyircs3v2 Field has size of zero.");
        }
        return size;
    }

    protected void writeHeader(final RandomAccessFile file, final int size) throws IOException {
        final String str;
        int offset = 0;
        final byte[] buffer = new byte[5];

        // todo change this to use pad String
        str = Integer.toString(getSize());
        for (int i = 0; i < (5 - str.length()); i++) {
            buffer[i] = (byte) '0';
        }
        offset += (5 - str.length());
        for (int i = 0; i < str.length(); i++) {
            buffer[i + offset] = (byte) str.charAt(i);
        }
        file.write(buffer);
    }
}