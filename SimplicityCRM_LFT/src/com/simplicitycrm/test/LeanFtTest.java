package com.simplicitycrm.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hp.lft.report.ReportException;
import com.hp.lft.report.Reporter;
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
		// browser = BrowserFactory.launch(BrowserType.CHROME);
		browser = BrowserFactory.launch(BrowserType.FIREFOX);
	}

	@After
	public void tearDown() throws Exception {
		
		browser.close();
	}

	@Test
	public void test() throws GeneralLeanFtException {

		// Report
		try
		{
			Reporter.startReportingContext("SimplicityCRM LeanFT Test");
			Reporter.startTest("Login Logout Test");

			ReportStep("Navigate to Application","Navigate to the Application", this.LaunchApplication());
			ReportStep("Log In","Log In to the Application",this.TestLogin()); 
			ReportStep("Log Out","Log Out of the Application",this.TestLogout());
			
			Reporter.endTest();
			
			System.out.println(Reporter.generateReport());
	
		} catch (ReportException re) {
			re.printStackTrace();
		}
		
	}
	
	public void ReportStep(String name, String desc, boolean passed)
	{
		try
		{
			if (passed)
				Reporter.reportEvent(name,  desc, Status.Passed);
			else
				Reporter.reportEvent(name, desc, Status.Failed);
		}
		catch (ReportException re) {
			re.printStackTrace();
		}
	}
	
	public boolean LaunchApplication()
	{
		try
		{
			browser.navigate("http://swongdemo.cloudapp.net/sugarcrm");
			
			// Verify Login Button is shown on the screen.
			Button loginBtn = browser.describe(Button.class, new ButtonDescription.Builder()
					.buttonType("submit").tagName("INPUT").name("Log In").build());
			return loginBtn.exists();

		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean TestLogin()
	{
		try
		{
			EditField username = browser.describe(EditField.class, new EditFieldDescription.Builder()
					.type("text").tagName("INPUT").name("user_name").build());
			EditField password = browser.describe(EditField.class, new EditFieldDescription.Builder()
					.type("password").tagName("INPUT").name("user_password").build());
			Button loginBtn = browser.describe(Button.class, new ButtonDescription.Builder()
					.buttonType("submit").tagName("INPUT").name("Log In").build());
			username.setValue("admin");
			password.setSecure("56ce2c1d8de9cb7d81009affe807e0304dcff4c10b0d84cc0153b1c9");
			loginBtn.click();
			
			// Logout link is present only after successful login
			// Verification checks for the presence of the Logout link.
			Link logoutLink = browser.describe(Link.class, new LinkDescription.Builder()
					.tagName("A").innerText("Log Out").build());
			
			return logoutLink.exists();
							
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
	}


	public boolean TestLogout()
	{
		try {
			Link logoutLink = browser.describe(Link.class, new LinkDescription.Builder()
					.tagName("A").innerText("Log Out").build());
			logoutLink.click();
			
			// Login screen is shown after logout is successful
			// So check for the existence of the Login button after logpout
			Button loginBtn = browser.describe(Button.class, new ButtonDescription.Builder()
					.buttonType("submit").tagName("INPUT").name("Log In").build());
			return loginBtn.exists();		
			
		} catch (GeneralLeanFtException ge) {
			ge.printStackTrace();
			return false;
		}
		
	}
	
	
	
}
 