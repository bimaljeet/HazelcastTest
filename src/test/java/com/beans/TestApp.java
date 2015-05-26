package com.beans;

/**
 * Created with IntelliJ IDEA.
 * User: bnayar
 * Date: 11/21/13
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */

import com.common.util.SpringForCmsWsTests;
import junit.framework.TestCase;

public class TestApp extends TestCase {

    HazelCastBean hazelCastBean;

    private TestApp() {
        hazelCastBean = (HazelCastBean) SpringForCmsWsTests.getInstance().getBean("hazelCastBean");
    }

    public TestApp( String testName ) {
        super( testName );
        hazelCastBean = (HazelCastBean) SpringForCmsWsTests.getInstance().getBean("hazelCastBean");
    }

    public void testMyConnection(){
        hazelCastBean.init();
        hazelCastBean.publishMessage("JUST_TEST", "hello");
    }
}
