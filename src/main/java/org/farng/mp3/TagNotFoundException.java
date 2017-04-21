package org.farng.mp3;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Thrown if the tag or MP3 Frame Header isn't found. This is different from the <code>InvalidTagException</code>. Each
 * tag (or MP3 Frame Header) has an ID string or some way saying that it simply exists. If this string is missing,
 * <code>TagNotFoundException</code> is thrown. If the ID string exists, then any other error while reading throws an
 * <code>InvalidTagException</code>.
 *
 * @author Eric Farng
 * @version $Revision: 1.1 $
 */
public class TagNotFoundException extends TagException {

    /**
     * Creates a new TagNotFoundException object.
     */
    public TagNotFoundException() {
        super();
    }

    /**
     * Creates a new TagNotFoundException object.
     */
    public TagNotFoundException(final Throwable exception) {
        super(exception);
    }

    /**
     * Creates a new TagNotFoundException object.
     *
     * @param message the detail message.
     */
    public TagNotFoundException(final String message) {
        super(message);
    }

    /**
     * Creates a new TagNotFoundException object.
     */
    public TagNotFoundException(final String message, final Throwable exception) {
        super(message, exception);
    }

    private void writeObject(final ObjectOutputStream out) {
        throw new UnsupportedOperationException("Cannot write to Output Stream: " + out.toString());
    }

    private void readObject(final ObjectInputStream in) {
        throw new UnsupportedOperationException("Cannot read from Input Stream: " + in.toString());
    }
}