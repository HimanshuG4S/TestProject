package resources;

import java.util.ArrayList;
import java.util.List;

import pojo.AddLocation;
import pojo.Location;

public class TestDataBuild {
	
	public AddLocation addPlacePayLoad(String name, String language, String address)
	{
		
		AddLocation al = new AddLocation();
		al.setAccuracy(50);
		al.setName(name);
		al.setPhone_number("(+91) 983 893 3937");
		al.setAddress(address);
		al.setWebsite("http://google.com");
		al.setLanguage(language);
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		al.setLocation(l);
		List<String> mylist = new ArrayList<String>();
		mylist.add("shoe park");
		mylist.add("shop");
		al.setTypes(mylist);
		return al;
	}

	public String deletePlacePayload(String placeId) {
		return "{\r\n\"place_id\":\""+placeId+"\"\r\n}";
	}
}
