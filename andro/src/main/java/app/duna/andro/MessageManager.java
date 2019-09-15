package app.duna.andro;

public class MessageManager
{
	public static final int ID_SUCCESS=0x0000;
	public static final int ID_NO_TOKEN=0x0001;
	public static final int ID_AUTH_FAILED=0x0005;
	public static final int ID_TOKEN_EXPIRED=0x000B;
	public static final int ID_TOKEN_LOCKED=0x000D;
	public static final int ID_ABSENT=0x0073;
	public static final int ID_SOUND=0x012C;
	public static final int ID_CONNECTION=0x012E;
	public static final int ID_RESPONSE=0x0300;
	public static final int ID_FILE_NAME=0xFF00;
	public static final int ID_FILE_DATA=0xFF01;

	public static String MSG_SUCCESS="Authentication Successful";
	public static String MSG_NO_TOKEN="Card does not exist";
	public static String MSG_AUTH_FAILED="Authentification failed";
	public static String MSG_TOKEN_EXPIRED="Card is expired";
	public static String MSG_TOKEN_LOCKED="Card is locked out";
	public static String MSG_ABSENT="Card is absent";
	public static String MSG_SOUND="Sound is not valid";
	public static String MSG_CONNECTION="No connecion";
	public static String MSG_RESPONSE="Invalid Response";
	public static String MSG_FILE_NAME="Wav file name is empty";
	public static String MSG_FILE_DATA="Wav file is invalid";


	public static String MSG_UNKNOWN="Unknown Error";

	//Retrieve decimal Serial number from Response in case of successful auth
	public static long  getSerialNumber(int responseCode)
	{
		long serial=responseCode;
		if (responseCode<0)
			serial=Integer.MAX_VALUE-responseCode;
		return serial;
	}

	public static long getSerial(int responseCode)
	{
		switch(responseCode)
		{
			case ID_SUCCESS:
			case ID_NO_TOKEN:
			case ID_AUTH_FAILED:
			case ID_TOKEN_EXPIRED:
			case ID_TOKEN_LOCKED:
			case ID_ABSENT:
			case ID_SOUND:
			case ID_CONNECTION:
			case ID_RESPONSE:
			case ID_FILE_NAME:
			case ID_FILE_DATA:
				return -1;
			default:
				return getSerialNumber(responseCode);
		}
	}

	public static String getMessage(int id)
	{
		switch(id)
		{
			case ID_SUCCESS:
				return MSG_SUCCESS;
			case ID_NO_TOKEN:
				return MSG_NO_TOKEN;
			case ID_AUTH_FAILED:
				return MSG_AUTH_FAILED;
			case ID_TOKEN_EXPIRED:
				return MSG_TOKEN_EXPIRED;
			case ID_TOKEN_LOCKED:
				return MSG_TOKEN_LOCKED;
			case ID_ABSENT:
				return MSG_ABSENT;
			case ID_SOUND:
				return MSG_SOUND;
			case ID_CONNECTION:
				return MSG_CONNECTION;
			case ID_RESPONSE:
				return MSG_RESPONSE;
			case ID_FILE_NAME:
				return MSG_FILE_NAME;
			case ID_FILE_DATA:
				return MSG_FILE_DATA;
			default:
				return MSG_SUCCESS;
		}
	}
}
