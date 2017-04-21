package org.farng.mp3.filename;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class is an iterator for the <code>FilenameToken</code> class.
 *
 * @author Eric Farng
 * @version $Revision: 1.1 $
 */
public class FilenameTokenIterator implements Iterator {

    /**
     * Token class that this iterator is for
     */
    private FilenameToken filenameToken;
    /**
     * if true, then the token has already been returned and this iterator is done
     */
    private boolean returnedToken = false;

    /**
     * Creates a new FilenameTokenIterator object.
     *
     * @param filenameToken <code>FilenameToken</code> class that this iterator will iterate through.
     */
    public FilenameTokenIterator(final FilenameToken filenameToken) {
        super();
        this.filenameToken = filenameToken;
    }

    /**
     * Returns true if the iteration has more elements. (In other words, returns true if next would return an element
     * rather than throwing an exception.)
     *
     * @return true if the iteration has more elements
     */
    public boolean hasNext() {
        return !returnedToken;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration.
     */
    public Object next() {
        if (!returnedToken) {
            returnedToken = true;
            return filenameToken;
        }
        throw new NoSuchElementException("Iteration has no more elements.");
    }

    /**
     * This method is not supported in this iterator.
     *
     * @throws UnsupportedOperationException This method is not supported in this iterator
     */
    public void remove() {
        //todo Implement this java.util.Iterator method
        throw new UnsupportedOperationException("Method remove() not yet implemented.");
    }
}