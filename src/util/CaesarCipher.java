/**
 * This class contains two methods, one for encrytpion and one for decrytpion 
 * the encrypt method will take values passed to it from the PlainTextActivity
 * and the decrypt method will take values from the EncryptedTextActivity 
 * Both these methods will loop through the string provided and first check if the character is a letter or a symbol
 * If it is a symbol, no action will be taken
 * if it is a letter the method will check if it is Upper or Lower case and encrypt it accordingly 
 * the method will then return the encrypted or decrypted text back to the activity 
 * 
 * @author Nathan Bisson (biss0180)
 * @version 1.0 
 * 
 */

package util;

public class CaesarCipher {
	
	private static final int MAX_ROTATION = 26;

	public static String encrypt( String plainMessage, char letter ) {
		
		StringBuffer message = new StringBuffer( plainMessage );
		
		for( int i = 0; i < plainMessage.length(); i++ ) 
		{
			char c = plainMessage.charAt( i );
			if(Character.isLetter( c )) 
			{
				if(Character.isUpperCase(c)) 
				{
					char cRotation = letter;
					int encryptedLetterASCII = (((int)c - 65 + ( cRotation - 65)) % MAX_ROTATION) + 65; 
					char encryptedLetter = (char) encryptedLetterASCII;
					message.setCharAt(i, encryptedLetter);
				} 
				else
				{
					char cRotation = letter;
					int encryptedLetterASCII = (((int)c - 65 + ( cRotation - 97)) % MAX_ROTATION) + 97;
					char encryptedLetter = (char) encryptedLetterASCII;
					message.setCharAt(i, encryptedLetter);
				}
			} 
			else 
			{
				 message.setCharAt( i, c );
			}
		}
		return message.toString();
	}
	
	public static String decrypt( String encryptMessage, char letter ) {
		
StringBuffer message = new StringBuffer( encryptMessage );
		
		for( int i = 0; i < encryptMessage.length(); i++ ) 
		{
			char c = encryptMessage.charAt( i );
			if(Character.isLetter( c )) 
			{
				if(Character.isUpperCase(c)) 
				{
					char cRotation = letter;
					int encryptedLetterASCII = (((int)c + 65 - ( cRotation - 65)) % MAX_ROTATION) + 65;
					char encryptedLetter = (char) encryptedLetterASCII;
					message.setCharAt(i, encryptedLetter);
				} 
				else
				{
					char upperLetter = Character.toUpperCase(c);
					char cRotation = letter;
					int encryptedLetterASCII = (((int)upperLetter + 65 - ( cRotation - 65)) % MAX_ROTATION) + 65;
					char encryptedLetter = (char) encryptedLetterASCII;
					char lowerLetter = Character.toLowerCase(encryptedLetter);
					message.setCharAt(i, lowerLetter);
				}
			} 
			else 
			{
				 message.setCharAt( i, c );
			}
		}
		return message.toString();
	}
	
}

//(((int) c + (int) cRotation) % MAX_ROTATION) + (int) 'A';
//((int) c + 'A' + (cRotation - 'A')) % MAX_ROTATION + (int) 'A';
