package pages;


import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class CustomerAccountPage {

    private final SelenideElement depositButton = $x("//button[@ng-click='deposit()']");
    private final SelenideElement withdrawalButton = $x("//button[@ng-click='withdrawl()']");

    private final SelenideElement fieldForEnterTheMount = $x("//input[@ng-model='amount']");

    private final SelenideElement submitButton = $x("//button[@type='submit']");

    public CustomerAccountPage clickDepositButton() {
        depositButton.click();
        return new CustomerAccountPage();
    }

    public CustomerAccountPage clickWithdrawalButton() {
        withdrawalButton.click();
        return new CustomerAccountPage();
    }

    public String takeDeposit(int amount) {
        fieldForEnterTheMount.setValue(String.valueOf(amount));
        submitButton.click();
        return String.valueOf(amount);
    }

    public String withdrawalMoney(int amount) {
        fieldForEnterTheMount.setValue(String.valueOf(amount));
        submitButton.click();
        return String.valueOf(amount);
    }
}
