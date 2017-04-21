package org.farng.mp3.filename;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class is an iterator for the <code>FilenameParenthesis</code> class.
 *
 * @author Eric Farng
 * @version $Revision: 1.1 $
 */
public class FilenameParenthesisIterator implements Iterator {

    /**
     * iterator after the parenthesis
     */
    private Iterator afterIterator = null;
    /**
     * iterator before the parenthesis
     */
    private Iterator beforeIterator = null;
    /**
     * iterator between the parenthesis
     */
    private Iterator middleIterator = null;

    /**
     * Creates a new FilenameParenthesisIterator object.
     *
     * @param filenameParenthesis FilenameParenthesis object to iterate through
     */
    public FilenameParenthesisIterator(final FilenameParenthesis filenameParenthesis) {
        super();
        if (filenameParenthesis.getBeforeComposite() != null) {
            beforeIterator = filenameParenthesis.getBeforeComposite().iterator();
        }
        if (filenameParenthesis.getMiddleComposite() != null) {
            middleIterator = filenameParenthesis.getMiddleComposite().iterator();
        }
        if (filenameParenthesis.getAfterComposite() != null) {
            afterIterator = filenameParenthesis.getAfterComposite().iterator();
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
        if (middleIterator != null) {
            nextFlag = nextFlag || middleIterator.hasNext();
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
        } else if (middleIterator != null && middleIterator.hasNext()) {
            return middleIterator.next();
        } else if (afterIterator != null && afterIterator.hasNext()) {
            return afterIterator.next();
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