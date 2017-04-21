package org.farng.mp3.lyrics3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is a manual iterator for a Lyrics3v1 tag
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class Lyrics3v1Iterator implements Iterator {

    private Lyrics3v1 tag = null;
    private int lastIndex = 0;
    private int removeIndex = 0;

    /**
     * Creates a new Lyrics3v1Iterator object.
     */
    public Lyrics3v1Iterator(final Lyrics3v1 lyrics3v1Tag) {
        this.tag = lyrics3v1Tag;
    }

    public boolean hasNext() {
        return !((this.tag.getLyric().indexOf('\n', this.lastIndex) < 0) &&
                 (this.lastIndex > this.tag.getLyric().length()));
    }

    public Object next() {
        final int nextIndex = this.tag.getLyric().indexOf('\n', this.lastIndex);
        this.removeIndex = this.lastIndex;
        final String line;
        if (this.lastIndex >= 0) {
            if (nextIndex >= 0) {
                line = this.tag.getLyric().substring(this.lastIndex, nextIndex);
            } else {
                line = this.tag.getLyric().substring(this.lastIndex);
            }
            this.lastIndex = nextIndex;
        } else {
            throw new NoSuchElementException("Iteration has no more elements.");
        }
        return line;
    }

    public void remove() {
        final String lyric = this.tag.getLyric().substring(0, this.removeIndex) +
                             this.tag.getLyric().substring(this.lastIndex);
        this.tag.setLyric(lyric);
    }
}