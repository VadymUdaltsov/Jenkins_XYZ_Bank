package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class ManagerAddAccountPage extends ManagerHomePage {
    private final SelenideElement firstNameField = $x("//input[@placeholder='First Name']");
    private final SelenideElement lastNameField = $x("//input[@placeholder='Last Name']");
    private final SelenideElement postCodeField = $x("//input[@placeholder='Post Code']");
    private final SelenideElement submitButton = $x("//button[@type='submit']");

    public ManagerAddAccountPage enterAccountDataAndSubmitAccount(String firstName, String lastName, String postCode) {
        firstNameField.setValue(firstName);
        lastNameField.setValue(lastName);
        postCodeField.setValue(postCode);
        submitButton.click();
        return new ManagerAddAccountPage();
    }
}
