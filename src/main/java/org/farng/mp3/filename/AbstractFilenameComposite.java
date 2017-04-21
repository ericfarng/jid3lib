package org.farng.mp3.filename;

import org.farng.mp3.AbstractMP3Tag;
import org.farng.mp3.id3.AbstractID3v2Frame;
import org.farng.mp3.id3.ID3v2_4;

import java.util.Iterator;

/**
 * The file name is parsed into a composite with this class as the base composite class.
 *
 * @author Eric Farng
 * @version $Revision: 1.3 $
 */
public abstract class AbstractFilenameComposite {

    /**
     * Keep a record of the original token that this composite is supposed to represent
     */
    private String originalToken = null;

    /**
     * Creates a new AbstractFilenameComposite object.
     */
    protected AbstractFilenameComposite() {
        super();
    }

    /**
     * Creates a new AbstractFilenameComposite object.
     */
    protected AbstractFilenameComposite(final AbstractFilenameComposite copyObject) {
        super();
        originalToken = copyObject.originalToken;
    }

    public abstract void setFrame(AbstractID3v2Frame frame);

    /**
     * Reconstruct the filename that is represented by this composite.
     *
     * @return the filename that is represented by this composite.
     */
    public abstract String composeFilename();

    /**
     * Returns an iterator through each <code>FilenameToken</code> in this composite in the correct order for the file
     * name.
     *
     * @return an iterator through each <code>FilenameToken</code> in this composite
     */
    public abstract Iterator iterator();

    /**
     * Match all elements of this composite against the keywords for this class type found in
     * <code>TagOptionSingleton</code>. If the <code>FilenameToken</code> matches the keyword, the token's class is
     * set.
     *
     * @param id3v2FrameBodyClass Class of keywords to match against.
     */
    public abstract void matchAgainstKeyword(Class id3v2FrameBodyClass);

    /**
     * Match all elements of this composite against the given tag. If any element of <code>matchTag</code> matches any
     * element of this tag's composite, then this tag's composite leaf node's class is set.
     *
     * @param matchTag Tag to match against
     */
    public abstract void matchAgainstTag(AbstractMP3Tag matchTag);

    /**
     * Sets the original string that this composite represents.
     *
     * @param originalToken the original string that this composite represents.
     */
    public void setOriginalToken(final String originalToken) {
        this.originalToken = originalToken;
    }

    /**
     * Get the original string that this composite represents.
     *
     * @return the original string that this composite represents.
     */
    public String getOriginalToken() {
        return originalToken;
    }

    public abstract ID3v2_4 createId3Tag();
}