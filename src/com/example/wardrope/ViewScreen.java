package com.example.wardrope;

import java.util.ArrayList;

import com.example.wardrope.DatabaseHandler;
import com.example.wardrope.HomeScreen;
import com.example.wardrope.Item;
import com.example.wardrope.ListViewAdapter;
import com.example.wardrope.R;
import com.example.wardrope.SearchScreen;
import com.example.wardrope.ViewScreen;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class ViewScreen extends Activity {

	ArrayList<Item> image_details;
	TextView title;
	String param1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_screen);
	


		DatabaseHandler dbHelper = new DatabaseHandler(
				this.getApplicationContext());
		Bundle bundle = this.getIntent().getExtras();
		param1 = bundle.getString("param1");
		setTitle(param1);

		if (param1.equalsIgnoreCase("All")) {
			image_details = dbHelper.getAllItems();
		}else if(param1.equalsIgnoreCase("searchItems"))
		{
			Item item = bundle.getParcelable("item");
			image_details = dbHelper.searchItem(item);
		}
		else {
			image_details = dbHelper.getCategoryWise(param1);

		}

		if (image_details.size() == 0) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// set title
			alertDialogBuilder.setTitle("Alert");

			if(param1.equalsIgnoreCase("searchItems"))
			{
				alertDialogBuilder
				.setMessage("No Items found.")
				.setCancelable(false)

				.setNegativeButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {

								Intent myIntent = new Intent(
										ViewScreen.this,
										SearchScreen.class);
								startActivity(myIntent);

								dialog.cancel();
							}
						});
			}
			else{
			// set dialog message
			alertDialogBuilder
					.setMessage("No Items Stored in this Category.")
					.setCancelable(false)

					.setNegativeButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									Intent myIntent = new Intent(
											ViewScreen.this,
											HomeScreen.class);
									startActivity(myIntent);

									dialog.cancel();
								}
							});
			}
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();

		}
		final ListView lv1 = (ListView) findViewById(R.id.listV_main);
		lv1.setAdapter(new ListViewAdapter(this, image_details));

	
	}
@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.view_screen, menu);
	return true;
}


}
