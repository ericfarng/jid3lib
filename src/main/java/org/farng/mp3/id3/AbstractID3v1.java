package org.farng.mp3.id3;

import org.farng.mp3.TagException;
import org.farng.mp3.TagNotFoundException;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Superclass for all ID3v1 tags.
 *
 * @author $author$
 * @version $Revision: 1.3 $
 */
public abstract class AbstractID3v1 extends AbstractID3 {

    /**
     * Creates a new AbstractID3v1 object.
     */
    protected AbstractID3v1() {
        super();
    }

    /**
     * Creates a new AbstractID3v1 object.
     */
    protected AbstractID3v1(final AbstractID3v1 copyObject) {
        super(copyObject);
    }

    public void append(final RandomAccessFile file) throws IOException, TagException {
        AbstractID3v1 oldTag;
        try {
            oldTag = new ID3v1_1(file);
            oldTag.append(this);
            oldTag.write(file);
        } catch (TagNotFoundException ex) {
            try {
                oldTag = new ID3v1(file);
                oldTag.append(this);
                oldTag.write(file);
            } catch (TagNotFoundException ex2) {
                write(file);
            }
        }
    }

    public void overwrite(final RandomAccessFile file) throws IOException, TagException {
        AbstractID3v1 oldTag;
        try {
            oldTag = new ID3v1_1(file);
            oldTag.overwrite(this);
            oldTag.write(file);
        } catch (TagNotFoundException ex) {
            try {
                oldTag = new ID3v1(file);
                oldTag.overwrite(this);
                oldTag.write(file);
            } catch (TagNotFoundException ex2) {
                write(file);
            }
        }
    }
}