package xyzbank.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class ManagerOpenAccountPage extends ManagerHomePage {
    private final SelenideElement customersDropDown = $x("//select[@id='userSelect']");
    private final SelenideElement currencyDropDown = $x("//select[@id='currency']");

    private final SelenideElement submitButton = $x("//button[@type='submit']");

    private final String xpath = "//option[text()='%s']";

    public ManagerOpenAccountPage clickCustomerDropDown() {
        customersDropDown.click();
        return new ManagerOpenAccountPage();
    }

    public boolean isAccountNameDisplayed(String accountName) {
        SelenideElement element = $x(String.format(xpath, accountName));
        return element.isDisplayed();
    }

    public ManagerOpenAccountPage clickAccountNameAtDropDown(String accountName) {
        SelenideElement element = $x(String.format(xpath, accountName));
        element.click();
        return new ManagerOpenAccountPage();
    }

    public ManagerOpenAccountPage selectCurrency(String currencyValue) {
        currencyDropDown.click();
        SelenideElement element = $x(String.format("//option[@value='%s']", currencyValue));
        element.click();
        return new ManagerOpenAccountPage();
    }

    public void submitAccount() {
        submitButton.click();
    }
}
