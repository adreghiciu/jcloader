/**
 *  JCL (Jar Class Loader)
 *
 *  Copyright (C) 2010  Xeus Technologies
 *
 *  This file is part of Jar Class Loader (JCL).
 *  Jar Class Loader (JCL) is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  JarClassLoader is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with JCL.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  @author Kamran Zafar
 *
 *  Contact Info:
 *  Email:  xeus.man@gmail.com
 *  Web:    http://xeustech.blogspot.com
 */

package org.xeustechnologies.jcl.context;

/**
 * Loads context from different sources
 * 
 * @author Kamran
 * 
 */
public interface JclContextLoader {
    /**
     * Loads the context
     */
    public void loadContext();

    /**
     * Clears the context
     */
    public void unloadContext();
}
