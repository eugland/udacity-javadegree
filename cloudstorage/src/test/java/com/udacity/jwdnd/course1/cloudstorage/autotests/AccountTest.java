package com.udacity.jwdnd.course1.cloudstorage.autotests;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginForm;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupForm;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountTest {
    private String HOST = "http://localhost:";

    @LocalServerPort
    private int port;
    private WebDriver driver;
    private LoginForm loginForm;
    private SignupForm signupForm;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        loginForm = new LoginForm(driver);
        signupForm = new SignupForm(driver);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null)
            driver.quit();
    }

    // return an error message or empty for success
    private Optional<String> tryLogin(String username, String password) {
        driver.get(HOST + port + "/login");
        loginForm.setUsernameField(username);
        loginForm.setPassWord(password);
        loginForm.submit();
        return loginForm.getErrorMessage();
    }

    private void trySignup(String fname, String lname, String username, String password) {
        driver.get(HOST + port + "/signup");
        signupForm.setFirstname(fname);
        signupForm.setLastname(lname);
        signupForm.setPassword(password);
        signupForm.setUsername(username);
        signupForm.submit();
    }

    @Test
    public void unauthRedirectToLoginTest() throws InterruptedException {
        driver.get(HOST + port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void nonExistingUserLoginFailure() throws InterruptedException {
        driver.get(HOST + port + "/login");
        Optional<String> error = tryLogin("xd", "td");
        System.out.println(error);
        Thread.sleep(1000);
        Assertions.assertEquals("Invalid username or password", error.get());
    }

    // Note this test will only work when the database init script is ran which provides a handful of
    // init accounts, credentials and a file
    @Test
    public void loginSuccess() {
        Optional<String> error = tryLogin("user1", "pass");
        System.out.println(error);
        Assertions.assertTrue(error.isEmpty());
    }

    @Test
    public void signUpAndLoginSuccess() {
        String username = "benjo", password = "password123";
        trySignup("Ben", "Johnson" , username, password);
        Optional<String> error = tryLogin(username, password);
        Assertions.assertTrue(error.isEmpty());
    }

    @Test
    public void signOut() throws Exception {
        Optional<String> error = tryLogin("user1", "pass");
        Assertions.assertTrue(error.isEmpty());

        driver.get(HOST + port + "/home");
        WebElement webElement = driver.findElement(By.id("btnLogout"));
        webElement.click();
        Assertions.assertEquals("Login", driver.getTitle());
        Thread.sleep(1000);
    }
}
