/**
 *  JCL (Jar Class Loader)
 *
 *  Copyright (C) 2006  Xeus Technologies
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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.log4j.Logger;

import xeus.jcl.exception.JclException;

/**
 * JarResources reads jar files and loads the class content/bytes in a HashMap
 * 
 * @author Kamran Zafar
 * 
 */
public class JarResources {

	private Map<String, byte[]> jarEntryContents;

	static Logger logger = Logger.getLogger(JarResources.class);

	/**
	 * @throws IOException
	 */
	public JarResources() {
		jarEntryContents = new HashMap<String, byte[]>();
	}

	/**
	 * @param name
	 * @return byte[]
	 */
	public byte[] getResource(String name) {
		return jarEntryContents.get(name);
	}

	/**
     * Reads the specified jar file
     * 
     * @param jarFile
     * @throws IOException
     * @throws JclException
     */
	public void loadJar(String jarFile) throws IOException, JclException {
		FileInputStream fis = new FileInputStream(jarFile);
		loadJar(fis);
		fis.close();
	}

	/**
	 * Reads the jar file from a specified URL
	 * 
	 * @param url
	 * @throws IOException
	 * @throws JclException 
	 */
	public void loadJar(URL url) throws IOException, JclException {
		InputStream in = url.openStream();
		loadJar(in);
		in.close();
	}

	/**
	 * Load the jar contents from InputStream
	 * 
	 * @throws IOException
	 * @throws JclException 
	 */
	public void loadJar(InputStream jarStream) throws IOException, JclException {

		BufferedInputStream bis = null;
		JarInputStream jis = null;

		try {
			bis = new BufferedInputStream(jarStream);
			jis = new JarInputStream(bis);

			JarEntry jarEntry = null;
			while ((jarEntry = jis.getNextJarEntry()) != null) {
			    logger.debug(dump(jarEntry));
			    
				if (jarEntry.isDirectory()) {
					continue;
				}
				
                if (jarEntryContents.containsKey(jarEntry.getName())) {
                    if (!Configuration.supressCollisionException())
                        throw new JclException("Class " + jarEntry.getName()
                                + " Already loaded");
                    else {
                        logger.debug("Class " + jarEntry.getName()
                                + " already loaded; ignoring entry...");
                        continue;
                    }
                }

				logger.debug("Entry Name: " + jarEntry.getName() + ","
						+ "Entry Size: " + jarEntry.getSize());

				int size = (int) jarEntry.getSize();

				byte[] b = new byte[(int) size];
				int rb = 0;
				int chunk = 0;
				while (((int) size - rb) > 0) {
					chunk = jis.read(b, rb, (int) size - rb);
					if (chunk == -1) {
						break;
					}
					rb += chunk;
				}

				// add to internal resource HashMap
				jarEntryContents.put(jarEntry.getName(), b);

				logger.debug(jarEntry.getName() + "  rb=" + rb + ",size="
						+ size + ",csize=" + jarEntry.getCompressedSize());
			}
		} catch (NullPointerException e) {
			logger.debug("Done loading.");
		} finally {
			jis.close();
			bis.close();
		}
	}

	/**
	 * For debugging
	 * 
	 * @param je
	 * @return String
	 */
	private String dump(JarEntry je) {
		StringBuffer sb = new StringBuffer();
		if (je.isDirectory()) {
			sb.append("d ");
		} else {
			sb.append("f ");
		}

		if (je.getMethod() == JarEntry.STORED) {
			sb.append("stored   ");
		} else {
			sb.append("defalted ");
		}

		sb.append(je.getName());
		sb.append("\t");
		sb.append("" + je.getSize());
		if (je.getMethod() == JarEntry.DEFLATED) {
			sb.append("/" + je.getCompressedSize());
		}

		return (sb.toString());
	}
}
