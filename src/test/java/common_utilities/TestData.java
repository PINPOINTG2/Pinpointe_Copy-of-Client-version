package common_utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.DataProvider;

public class TestData {

	private Utilities utils = new Utilities();

	public ArrayList<String[]> getaddSubscriberData() {
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_postiveData"));
		return data;
	}

	public ArrayList<String[]> getCustomeFieldDataforAddSubscriber() {
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_postive_CustomfieldData"));
		return data;
	}

	public ArrayList<String[]>  getupdateSubscriberData() {
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_postiveData"));
		return data;
	}

	public ArrayList<String[]> getCustomeFieldDataforUpdateSubscriber() {
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_postive_CustomfieldData"));
		return data;
	}

	public ArrayList<String[]> getdeleteSubscriberData(){
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_postiveData"));
		return data;
	}

	public ArrayList<String[]> getSubscriberData(){
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_postiveData"));
		return data;
	}

	public ArrayList<String[]> getListsData(){
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Lists_postiveData"));
		return data;
	}

	public ArrayList<String[]> getTagsData(){
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Tags_postiveData"));
		return data;
	}

	public ArrayList<String[]> getCreateTagsData(){
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Tags_PostiveData_CreateTags"));
		return data;
	}

	@DataProvider(name = "getaddSubscriber_negData")
	public Object[] getaddSubscriber_negData() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_AddSubscribers_NegativeData"));
		Object[] obj = data.toArray();

		return obj;
	}

	@DataProvider(name = "addMultipleSubscriberBulkData")
	public Object[] addMultipleSubscriberBulkData() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_addMultipleSubscriberBulkData"));
		Object[] obj = data.toArray();

		return obj;
	}

	@DataProvider(name = "getSubscriber_negData")
	public Object[] getSubscriber_negData() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_GetSubscribers_NegativeData"));
		Object[] obj = data.toArray();

		return obj;
	}

	@DataProvider(name = "getupdateSubscriber_negData")
	public Object[] getupdateSubscriber_negData() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_UpdateSubscribers_NegativeData"));
		Object[] obj = data.toArray();

		return obj;
	}

	@DataProvider(name = "getDeleteSubscriber_negData")
	public Object[] getDeleteSubscriber_negData() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_DeleteSubscribers_NegativeData"));
		Object[] obj = data.toArray();

		return obj;
	}

	@DataProvider(name = "CheckListForSubscriber_negData")
	public Object[] CheckListForSubscriber_negData() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_CheckListForContact_NegativeData"));
		Object[] obj = data.toArray();

		return obj;
	}
	@DataProvider(name = "getUnsubscriber_negData")
	public Object[] getUnsubscriber_negData() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_Unsubscriber_NegativeData"));
		Object[] obj = data.toArray();

		return obj;
	}

	@DataProvider(name = "getCheckListForUnsubscriber_negData")
	public Object[] getCheckListForUnsubscriber_negData() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_CheckListForUnsubscriber_NegativeData"));
		Object[] obj = data.toArray();

		return obj;
	}

	@DataProvider(name = "getaddmultipleSubscribers_negData")
	public Object[] getaddmultipleSubscribers_negData() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_AddMultipleSubscribers_NegativeData"));
		Object[] obj = data.toArray();

		return obj;
	}

	@DataProvider(name = "getaddmultipleSubscribers")
	public Object[] getaddmultipleSubscribers() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_AddMultipleSubscribers"));
		Object[] obj = data.toArray();

		return obj;
	}

	@DataProvider(name = "getLists_Negative_FindLists")
	public Object[] getLists_Negative_FindLists() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Lists_Negative_FindLists"));
		Object[] obj = data.toArray();

		return obj;
	}

	@DataProvider(name = "getLists_Negative_GetListSubscribers")
	public Object[] getLists_Negative_GetListSubscribers() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Lists_Negative_GetListSubscribers"));
		Object[] obj = data.toArray();

		return obj;
	}

	@DataProvider(name = "getLists_Negative_GetCustomFieldStructure")
	public Object[] getLists_Negative_GetCustomFieldStructure() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Lists_Negative_GetCustomFieldStructure"));
		Object[] obj = data.toArray();

		return obj;
	}

	@DataProvider(name = "getLists_Negative_GetUsersCustomField")
	public Object[] getLists_Negative_GetUsersCustomField() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Lists_Negative_GetUsersCustomField"));
		Object[] obj = data.toArray();

		return obj;
	}

	@DataProvider(name = "gettags_Negative_TagSubscribers")
	public Object[] gettags_Negative_TagSubscribers() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Tags_Negative_TagSubscribers"));
		Object[] obj = data.toArray();

		return obj;
	}

	@DataProvider(name = "gettags_negative_GetTagSubscribers")
	public Object[] gettags_negative_GetTagSubscribers() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Tags_Negative_GetTagSubscribers"));
		Object[] obj = data.toArray();

		return obj;
	}


	@DataProvider(name = "gettags_Negative_untagSubscribers")
	public Object[] gettags_Negative_untagSubscribers() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Tags_Negative_UntagSubscribers"));
		Object[] obj = data.toArray();

		return obj;
	}

	@DataProvider(name = "gettags_Negative_CreateTags")
	public Object[] gettags_Negative_CreateTags() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Tags_Negative_CreateTags"));
		Object[] obj = data.toArray();

		return obj;
	}

	public ArrayList<String[]> 	getNegativeCreateTagsData(){
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Tags_Negative_CreateTags_Tags"));
		return data;
	}


	public ArrayList<String[]> getContactforAddMultipleSubscribers(){
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_Contact_AddMultipleSubscribers"));
		return data;
	}

	public ArrayList<String[]> getCustomeFieldforAddMultipleSubscriber(){
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_Custom_AddMultipleSubscribers"));
		return data;
	}

	public ArrayList<String[]> getContactforAddMultipleSubscribersBulkData(){
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_Contact_AddMultipleSubscribersBulkData"));
		return data;
	}

	public ArrayList<String[]> getCustomeFieldforAddMultipleSubscriberBulkData(){
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_Custom_AddMultipleSubscribersBulkData"));
		return data;
	}



	public ArrayList<String[]> getContactforAddMultipleSubscribers_negData(){
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_Contact_AddMultipleSubscribers_NegativeData"));
		return data;
	}

	public ArrayList<String[]> getCustomeFieldforAddMultipleSubscriber_negData(){
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_Custom_AddMultipleSubscribers_NegativeData"));
		return data;
	}


	public ArrayList<String[]> getCustomeFieldforAddSubscriber_negData(){
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_Custom_AddSubscribers_NegativeData"));
		return data;
	}


	public ArrayList<String[]> getCustomeFieldforupdateSubscriber_negData(){
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscriber_Custom_updateSubscribers_NegativeData"));
		return data;
	}


	public ArrayList<String[]> getContactforCreateTagsBulkData(){
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Tags_Bulk_CreateTags"));
		return data;



	}
	


	public ArrayList<String[]> getCopyContactData(){
		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "Subscribers_Postive_Copy_Contact_List"));
		return data;
	}
	

	public ArrayList<String[]> getContactDataforAddMultiSubscriber() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "AddmultiSubscribers_Contact"));

		return data;
	}

	public ArrayList<String[]> getCustomeFieldDataforAddMultiSubscriber() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "AddmultiSubscribers_Fields"));

		return data;
	}

	@DataProvider(name = "getSubscriberDataBulk")
	public Object[] getSubscriberDataBulk() {

		ArrayList<String[]> data = GetdataFromCSV(utils.getproperty("config", "GetSubscriberDataBulk"));
		Object[] obj = data.toArray();

		return obj;
	}

	public ArrayList<String[]> GetdataFromCSV(String dataFile) {
		ArrayList<String[]> data = new ArrayList<String[]>();
		int i=1;
		String Seperator = ",";/*System.getProperty("file.separator");*/

		FileReader file = null;
		try {
			file = new FileReader("testdata/" + dataFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader csvReader = new BufferedReader(file);
		String row =null;
		try {
			while ((row = csvReader.readLine()) != null) {  
				if(i!=1) {
					String[] dataStrings = row.split(Seperator);
					data.add(dataStrings);
				}

				i = i+1;
			}
			csvReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
