package com.simplicitycrm.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hp.lft.report.ReportException;
import com.hp.lft.report.Status;
import com.hp.lft.sdk.*;
import com.hp.lft.sdk.web.Browser;
import com.hp.lft.sdk.web.BrowserFactory;
import com.hp.lft.sdk.web.BrowserType;
import com.hp.lft.sdk.web.Button;
import com.hp.lft.sdk.web.ButtonDescription;
import com.hp.lft.sdk.web.EditField;
import com.hp.lft.sdk.web.EditFieldDescription;
import com.hp.lft.sdk.web.Link;
import com.hp.lft.sdk.web.LinkDescription;
import com.hp.lft.unittesting.UnitTestBase;

import unittesting.*;

public class LeanFtTest extends UnitTestClassBase {

	private Browser browser;
	private boolean passed;
	
	public LeanFtTest() {
		//Change this constructor to private if you supply your own public constructor
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		instance = new LeanFtTest();
		globalSetup(LeanFtTest.class);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		globalTearDown();
	}

	@Before
	public void setUp() throws Exception {
		browser = BrowserFactory.launch(BrowserType.CHROME);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws GeneralLeanFtException {
		browser.navigate("http://swongdemo.cloudapp.net/sugarcrm");
		EditField username = browser.describe(EditField.class, new EditFieldDescription.Builder()
				.type("text").tagName("INPUT").name("user_name").build());
		EditField password = browser.describe(EditField.class, new EditFieldDescription.Builder()
				.type("password").tagName("INPUT").name("user_password").build());
		Button loginBtn = browser.describe(Button.class, new ButtonDescription.Builder()
				.buttonType("submit").tagName("INPUT").name("Log In").build());
		username.setValue("admin");
		password.setSecure("56ce2c1d8de9cb7d81009affe807e0304dcff4c10b0d84cc0153b1c9");
		loginBtn.click();
		
		Link logoutLink = browser.describe(Link.class, new LinkDescription.Builder()
				.tagName("A").innerText("Log Out").build());
		
		if (logoutLink.exists())
		{
			logoutLink.click();
			passed = true;
			browser.close();
			
		} else {
			// no logout link.
			passed = false;
		}
		
		
		// Report
		try {
			if (passed) {
				UnitTestBase.getReporter().reportEvent("Logout", "Logout Link found and clicked", Status.Passed);
			}
			else
			{
				UnitTestBase.getReporter().reportEvent("Logout", "Logout Link cannot be found", Status.Failed);
			}
		} catch (ReportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
 