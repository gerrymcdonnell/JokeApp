package com.example.jokeapp.xml;

import java.util.ArrayList;

import android.util.Log;

import com.example.jokeapp.business.Joke;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class JokeXMLReaderSax extends DefaultHandler {

	boolean currentElement = false;
	String currentValue = "";

	
	Joke myJoke;
	ArrayList<Joke> jokeList;

	
	public ArrayList<Joke> getjokeList() {
		return jokeList;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = true;

		if (qName.equals("jokeList")) {
			jokeList = new ArrayList<Joke>();
		} else if (qName.equals("Joke")) {
			myJoke = new Joke();
		}

	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		currentElement = false;

		
		//Log.v("uri",uri);
		//Log.v("localname",localName);
		Log.v("qname=",qName);
		
		
		if (qName.equalsIgnoreCase("sJoke"))
			myJoke.setsJoke(currentValue.trim());
		
		else if (qName.equalsIgnoreCase("spunchline"))
			myJoke.setsPunchline(currentValue.trim());
		
		else if(qName.equalsIgnoreCase("iCategoryID"))			
			myJoke.setsPunchline(currentValue.trim());
		
		else if (qName.equalsIgnoreCase("Joke"))
			   jokeList.add(myJoke);
		
		currentValue = "";
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (currentElement) {
			currentValue = currentValue + new String(ch, start, length);
		}

	}

}

