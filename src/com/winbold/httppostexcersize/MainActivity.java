package com.winbold.httppostexcersize;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.redpine.Redpine;

public class MainActivity extends Activity {

EditText username, password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		username = (EditText) findViewById(R.id.editText2);
		password = (EditText) findViewById(R.id.editText3);
		
		Button button = (Button) findViewById(R.id.sendEmail);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//call a method when button clicked
				//pleaseLogin();
				new mySmallTask().execute(username.getText().toString(), password.getText().toString());
				
			}
		});
	}
	
	public class mySmallTask extends AsyncTask<String, Void, JSONObject>{
		
		Context context;
	    private mySmallTask(Context context) {
	        this.context = context.getApplicationContext();
	    }
	    
	    private mySmallTask(){
	    	
	    }

		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			//BaasBox baas = new BaasBox();
			Redpine redpine = new Redpine();
			//JSONObject jsonResponse = redpine.getVersion(params[0], params[1]);
			JSONObject jsonResponse = redpine.Login(params[0], params[1]);
			return jsonResponse;
		}
		
		@Override
		protected void onPostExecute(JSONObject result) {
			Log.i("Winbold", result.toString());
			
			try {
				String response = result.getString("result");
				Log.i("Winbold", response.toString());
				if(response.equalsIgnoreCase("ok")){
					JSONObject data = result.getJSONObject("data");
					Log.i("Winbold", data.toString());
					String session = data.getString("X-BB-SESSION");
					Log.i("Winbold", session.toString());
					Intent intent = new Intent(MainActivity.this, NextActivity.class);
				    startActivity(intent);
				}
				else
					Log.i("Winbold", "Authentication Failed");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
		
	}
}
