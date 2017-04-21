package org.farng.mp3.object;

import org.farng.mp3.TagUtility;

/**
 * ID3v2 and Lyrics3v2 tags have individual fields <code>AbstractMP3Fragment</code>s Then each fragment is broken down
 * in to individual <code>AbstractMP3Object</code>s
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class ObjectStringDate extends ObjectStringFixedLength {

    /**
     * Creates a new ObjectStringDate object.
     */
    public ObjectStringDate(final String identifier) {
        super(identifier, 8);
    }

    /**
     * Creates a new ObjectStringDate object.
     */
    public ObjectStringDate(final ObjectStringDate object) {
        super(object);
    }

    public void setValue(final Object value) {
        if (value != null) {
            this.value = TagUtility.stripChar(value.toString(), '-');
        }
    }

    public Object getValue() {
        if (this.value != null) {
            return TagUtility.stripChar(this.value.toString(), '-');
        }
        return null;
    }

    public boolean equals(final Object obj) {
        if (obj instanceof ObjectStringDate == false) {
            return false;
        }
        return super.equals(obj);
    }
}