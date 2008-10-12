/**
 *  JCL (Jar Class Loader)
 *
 *  Copyright (C) 2008  Xeus Technologies
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 *  @author Kamran Zafar    
 *
 *  Contact Info:
 *  Email:  xeus.man@gmail.com
 *  Web:    http://xeustech.blogspot.com
 */

package xeus.jcl;

import java.util.ResourceBundle;

/**
 * General configuration
 * 
 * @author Kamran Zafar
 *
 */
public class Configuration {
    private static ResourceBundle bundle = ResourceBundle.getBundle("jcl");

    /**
     * Flag to ignore duplicate resources
     * 
     * @return boolean
     */
    public static boolean supressCollisionException() {
        return bundle.getString("jcl.suppressCollisionException")
                .equals("true") ? true : false;
    }
}
