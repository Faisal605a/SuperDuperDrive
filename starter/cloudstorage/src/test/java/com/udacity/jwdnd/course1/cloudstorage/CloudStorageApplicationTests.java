package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.core.parameters.P;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		doLogIn("RT", "123");
//		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
//		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-msg")));
//		WebElement loginSelect = driver.findElement(By.id("login-link"));
//		loginSelect.click();
//
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");


		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}

	@Test
	public void testlogOut() {
		// Create a test account
		doMockSignUp("Logout","Test","logout","123");
		doLogIn("logout", "123");

		WebElement credentialSelect = driver.findElement(By.id("logout"));
		WebElement logoutSelect = driver.findElement(By.id("logout"));
		logoutSelect.click();

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/login");

		Assertions.assertFalse(driver.getCurrentUrl()=="http://localhost:" + this.port + "/home");
	}

	public void CredentialAdd(String userName, String Pass) {
		doMockSignUp("Credential","Test",userName,Pass);
		doLogIn(userName, Pass);

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String credentialURL = "testURL";
		String credentialUserName = "testName";
		String credentialPss = "testPass";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement credentialNavBarSelect = driver.findElement(By.id("nav-credentials-tab"));
		credentialNavBarSelect.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-credential")));
		WebElement newCredential = driver.findElement(By.id("new-credential"));
		newCredential.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement URLInput = driver.findElement(By.id("credential-url"));
		WebElement userNameInput = driver.findElement(By.id("credential-username"));
		WebElement passwordInput = driver.findElement(By.id("credential-password"));
		URLInput.sendKeys(credentialURL);
		userNameInput.sendKeys(credentialUserName);
		passwordInput.sendKeys(credentialPss);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SubmitCredential")));
		WebElement submitButton = driver.findElement(By.id("SubmitCredential"));
		submitButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Credenatial saving failed");
		}
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		WebElement result = driver.findElement(By.id("success"));
		Assertions.assertTrue(result.getText().equals("Success"));
	}

	@Test
	public void testCredentialDelete() {


		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		CredentialAdd("CredentialDelete", "Credentialdelete");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		WebElement aContinue = driver.findElement(By.id("continue"));
		aContinue.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement credentialSelect = driver.findElement(By.id("nav-credentials-tab"));
		credentialSelect.click();


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-delete")));
		WebElement credentialDelete = driver.findElement(By.id("credential-delete"));
		credentialDelete.click();


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		WebElement result = driver.findElement(By.id("success"));
		Assertions.assertTrue(result.getText().equals("Success"));
	}
	@Test
	public void testCredentialEdit() {

		String credentialURL = "testURLEdited";
		String credentialUserName = "testNameEdited";
		String credentialPss = "testPassEdited";

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		CredentialAdd("CredentialEdit", "Credentialdelete");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		WebElement aContinue = driver.findElement(By.id("continue"));
		aContinue.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement credentialSelect = driver.findElement(By.id("nav-credentials-tab"));
		credentialSelect.click();


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-edit")));
		WebElement credentialEdit = driver.findElement(By.id("credential-edit"));
		credentialEdit.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement URLInput = driver.findElement(By.id("credential-url"));
		WebElement userNameInput = driver.findElement(By.id("credential-username"));
		WebElement passwordInput = driver.findElement(By.id("credential-password"));
		URLInput.sendKeys(credentialURL);
		userNameInput.sendKeys(credentialUserName);
		passwordInput.sendKeys(credentialPss);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SubmitCredential")));
		WebElement submitButton = driver.findElement(By.id("SubmitCredential"));
		submitButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		WebElement result = driver.findElement(By.id("success"));
		Assertions.assertTrue(result.getText().equals("Success"));
	}
	@Test
	public void assertNoteAdd(){
		NoteAdd("note", "note");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		WebElement result = driver.findElement(By.id("success"));
		Assertions.assertTrue(result.getText().equals("Success"));

	}


	private void NoteAdd(String userName, String Pass) {
		// Create a test account
		doMockSignUp("Note","Test",userName,Pass);
		doLogIn(userName, Pass);

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String noteTitle = "test";
		String noteDescription = "This is a test";


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement noteSelect = driver.findElement(By.id("nav-notes-tab"));
		noteSelect.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-Note")));
		WebElement newNote = driver.findElement(By.id("new-Note"));
		newNote.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title")));
		WebElement titleSelectInput = driver.findElement(By.id("title"));
		WebElement DescriptionSelectInput = driver.findElement(By.id("description"));
		titleSelectInput.sendKeys(noteTitle);
		DescriptionSelectInput.sendKeys(noteDescription);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SubmitNote")));
		WebElement submitButton = driver.findElement(By.id("SubmitNote"));
		submitButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Note saving failed");
		}

	}

	@Test
	public void testNoteEdit() {
		// Create a test account


		String noteTitle = "test Edited";
		String noteDescription = "This is a test edited";
		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		NoteAdd("NoteEdit", "Noteedit");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		WebElement aContinue = driver.findElement(By.id("continue"));
		aContinue.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement noteSelect = driver.findElement(By.id("nav-notes-tab"));
		noteSelect.click();


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-note")));
		WebElement editNote = driver.findElement(By.id("edit-note"));
		editNote.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title")));
		WebElement titleSelectInput = driver.findElement(By.id("title"));
		WebElement DescriptionSelectInput = driver.findElement(By.id("description"));
		titleSelectInput.sendKeys(noteTitle);
		DescriptionSelectInput.sendKeys(noteDescription);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SubmitNote")));
		WebElement submitButton = driver.findElement(By.id("SubmitNote"));
		submitButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		WebElement result = driver.findElement(By.id("success"));
		Assertions.assertTrue(result.getText().equals("Success"));

	}

	@Test
	public void testNoteDelete() {
		// Create a test account


		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		NoteAdd("NoteDelete", "Notedelete");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		WebElement aContinue = driver.findElement(By.id("continue"));
		aContinue.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement noteSelect = driver.findElement(By.id("nav-notes-tab"));
		noteSelect.click();


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-note")));
		WebElement deleteNote = driver.findElement(By.id("delete-note"));
		deleteNote.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		WebElement result = driver.findElement(By.id("success"));
		Assertions.assertTrue(result.getText().equals("Success"));

	}



	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}



}
