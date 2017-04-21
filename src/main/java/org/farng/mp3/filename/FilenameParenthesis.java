package org.farng.mp3.filename;

import org.farng.mp3.AbstractMP3Tag;
import org.farng.mp3.TagOptionSingleton;
import org.farng.mp3.TagUtility;
import org.farng.mp3.id3.AbstractID3v2Frame;
import org.farng.mp3.id3.AbstractID3v2FrameBody;
import org.farng.mp3.id3.ID3v2_4;

import java.util.Iterator;

/**
 * This composite subclass is for parenthesis elements. It is different from the delimiter because it has three
 * children. The middle child includes the items contained within the parenthesis themselves.
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class FilenameParenthesis extends FilenameDelimiter {

    /**
     * composite that goes between the parenthesis
     */
    private AbstractFilenameComposite middleComposite = null;
    /**
     * open parenthesis
     */
    private String openDelimiter = null;

    /**
     * Creates a new FilenameParenthesis object.
     */
    public FilenameParenthesis() {
        super();
    }

    /**
     * Creates a new FilenameParenthesis object.
     */
    public FilenameParenthesis(final FilenameParenthesis copyObject) {
        super(copyObject);
        openDelimiter = copyObject.openDelimiter;
        middleComposite = (AbstractFilenameComposite) TagUtility.copyObject(copyObject.middleComposite);
    }

    public void setFrame(final AbstractID3v2Frame frame) {
        if (frame != null) {
            if (getBeforeComposite() != null) {
                getBeforeComposite().setFrame(frame);
            }
            if (middleComposite != null) {
                middleComposite.setFrame(frame);
            }
            if (getAfterComposite() != null) {
                getAfterComposite().setFrame(frame);
            }
        }
    }

    /**
     * Sets the composite that goes between the parenthesis
     *
     * @param middleComposite the composite that goes between the parenthesis
     */
    public void setMiddleComposite(final AbstractFilenameComposite middleComposite) {
        this.middleComposite = middleComposite;
    }

    /**
     * Returnsthe composite that goes between the parenthesis
     *
     * @return the composite that goes between the parenthesis
     */
    public AbstractFilenameComposite getMiddleComposite() {
        return middleComposite;
    }

    /**
     * Sets the opening parenthesis
     *
     * @param openDelimiter the opening parenthesis
     */
    public void setOpenDelimiter(final String openDelimiter) {
        this.openDelimiter = openDelimiter;
    }

    /**
     * Returns the opening parenthesis
     *
     * @return the opening parenthesis
     */
    public String getOpenDelimiter() {
        return openDelimiter;
    }

    /**
     * Reconstruct the filename that is represented by this composite.
     *
     * @return the filename that is represented by this composite.
     */
    public String composeFilename() {
        final StringBuffer stringBuffer = new StringBuffer(128);
        if (getBeforeComposite() != null) {
            stringBuffer.append(getBeforeComposite().composeFilename());
        }
        stringBuffer.append(' ');
        stringBuffer.append(openDelimiter);
        if (middleComposite != null) {
            stringBuffer.append(middleComposite.composeFilename());
        }
        stringBuffer.append(TagOptionSingleton.getInstance().getCloseParenthesis(openDelimiter));
        stringBuffer.append(' ');
        if (getAfterComposite() != null) {
            stringBuffer.append(getAfterComposite().composeFilename());
        }
        return stringBuffer.toString().trim();
    }

    public ID3v2_4 createId3Tag() {
        ID3v2_4 newTag = null;
        if (getBeforeComposite() != null) {
            newTag = getBeforeComposite().createId3Tag();
        }
        if (middleComposite != null) {
            if (newTag != null) {
                newTag.append(middleComposite.createId3Tag());
            } else {
                newTag = middleComposite.createId3Tag();
            }
        }
        if (getAfterComposite() != null) {
            if (newTag != null) {
                newTag.append(getAfterComposite().createId3Tag());
            } else {
                newTag = getAfterComposite().createId3Tag();
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
        return new FilenameParenthesisIterator(this);
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
            if (getBeforeComposite() != null) {
                getBeforeComposite().matchAgainstKeyword(id3v2FrameBodyClass);
            }
            if (middleComposite != null) {
                middleComposite.matchAgainstKeyword(id3v2FrameBodyClass);
            }
            if (getAfterComposite() != null) {
                getAfterComposite().matchAgainstKeyword(id3v2FrameBodyClass);
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
            if (getBeforeComposite() != null) {
                getBeforeComposite().matchAgainstTag(matchTag);
            }
            if (middleComposite != null) {
                middleComposite.matchAgainstTag(matchTag);
            }
            if (getAfterComposite() != null) {
                getAfterComposite().matchAgainstTag(matchTag);
            }
        }
    }
}