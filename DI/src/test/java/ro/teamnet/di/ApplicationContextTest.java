package ro.teamnet.di;

import my.di.container.ApplicationContext;
import my.di.container.ApplicationContextImpl;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: Hanna
 * Date: 11/13/13
 * Time: 1:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationContextTest {

    @Test
    public void testInitContainer() throws Exception {       
        ApplicationContext applicationContext = ApplicationContextImpl.factory(MyConfiguration.class);
        applicationContext.initContainer();
    }
}
