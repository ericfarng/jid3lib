package org.farng.mp3.filename;

import org.farng.mp3.AbstractMP3Tag;
import org.farng.mp3.TagOptionSingleton;
import org.farng.mp3.id3.AbstractFrameBodyTextInformation;
import org.farng.mp3.id3.AbstractFrameBodyUrlLink;
import org.farng.mp3.id3.AbstractID3v2Frame;
import org.farng.mp3.id3.AbstractID3v2FrameBody;
import org.farng.mp3.id3.FrameBodyCOMM;
import org.farng.mp3.id3.ID3v2_4;
import org.farng.mp3.id3.ID3v2_4Frame;

import java.util.Iterator;

/**
 * This composite subclass is the leaf of the tree. It contains the actual strings found in the filename.
 *
 * @author Eric Farng
 * @version $Revision: 1.7 $
 */
public class FilenameToken extends AbstractFilenameComposite {

    /**
     * what information this object represents.
     */
    private Class id3v2FrameBodyClass = null;
    /**
     * token that this object represents
     */
    private String token = null;

    /**
     * Creates a new FilenameToken object.
     */
    public FilenameToken() {
        super();
    }

    /**
     * Creates a new FilenameToken object.
     */
    public FilenameToken(final FilenameToken copyObject) {
        super(copyObject);
        try {
            id3v2FrameBodyClass = id3v2FrameBodyClass.newInstance().getClass();
        } catch (IllegalAccessException ex) {
            throw new NullPointerException("IllegalAccessException: No access to run constructor to create copy " +
                                           ex.getMessage());
        } catch (InstantiationException ex) {
            throw new NullPointerException("InstantiationException: Unable to instantiate constructor to copy " +
                                           ex.getMessage());
        }
        token = copyObject.token;
    }

    public void setFrame(final AbstractID3v2Frame frame) {
        if (id3v2FrameBodyClass != null && id3v2FrameBodyClass.equals(frame.getBody().getClass())) {
            //todo add support for more tag matches. only doing text
            //      information and URL links right now because i'm lazy
            if (AbstractFrameBodyTextInformation.class.isInstance(frame.getBody())) {
                token = ((AbstractFrameBodyTextInformation) frame.getBody()).getText();
            } else if (AbstractFrameBodyUrlLink.class.isInstance(frame.getBody())) {
                token = ((AbstractFrameBodyUrlLink) frame.getBody()).getUrlLink();
            }
        }
    }

    /**
     * Sets the ID3v2 frame body that this token represents
     *
     * @param id3v2FrameBodyClass the ID3v2 frame body that this token represents
     */
    public void setId3v2FrameBodyClass(final Class id3v2FrameBodyClass) {
        this.id3v2FrameBodyClass = id3v2FrameBodyClass;
    }

    /**
     * Returns the ID3v2 frame body that this token represents
     *
     * @return the ID3v2 frame body that this token represents
     */
    public Class getId3v2FrameBodyClass() {
        return id3v2FrameBodyClass;
    }

    /**
     * Sets the token that this class contains
     *
     * @param token the token that this class contains
     */
    public void setToken(final String token) {
        this.token = token.trim();
    }

    /**
     * Return the token that this class contains
     *
     * @return the token that this class contains
     */
    public String getToken() {
        return token;
    }

    /**
     * Reconstruct the filename that is represented by this composite.
     *
     * @return the filename that is represented by this composite.
     */
    public String composeFilename() {
        return token;
    }

    public ID3v2_4 createId3Tag() {
        final ID3v2_4 newTag = new ID3v2_4();
        if (id3v2FrameBodyClass != null) {
            try {
                final AbstractID3v2FrameBody body = (AbstractID3v2FrameBody) id3v2FrameBodyClass.newInstance();

                //todo need to add support for more frame bodies here
                if (body instanceof AbstractFrameBodyTextInformation) {
                    ((AbstractFrameBodyTextInformation) body).setText(token);
                    ((AbstractFrameBodyTextInformation) body).setTextEncoding((byte) 0);
                } else if (body instanceof AbstractFrameBodyUrlLink) {
                    ((AbstractFrameBodyUrlLink) body).setUrlLink(token);
                } else if (body instanceof FrameBodyCOMM) {
                    ((FrameBodyCOMM) body).setText(token);
                    ((FrameBodyCOMM) body).setDescription("");
                    ((FrameBodyCOMM) body).setLanguage(TagOptionSingleton.getInstance().getLanguage());
                    ((FrameBodyCOMM) body).setTextEncoding(TagOptionSingleton.getInstance().getTextEncoding());
                }
                final ID3v2_4Frame frame = new ID3v2_4Frame();
                frame.setBody(body);
                newTag.setFrame(frame);
            } catch (IllegalAccessException ex) {
                // catch and dont' create the frame
            } catch (InstantiationException ex) {
                // catch and dont' create the frame
            }
        }
        return newTag;
    }

    /**
     * Returns an iterator through each <code>FilenameToken</code> in this composite in the correct order for the file
     * name.
     *
     * @return an iterator through each <code>FilenameToken</code> in this composite
     */
    public Iterator iterator() {
        return new FilenameTokenIterator(this);
    }

    /**
     * Match all elements of this composite against the keywords for this class type found in
     * <code>TagOptionSingleton</code>. If the <code>FilenameToken</code> matches the keyword, the token's class is
     * set.
     *
     * @param matchId3v2FrameBodyClass Class of keywords to match against.
     */
    public void matchAgainstKeyword(final Class matchId3v2FrameBodyClass) {
        if (AbstractID3v2FrameBody.class.isAssignableFrom(matchId3v2FrameBodyClass)) {
            if (TagOptionSingleton.getInstance().isCompositeMatchOverwrite() || id3v2FrameBodyClass == null) {
                final Iterator iterator = TagOptionSingleton.getInstance()
                        .getKeywordListIterator(matchId3v2FrameBodyClass);
                final String lowerCaseToken = token.toLowerCase();
                while (iterator.hasNext()) {
                    final String matchString = ((String) iterator.next()).toLowerCase();
                    if (matchString.equals(lowerCaseToken) ||
                        matchString.indexOf(lowerCaseToken) >= 0 ||
                        lowerCaseToken.indexOf(matchString) >= 0) {
                        setId3v2FrameBodyClass(matchId3v2FrameBodyClass);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Match all elements of this composite against the given tag. If any element of <code>matchTag</code> matches any
     * element of this tag's composite, then this tag's composite leaf node's class is set.
     *
     * @param matchTag Tag to match against
     */
    public void matchAgainstTag(final AbstractMP3Tag matchTag) {
        if (TagOptionSingleton.getInstance().isCompositeMatchOverwrite() || id3v2FrameBodyClass == null) {
            final ID3v2_4 tag;
            if (matchTag instanceof ID3v2_4) {
                tag = (ID3v2_4) matchTag;
            } else {
                tag = new ID3v2_4(matchTag);
            }
            final Iterator iterator = tag.iterator();
            AbstractID3v2Frame frame;
            AbstractID3v2FrameBody body;
            String matchString = null;
            final String lowerCaseToken = token.toLowerCase();
            while (iterator.hasNext()) {
                frame = (ID3v2_4Frame) iterator.next();
                body = (AbstractID3v2FrameBody) frame.getBody();
                //todo add support for more tag matches. only doing text
                //      information and URL links right now because i'm lazy
                if (body instanceof AbstractFrameBodyTextInformation) {
                    matchString = ((AbstractFrameBodyTextInformation) body).getText();
                    if (matchString != null) {
                        matchString = matchString.toLowerCase();
                    }
                } else if (body instanceof AbstractFrameBodyUrlLink) {
                    matchString = ((AbstractFrameBodyUrlLink) body).getUrlLink();
                    if (matchString != null) {
                        matchString = matchString.toLowerCase();
                    }
                } else if (body instanceof FrameBodyCOMM) {
                    matchString = ((FrameBodyCOMM) body).getText();
                }
                if (lowerCaseToken.equals(matchString) ||
                    matchString != null &&
                    (matchString.indexOf(lowerCaseToken) >= 0 || lowerCaseToken.indexOf(matchString) >= 0)) {
                    setId3v2FrameBodyClass(body.getClass());
                    break;
                }
            }
        }
    }

    /**
     * Returns a string containing debug information about this class
     *
     * @return a string containing debug information about this class
     */
    public String toString() {
        return id3v2FrameBodyClass + ": " + token;
    }
}