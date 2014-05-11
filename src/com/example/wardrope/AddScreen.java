package com.example.wardrope;

import java.util.ArrayList;

import com.example.wardrope.AddScreen;
import com.example.wardrope.Item;
import com.example.wardrope.DatabaseHandler;
import com.example.wardrope.R;
import com.example.wardrope.HomeScreen;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class AddScreen extends Activity implements OnItemSelectedListener {

	Spinner spinCategory;
	String category;
	// String selectedItem;
	String type;
	String stype;

	Spinner spinner2;
	ArrayAdapter<CharSequence> adapter1;
	ArrayAdapter<CharSequence> adapter2;
	ArrayAdapter<CharSequence> adapter3;
	Spinner spinner3;
	ArrayAdapter<CharSequence> adapter4;
	ArrayAdapter<CharSequence> adapter5;
	ArrayAdapter<CharSequence> adapter6;
	EditText txtDesc;
	EditText txtDate;

	EditText txtPrice;
	//Button btnStore;
	Button btnMatch;
	ArrayList<Item> dataArrayTemp = new ArrayList<Item>();

	String Desc = "";

	Bitmap bm;
	ImageButton iv;
	DatabaseHandler db = new DatabaseHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_screen);

		LinearLayout linear = new LinearLayout(this);
		linear.setOrientation(LinearLayout.VERTICAL);

		TextView textView = new TextView(this);
		textView.setText("Category:");
		linear.addView(textView);
		spinCategory = new Spinner(this);
		linear.addView(spinCategory);

		TextView textView1 = new TextView(this);
		textView1.setText("Description:");
		linear.addView(textView1);
		txtDesc = new EditText(this);
		linear.addView(txtDesc);

		TextView textView3 = new TextView(this);
		textView3.setText("Price:");
		linear.addView(textView3);
		txtPrice = new EditText(this);
		

		linear.addView(txtPrice);

		TextView textView4 = new TextView(this);
		textView4.setText("Date:");
		linear.addView(textView4);
		txtDate = new EditText(this);
		linear.addView(txtDate);

		TextView textView5 = new TextView(this);
		textView5.setText("Type:");
		linear.addView(textView5);
		spinner2 = new Spinner(this);
		linear.addView(spinner2);

		TextView textView6 = new TextView(this);
		textView6.setText("Sub Type:");
		linear.addView(textView6);
		spinner3 = new Spinner(this);
		linear.addView(spinner3);

		iv = new ImageButton(this);


		linear.addView(iv);
		iv.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				img(view);

			}
		});

		SharedPreferences preferences = getPreferences(MODE_PRIVATE);

		txtDesc.setText(preferences.getString("Desc", null));
		txtPrice.setText(preferences.getString("Price", null));
		txtDate.setText(preferences.getString("Date", null));

		btnMatch = new Button(this);
		btnMatch.setText("Match It");
		linear.addView(btnMatch);

		btnMatch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				matchIt(view);

			}
		});

		dataArrayTemp = getIntent()
				.getParcelableArrayListExtra("selectedItems");

		TextView valueTV1 = new TextView(this);
		TextView valueTV = new TextView(this);

		valueTV1.setText("Matched With:");
		valueTV.setText(" ");

		linear.addView(valueTV1);
		if (dataArrayTemp != null) {

			for (int i = 0; i < dataArrayTemp.size(); i++) {
				Item item = dataArrayTemp.get(i);

				Desc += item.get_desc() + "  ";
			}

			valueTV.setText(Desc);
			linear.addView(valueTV);

		}

//		btnStore = new Button(this);
//		btnStore.setText("Store");
//		btnStore.setWidth(59);
//		linear.addView(btnStore);
//
//		btnStore.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View view) {
//
//				storeItem();
//
//			}
//		});

		setContentView(linear);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.category2, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinCategory.setAdapter(adapter);
		spinCategory.setOnItemSelectedListener(this);

		adapter1 = ArrayAdapter.createFromResource(this, R.array.typeClothes,
				android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		adapter2 = ArrayAdapter.createFromResource(this, R.array.typeJewellery,
				android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		adapter3 = ArrayAdapter.createFromResource(this, R.array.typeFootwear,
				android.R.layout.simple_spinner_item);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		adapter4 = ArrayAdapter.createFromResource(this, R.array.stypeClothes,
				android.R.layout.simple_spinner_item);
		adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		adapter5 = ArrayAdapter.createFromResource(this, R.array.stypeJewelley,
				android.R.layout.simple_spinner_item);
		adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		adapter6 = ArrayAdapter.createFromResource(this, R.array.stypeFootwear,
				android.R.layout.simple_spinner_item);
		adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinCategory.setSelection(preferences.getInt("spinCat", 0));
		spinner2.setSelection(preferences.getInt("spinType", 0));
		spinner3.setSelection(preferences.getInt("spinSType", 0));

		spinCategory.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long rowId) {

				category = spinCategory.getItemAtPosition(position).toString();

				Log.d("CAT", category);
				if (category.equalsIgnoreCase("Apparel")) {

					spinner2.setAdapter(adapter1);
					spinner3.setAdapter(adapter4);

					spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View v, int position, long rowId) {

							type = spinner2.getItemAtPosition(position)
									.toString();
							Log.d("Type:", type);

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});

					spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View v, int position, long rowId) {

							stype = spinner3.getItemAtPosition(position)
									.toString();
							Log.d("Type:", stype);

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});

				}

				if (category.equalsIgnoreCase("Jewelry")) {

					spinner2.setAdapter(adapter2);
					spinner3.setAdapter(adapter5);

					spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View v, int position, long rowId) {

							type = spinner2.getItemAtPosition(position)
									.toString();
							Log.d("Type:", type);

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});
					spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View v, int position, long rowId) {

							stype = spinner3.getItemAtPosition(position)
									.toString();
							Log.d("Type:", stype);

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});

				}

				if (category.equalsIgnoreCase("Footwear")) {

					spinner2.setAdapter(adapter3);
					spinner3.setAdapter(adapter6);

					spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View v, int position, long rowId) {

							type = spinner2.getItemAtPosition(position)
									.toString();
							Log.d("Type:", type);

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});

					spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View v, int position, long rowId) {

							stype = spinner3.getItemAtPosition(position)
									.toString();
							Log.d("Type:", stype);

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});

				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

	}

	// }
	public void matchIt(View v) {

		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit(); // Put the values
																// from the UI
		String strDesc = txtDesc.getText().toString();
		editor.putString("Desc", strDesc);
		String strPrice = txtPrice.getText().toString();
		editor.putString("Price", strPrice);
		String strDate = txtDate.getText().toString();
		editor.putString("Date", strDate);

		int spinCat = spinCategory.getSelectedItemPosition();
		editor.putInt("spinCat", spinCat);
		int spinType = spinner2.getSelectedItemPosition();
		editor.putInt("spinType", spinType);
		int spinSType = spinner3.getSelectedItemPosition();
		editor.putInt("spinSType", spinSType);
		editor.commit();

		Intent myIntent = new Intent(AddScreen.this, MatchScreen.class);
		startActivityForResult(myIntent, 0);

	}

	public void storeItem() {

		//|| (bm != null && bm.getByteCount() == 0)
		
		if (txtDesc.getText().length() == 0 || txtDate.getText().length() == 0
				|| txtPrice.getText().length() == 0 ) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);

			// set title
			alertDialogBuilder.setTitle("Alert");

			// set dialog message
			alertDialogBuilder
					.setMessage("Fill All Information.")
					.setCancelable(false)

					.setNegativeButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, just close
									// the dialog box and do nothing
									dialog.cancel();
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		}

		else {

			String desc = txtDesc.getText().toString();
			String price = txtPrice.getText().toString();
			String date = txtDate.getText().toString();

			DatabaseHandler db = new DatabaseHandler(this);

			// Contact im = new Contact(bm,"img"+count);
			// db.createImgEntry(im);

			db.addItem(new Item(category, desc, price, date, type, stype),
					new Item(bm));

			int current_Id = 0;

			Item itm = new Item();

			itm = db.getLastID();

			current_Id = itm.get_id();

			if (dataArrayTemp != null) {
				int match_Id = 0;

				for (int i = 0; i < dataArrayTemp.size(); i++) {
					Item item = dataArrayTemp.get(i);

					Desc = item.get_desc();
					match_Id = item.get_id();

					db.addMatchItem(new Item(match_Id, current_Id));

				}
			}
			// Testtinggg
			// String id = Integer.toString(current_Id);
			// ArrayList<Item> matchedItems = new ArrayList();
			// Item i = new Item();
			// matchedItems = db.getMatched(id);
			// //matchedItems = db.getAll();
			//
			// for(int t = 0 ; t < matchedItems.size(); t++)
			// {
			//
			// i = matchedItems.get(t);
			//
			// int m = i.get_id();
			// int n = i .get_mid();
			// String nt = Integer.toString(m);
			// String nt2 = Integer.toString(n);
			//
			// Log.d("Valuesssss are 1", nt );
			// Log.d("Valuesssss are 2", nt2 );
			//
			//
			// }
			//

			SharedPreferences preferences = getPreferences(MODE_PRIVATE);

			SharedPreferences.Editor editor = preferences.edit(); // Put the
																	// values
			editor.clear();
			editor.commit();

			Intent myIntent = new Intent(AddScreen.this, HomeScreen.class);
			startActivity(myIntent);
		}
	}

	public void img(View V) {
		Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(i, 0);

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// count++;
		super.onActivityResult(requestCode, resultCode, data);

		bm = (Bitmap) data.getExtras().get("data");

		iv.setImageBitmap(bm);
		// imgBtnText.setText("");

	}

	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_screen, menu);
		return true;
	}
	
	
	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_save:
	    	storeItem();
	      break;
	    case R.id.action_discard:
	    	Intent myIntent = new Intent(AddScreen.this, HomeScreen.class);
			startActivityForResult(myIntent, 0);
	      break;
	    default:
	      break;
	    }

	    return true;
	  } 


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub

	}

}
