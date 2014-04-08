package de.telekom.bmp.pages.account;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.openqa.selenium.support.FindBy;

import de.telekom.bmp.BmpApplication;
import de.telekom.testframework.selenium.Page;
import de.telekom.testframework.selenium.annotations.Path;
import de.telekom.testframework.selenium.controls.Button;
import de.telekom.testframework.selenium.controls.Form;
import de.telekom.testframework.selenium.controls.Link;
import de.telekom.testframework.selenium.controls.Select;
import de.telekom.testframework.selenium.controls.Text;
import de.telekom.testframework.selenium.controls.TextField;

/**
 * 
 * @author Tatiana
 */

@Singleton
@Path("/account/bills")
public class Bills extends Page {

	@Inject
	public Bills(BmpApplication app) {
		super(app);
	}

	@FindBy(xpath = ".//div[contains(@class, 'accountMenu')]//li[3]/a")
	public Link payments;

	@FindBy(xpath = ".//div[contains(@class, 'accountMenu')]//li[4]/a")
	public Form paymentDetails;

	@FindBy(xpath = ".//button[contains(@class, 'tableToggle show')]")
	public Button showFiltersBtn;

	@FindBy(xpath = ".//button[contains(@class, 'tableToggle hide')]")
	public Button hideFiltersBtn;

	@FindBy(xpath = ".//input[contains(@class, 'searchBox')]")
	public TextField searchInput;

	@FindBy(xpath = ".//table[contains(@class, 'item-table')]")
	public Form billsTable;

	@FindBy(xpath = ".//div[contains(@class, 'dropdown')]")
	public List<Select> dropdownFilter;

	@FindBy(xpath = ".//div[contains(@class, 'checkbox')]")
	public List<Select> checkboxFilter;

	@FindBy(xpath = ".//div[contains(@class, 'time')]")
	public List<Select> timeFilter;

	@FindBy(xpath = ".//th[contains(@class, 'sortable')]")
	public List<Select> sortingCriteria;

	// only for "Abrechnungsdetails

	@FindBy(xpath = ".//div[contains(@class, 'billingForm')]//h2")
	public Text title;

	@FindBy(xpath = ".//select[contains(@id, 'salutation')]")
	public Select salutation;

	@FindBy(xpath = ".//input[@id = 'company-name']")
	public TextField companyInput;

	@FindBy(xpath = ".//input[@id = 'first-name']")
	public TextField nameInput;

	@FindBy(xpath = ".//input[@id = 'last-name']")
	public TextField lastnameInput;

	@FindBy(xpath = ".//input[contains(@name, 'address')]")
	public TextField addressInput;

	@FindBy(xpath = ".//input[@id = 'postal-code']")
	public TextField postcodeInput;

	@FindBy(xpath = ".//input[@id = 'city']")
	public TextField cityInput;

	@FindBy(xpath = ".//select[contains(@id, 'countries')]")
	public List<Select> countries;

	@FindBy(xpath = ".//select[contains(@id, '%s')]")
	public Select country;

	@FindBy(xpath = ".//select[contains(@id, 'state')]")
	public List<Select> states;

	@FindBy(xpath = ".//select[contains(@id, '%s')]")
	public Select state;

	@FindBy(xpath = ".//input[@id = 'vat-id']")
	public TextField vatInput;

}
