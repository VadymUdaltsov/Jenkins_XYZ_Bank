package pages;


import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {
    private final SelenideElement hpathButtonCustomer = $x("//button[@ng-click='customer()']");
    private final SelenideElement hpathButtonManager = $x("//button[@ng-click='manager()']");

    public LoginPage() {
        Selenide.open("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
    }

    public CustomerLoginPage openCustomerPage() {

        hpathButtonCustomer.click();
        return new CustomerLoginPage();
    }

    public ManagerHomePage openManagerPage() {
        hpathButtonManager.click();
        return new ManagerHomePage();
    }
}
