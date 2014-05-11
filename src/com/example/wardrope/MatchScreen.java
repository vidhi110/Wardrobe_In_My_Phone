package com.example.wardrope;

import java.util.ArrayList;

import com.example.wardrope.Item;
import com.example.wardrope.ListViewAdapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.R.*;

public class MatchScreen extends Activity {

	ArrayList<Item> dataArray;
	// ArrayList<Item> dataArrayTemp;

	ArrayList<Item> dataArrayTemp = new ArrayList<Item>();

	String my_sel_items;
	String tempDesc;

	Button done;
	ListView lstView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_screen);

		DatabaseHandler db = new DatabaseHandler(this);

		dataArray = db.getAllItems();

		done = (Button) findViewById(R.id.button1);

		lstView = (ListView) findViewById(R.id.listV_main);
		lstView.setAdapter(new ListViewAdapter(this, dataArray));
		lstView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		// ArrayAdapter<String> adapter = new
		// ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,
		// listContent);

		lstView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		// lstView.setAdapter(adapter);

		// done.setOnClickListener(new Button.OnClickListener(){
		// public void onClick(View v) {
		//
		// String selected = "";
		//
		// int cntChoice = lstView.getCount();
		//
		// SparseBooleanArray sparseBooleanArray =
		// lstView.getCheckedItemPositions();
		//
		// for(int i = 0; i < cntChoice; i++){
		//
		// if(sparseBooleanArray.get(i)) {
		//
		// selected += lstView.getItemAtPosition(i).toString() + "\n";
		//
		// }
		// }
		//
		// Toast.makeText(MatchScreen.this,
		//
		// selected,
		//
		// Toast.LENGTH_LONG).show();
		// }});
		//

		lstView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView arg0, View arg1, int arg2,
					long arg3) {

				int cntChoice = lstView.getCount();

				String checked = "";
				String selected = "";

				Item item = new Item();

				String unchecked = "";
				SparseBooleanArray sparseBooleanArray = lstView
						.getCheckedItemPositions();

				for (int i = 0; i < cntChoice; i++) {

					if (sparseBooleanArray.get(i) == true) {
						item = (Item) lstView.getItemAtPosition(i);
						// String str = item.get_desc();

						selected += item.get_desc().toString() + "\n";

						Toast.makeText(MatchScreen.this,

						selected,

						Toast.LENGTH_LONG).show();

					}
					;

				}
			}
		});
	}

	// @SuppressWarnings("unchecked")
	public void selectDone(View v) {

		int cntChoice = lstView.getCount();

		String checked = "";
		// String selected="";

		Item item = new Item();

		String unchecked = "";
		SparseBooleanArray sparseBooleanArray = lstView
				.getCheckedItemPositions();

		for (int i = 0; i < cntChoice; i++) {

			if (sparseBooleanArray.get(i) == true) {
				item = (Item) lstView.getItemAtPosition(i);
				// String str = item.get_desc();
				item.set_id(item.get_id());
				item.set_category(item.get_category());

				item.set_desc(item.get_desc());

				dataArrayTemp.add(item);
				// selected += item.get_desc().toString() + "\n";

			}

		}
		// else if(sparseBooleanArray.get(i) == false)
		// {
		// unchecked+= lstView.getItemAtPosition(i).toString() + "\n";
		//
		// }

		Intent intent = new Intent()
				.setClass(MatchScreen.this, AddScreen.class);

		intent.putParcelableArrayListExtra("selectedItems", dataArrayTemp);
		startActivityForResult(intent, 0);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.match_screen, menu);
		return true;
	}

}
