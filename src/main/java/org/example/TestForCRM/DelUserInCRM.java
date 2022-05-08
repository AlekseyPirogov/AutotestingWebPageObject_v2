package org.example.TestForCRM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DelUserInCRM extends AddUserInCRM {

   public DelUserInCRM(WebDriver driver) {
        super(driver);
    }

   @FindBy(xpath = "//tr[contains(.,'Harry')]/td[*]/button[contains(.,'Delete')]")
   private WebElement deleteUserHarry;
   @FindBy(xpath = "//tr[contains(.,'Ron')]/*/button[contains(.,'Delete')]")
   private WebElement deleteUserRon;
   @FindBy(xpath = "//tr[contains(.,'Neville')]/*/button[contains(.,'Delete')]")
   private WebElement deleteUserNeville;

    @FindBy(xpath = "//button[contains(.,'Remove') and @id='remove-sauce-labs-bike-light']")
    private WebElement removeInCartSauceLabsBikeLight;

   @Override
   public DelUserInCRM deleteUser(String firstName) {
       if(firstName.equals("Harry")) deleteUserHarry.click();
       if(firstName.equals("Ron")) deleteUserRon.click();
       if(firstName.equals("Neville")) deleteUserNeville.click();
       return this;
   }
}
