
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import data.Customer;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ManagerAddAccountPage;
import pages.ManagerCustomersPage;
import pages.LoginPage;
import pages.ManagerOpenAccountPage;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$x;
import static data.UserData.*;

public class VerifyAllActionsForAccountWhenLoginAsManagerTest extends BaseTest {
    private int customerNumberAtFile;
    private final List<Customer> customers;

    public VerifyAllActionsForAccountWhenLoginAsManagerTest() {
        this.customers = new ArrayList<>();
    }

    private void writeCustomerInfoToFile(String name, String lastName, String id) throws IOException {
        Customer customer = new Customer(name, lastName, id);
        FileWriter writer = new FileWriter("D:\\JavaProject\\XYZ_Bank\\src\\main\\java\\data\\Users", true);
        customerNumberAtFile++;
        writer.write(customerNumberAtFile + ": " + customer + '\n');
        writer.write(String.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)) + '\n');

        customers.add(customer);

        writer.close();
    }

    @Test
    public void verifyThatAccountIsCreated() {
        new LoginPage().openManagerPage()
                .openAddCustomerTab()
                .enterAccountDataAndSubmitAccount(FIRST_NAME, LAST_NAME, POST_CODE);
        new ManagerAddAccountPage().openOpenAccountTab()
                .clickCustomerDropDown();

        Assert.assertTrue(new ManagerOpenAccountPage().isAccountNameDisplayed(FULL_NAME),
                "Created account should be displayed");
    }

    @Test
    public void verifyThatAccountIsDeleted() {
        new LoginPage().openManagerPage()
                .openAddCustomerTab()
                .enterAccountDataAndSubmitAccount(FIRST_NAME, LAST_NAME, POST_CODE);
        new ManagerAddAccountPage().openOpenAccountTab()
                .clickCustomerDropDown()
                .clickAccountNameAtDropDown(FULL_NAME)
                .selectCurrency("Dollar")
                .submitAccount();
        String alertMessage = Selenide.switchTo()
                .alert()
                .getText()
                .replaceAll("[^0-9]", "");
        new ManagerCustomersPage().openCustomersTab().findAccount(alertMessage);

        SelenideElement customerID = $x(String.format("//tbody//tr//td//span[text()=%s]", alertMessage));
        if (customerID.isDisplayed()) {
            new ManagerCustomersPage().clickDeleteButton();
        }

        Assert.assertFalse(customerID.isDisplayed());
    }

    @Test
    public void writeCustomerInfoToFileAtPackageData() throws IOException {
        new LoginPage().openManagerPage()
                .openAddCustomerTab()
                .enterAccountDataAndSubmitAccount(FIRST_NAME, LAST_NAME, POST_CODE);
        new ManagerAddAccountPage().openOpenAccountTab()
                .clickCustomerDropDown()
                .clickAccountNameAtDropDown(FULL_NAME)
                .selectCurrency("Dollar")
                .submitAccount();
        String idFromAlertMessage = Selenide.switchTo()
                .alert()
                .getText()
                .replaceAll("[^0-9]", "");

        writeCustomerInfoToFile(FIRST_NAME, LAST_NAME, idFromAlertMessage);
        writeCustomerInfoToFile("Yaroslava", "Lebedynska", "2054");


        Assert.assertTrue(customers.contains(new Customer(FIRST_NAME, LAST_NAME, idFromAlertMessage)),
                "This user didn't find");
    }
}
