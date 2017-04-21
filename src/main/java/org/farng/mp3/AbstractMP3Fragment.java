package org.farng.mp3;

/**
 * This class represents parts of tags. It contains methods that they all use use. ID3v2 tags have frames. Lyrics3 tags
 * have fields. ID3v1 tags do not have parts. It also contains thier header while the body contains the actual
 * fragments.
 *
 * @author Eric Farng
 * @version $Revision: 1.2 $
 */
public abstract class AbstractMP3Fragment extends AbstractMP3FileItem {

    /**
     * actual data this fragment holds
     */
    private AbstractMP3FragmentBody body;

    /**
     * Creates a new AbstractMP3Fragment object.
     */
    protected AbstractMP3Fragment() {
        super();
    }

    /**
     * Creates a new AbstractMP3Fragment object.
     */
    protected AbstractMP3Fragment(final AbstractMP3FragmentBody body) {
        super();
        this.body = body;
    }

    /**
     * Creates a new AbstractMP3Fragment object.
     */
    protected AbstractMP3Fragment(final AbstractMP3Fragment copyObject) {
        super(copyObject);
        final AbstractMP3FragmentBody copyObjectBody = copyObject.getBody();
        body = (AbstractMP3FragmentBody) TagUtility.copyObject(copyObjectBody);
    }

    /**
     * Sets the body object for this fragment. The body object contains the actual information for the fragment.
     *
     * @param body the body object
     */
    public void setBody(final AbstractMP3FragmentBody body) {
        this.body = body;
    }

    /**
     * Returns the body object for this fragment. The body object contains the actual information for the fragment.
     *
     * @return the body object
     */
    public AbstractMP3FragmentBody getBody() {
        return body;
    }

    /**
     * Returns true if this object and it's body is a subset of the argument. This object is a subset if the argument is
     * the same class.
     *
     * @param object object to determine if subset of
     *
     * @return true if this object and it's body is a subset of the argument.
     */
    public boolean isSubsetOf(final Object object) {
        final boolean subsetOf;
        final AbstractMP3FragmentBody localBody = body;
        if (object == null) {
            subsetOf = false;
        } else if (!(object instanceof AbstractMP3Fragment)) {
            subsetOf = false;
        } else {
            final AbstractMP3FragmentBody superSetBody = ((AbstractMP3Fragment) object).getBody();
            if (localBody == null && superSetBody == null) {
                subsetOf = true;
            } else {
                subsetOf = localBody != null &&
                           superSetBody != null &&
                           localBody.isSubsetOf(superSetBody) &&
                           super.isSubsetOf(object);
            }
        }
        return subsetOf;
    }

    /**
     * Returns true if this object and its body equals the argument and its body. this object is equal if and only if
     * they are the same class and have the same <code>getIdentifier</code> id string.
     *
     * @param obj object to determine equality of
     *
     * @return true if this object and its body equals the argument and its body.
     */
    public boolean equals(final Object obj) {
        final boolean equals;
        if (obj instanceof AbstractMP3Fragment) {
            final AbstractMP3Fragment abstractMP3Fragment = (AbstractMP3Fragment) obj;
            final String equalsIdentifier = abstractMP3Fragment.getIdentifier();
            final String thisIdentifier = getIdentifier();
            if (thisIdentifier.equals(equalsIdentifier)) {
                final AbstractMP3FragmentBody equalsBody = abstractMP3Fragment.getBody();
                final AbstractMP3FragmentBody thisBody = getBody();
                if (thisBody.equals(equalsBody)) {
                    equals = super.equals(obj);
                } else {
                    equals = false;
                }
            } else {
                equals = false;
            }
        } else {
            equals = false;
        }
        return equals;
    }
}