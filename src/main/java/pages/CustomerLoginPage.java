package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class CustomerLoginPage {
    private final SelenideElement customersDropDown = $x("//select[@id='userSelect']");
    private final SelenideElement loginButton = $x("//button[@type='submit']");

    public CustomerLoginPage() {
    }

    public CustomerAccountPage logInAsCustomer(String fullName) {
        customersDropDown.click();
        SelenideElement customer = $x(String.format("//option[text()='%s']", fullName));
        customer.click();
        loginButton.click();
        return new CustomerAccountPage();
    }
}
