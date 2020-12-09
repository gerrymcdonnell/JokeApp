package com.example.jokeapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jokeapp.business.Joke;
import com.example.jokeapp.db.DatabaseHandler;
import com.example.jokeapp.util.MyRandomNumber;
import com.example.jokeapp.xml.JokeXMLReaderSax;



public class MainActivity extends Activity {
	
	DatabaseHandler db = new DatabaseHandler(this);
	MyRandomNumber myRandom;
	int iTotalJokes=-1;
	
	//ArrayList <String> myJokes=new ArrayList();
	
	ArrayList<Joke> jokeList=new ArrayList();
	
	final String JOKE_XML="JokeListData.xml";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		addHandlerforAddJokeButton();
		addHandlerforGetJokeButton();
		addHandlerforTestButton();
		
		iTotalJokes=loadJokesFromDB();
		
		Toast.makeText(this, "Found " + iTotalJokes,Toast.LENGTH_SHORT).show();	
		
		///Log.v("tot jokes",""+iTotalJokes);

	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		
		case R.id.action_delete_alljokes:
			db.deleteAllJokes();
			iTotalJokes=loadJokesFromDB();
			return true;
		
		case R.id.action_addjoke:
			testAddJoke();
			iTotalJokes=loadJokesFromDB();
			return true;
			
		case R.id.action_import_xml_jokes:
			parseXML();	
			reloadJokesFromDB();
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	

	private int loadJokesFromDB() {
		int iJokesinDB=-1;
		try{
			jokeList.clear();
			
			iJokesinDB=db.getJokeCount()-1;
			
			if(iJokesinDB>-1)
				myRandom = new MyRandomNumber(iJokesinDB,true);
			else
				return -1;
			
			//load jokes
			db.getAllJokes(jokeList);
			
			//Log.v("Load Jokes DB",""+jokeList.size());
			//Log.v("Joke 1",jokeList.get(0).getsJoke());
		}
		catch (Exception e)
		{
			Log.v("Error","loadJokesFromDB()");
			iJokesinDB=-1;
		}
		return iJokesinDB;
	}
	
	private void addHandlerforTestButton() {
		// TODO Auto-generated method stub
		//create action listeners for buttons
		Button btnAnswer1=(Button) findViewById(R.id.btnTest);
		btnAnswer1.setOnClickListener(new OnClickListener() 
		{
		    @Override          
		    public void onClick(View v) { 		    	
					parseXML();	
					reloadJokesFromDB();
		     }         
		});		
	}
	

	private void addHandlerforGetJokeButton() {
		// TODO Auto-generated method stub
		//create action listeners for buttons
		Button btnAnswer1=(Button) findViewById(R.id.btnGetJoke);
		btnAnswer1.setOnClickListener(new OnClickListener() 
		{
		    @Override          
		    public void onClick(View v) {              
		    	//testJoke();
		    	displayRandomJoke();
		    	printAllJokes();
		     }         
		});		
	}

	private void addHandlerforAddJokeButton() {
		// TODO Auto-generated method stub
		//create action listeners for buttons
		Button btnAnswer1=(Button) findViewById(R.id.btnAddJoke);
		btnAnswer1.setOnClickListener(new OnClickListener() 
		{
		    @Override          
		    public void onClick(View v) {              
		    	testAddJoke();
		    	reloadJokesFromDB();
		    	//printAllJokes();
		     }

         
		});		
	}
	
	private void reloadJokesFromDB() {
		// TODO Auto-generated method stub
    	iTotalJokes=loadJokesFromDB();
    	myRandom.setEndRange(iTotalJokes);    	
	}
	
	
	void printAllJokes(){
		for(Joke j:jokeList)
		{
			Log.v("joke",j.getsJoke());
		}
	}
	
	
	
	void displayJoke(int n){
		Joke j=new Joke();
		TextView txt=(TextView) findViewById(R.id.textJoke);
		
		ActionBar ab = getActionBar();
		ab.setSubtitle("Joke "+n);
		
		j=jokeList.get(n);
		txt.setText(j.getsJoke());
		
		Log.v("displayJoke() "+n,j.getsJoke());
	}
	
	
	
	void displayRandomJoke(){
		int n=myRandom.getNum();		
		
		displayJoke(n);
		
		Log.v("Joke #",""+n);
	}
	
	/*
	void testJoke(){
		String sJoke="The old lady was standing at the railing of the cruise ship holding her hat on tight so that it would not blow off in the wind."+
				"A gentleman approached her and said: 'Pardon me, madam. I do not intend to be forward, but did you know that your dress is blowing up in this high wind?"+
				"'Yes, I know,' said the lady, 'I need both hands to hold onto this hat.” “But, madam, you must know that your privates are exposed!” said the gentleman in earnest."+
				"The woman looked down, then back up at the man and replied, 'Sir, anything you see down there is 85 years old. I just bought this hat yesterday!'"+
				"The old lady was standing at the railing of the cruise ship holding her hat on tight so that it would not blow off in the wind."+
				"A gentleman approached her and said: 'Pardon me, madam. I do not intend to be forward, but did you know that your dress is blowing up in this high wind?"+
				"'Yes, I know,' said the lady, 'I need both hands to hold onto this hat.” “But, madam, you must know that your privates are exposed!” said the gentleman in earnest."+
				"The woman looked down, then back up at the man and replied, 'Sir, anything you see down there is 85 years old. I just bought this hat yesterday!'";
		
		displayJoke(sJoke);
		
		//db.addJoke(sJoke);
	}
	*/
	
	void testAddJoke(){
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Enter New Joke");
		alert.setMessage("Joke");
		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		Editable value = input.getText();
		 // Do something with value!
			Joke j=new Joke();
			//String sNewJoke=value.toString();
			db.addJoke(j);
		 }
		});
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		 public void onClick(DialogInterface dialog, int whichButton) {
		     // Canceled.
		}
		});
		alert.show();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	private void parseXML() {
		AssetManager assetManager = getBaseContext().getAssets();
		try {
			
			InputStream is = assetManager.open(JOKE_XML);			
			
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			JokeXMLReaderSax myXMLHandler = new JokeXMLReaderSax ();
			xr.setContentHandler(myXMLHandler);
			InputSource inStream = new InputSource(is);
			xr.parse(inStream);

			ArrayList<Joke> jokeList=new ArrayList();
			jokeList = myXMLHandler.getjokeList();
			
			Log.v("print jokes","printing "+jokeList.size()+" jokes");
			
			for (Joke j : jokeList) {
				Log.v("joke",j.getsJoke());	
				
				//add to database
				String sJoke=j.getsJoke();
				
				db.addJoke(sJoke);
			}

			is.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

}
