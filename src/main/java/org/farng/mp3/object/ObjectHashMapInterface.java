package org.farng.mp3.object;

import java.util.HashMap;
import java.util.Iterator;

public interface ObjectHashMapInterface {

    public HashMap getIdToString();

    public HashMap getStringToId();

    public Iterator iterator();
}