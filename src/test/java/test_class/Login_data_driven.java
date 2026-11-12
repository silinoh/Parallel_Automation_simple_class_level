package test_class;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base_class.Base_class;
import page_object.Login_page_object;
import utils.Excel_utils;

public class Login_data_driven extends Base_class {

	@BeforeClass
	public void openTestData() throws Exception {
		// Open Excel sheet
		Excel_utils.openExcel("Login");
	}

	@Test
	public void login_validation() {
		int row_count = Excel_utils.getRowCount();
		SoftAssert sa = new SoftAssert();

		for (int i = 1; i <= row_count; i++) {
			driver.get("https://practicetestautomation.com/practice-test-login/");
			String user_name = Excel_utils.getCellData(i, 0);
			String password = Excel_utils.getCellData(i, 1);
			String expected = Excel_utils.getCellData(i, 2);

			Login_page_object obj = new Login_page_object(driver);
			obj.enter_username(user_name);
			obj.enter_password(password);
			obj.click_submit();

			// validation
			String actual;

			// url check
			if (obj.get_url().contains("logged-in-successfully")) {
				actual = obj.get_url();
			}

			else {
				try {
					actual = obj.error_message();
				}

				catch (Exception e) {
					actual = e.getMessage();
				}
			}

			sa.assertEquals(actual, expected);

			// Write result to Excel -> actual result
			Excel_utils.setCellData(i, 3, actual);

			// Ternary or conditional operator
			// write into excel -> status
			String status = (expected.equalsIgnoreCase(actual)) ? "Pass" : "Fail";
			Excel_utils.setCellData(i, 4, status);

			System.out.println("Row " + i + ":" + actual + "--> " + status);
		}

		// outside for loop call assertall()
		sa.assertAll();
	}

	@AfterClass
	public void saveTestData() throws Exception {
		// Save and close Excel
		Excel_utils.saveAndClose();
	}

}
