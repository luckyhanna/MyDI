package ro.teamnet.di;

import my.di.util.Bean;
import my.di.util.Scanner;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Hanna
 * Date: 11/10/13
 * Time: 5:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScannerTest {

    @Test
    public void testScanBeans() throws Exception {
        Map<String,Bean> beans = Scanner.scanBeans(MyConfiguration.class);
        Assert.assertNotNull(beans);

    }
}
