package org.jpa.sql.injection.tester.test;

import java.util.logging.Logger;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jpa.sql.injection.tester.model.User;
import org.jpa.sql.injection.tester.service.UserManager;
import org.jpa.sql.injection.tester.util.Resources;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SqlInjectionTest {
   @Deployment
   public static Archive<?> createTestArchive() {
      return ShrinkWrap.create(WebArchive.class, "test.war")
            .addClasses(User.class, UserManager.class, Resources.class)
            .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            // Deploy our test datasource
            .addAsWebInfResource("test-ds.xml", "test-ds.xml");
   }

   @Inject
   UserManager userManager;

   @Inject
   Logger log;

   @Test 
   public void testAddGetUser() throws Exception {
      userManager.saveUser("niko");
      Assert.assertNotNull(userManager.getUserByName("niko"));
      userManager.deleteUserByName("niko");
      Assert.assertNull(userManager.getUserByName("niko"));
   }
   
   @Test 
   public void testAddGetUserWithInjection() throws Exception {
      userManager.saveUser("niko1");
      userManager.saveUser("niko2");
      userManager.saveUser("niko3");
      Assert.assertEquals("niko1", userManager.getUserByName("niko3' OR 'a'='a").getName());
   }
   
   @Test 
   public void testAddGetUserWithInjectionWithNamedQuery() throws Exception {
      userManager.saveUser("niko4");
      userManager.saveUser("niko5");
      userManager.saveUser("niko6");
      Assert.assertNull(userManager.getUserByNameWithNamedQuery("'niko6' OR 'a'='a'"));
   }
   
}
