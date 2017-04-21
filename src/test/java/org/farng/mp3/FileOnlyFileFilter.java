/*
 * MP3 Tag library. It includes an implementation of the ID3 tags and Lyrics3
 * tags as they are defined at www.id3.org
 *
 * Copyright (C) Eric Farng 2004
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.farng.mp3;

import java.io.File;
import java.io.FileFilter;

/*
 * Created on Dec 20, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Eric Farng  TODO To change the template for this generated type comment go to Window - Preferences - Java -
 *         Code Style - Code Templates
 */
public class FileOnlyFileFilter implements FileFilter {

    /**
     * Default Constructor
     */
    public FileOnlyFileFilter() {
        super();

        // TODO Auto-generated constructor stub
    }

    /**
     * @see java.io.FileFilter#accept(java.io.File)
     */
    public boolean accept(File arg0) {
        return arg0.isFile();
    }
}
