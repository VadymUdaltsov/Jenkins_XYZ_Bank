package xyzbank.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class ManagerCustomersPage extends ManagerHomePage {
    private final SelenideElement searchCustomerInput = $x("//input[@placeholder='Search Customer']");
    private final SelenideElement deleteAccountButton = $x("//button[@ng-click='deleteCust(cust)']");

    public void findAccount(String firstName) {
        searchCustomerInput.setValue(firstName);
    }

    public void clickDeleteButton() {
        deleteAccountButton.click();
    }
}
