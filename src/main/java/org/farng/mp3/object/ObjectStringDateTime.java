package org.farng.mp3.object;

/**
 * ID3v2 and Lyrics3v2 tags have individual fields <code>AbstractMP3Fragment</code>s Then each fragment is broken down
 * in to individual <code>AbstractMP3Object</code>s
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class ObjectStringDateTime extends ObjectStringSizeTerminated {

    /**
     * Creates a new ObjectStringDateTime object.
     */
    public ObjectStringDateTime(final String identifier) {
        super(identifier);
    }

    /**
     * Creates a new ObjectStringDateTime object.
     */
    public ObjectStringDateTime(final ObjectStringDateTime object) {
        super(object);
    }

    public void setValue(final Object value) {
        if (value != null) {
            this.value = value.toString().replace(' ', 'T');
        }
    }

    public Object getValue() {
        if (this.value != null) {
            return this.value.toString().replace(' ', 'T');
        }
        return null;
    }

    public boolean equals(final Object obj) {
        if (obj instanceof ObjectStringDateTime == false) {
            return false;
        }
        return super.equals(obj);
    }
}