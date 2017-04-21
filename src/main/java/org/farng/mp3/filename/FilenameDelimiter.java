package org.farng.mp3.filename;

import org.farng.mp3.AbstractMP3Tag;
import org.farng.mp3.TagUtility;
import org.farng.mp3.id3.AbstractID3v2Frame;
import org.farng.mp3.id3.AbstractID3v2FrameBody;
import org.farng.mp3.id3.ID3v2_4;

import java.util.Iterator;

/**
 * This class is a composite subclass which represents a delimiter within a filename. Delimiters are usually punctuation
 * such as " - "
 *
 * @author Eric Farng
 * @version $Revision: 1.5 $
 */
public class FilenameDelimiter extends AbstractFilenameComposite {

    /**
     * The composite that comes before the delimiter
     */
    private AbstractFilenameComposite afterComposite = null;
    /**
     * The composite that comes after the delimiter
     */
    private AbstractFilenameComposite beforeComposite = null;
    /**
     * The delimiter that was used to split the token
     */
    private String delimiter = null;

    /**
     * Creates a new FilenameDelimiter object.
     */
    public FilenameDelimiter() {
        super();
    }

    /**
     * Creates a new FilenameDelimiter object.
     */
    public FilenameDelimiter(final FilenameDelimiter copyObject) {
        super(copyObject);
        delimiter = copyObject.delimiter;
        afterComposite = (AbstractFilenameComposite) TagUtility.copyObject(copyObject.afterComposite);
        beforeComposite = (AbstractFilenameComposite) TagUtility.copyObject(copyObject.beforeComposite);
    }

    /**
     * Sets the composite that comes after the delimiter.
     *
     * @param afterComposite The composite that comes after the delimiter.
     */
    public void setAfterComposite(final AbstractFilenameComposite afterComposite) {
        this.afterComposite = afterComposite;
    }

    /**
     * Returns the composite that comes after the delimiter.
     *
     * @return the composite that comes after the delimiter.
     */
    public AbstractFilenameComposite getAfterComposite() {
        return afterComposite;
    }

    /**
     * Sets the composite that comes before the delimiter
     *
     * @param beforeComposite the composite that comes before the delimiter
     */
    public void setBeforeComposite(final AbstractFilenameComposite beforeComposite) {
        this.beforeComposite = beforeComposite;
    }

    /**
     * Returns the composite that comes before the delimiter
     *
     * @return the composite that comes before the delimiter
     */
    public AbstractFilenameComposite getBeforeComposite() {
        return beforeComposite;
    }

    /**
     * Sets the delimiter that splits the two halves of this composite
     *
     * @param delimiter delimiter that splits the two halves of this composite
     */
    public void setDelimiter(final String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * Returns the delimiter that splits the two halfs of this composite
     *
     * @return the delimiter that splits the two halfs of this composite
     */
    public String getDelimiter() {
        return delimiter;
    }

    public void setFrame(final AbstractID3v2Frame frame) {
        if (frame != null) {
            if (beforeComposite != null) {
                beforeComposite.setFrame(frame);
            }
            if (afterComposite != null) {
                afterComposite.setFrame(frame);
            }
        }
    }

    /**
     * Create the filename that this composite represents
     *
     * @return the filename that this composite represents
     */
    public String composeFilename() {
        final StringBuffer stringBuffer = new StringBuffer(128);
        if (beforeComposite != null) {
            stringBuffer.append(beforeComposite.composeFilename());
        }
        stringBuffer.append(delimiter);
        stringBuffer.append(' ');
        if (afterComposite != null) {
            stringBuffer.append(afterComposite.composeFilename());
        }
        return stringBuffer.toString();
    }

    public ID3v2_4 createId3Tag() {
        ID3v2_4 newTag = null;
        if (beforeComposite != null) {
            newTag = beforeComposite.createId3Tag();
        }
        if (afterComposite != null) {
            if (newTag != null) {
                newTag.append(afterComposite.createId3Tag());
            } else {
                newTag = afterComposite.createId3Tag();
            }
        }
        return newTag;
    }

    /**
     * Returns an iterator through each <code>FilenameToken</code> in this composite in the correct order for the file
     * name.
     *
     * @return an iterator through each <code>FilenameToken</code> in this composite
     */
    public Iterator iterator() {
        return new FilenameDelimiterIterator(this);
    }

    /**
     * Match all elements of this composite against the keywords for this class type found in
     * <code>TagOptionSingleton</code>. If the <code>FilenameToken</code> matches the keyword, the token's class is
     * set.
     *
     * @param id3v2FrameBodyClass Class of keywords to match against.
     */
    public void matchAgainstKeyword(final Class id3v2FrameBodyClass) {
        if (AbstractID3v2FrameBody.class.isAssignableFrom(id3v2FrameBodyClass)) {
            if (beforeComposite != null) {
                beforeComposite.matchAgainstKeyword(id3v2FrameBodyClass);
            }
            if (afterComposite != null) {
                afterComposite.matchAgainstKeyword(id3v2FrameBodyClass);
            }
        }
    }

    /**
     * Match all elements of this composite against the given tag. If any element of <code>matchTag</code> matches any
     * element of this tag's composite, then this tag's composite leaf node's class is set.
     *
     * @param matchTag Tag to match against
     */
    public void matchAgainstTag(final AbstractMP3Tag matchTag) {
        if (matchTag != null) {
            if (beforeComposite != null) {
                beforeComposite.matchAgainstTag(matchTag);
            }
            if (afterComposite != null) {
                afterComposite.matchAgainstTag(matchTag);
            }
        }
    }
}