/**
 * This page will be used to display the encrytped message and set the key to decrypt the message
 * If no key is selected then the key will be set to the default letter set in Constants.java ("N")
 * On right swipe a new intent will be called, which inflates the Plain Text Activity, sending over both the user selected key and encrypted message
 * Here we will also receive the values sent over from the Plain Text Activity
 * This values will be taken, and then passed through to the encrypt method in the CaesarCipher.java Class on activity inflation
 * The encrypted message will then be placed in the text block to be viewed by the user. 
 * 
 * @author Nathan Bisson (biss0180)
 * @version 1.0 
 * 
 */

package com.algonquincollege.biss0180.caesarcipher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import util.CaesarCipher;

public class EncryptedTextActivity extends Activity implements Constants {
	private GestureDetector gestureDetector;
	
	private char letter;
	private CharacterPickerDialog letterPickInput;
	private EditText encryptedTextInput;
	
	@SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypted_text);
        
        letter = DEFAULT_LETTER;
        
        letterPickInput = new CharacterPickerDialog(this, new View(this), null, LETTERS, false) {
        	@Override
            public void onClick(View v) {
        		int index = LETTERS.indexOf( ((Button)v).getText().toString() );
        		if ( index >= 0 ) {
        			letter = LETTERS.charAt( index );
        		}
            	dismiss();
            }
        };
        
        Button clearButton = (Button) findViewById( R.id.button_capitalized );
		clearButton.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				encryptedTextInput.setText( "" );
			}
		});
		
		encryptedTextInput = (EditText) findViewById( R.id.text_capitalized );
		
		Bundle bundle = getIntent().getExtras();
		if ( bundle != null ) {
			String plainMessage = bundle.getString( THE_MESSAGE );
			char rletter = bundle.getChar( THE_LETTER );
			encryptedTextInput.setText(CaesarCipher.encrypt(plainMessage, rletter));
		}
		
		gestureDetector = new GestureDetector(
                new SwipeGestureDetector());
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.encrypted_text, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_author) {
            return true;
        }
        if (id == R.id.action_settings) {
        	letterPickInput.show();
			return true;
		}
        return super.onOptionsItemSelected(item);
    }
	
	@Override
	  public boolean onTouchEvent(MotionEvent event) {
	    if (gestureDetector.onTouchEvent(event)) {
	      return true;
	    }
	    return super.onTouchEvent(event);
	  }

	  private void onLeftSwipe() {
	    // Do something
		  
	  }

	  private void onRightSwipe() {
	    // Do something
		  String encryptedText = encryptedTextInput.getText().toString();
		  
		  if(encryptedText.matches("")) {
	    		Toast.makeText(getApplicationContext(), "Please Enter a Message to Decrypt.", Toast.LENGTH_SHORT).show();
	    	} else {
	    		Intent intent = new Intent( getApplicationContext(), PlainTextActivity.class );
		    	intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
		    	intent.putExtra( THE_MESSAGE, encryptedText );
		    	intent.putExtra( THE_LETTER, letter );
		    	startActivity( intent );
	    	}
		}

	  // Private class for gestures
	  private class SwipeGestureDetector 
	          extends SimpleOnGestureListener {
	    // Swipe properties, you can change it to make the swipe 
	    // longer or shorter and speed
	    private static final int SWIPE_MIN_DISTANCE = 120;
	    private static final int SWIPE_MAX_OFF_PATH = 200;
	    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	    @Override
	    public boolean onFling(MotionEvent e1, MotionEvent e2,
	                         float velocityX, float velocityY) {
	      try {
	        float diffAbs = Math.abs(e1.getY() - e2.getY());
	        float diff = e1.getX() - e2.getX();

	        if (diffAbs > SWIPE_MAX_OFF_PATH)
	          return false;
	        
	        // Left swipe
	        if (diff > SWIPE_MIN_DISTANCE
	        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	        	EncryptedTextActivity.this.onLeftSwipe();

	        // Right swipe
	        } else if (-diff > SWIPE_MIN_DISTANCE
	        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	        	EncryptedTextActivity.this.onRightSwipe();
	        }
	      } catch (Exception e) {
	        Log.e("YourActivity", "Error on gestures");
	      }
	      return false;
	    }
	  }
}
