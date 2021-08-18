package com.udacity.jwdnd.course1.cloudstorage.autotests;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomeForm;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginForm;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTest {
    private final String HOST = "http://localhost:";

    @LocalServerPort
    private int port;
    private WebDriver driver;
    private HomeForm homeForm;



    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        driver.get(HOST + port + "/login");
        LoginForm loginForm = new LoginForm(driver);
        loginForm.setUsernameField("user1");
        loginForm.setPassWord("pass");
        loginForm.submit();
        Optional<String> result = loginForm.getErrorMessage();

        // check if login success
        // otherwise just fail whatever test going on.
        Assertions.assertTrue(result.isEmpty());
        Assertions.assertEquals("Home", driver.getTitle());
        homeForm = new HomeForm(driver);
    }

    @AfterEach
    public void afterEach() {
        if (driver != null) {
            driver.quit();
        }
        //clear homeForm Reference?
        homeForm = null;
    }

    private void goHome() {
        driver.get(HOST + port + "/home");
    }

    @Test
    public void getInitialCredList() {
        homeForm.selectCredentialsWindow();
        List<Credential> creds = homeForm.getCredentials();
        // System.out.println(creds);
        // check if each is a Note object
        Assertions.assertNotNull(creds);
        creds.parallelStream().forEach(Assertions::assertNotNull);
    }

    @Test
    public void createCredential() {
        // go to notes window and add
        homeForm.selectCredentialsWindow();
        Credential toAdd = Credential.buildPartialCredential(null, "www.google.com", "Benjo", "password123");
        homeForm.addCredential(toAdd);
        System.out.println("cred to add" + toAdd);

        // assert page jump to result page, and then has a successful result message
        Assertions.assertEquals("Result", driver.getTitle());
        Assertions.assertNotNull(driver.findElement(By.id("divSuccess")));
        Assertions.assertThrows(NoSuchElementException.class, ()->driver.findElement(By.id("divFailed")));

        // go home and check if the notes has been added
        goHome();
        homeForm.selectCredentialsWindow();
        List<Credential> updatedCredentials = homeForm.getCredentials();
        System.out.println("updated Cred " + updatedCredentials);
        Assertions.assertTrue(homeForm.credisIn(toAdd, updatedCredentials));
    }

    @Test
    public void updateCredential() {
        homeForm.selectCredentialsWindow();
        // Note toAdd = new Note(null, "NoteTitle", "This is a note added to description and this note will be used to test createNote and viewNotes", null);
        List<Credential> creds = homeForm.getCredentials();
        Credential toUpdate = creds.get(0);
        toUpdate.setUsername("euG");
        toUpdate.setDecryptedPass("modifiedPass");
        toUpdate.setUrl("eugslavia");
        homeForm.updateCred(toUpdate);

        // assert page jump to result page, and then has a successful result message
        Assertions.assertEquals("Result", driver.getTitle());
        Assertions.assertNotNull(driver.findElement(By.id("divSuccess")));
        Assertions.assertThrows(NoSuchElementException.class, ()->driver.findElement(By.id("divFailed")));

        // go home and check if the notes has been updated
        goHome();
        homeForm.selectCredentialsWindow();
        List<Credential> updatedNotes = homeForm.getCredentials();
        Assertions.assertTrue(homeForm.credisIn(toUpdate, updatedNotes));
    }

    @Test
    public void deleteCredential() {
        homeForm.selectCredentialsWindow();
        // Note toAdd = new Note(null, "NoteTitle", "This is a note added to description and this note will be used to test createNote and viewNotes", null);
        List<Credential> credentials = homeForm.getCredentials();
        Credential toDelete = credentials.get(0);
        homeForm.deleteCred(toDelete);

        // assert page jump to result page, and then has a successful result message
        Assertions.assertEquals("Result", driver.getTitle());
        Assertions.assertNotNull(driver.findElement(By.id("divSuccess")));
        Assertions.assertThrows(NoSuchElementException.class, ()->driver.findElement(By.id("divFailed")));

        // go home and check if the notes has been deleted
        goHome();
        homeForm.selectCredentialsWindow();
        List<Credential> updatedCred = homeForm.getCredentials();
        Assertions.assertFalse(homeForm.credisIn(toDelete, updatedCred));
    }
}
