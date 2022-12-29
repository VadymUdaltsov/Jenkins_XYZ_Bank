package xyzbanktest;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import xyzbank.pages.LoginPage;
import xyzbank.pages.ManagerAddAccountPage;
import xyzbank.pages.ManagerCustomersPage;


import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static xyzbank.data.UserData.*;
import static xyzbank.data.UserData.POST_CODE;

public class VerifyAllActionsForAccountWhenLoginAsCustomerTest {
    private static final SelenideElement fieldWithBalanceOfMoney = $x("//strong[@class='ng-binding'][2]");
    private static final SelenideElement operationStatusMessage = $x("//span[@ng-show='message']");
    private String depositAmount;
    private String alertMessage;

    private void createCustomerForTests() {
        new LoginPage().openManagerPage()
                .openAddCustomerTab()
                .enterAccountDataAndSubmitAccount(FIRST_NAME, LAST_NAME, POST_CODE);
        new ManagerAddAccountPage().openOpenAccountTab()
                .clickCustomerDropDown()
                .clickAccountNameAtDropDown(FULL_NAME)
                .selectCurrency("Dollar")
                .submitAccount();
        alertMessage = Selenide.switchTo()
                .alert()
                .getText()
                .replaceAll("[^0-9]", "");
    }

    private void receiveDepositForCustomer() {
        depositAmount = new LoginPage().openCustomerPage()
                .logInAsCustomer(FULL_NAME)
                .clickDepositButton()
                .takeDeposit(2000);
    }

    private void deleteAccount() {
        new LoginPage().openManagerPage()
                .openCustomersTab()
                .findAccount(alertMessage);
        new ManagerCustomersPage().clickDeleteButton();
        SelenideElement customerID = $x(String.format("//tbody//tr//td//span[text()=%s]", alertMessage));
        Assert.assertFalse(customerID.isDisplayed());
    }

    @Test
    public void customerIsLogged() {
        createCustomerForTests();
        new LoginPage().openCustomerPage().logInAsCustomer(FULL_NAME);

        $(By.xpath(String.format("//span[text()='%s']", FULL_NAME))).shouldBe(visible, Duration.ofSeconds(10));

        Assert.assertEquals(WebDriverRunner.url(),
                "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/account");

        deleteAccount();
    }

    @Test
    public void depositWasReceived() {
        createCustomerForTests();
        receiveDepositForCustomer();

        Assert.assertTrue(operationStatusMessage.isDisplayed());
        Assert.assertEquals(operationStatusMessage.getText(),
                "Deposit Successful");
        Assert.assertEquals(fieldWithBalanceOfMoney.getText(), depositAmount);

        deleteAccount();
    }

    @Test
    public void successfulWithdrawlOperation() {
        createCustomerForTests();
        receiveDepositForCustomer();

        int balance = Integer.parseInt(fieldWithBalanceOfMoney.getText());

        new LoginPage().openCustomerPage()
                .logInAsCustomer(FULL_NAME)
                .clickWithdrawalButton()
                .withdrawalMoney(balance);

        Assert.assertTrue(operationStatusMessage.isDisplayed());
        Assert.assertEquals(operationStatusMessage.getText(),
                "Transaction successful");
        Assert.assertTrue(balance >= 0);

        deleteAccount();
    }

    @Test
    public void unSuccessfulWithdrawalOperation() {
        createCustomerForTests();
        receiveDepositForCustomer();

        int balance = Integer.parseInt(fieldWithBalanceOfMoney.getText());
        int amountForWithdrawal = balance + 1;

        new LoginPage().openCustomerPage()
                .logInAsCustomer(FULL_NAME)
                .clickWithdrawalButton()
                .withdrawalMoney(amountForWithdrawal);
        Assert.assertTrue(operationStatusMessage.isDisplayed());
        Assert.assertEquals(operationStatusMessage.getText(),
                "Transaction Failed. You can not withdraw amount more than the balance.");
        Assert.assertTrue(balance < amountForWithdrawal);

        deleteAccount();
    }
}
