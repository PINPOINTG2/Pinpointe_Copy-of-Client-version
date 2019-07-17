package com.api.pinpointe;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class TestRunner {

	static int totalOptions;
	static int option ;
	static String ServerURL ="";
	static String ServerUsername ="";
	static String ServerUserToken ="";
	static String executeFile ="";
	public static void main(String[] args) throws IOException {

		Properties properties = new Properties();

		String propertyFileLocation = "properties/config.properties";
		File proFile = new File(propertyFileLocation);
		FileInputStream propertyFileinput = new FileInputStream(proFile);
		properties.load(propertyFileinput);

		String propertyValue = properties.getProperty("api-url").trim();

		List<String> urls = Arrays.asList(propertyValue.split(","));
		System.out.println("Please Select the SERVER in which you want to Perform the Tests");
		System.out.println("===============================================");
		totalOptions = urls.size();
		for (int i = 0; i < totalOptions; i++) {
			System.out.println((i+1) +". "+urls.get(i).toLowerCase().trim());
		} 	
		totalOptions = totalOptions+1;
		System.out.println((totalOptions) +". Other");
		System.out.println("===============================================");
		System.out.println("Please ENTER your desired Option:");
		option = getserver();
		validateoption(option);
		if(option != totalOptions) {
			ServerURL = urls.get(option-1).toString().trim();
		}
		else {
			ServerURL = getdatafromConsole("url");
		}

		System.out.println("\n\n\nPlease Select the USER to use for the Tests");
		System.out.println("=================================================");

		propertyValue = properties.getProperty("username").trim();
		List<String> username = Arrays.asList(propertyValue.split(","));
		totalOptions = username.size();
		for (int i = 0; i < totalOptions; i++) {
			System.out.println((i+1) +". "+username.get(i).toLowerCase().trim());
		} 	
		totalOptions = totalOptions+1;
		System.out.println((totalOptions) +". Other");
		System.out.println("=================================================");
		System.out.println("Please ENTER your desired Option:");
		option = getserver();
		validateoption(option);
		if(option != totalOptions) {
			ServerUsername = username.get(option-1).toString().trim();
		}
		else {
			ServerUsername = getdatafromConsole("username");
		}


		System.out.println("\n\n\nPlease Select the MAGIC-TOKEN to use for the Tests");
		System.out.println("=================================================");

		propertyValue = properties.getProperty("api-token").trim();
		List<String> tokens = Arrays.asList(propertyValue.split(","));
		totalOptions = tokens.size();
		for (int i = 0; i < totalOptions; i++) {
			System.out.println((i+1) +". "+tokens.get(i).toLowerCase().trim());
		} 	
		totalOptions = totalOptions+1;
		System.out.println((totalOptions) +". Other");
		System.out.println("=================================================");
		System.out.println("Please ENTER your desired Option:");
		option = getserver();
		validateoption(option);

		if(option != totalOptions) {
			ServerUserToken = tokens.get(option-1).toString().trim();
		}
		else {
			ServerUserToken = getdatafromConsole("token");
		}

		List<String> files = xmlFiles();		
		System.out.println("\n\n\nPlease Select the TestNG TEST-SUITE which you want to Execute");
		System.out.println("=================================================");

		totalOptions = files.size();
		for (int i = 0; i < files.size(); i++) {
			System.out.println((i+1) +". "+files.get(i).toLowerCase().trim());
		} 	
		System.out.println("=================================================");
		System.out.println("Please ENTER your desired Option:");
		option = getserver();
		validateoption(option);
		executeFile = files.get(option-1).toString().trim();

		System.out.println("\n\n"
				+ "Selected Server: " + ServerURL
				+ "\nSelected Username: " + ServerUsername
				+ "\nSelected token: "+ ServerUserToken
				+ "\nTest Execute xml: "+ executeFile);

		System.out.println("\n\n=========================\nMaven run initiating.....\n=========================");
		runCode();
	}

	public static void runCode() {
		String command = "";

		String os = System.getProperty("os.name");
		System.out.println("\n\nOS  =" + os);
		if(os.toLowerCase().contains("windows")) {
			command = "cmd.exe /c start cmd.exe /k \"mvn clean test -Dtestfile=\""+ executeFile +"\" -Duri=\""+ ServerURL +"\" -Dusername=\""+ ServerUsername +"\" -Dtoken=\""+ ServerUserToken +"\" \"";
		}
		else if(os.toLowerCase().contains("linux") || os.toLowerCase().contains("centos")) {
			command = "mvn clean test -Dtestfile=TAGS_Positive_TestSuite.xml -Duri=https://ahub.ppt001.com/xml.php -Dusername=apitest -Dtoken=5055ba363472f8783b334e3cef596e73ab23e340";
		}
		String line;  
		try {
			Process p = Runtime.getRuntime().exec(command);
			System.out.println("Waiting for batch file ...");
			BufferedReader input =  
					new BufferedReader  
					(new InputStreamReader(p.getInputStream()));  
			while ((line = input.readLine()) != null) {  
//				System.out.println(line);  
			}  
		      input.close();  
				System.out.println("Batch file done.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

		public static void createmvnsh() {
			File file = new File("mvncode.sh");
			System.out.println("Created path: " + file.getAbsolutePath());
			if(file.exists()) {
				file.delete();
			}

			try{    
				FileWriter fw=new FileWriter(file);    
				fw.write("gnome-terminal -e \"mvn clean test -Dtestfile=\'"+ executeFile +"\' -Duri=\'"+ ServerURL +"\' -Dusername=\'"+ ServerUsername +"\' -Dtoken=\'"+ ServerUserToken +"\'\"");    
				fw.close();    
			}catch(Exception e){System.out.println(e);}    
			System.out.println("Success..."); 

		}

		private static String getdatafromConsole(String name) {
			String out = "";
			try {
				System.out.println("\n\n Enter the "+name+" :");
				BufferedReader reader =	new BufferedReader(new InputStreamReader(System.in));
				out = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return out;
		}

		private static List<String> xmlFiles() {
			List<String> files = new ArrayList<String>();
			File folder = new File("testrunner/");
			System.out.print(folder.getAbsolutePath());
			File[] listOfFiles = folder.listFiles();
			for(int i = 0; i < listOfFiles.length; i++){
				String filename = listOfFiles[i].getName();
				if(filename.endsWith(".xml")||filename.endsWith(".XML"))
				{
					files.add(filename);
				}
			}
			return files;
		}

		private static boolean validateoption(int opt) {
			boolean res = false;
			if(opt >totalOptions || opt <= 0) {
				System.out.println("\nIncorrect Options. Please Try valid Option. ");
				System.out.println("\nPlease select the option:");
				option = getserver();
				validateoption(option);
				res=false;
			}
			else {
				res = true;
			}
			return res;
		}

		private static int getserver() {
			int option = 0;
			try {
				BufferedReader reader =	new BufferedReader(new InputStreamReader(System.in));
				String api_uri = reader.readLine();
				option = Integer.parseInt(api_uri);
			} catch (NumberFormatException e) {
				System.out.println("\nInvalid option please try again: ");
				option = getserver();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return option;
		}
	}