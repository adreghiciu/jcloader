package xeus.jcl.junit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

import xeus.jcl.JarClassLoader;
import xeus.jcl.exception.JclException;

@SuppressWarnings("all")
public class LoadTest extends TestCase {

	private static Logger logger = Logger.getLogger(LoadTest.class);

	public void testWithResourceName() throws IOException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, IllegalArgumentException,
			SecurityException, InvocationTargetException,
			NoSuchMethodException, JclException {
		JarClassLoader jc = new JarClassLoader(new String[] { "test-jcl.jar",
				"./test-classes" });

		Object testObj = jc.loadClass("xeus.jcl.test.Test").newInstance();
		assertNotNull(testObj);

		testObj.getClass().getDeclaredMethod("sayHello", null).invoke(testObj,
				null);
	}

	public void testWithClassFolder() throws IOException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, IllegalArgumentException,
			SecurityException, InvocationTargetException,
			NoSuchMethodException, JclException {
		JarClassLoader jc = new JarClassLoader(
				new String[] { "./test-classes" });

		Object testObj = jc.loadClass("xeus.jcl.test.Test").newInstance();
		assertNotNull(testObj);

		testObj.getClass().getDeclaredMethod("sayHello", null).invoke(testObj,
				null);
	}

	public void testWithUrl() throws IOException, InstantiationException,
			IllegalAccessException, ClassNotFoundException,
			IllegalArgumentException, SecurityException,
			InvocationTargetException, NoSuchMethodException, JclException,
			URISyntaxException {
		// URL url = new URL("http://localhost:8080/blank/test-jcl.jar");
		File f = new File("test-jcl.jar");

		JarClassLoader jc = new JarClassLoader(new URL[] { f.toURI().toURL() });
		Object testObj = jc.loadClass("xeus.jcl.test.Test").newInstance();
		assertNotNull(testObj);

		testObj.getClass().getDeclaredMethod("sayHello", null).invoke(testObj,
				null);
	}

	public void testWithInputStream() throws IOException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, IllegalArgumentException,
			SecurityException, InvocationTargetException,
			NoSuchMethodException, JclException {
		FileInputStream fis = new FileInputStream("test-jcl.jar");
		JarClassLoader jc = new JarClassLoader(new FileInputStream[] { fis });
		Object testObj = jc.loadClass("xeus.jcl.test.Test").newInstance();
		assertNotNull(testObj);

		testObj.getClass().getDeclaredMethod("sayHello", null).invoke(testObj,
				null);
		fis.close();
	}

	public void testWithSpring() throws FileNotFoundException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, IllegalArgumentException,
			SecurityException, InvocationTargetException, NoSuchMethodException {
		XmlBeanFactory bf = new XmlBeanFactory(new FileSystemResource(
				"spring-test.xml"));
		Object testObj = bf.getBean("test");
		assertNotNull(testObj);

		testObj.getClass().getDeclaredMethod("sayHello", null).invoke(testObj,
				null);
	}

	public void testUnloading() throws IOException, InstantiationException,
			IllegalAccessException, ClassNotFoundException,
			IllegalArgumentException, SecurityException,
			InvocationTargetException, NoSuchMethodException, JclException {
		JarClassLoader jc = new JarClassLoader(new String[] { "./test-classes" });

		Object testObj = null;
		jc.loadClass("xeus.jcl.test.Test");
		jc.unloadClass("xeus.jcl.test.Test");

		try {
			testObj = jc.loadClass("xeus.jcl.test.Test").newInstance();
		} catch (ClassNotFoundException cnfe) {
			logger.debug(cnfe);
			testObj = null;
		}

		assertNull(testObj);
	}
}
