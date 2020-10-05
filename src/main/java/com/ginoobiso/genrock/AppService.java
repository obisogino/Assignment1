package com.ginoobiso.genrock;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.ginoobiso.dto.Employee;
import com.ginoobiso.dto.Wrapper;
import com.google.gson.Gson;

/**
 * App Service to request and generate CSV File
 * 
 * @author gino
 *
 */
public class AppService {

	/* folder path where CSV File will be generated */
	final private static String FOLDER_PATH = "src/main/resources/";

	/**
	 * @param name      File name of CSV file
	 * @param employees the list of employees
	 */
	private void writeToFile(String name, List<Employee> employees) {
		ICsvBeanWriter beanWriter = null;
		final CsvPreference NUM_DELIMITER = new CsvPreference.Builder('"', '#', "\n").build();
		try {

			beanWriter = new CsvBeanWriter(new FileWriter(FOLDER_PATH + name), NUM_DELIMITER);
			String[] header = { "Employee Name", "Employee Salary", "Employee Age" };
			beanWriter.writeHeader(header);
			String[] fieldMapping = new String[] { "name", "salary", "age" };
			for (Employee e : employees) {
				beanWriter.write(e, fieldMapping);
			}

		} catch (IOException ex) {
			System.err.println("Error writing the CSV file: " + ex);

		} finally {
			if (beanWriter != null) {
				try {
					beanWriter.close();
				} catch (IOException ex) {
					System.err.println("Error closing the writer: " + ex);
				}
			}
			System.out.println("CSV File Generated. See " + FOLDER_PATH + " folder.");
		}
	}

	public void generateFileFromRequest() {
		String jsonString = sendGet();
		Wrapper wrapper = new Gson().fromJson(jsonString, Wrapper.class);
		if (wrapper.getStatus().equals("success")) {
			List<Employee> employees = wrapper.getData();
			List<Employee> tenToThirty = employees.stream().filter(Employee::isTenToThirty)
					.collect(Collectors.toList());
			List<Employee> thirtyToSixty = employees.stream().filter(Employee::isThirtyToSixty)
					.collect(Collectors.toList());
			List<Employee> sixtyToHundred = employees.stream().filter(Employee::isSixtyToHundred)
					.collect(Collectors.toList());

			writeToFile("employee_10_30.csv", tenToThirty);
			writeToFile("employee_30_60.csv", thirtyToSixty);
			writeToFile("employee_60_100.csv", sixtyToHundred);
		}
	}

	private String sendGet() {

		URL url;

		StringBuilder result = new StringBuilder();
		try {
			url = new URL("http://dummy.restapiexample.com/api/v1/employees");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();

		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return result.toString();

	}
}
