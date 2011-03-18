package org.xeustechnologies.jcl.test;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class Test
    implements Serializable, TestInterface
{

    private static final long serialVersionUID = 1L;

    private String firstName;

    private String lastName;

    private static Logger logger = Logger.getLogger( Test.class );

    public Test()
    {
        firstName = "World";
        lastName = "";
    }

    public Test( String firstName )
    {
        this.firstName = firstName;
    }

    public String sayHello()
    {
        String hello = "Hello " + firstName + " " + lastName;

        if ( logger.isDebugEnabled() )
            logger.debug( "Hello " + firstName + " " + lastName );

        return hello;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }
}
