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
         // ��LogCat�п��Բ鿴��number��message�������Ϣ 
         Log.i("number", number); 
         Log.i("message", message); 
         /*��ȡϵͳĬ�ϵ���Ϣ��������һ��Ҫע�����SmsManager��android.telephony.SmsManager;��� 
         *����ʹ�õİ汾�йأ��� Android 2.0 ��ǰ Ӧ��ʹ�� android.telephony.gsm.SmsManager 
         *Android 2.0 ֮��İ汾Ӧ���� android.telephony.SmsManager�� 
         */ 
        SmsManager smsManager = SmsManager.getDefault(); 
        /*PendingIntent.getBroadcast����һ�����ڹ㲥��PendingIntent���������ڵ���Content.sendBroadcast(); 
         */
        PendingIntent paIntent = PendingIntent.getBroadcast(Send.this, 0, new Intent("SMS_SENT"), 0); 
        PendingIntent deliveryIntent = PendingIntent.getBroadcast(Send.this, 0, new Intent("SMS_DELIVERED"), 0); 
        // smsManager.divideMessage��Щʱ�����������������������Ǿ���Ҫ��������������ǲ�ֶ������ݡ� 
        ArrayList<String> smses = smsManager.divideMessage(message); 
        Iterator<String> iterator = smses.iterator(); 
        while(iterator.hasNext()){ 
          String temp = iterator.next(); 
          //���Ͷ��� 
          smsManager.sendTextMessage(number, null, temp, paIntent, deliveryIntent); 
        } 
        // ����һ����������ʾ��ʾ���ݣ�Toast.LENGTH_LONG����������ʾʱ��ĳ��� 
        Toast.makeText(Send.this, "���ŷ������", Toast.LENGTH_LONG).show(); 
  
          
      } 
    }); 
      
  } 
} 
