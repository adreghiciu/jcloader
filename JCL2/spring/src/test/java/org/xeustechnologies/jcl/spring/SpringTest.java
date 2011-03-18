package org.xeustechnologies.jcl.spring;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest
{
    @Test
    public void testWithSpring()
        throws Exception
    {
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext( "classpath:spring-test.xml" );

        // Bean loaded with JCL
        Object test1 = appContext.getBean( "test1" );

        // Bean loaded with parent CL
        Object test2 = appContext.getBean( "test2" );

        assertEquals( "org.xeustechnologies.jcl.JarClassLoader", test1.getClass().getClassLoader().getClass().getName() );
        assertEquals( "sun.misc.Launcher$AppClassLoader", test2.getClass().getClassLoader().getClass().getName() );
    }
}
