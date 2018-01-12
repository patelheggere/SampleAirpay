package com.example.sampleairpay_upi;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airpay.airpaysdk_simplifiedotp.AirpayActivity;
import com.airpay.airpaysdk_simplifiedotp.ResponseMessage;
import com.airpay.airpaysdk_simplifiedotp.Transaction;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

/*
import com.airpay.activity.AirpayActivity;
import com.airpay.activity.ResponseMessage;
import com.airpay.activity.Transaction;
*/

public class MainActivity extends Activity implements ResponseMessage,OnClickListener
{
	EditText emailId,phone_et,firstName,lastName,address,city,state,country,pincode,orderid,amount;
	Button nextButton;
	ResponseMessage resp;
	ArrayList<Transaction>transactionList;
	private String ErrorMessage="invalid";
	public boolean ischaracter;
	public boolean boolIsError_new = true;
	private ImageView img_down;
	private LinearLayout layout_address;
	private Dialog dialogconf;
	private Button btyes;
	private Button btno;
	private int k=0;


	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.payment_activity);
		
		emailId = (EditText)findViewById(R.id.emailId);
		phone_et = (EditText)findViewById(R.id.phone_et);
		firstName = (EditText)findViewById(R.id.firstName_et);
		lastName = (EditText)findViewById(R.id.lastName_et);
		address = (EditText)findViewById(R.id.address_et);
		city = (EditText)findViewById(R.id.city_et);
		state = (EditText)findViewById(R.id.state_et);
		country = (EditText)findViewById(R.id.country_et);
		pincode = (EditText)findViewById(R.id.pincode_et);
		orderid = (EditText)findViewById(R.id.orderId_et);
		amount = (EditText)findViewById(R.id.amount_et);
		layout_address = (LinearLayout)findViewById(R.id.layout_address);
		img_down = (ImageView)findViewById(R.id.img_down);
		nextButton = (Button)findViewById(R.id.nextButton);
		nextButton.setOnClickListener(this);
		img_down.setImageResource(R.drawable.drop_down);


		Display display = getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics ();
		display.getMetrics(outMetrics);

		int density  = (int)getResources().getDisplayMetrics().density;
		int dpHeight = outMetrics.heightPixels / density;
		int dpWidth  = outMetrics.widthPixels / density;


		dialogconf = new Dialog(this);
		dialogconf.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogconf.getWindow().setLayout(dpWidth, dpHeight);
		dialogconf.setCanceledOnTouchOutside(false);
		dialogconf.setContentView(R.layout.dialog_exit);

		//dialogconf.getWindow().getAttributes().windowAnimations = R.style.animationName;
		//	dialogconf.setAnimationStyle(R.style.animationName);
		btyes=(Button)dialogconf.findViewById(R.id.btdelete);
		btno=(Button)dialogconf.findViewById(R.id.btcancel);

		btyes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				finish();
				ActivityCompat.finishAffinity(MainActivity.this);

			}
		});
		btno.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

		//		dialogconf.dismiss();
			}
		});



		img_down.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if(layout_address.getVisibility() == View.VISIBLE)
				{
					layout_address.setVisibility(View.GONE);
					img_down.setImageResource(R.drawable.drop_down);


				}else
				{

					layout_address.setVisibility(View.VISIBLE);
					layout_address.animate().alpha(1.0f);
					img_down.setImageResource(R.drawable.drop_up);

				}
			}
		});

	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setMessage("Do you want to Exit?");
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//if user pressed "yes", then he is allowed to exit from application
				finish();
				ActivityCompat.finishAffinity(MainActivity.this);

			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//if user select "No", just cancel this dialog and continue with app
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

	}


	public void callback(ArrayList<Transaction> data, boolean flag)
	{
		if(data!= null)
		{}
	}

	private final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(

			"^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
					+ "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
					+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
					+ "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
					+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
					+ "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$");
	public static boolean checkEmail(String email)
	{
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}


	public void onClick(View v) 
	{
		if(v.getId() == R.id.nextButton)
		{

			if(emailId.getText().toString().equalsIgnoreCase("") && phone_et.getText().toString().equalsIgnoreCase(""))
			{
				Toast.makeText(getApplicationContext(),"Please Enter Email Address Or Phone No", Toast.LENGTH_LONG).show();
			}
			else if(!emailId.getText().toString().equalsIgnoreCase("") && phone_et.getText().toString().equalsIgnoreCase("") && !checkEmail(emailId.getText().toString()))
			{
				Toast.makeText(getApplicationContext(),"Please Enter Valid Email", Toast.LENGTH_LONG).show();
				emailId.setError("Please Enter Valid Email");
			}
			else if(emailId.getText().toString().equalsIgnoreCase("") && !phone_et.getText().toString().equalsIgnoreCase("") && phone_et.getText().toString().trim().length() < 5)
			{
				Toast.makeText(getApplicationContext(),"Phone No should be minimum 5 digit", Toast.LENGTH_LONG).show();
				phone_et.setError("Phone No should be minimum 5 digit");
			}
			else if(emailId.getText().toString().equalsIgnoreCase("") && !phone_et.getText().toString().equalsIgnoreCase("") && !PhoneNumberUtils.isGlobalPhoneNumber(phone_et.getText().toString().trim()) ||  phone_et.getText().toString().trim().contains(".") || phone_et.getText().toString().trim().contains("-"))
			{
				Toast.makeText(getApplicationContext(),"Please Enter Valid Phone No", Toast.LENGTH_LONG).show();
				phone_et.setError("Please Enter Valid Phone No");
			}
			else if(emailId.getText().toString().equalsIgnoreCase("") && !phone_et.getText().toString().equalsIgnoreCase("") && Float.parseFloat(phone_et.getText().toString().trim())== 0)
			{
				Toast.makeText(getApplicationContext(),"Phone No should not be zero", Toast.LENGTH_LONG).show();
				phone_et.setError("Phone No should not be zero");
			}
			else if(!emailId.getText().toString().trim().equalsIgnoreCase("") && !phone_et.getText().toString().trim().equalsIgnoreCase("")&& !checkEmail(emailId.getText().toString()))
			{
				Toast.makeText(getApplicationContext(),"Please Enter Valid Email", Toast.LENGTH_LONG).show();
				emailId.setError("Please Enter Valid Email");
			}
			else if(!emailId.getText().toString().trim().equalsIgnoreCase("") && !phone_et.getText().toString().trim().equalsIgnoreCase("")&& phone_et.getText().toString().trim().length() < 5)
			{
				Toast.makeText(getApplicationContext(),"Phone No should be minimum 5 digit", Toast.LENGTH_LONG).show();
				phone_et.setError("Phone No should be minimum 5 digit");
			}
			else if(!emailId.getText().toString().trim().equalsIgnoreCase("") && !phone_et.getText().toString().trim().equalsIgnoreCase("")&& !PhoneNumberUtils.isGlobalPhoneNumber(phone_et.getText().toString().trim()))
			{
				Toast.makeText(getApplicationContext(),"Please Enter Valid Phone No", Toast.LENGTH_LONG).show();
				phone_et.setError("Please Enter Valid Phone No");
			}
			else if(!emailId.getText().toString().trim().equalsIgnoreCase("") && !phone_et.getText().toString().trim().equalsIgnoreCase("")&& !PhoneNumberUtils.isGlobalPhoneNumber(phone_et.getText().toString().trim()) || phone_et.getText().toString().trim().contains(".") || phone_et.getText().toString().trim().contains("-"))
			{
				Toast.makeText(getApplicationContext(),"Please Enter Valid Phone No", Toast.LENGTH_LONG).show();
				phone_et.setError("Please Enter Valid Phone No");
			}
			else if(!emailId.getText().toString().trim().equalsIgnoreCase("") && !phone_et.getText().toString().trim().equalsIgnoreCase("")&& Float.parseFloat(phone_et.getText().toString().trim())== 0)
			{
				Toast.makeText(getApplicationContext(),"Phone No should not be zero", Toast.LENGTH_LONG).show();
				phone_et.setError("Phone No should not be zero");
			}
			//if(Float.parseFloat(phone_et.getText().toString().trim()) == 0)       Float.parseFloat(phone_et.getText().toString().trim()) == 0
			else if(firstName.getText().toString().equalsIgnoreCase(""))
			{
				Toast.makeText(getApplicationContext(),"Please Enter First Name", Toast.LENGTH_LONG).show();
				firstName.setError("Please Enter First Name");

			}
			else if(lastName.getText().toString().equalsIgnoreCase(""))
			{
				Toast.makeText(getApplicationContext(),"Please Enter Last Name", Toast.LENGTH_LONG).show();
				lastName.setError("Please Enter Last Name");

			}
			 else if(orderid.getText().toString().equalsIgnoreCase(""))
			{
				Toast.makeText(getApplicationContext(),"Please Enter Order Id", Toast.LENGTH_LONG).show();
				orderid.setError("Please Enter Order Id");

			}
			else if(amount.getText().toString().equalsIgnoreCase(""))
			{
				Toast.makeText(getApplicationContext(),"Please Enter Amount", Toast.LENGTH_LONG).show();
				amount.setError("Please Enter Amount");
			}else if(!isAlpha(firstName.getText().toString()))
			{
				Toast.makeText(getApplicationContext(), "Please Enter Valid First Name", Toast.LENGTH_LONG).show();
				firstName.setError("Please Enter Valid First Name");
			}
			else if(!isAlpha(lastName.getText().toString()))
			{
				Toast.makeText(getApplicationContext(), "Please Enter Valid Last Name", Toast.LENGTH_LONG).show();
				lastName.setError("Please Enter Valid Last Name");

			}
			else
				 {

					 /*if(amount.getText().toString().contains("0")) {
						 if(Float.parseFloat(amount.getText().toString().trim()) == 0)
						 {
							 Toast.makeText(getApplicationContext(), "Amount should not be zero", Toast.LENGTH_LONG).show();
							 boolIsError_new = true;
							 amount.setError("Amount should not be zero");
						 }
						 else {

							 Toast.makeText(getApplicationContext(),"Bhagyashree***************88", Toast.LENGTH_LONG ).show();
							 boolIsError_new = false;

						 }
					 }
					 else*/ if(amount.getText().toString().trim().contains("."))
			 			{
				 			String value=amount.getText().toString().trim();

				 			value=value.replace(".","##");
				 			if(value.contains("##"))
							{
								String[] arr=value.split("##");

							 if(arr.length>1)
							 {
						 		if (arr[1].length() > 2)
						 		{
								 Toast.makeText(getApplicationContext(), "Please Enter Valid Amount", Toast.LENGTH_LONG).show();
								 amount.setError("Please Enter Valid Amount");
									boolIsError_new = true;


								}
						 		else
								{
									if(Float.parseFloat(amount.getText().toString().trim()) == 0)
									{
										Toast.makeText(getApplicationContext(), "Amount should not be zero", Toast.LENGTH_LONG).show();
										boolIsError_new = true;
										amount.setError("Amount should not be zero");


									}else {
										boolIsError_new = false;
									}
								}
							 } else
							 {
						 		Toast.makeText(getApplicationContext(),"Please Enter an Amount upto 2 Decimal Places",Toast.LENGTH_LONG).show();
						 		amount.setError("Please Enter an Amount upto 2 Decimal Places");
								 boolIsError_new = true;


							 }
				 			}
					 	}else
				 		{
							if(Float.parseFloat(amount.getText().toString().trim()) == 0)
							{
								Toast.makeText(getApplicationContext(), "Amount should not be zero", Toast.LENGTH_LONG).show();
								boolIsError_new = true;
								amount.setError("Amount should not be zero");


							}else {
								boolIsError_new = false;
							}

						}


				 if(boolIsError_new == false)
				 {
					 Intent myIntent = new Intent(this, AirpayActivity.class);

					 Bundle b = new Bundle();

					 Log.e("", "*********** OnCreate Application ********** ");
					 //	Toast.makeText(getApplicationContext(),"*********** OnCreate Application ********** ",Toast.LENGTH_LONG).show();

					 b.putString("USERNAME", "8419743");
					 b.putString("PASSWORD", "JRLcAz5Y");
					 b.putString("SECRET", "74QpNYaT1oyqhxdL");
					 b.putString("MERCHANT_ID", "1");

					 Log.e("", "Email Id Tushar ---->>>>>> " + emailId.getText().toString().trim());
					 b.putString("EMAIL", emailId.getText().toString().trim());
					 b.putString("PHONE", "9611620128");
					 b.putString("FIRSTNAME", firstName.getEditableText().toString().trim());
					 b.putString("LASTNAME", lastName.getEditableText().toString().trim());
					 b.putString("ADDRESS", address.getEditableText().toString().trim());
					 b.putString("CITY", city.getEditableText().toString().trim());
					 b.putString("STATE", state.getEditableText().toString().trim());
					 b.putString("COUNTRY", country.getEditableText().toString().trim());
					 b.putString("PIN_CODE", pincode.getEditableText().toString().trim());
					 b.putString("ORDER_ID", orderid.getEditableText().toString().trim());
					 b.putString("AMOUNT", amount.getEditableText().toString().trim());
					 b.putString("MODE", "");
					 b.putString("CUSTVAR", "air payment");
					 b.putString("TXNSUBTYPE", "1");
					 b.putString("WALLET", "0");
					 b.putString("SUCCESS_URL", "http://www.theroadiesstore.in/airpay/transact/response");

					 b.putParcelable("RESPONSEMESSAGE", (Parcelable) resp);

					 myIntent.putExtras(b);
					 startActivityForResult(myIntent, 120);

				 }


			 }


			////

			emailId.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}

				public void afterTextChanged(Editable edt) {
					if (emailId.getText().length() > 0) {
						emailId.setError(null);
					}
				}

			});

			phone_et.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}

				public void afterTextChanged(Editable edt) {
					if (phone_et.getText().length() > 0) {
						phone_et.setError(null);
					}
				}

			});


			firstName.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}

				public void afterTextChanged(Editable edt) {
													 if (firstName.getText().length() > 0) {
														 firstName.setError(null);
													 }
												 }

											 });

			lastName.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}

				public void afterTextChanged(Editable edt) {
					if (lastName.getText().length() > 0) {
						lastName.setError(null);
					}
				}

			});

			orderid.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}

				public void afterTextChanged(Editable edt) {
					if (orderid.getText().length() > 0) {
						orderid.setError(null);
					}
				}

			});

			amount.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

				}

				public void afterTextChanged(Editable edt) {
					if (amount.getText().length() > 0) {
						amount.setError(null);
					}
				}

			});

			////

		}


	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		try 
		{
			Bundle bundle = data.getExtras();
			transactionList = new ArrayList<Transaction>();
			transactionList = (ArrayList<Transaction>)bundle.getSerializable("DATA");
			if(transactionList != null)
			{
				Toast.makeText(this, transactionList.get(0).getSTATUS()+"\n"+transactionList.get(0).getSTATUSMSG(), Toast.LENGTH_LONG).show();;

				System.out.println("A="+transactionList.get(0).getSTATUS()); // 200= success
				System.out.println("B="+transactionList.get(0).getMERCHANTKEY());
				System.out.println("C="+transactionList.get(0).getMERCHANTPOSTTYPE());
				System.out.println("D="+transactionList.get(0).getSTATUSMSG()); //  success or fail
				System.out.println("E="+transactionList.get(0).getTRANSACTIONAMT());
				System.out.println("F="+transactionList.get(0).getTXN_MODE());
				System.out.println("G="+transactionList.get(0).getMERCHANTTRANSACTIONID()); // order id
				System.out.println("H="+transactionList.get(0).getSECUREHASH());
				System.out.println("I="+transactionList.get(0).getCUSTOMVAR());
				System.out.println("J="+transactionList.get(0).getTRANSACTIONID());
				System.out.println("K="+transactionList.get(0).getTRANSACTIONSTATUS());
				
				
				Log.e("11111111111 ","A="+transactionList.get(0).getSTATUS()); // 200= success
				Log.e("22222222222 ","B="+transactionList.get(0).getMERCHANTKEY());
				Log.e("33333333333 ","C="+transactionList.get(0).getMERCHANTPOSTTYPE());
				Log.e("44444444444 ","D="+transactionList.get(0).getSTATUSMSG()); //  success or fail
				Log.e("55555555555 ","E="+transactionList.get(0).getTRANSACTIONAMT());
				Log.e("66666666666 ","F="+transactionList.get(0).getTXN_MODE());
				Log.e("77777777777 ","G="+transactionList.get(0).getMERCHANTTRANSACTIONID()); // order id
				Log.e("88888888888 ","H="+transactionList.get(0).getSECUREHASH());
				Log.e("99999999999 ","I="+transactionList.get(0).getCUSTOMVAR());
				Log.e("00000000000 ","J="+transactionList.get(0).getTRANSACTIONID());
				Log.e("45644646646 ","K="+transactionList.get(0).getTRANSACTIONSTATUS());
				
				
		        String transid = transactionList.get(0).getMERCHANTTRANSACTIONID();
		        String apTransactionID = transactionList.get(0).getTRANSACTIONID();
		    	String amount = transactionList.get(0).getTRANSACTIONAMT();
				String transtatus = transactionList.get(0).getTRANSACTIONSTATUS();
				String message = transactionList.get(0).getSTATUSMSG();
				String merchantid= "1" ; //provided by Airpay
				String username="8419743";		//provided by Airpay
				String sParam = transid + ":" + apTransactionID+ ":" + amount + ":" + transtatus + ":" + message + ":" + merchantid +":" + username;
				CRC32 crc = new CRC32();
		        crc.update(sParam.getBytes());
		        String sCRC = ""+crc.getValue();
		        System.out.println("Verified Hash= "+sCRC);


				
			}
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("","Error Message --- >>> "+e.getMessage());
		}
	}
	
	private String getProtocolDomain(String sURL)
	{
		int k = sURL.indexOf("/",sURL.indexOf("://")+3);
		return sURL.substring(0, k);
	}

	public boolean isAlpha(String name) {
		char[] chars = name.toCharArray();

		for (char c : chars) {
			if(!Character.isLetter(c)) {
				return false;
			}
		}

		return true;
	}


	/*private void createSecHash()
	{

	}
	*/
}