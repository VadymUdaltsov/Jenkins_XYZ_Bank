package xyzbank.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class ManagerHomePage {
    private final SelenideElement addCustomerButton = $x("//button[@ng-click='addCust()']");
    private final SelenideElement openAccountButton = $x("//button[@ng-click='openAccount()']");

    private final SelenideElement customersListButton = $x("//button[@ng-click='showCust()']");


    public ManagerAddAccountPage openAddCustomerTab() {
        addCustomerButton.click();
        return new ManagerAddAccountPage();
    }

    public ManagerOpenAccountPage openOpenAccountTab() {
        openAccountButton.click();
        return new ManagerOpenAccountPage();
    }

    public ManagerCustomersPage openCustomersTab() {
        customersListButton.click();
        return new ManagerCustomersPage();
    }

}
