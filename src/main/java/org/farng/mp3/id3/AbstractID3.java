package org.farng.mp3.id3;

import org.farng.mp3.AbstractMP3Tag;

/**
 * Superclass for all ID3 tags
 *
 * @author Eric Farng
 * @version $Revision: 1.3 $
 */
public abstract class AbstractID3 extends AbstractMP3Tag {

    /**
     * Creates a new AbstractID3 object.
     */
    protected AbstractID3() {
        super();
    }

    /**
     * Creates a new AbstractID3 object.
     */
    protected AbstractID3(final AbstractID3 copyObject) {
        super(copyObject);
    }
}