package utils.testhelper;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSms {
  public static final String ACCOUNT_SID = "";
  public static final String AUTH_TOKEN = "";

  public static void sendSms() {

  }

  public static void main(String[] args) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    Message message = Message.creator(new PhoneNumber("+84973981639"), // to
        new PhoneNumber("+14084797373"), // from
        "").create();

  }
}
