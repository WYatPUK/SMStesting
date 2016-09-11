package com.example.msgtesting;
import java.util.ArrayList; 
import java.util.Iterator; 
  
import android.app.Activity; 
import android.app.PendingIntent; 
import android.content.Intent; 
import android.os.Bundle; 
import android.telephony.SmsManager; 
import android.util.Log; 
import android.view.View; 
import android.widget.Button; 
import android.widget.EditText; 
import android.widget.Toast; 
  
public class Send extends Activity { 
  private String message; 
  private String number ; 
  private EditText editText; 
  private EditText editText2; 
  @Override
  public void onCreate(Bundle savedInstanceState) { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main); 
     editText = (EditText) this.findViewById(R.id.number); 
     editText2 = (EditText)this.findViewById(R.id.message); 
      
    Button button = (Button)this.findViewById(R.id.button); 
    button.setOnClickListener(new View.OnClickListener() { 
        
      public void onClick(View v) { 
         number = editText.getText().toString(); 
         message = editText2.getText().toString(); 
         // 在LogCat中可以查看到number和message的相关信息 
         Log.i("number", number); 
         Log.i("message", message); 
         /*获取系统默认的信息管理器，一定要注意的是SmsManager是android.telephony.SmsManager;这和 
         *我们使用的版本有关，在 Android 2.0 以前 应该使用 android.telephony.gsm.SmsManager 
         *Android 2.0 之后的版本应该用 android.telephony.SmsManager。 
         */ 
        SmsManager smsManager = SmsManager.getDefault(); 
        /*PendingIntent.getBroadcast返回一个用于广播的PendingIntent对象，类似于调用Content.sendBroadcast(); 
         */
        PendingIntent paIntent = PendingIntent.getBroadcast(Send.this, 0, new Intent("SMS_SENT"), 0); 
        PendingIntent deliveryIntent = PendingIntent.getBroadcast(Send.this, 0, new Intent("SMS_DELIVERED"), 0); 
        // smsManager.divideMessage有些时候短信如果超过了字数，我们就需要这个方法来帮我们拆分短信内容。 
        ArrayList<String> smses = smsManager.divideMessage(message); 
        Iterator<String> iterator = smses.iterator(); 
        while(iterator.hasNext()){ 
          String temp = iterator.next(); 
          //发送短信 
          smsManager.sendTextMessage(number, null, temp, paIntent, deliveryIntent); 
        } 
        // 弹出一个浮动框显示提示内容，Toast.LENGTH_LONG代表浮动框显示时间的长短 
        Toast.makeText(Send.this, "短信发送完成", Toast.LENGTH_LONG).show(); 
  
          
      } 
    }); 
      
  } 
} 
