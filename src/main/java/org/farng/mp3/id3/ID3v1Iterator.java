package org.farng.mp3.id3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is a manual iterator for an ID3v2 tag
 *
 * @author Eric Farng
 * @version $Revision: 1.4 $
 */
public class ID3v1Iterator implements Iterator {

    private static final int TITLE = 1;
    private static final int ARTIST = 2;
    private static final int ALBUM = 3;
    private static final int COMMENT = 4;
    private static final int YEAR = 5;
    private static final int GENRE = 6;
    private static final int TRACK = 7;
    private ID3v1 id3v1tag;
    private int lastIndex = 0;

    /**
     * Creates a new ID3v1Iterator object.
     */
    public ID3v1Iterator(final ID3v1 id3v1tag) {
        this.id3v1tag = id3v1tag;
    }

    public boolean hasNext() {
        return hasNext(this.lastIndex);
    }

    public Object next() {
        return next(this.lastIndex);
    }

    public void remove() {
        switch (this.lastIndex) {
            case TITLE:
                this.id3v1tag.title = "";
            case ARTIST:
                this.id3v1tag.artist = "";
            case ALBUM:
                this.id3v1tag.album = "";
            case COMMENT:
                this.id3v1tag.comment = "";
            case YEAR:
                this.id3v1tag.year = "";
            case GENRE:
                this.id3v1tag.genre = -1;
            case TRACK:
                if (this.id3v1tag instanceof ID3v1_1) {
                    ((ID3v1_1) this.id3v1tag).track = -1;
                }
        }
    }

    private boolean hasNext(final int index) {
        switch (index) {
            case TITLE:
                return (this.id3v1tag.title.length() > 0) || hasNext(index + 1);
            case ARTIST:
                return (this.id3v1tag.artist.length() > 0) || hasNext(index + 1);
            case ALBUM:
                return (this.id3v1tag.album.length() > 0) || hasNext(index + 1);
            case COMMENT:
                return (this.id3v1tag.comment.length() > 0) || hasNext(index + 1);
            case YEAR:
                return (this.id3v1tag.year.length() > 0) || hasNext(index + 1);
            case GENRE:
                return (this.id3v1tag.genre >= 0) || hasNext(index + 1);
            case TRACK:
                if (this.id3v1tag instanceof ID3v1_1) {
                    return (((ID3v1_1) this.id3v1tag).track >= 0) || hasNext(index + 1);
                }
            default:
                return false;
        }
    }

    private Object next(final int index) {
        switch (this.lastIndex) {
            case 0:
                return (this.id3v1tag.title.length() > 0) ? this.id3v1tag.title : next(index + 1);
            case TITLE:
                return (this.id3v1tag.artist.length() > 0) ? this.id3v1tag.artist : next(index + 1);
            case ARTIST:
                return (this.id3v1tag.album.length() > 0) ? this.id3v1tag.album : next(index + 1);
            case ALBUM:
                return (this.id3v1tag.comment.length() > 0) ? this.id3v1tag.comment : next(index + 1);
            case COMMENT:
                return (this.id3v1tag.year.length() > 0) ? this.id3v1tag.year : next(index + 1);
            case YEAR:
                return (this.id3v1tag.genre >= 0) ? new Byte(this.id3v1tag.genre) : next(index + 1);
            case GENRE:
                return (this.id3v1tag instanceof ID3v1_1 && (((ID3v1_1) this.id3v1tag).track >= 0)) ?
                       new Byte(((ID3v1_1) this.id3v1tag).track) :
                       null;
            default:
                throw new NoSuchElementException("Iteration has no more elements.");
        }
    }
}