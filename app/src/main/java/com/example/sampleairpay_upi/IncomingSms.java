package com.example.sampleairpay_upi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;

import com.airpay.airpaysdk_simplifiedotp.AirpayActivity;

import java.util.Arrays;
import java.util.List;


/**
 * Created by Tushar on 11/21/2015.
 */
public class IncomingSms extends BroadcastReceiver {

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();
    private String web_otp_value ="";
    private String axis_msg_otp = "";
    private Handler handler;
    private String sbi_msg_otp="";
    private String axis_final_value="";


    public void onReceive(Context context, Intent intent)
    {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {
                String message="";
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++)
                {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                     message += currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);


                } // end for loop
                

                if(message.contains(" "))
                {

                    String[] arr=message.split(" ");


                    for(int i=0;i<arr.length;i++)
                    {

                    }
                    if(arr.length > 3)
                    {
                        AirpayActivity.otp_final = ""+arr[2];

                        AirpayActivity.otp_value.setText("OTP Received");

                        AirpayActivity.dialogoption_hdfc_otp.dismiss();

                        if(AirpayActivity.bank_code.equalsIgnoreCase("ICICI"))
                        {
                            web_otp_value="txtAutoOtp";

                            String javascript="javascript: document.getElementById('"+web_otp_value+"').value="+arr[17]+";";

                            AirpayActivity.webView.loadUrl(javascript);

                            String javascript_submit_icici="javascript: submitPassword();";
                            AirpayActivity.webView.loadUrl(javascript_submit_icici);


                        }else if(AirpayActivity.bank_code.equalsIgnoreCase("HDFC"))
                        {
                            web_otp_value="txtOtpPassword";

                            String javascript="javascript: document.getElementById('"+web_otp_value+"').value="+arr[2]+";";

                            AirpayActivity.webView.loadUrl(javascript);

                            String javascript_submit = "javascript: document.getElementById('cmdSubmit').click();";
                            AirpayActivity.webView.loadUrl(javascript_submit);


                        }
                        else if(AirpayActivity.bank_code.equalsIgnoreCase("KOTAK"))
                        {
                            web_otp_value="otpValue";

                            String javascript_kotak="javascript: document.getElementById('"+web_otp_value+"').value="+arr[1]+";";

                            AirpayActivity.webView.loadUrl(javascript_kotak);

                            String javascript_submit = "javascript: document.getElementById('cmdSubmit').click();";
                            AirpayActivity.webView.loadUrl(javascript_submit);


                        } else if(AirpayActivity.bank_code.equalsIgnoreCase("CITY_BANK"))
                        {
                            web_otp_value="otp1";

                            String javascript_city_bank="javascript: document.getElementById('"+web_otp_value+"').value="+arr[1]+";";

                            AirpayActivity.webView.loadUrl(javascript_city_bank);


                        }
                        else if(AirpayActivity.bank_code.equalsIgnoreCase("HSBC"))
                        {
                            web_otp_value="password";

                            String hsbc_otp = arr[12];
                            String hsbc_otp_final = hsbc_otp.replace(".","");

                             //  String javascript="javascript: document.getElementByName('"+web_otp_value+"').value="+hsbc_otp_final+";";
                            String javascript="javascript: document.getElementsByName('"+web_otp_value+"')[0].value = "+hsbc_otp_final+";";
                            AirpayActivity.webView.loadUrl(javascript);

                            String javascript_submit = "javascript: document.getElementById('sendotp').click();";
                            AirpayActivity.webView.loadUrl(javascript_submit);

                        }else if(AirpayActivity.bank_code.equalsIgnoreCase("YES_BANK"))
                        {

                            web_otp_value="otpentrypin";

                            String yes_bank_otp = arr[0];

                            String yes_bank_otp_final = yes_bank_otp.replace(".","");

                            String javascript="javascript: document.getElementById('"+web_otp_value+"').value="+yes_bank_otp_final+";";
                            AirpayActivity.webView.loadUrl(javascript);

                            String javascript_checkbox="javascript: document.getElementById('disclaimer').checked=true;";
                            AirpayActivity.webView.loadUrl(javascript_checkbox);

                            String javascript_submit = "javascript: document.getElementById('sendotp').click();";
                            AirpayActivity.webView.loadUrl(javascript_submit);


                        }
                        else if(AirpayActivity.bank_code.equalsIgnoreCase("INDUSBANK"))
                        {
                            // Terms and condition - disclaimer

                            web_otp_value="otpentrypin";

                            String indus_otp = arr[12];

                            String indus_otp_final = indus_otp.replace(".","");

                            String javascript="javascript: document.getElementById('"+web_otp_value+"').value="+indus_otp_final+";";
                            AirpayActivity.webView.loadUrl(javascript);

                            String javascript_checkbox="javascript: document.getElementById('disclaimer').checked=true;";
                            AirpayActivity.webView.loadUrl(javascript_checkbox);

                            String javascript_submit = "javascript: document.getElementById('sendotp').click();";
                            AirpayActivity.webView.loadUrl(javascript_submit);


                        }

                        else if(AirpayActivity.bank_code.equalsIgnoreCase("MAHARASHTRA"))
                        {
                            // Terms and condition - disclaimer
                            web_otp_value="enterPIN";

                            String maha_otp = arr[49];

                            String maha_otp_final = maha_otp.replace(".","");

                            String javascript="javascript: document.getElementById('"+web_otp_value+"').value="+maha_otp_final+";";
                            AirpayActivity.webView.loadUrl(javascript);

                            String javascript_checkbox="javascript: document.getElementById('disclaimer').checked=true;";
                            AirpayActivity.webView.loadUrl(javascript_checkbox);

                            String javascript_submit = "javascript: document.getElementById('sendotp').click();";
                            AirpayActivity.webView.loadUrl(javascript_submit);


                        }else if(AirpayActivity.bank_code.equalsIgnoreCase("MAHARASHTRA_RUPAY"))
                        {

                            // Terms and condition - disclaimer
                            web_otp_value="1281088971-i-otp";

                            String maha_otp = arr[12];
                            String maha_otp_final = maha_otp.replace(".","");

                            String javascript_submit = "javascript: document.getElementById('1281088971-a-submit').click();";
                            AirpayActivity.webView.loadUrl(javascript_submit);


                        }else if(AirpayActivity.bank_code.equalsIgnoreCase("AXIS"))
                        {
                            web_otp_value="otpValue";

                            // String text = "Vodaphone 7875869896, your one time password fpr ecom is 123456. This OTP is usable for only onece. 7777777 & @ (";

                            String message_text = message.replaceAll("[^-?0-9]+", " ");
                            List Otp_data= Arrays.asList(message_text.trim().split(" "));
                            for (int j = 0; j < Otp_data.size(); j++) {
                                if (Otp_data.get(j).toString().length() == 6) {

                                    axis_final_value = "" + Otp_data.get(j);
                                }
                            }

/////////////////////////////////
                            String javascript="javascript: document.getElementById('"+web_otp_value+"').value="+axis_final_value+";";

                            AirpayActivity.webView.loadUrl(javascript);


                            String javascript_submit = "javascript: document.getElementById('cmdSubmit').click();";
                            AirpayActivity.webView.loadUrl(javascript_submit);

                            AirpayActivity.dialog_axis.dismiss();

                            handler.post(new Runnable() {

                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub

                                    AirpayActivity.dialogoption_hdfc_otp.show();

                                }
                            });

                        }
                        else if(AirpayActivity.bank_code.equalsIgnoreCase("AMERICAN_EXPRESS"))
                        {
                            // Not Tested - Tushar

                            web_otp_value="OTCTEXT";

                            String javascript="javascript: document.getElementById('"+web_otp_value+"').value="+arr[0]+";";

                            AirpayActivity.webView.loadUrl(javascript);

                            String javascript_onkeyDown="javascript: validateFieldOnKeyDown();";
                            AirpayActivity.webView.loadUrl(javascript_onkeyDown);

                            String javascript_submit_American="javascript: validateOTC();";
                            AirpayActivity.webView.loadUrl(javascript_submit_American);

                        }else if(AirpayActivity.bank_code.equalsIgnoreCase("SBI_VISA"))
                        {

                            // 19  for -->>> visa card message
                            web_otp_value="otp";

                            String[] sbi_bank_code=arr[19].split(".Don't");

                            for(int i=0;i<sbi_bank_code.length;i++)
                            {
                                sbi_msg_otp = sbi_bank_code[i];

                            }


                            //    String javascript="javascript: document.getElementByName('"+web_otp_value+"')[0].value="+sbi_msg_otp+";";
                            String javascript="javascript: document.getElementById('"+web_otp_value+"').value="+sbi_msg_otp+";";

                            AirpayActivity.webView.loadUrl(javascript);

                            String javascript_submit = "javascript: document.getElementsByName('submits')[0].click();";
                            AirpayActivity.webView.loadUrl(javascript_submit);

                        }
                        else if(AirpayActivity.bank_code.equalsIgnoreCase("SBI_VISA_CREDIT"))
                        {
                            // Code for SBI Visa Card ->>>

                            String javascript_visa="javascript: document.getElementById('enterPIN').value="+arr[1]+";";

                            AirpayActivity.webView.loadUrl(javascript_visa);

                            String javascript_submit = "javascript: document.getElementById('sendotp').click();";
                            AirpayActivity.webView.loadUrl(javascript_submit);

                        }
                        else if(AirpayActivity.bank_code.equalsIgnoreCase("SBI_MASTERCARD"))
                        {

                            web_otp_value="otp";

                            String[] sbi_bank_code=arr[20].split(".Don't");

                            for(int i=0;i<sbi_bank_code.length;i++)
                            {
                                sbi_msg_otp = sbi_bank_code[i];

                            }

                            String javascript="javascript: document.getElementsById('"+web_otp_value+"').value="+sbi_msg_otp+";";

                            AirpayActivity.webView.loadUrl(javascript);


                        }
                    }
                }

            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }
}