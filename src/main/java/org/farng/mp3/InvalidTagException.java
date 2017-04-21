package org.farng.mp3;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * An <code>InvalidTagException</code> is thrown if a parse error occurs while a tag is being read from a file. This is
 * different from a <code>TagNotFoundException</code>. Each tag (or MP3 Frame Header) has an ID string or some way
 * saying that it simply exists. If this string is missing, <code>TagNotFoundException</code> is thrown. If the ID
 * string exists, then any other error while reading throws an <code>InvalidTagException</code>.
 *
 * @author Eric Farng
 * @version $Revision: 1.1 $
 */
public class InvalidTagException extends TagException {

    /**
     * Creates a new InvalidTagException object.
     */
    public InvalidTagException() {
        super();
    }

    /**
     * Creates a new InvalidTagException object.
     */
    public InvalidTagException(final Throwable exception) {
        super(exception);
    }

    /**
     * Creates a new InvalidTagException object.
     *
     * @param message the detail message.
     */
    public InvalidTagException(final String message) {
        super(message);
    }

    /**
     * Creates a new InvalidTagException object.
     */
    public InvalidTagException(final String message, final Throwable exception) {
        super(message, exception);
    }

    private void writeObject(final ObjectOutputStream out) {
        throw new UnsupportedOperationException("Cannot write to Output Stream: " + out.toString());
    }

    private void readObject(final ObjectInputStream in) {
        throw new UnsupportedOperationException("Cannot read from Input Stream: " + in.toString());
    }
}