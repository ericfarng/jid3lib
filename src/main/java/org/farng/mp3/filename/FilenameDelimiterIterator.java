package org.farng.mp3.filename;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class is an iterator for the <code>FilenameDelimiter</code> class.
 *
 * @author Eric Farng
 * @version $Revision: 1.1 $
 */
public class FilenameDelimiterIterator implements Iterator {

    /**
     * iterator for the composite before the delimiter
     */
    private Iterator afterIterator = null;
    /**
     * iterator for the composite after the delimiter
     */
    private Iterator beforeIterator = null;

    /**
     * Creates a new FilenameDelimiterIterator object.
     *
     * @param filenameDelimiter FilenameDelimiter to create an interator from.
     */
    public FilenameDelimiterIterator(final FilenameDelimiter filenameDelimiter) {
        super();
        if (filenameDelimiter.getBeforeComposite() != null) {
            beforeIterator = filenameDelimiter.getBeforeComposite().iterator();
        }
        if (filenameDelimiter.getAfterComposite() != null) {
            afterIterator = filenameDelimiter.getAfterComposite().iterator();
        }
    }

    /**
     * Returns true if the iteration has more elements. (In other words, returns true if next would return an element
     * rather than throwing an exception.)
     *
     * @return true if the iteration has more elements
     */
    public boolean hasNext() {
        boolean nextFlag = false;
        if (beforeIterator != null) {
            nextFlag = beforeIterator.hasNext();
        }
        if (afterIterator != null) {
            nextFlag = nextFlag || afterIterator.hasNext();
        }
        return nextFlag;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration.
     */
    public Object next() {
        if (beforeIterator != null && beforeIterator.hasNext()) {
            return beforeIterator.next();
        } else if (afterIterator != null && afterIterator.hasNext()) {
            return afterIterator.next();
        } else {
            throw new NoSuchElementException("Iteration has no more elements.");
        }
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