package utils.common;

import java.io.IOException;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import utils.logging.Log;

public class MailBox {






  public class MailUtils {
    private String host = "smtp.gmail.com"; // for gmail
    private String port = "587";
    private Store store;
    private Folder inboxFolder;
    private Session session;
    public String url = ""; // Url found from email
    private String userName = "";

    /**
     * Constructor of mailbox
     * 
     * @param userName
     * @param password
     * @throws MessagingException
     */
    public MailUtils(String userName, String password) throws MessagingException {
      Properties props = new Properties();
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", port);
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true"); // TLS

      // Doesn't need to create session again
      if (!this.userName.equals(userName)) {
        this.userName = userName;
        session = Session.getInstance(props, new Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
          }
        });
      }

      store = session.getStore("imaps");
      Log.details("Connect to mailbox " + userName + " with password is " + password);
      store.connect("smtp.gmail.com", userName, password);

      inboxFolder = store.getFolder("INBOX");
      inboxFolder.open(Folder.READ_WRITE);
    }

    /**
     * Return last email sent to mailbox
     * 
     * @return Message msg
     * @throws MessagingException
     */
    public Message getLastEmail(boolean unseen) throws MessagingException {
      Message[] msgs;
      if (unseen) {
        Flags seen = new Flags(Flags.Flag.SEEN);
        FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
        msgs = inboxFolder.search(unseenFlagTerm);
      } else {
        msgs = inboxFolder.getMessages();
      }
      FetchProfile fetchProfile = new FetchProfile();
      fetchProfile.add(FetchProfile.Item.ENVELOPE);
      inboxFolder.fetch(msgs, fetchProfile);
      return msgs[msgs.length - 1];
    }

    public Message getLastEmail() throws MessagingException {
      boolean unseen = false;
      Message[] msgs = new Message[5000];
      if (unseen) {
        Flags seen = new Flags(Flags.Flag.SEEN);
        FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
        msgs = inboxFolder.search(unseenFlagTerm);
      } else {
        msgs = inboxFolder.getMessages();
      }
      FetchProfile fetchProfile = new FetchProfile();
      fetchProfile.add(FetchProfile.Item.ENVELOPE);
      inboxFolder.fetch(msgs, fetchProfile);

      if (msgs.length == 0) {
        return null;
      } else {
        return msgs[msgs.length - 1];
      }
    }

    /**
     * Get decoded email content
     * 
     * @param msg
     * @return String content
     * @throws IOException
     * @throws MessagingException
     */
    private String getEmailContent(Message msg) throws MessagingException, IOException {
      String content = "";
      if (msg.isMimeType("text/plain")) {
        content = msg.getContent().toString();
      } else if (msg.isMimeType("multipart/*")) {
        MimeMultipart mimeMultipart = (MimeMultipart) msg.getContent();
        content = getTextFromMimeMultipart(mimeMultipart);
      } else if (msg.isMimeType("text/html")) {
        content = new HtmlUtils((String) msg.getContent()).getRawText();
      }
      return content;
    }

    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart)
        throws MessagingException, IOException {
      String content = "";
      int count = mimeMultipart.getCount();
      for (int i = 0; i < count; i++) {
        BodyPart bodyPart = mimeMultipart.getBodyPart(i);
        if (bodyPart.isMimeType("text/plain")) {
          content += "\n" + bodyPart.getContent();
          break; // without break same text appears twice in my tests
        } else if (bodyPart.isMimeType("text/html")) {
          String html = (String) bodyPart.getContent();
          content += "\n" + org.jsoup.Jsoup.parse(html).text();
        } else if (bodyPart.getContent() instanceof MimeMultipart) {
          content += getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
        }
      }
      return content;
    }

    /**
     * Verify email sent to mailbox: Wait for email with subject then get url and verify its content
     * 
     * @param subject
     * @param content
     * @return
     * @throws Exception
     * @throws MessagingException
     * @throws InterruptedException
     */
    public boolean verifyEmail(String subject, String content, boolean isUrl, int timeout)
        throws InterruptedException, MessagingException, Exception {
      if (!waitForEmail(subject, timeout))
        throw new Exception("Failed to wait for email after ${timeout} seconds");
      Message msg = getLastEmail();
      String emailContent = getEmailContent(msg);
      emailContent = emailContent.trim().replaceAll("(?m)^[ \t]*\r?\n", "").replace("\r", ""); // trim
                                                                                               // and
                                                                                               // remove
                                                                                               // empty
                                                                                               // line

      if (isUrl) {
        url = getUrl(emailContent);
        content = MessageFormat.format(content, url); // add url into content to verify
      }
      if (!content.trim().replaceAll("(?m)^[ \t]*\r?\n", "").replace("\r", "").equals(emailContent)
          && !content.isEmpty()) {
        return false;
      }
      return true;
    }

    public boolean verifyEmail(String subject, String content, String linkText, int timeout)
        throws InterruptedException, MessagingException, Exception {
      // Wait for email
      if (!waitForEmail(subject, timeout)) {
        throw new Exception("Failed to wait for email after " + timeout + " seconds");
      }
      // Verify email content
      Log.details("Verify content:\n" + content);
      Message msg = getLastEmail();
      String emailContent = getEmailContent(msg);
      emailContent = emailContent.trim().replaceAll("(?m)^[ \t]*\r?\n", "").replace("\r", ""); // trim
                                                                                               // and
                                                                                               // remove
                                                                                               // empty
                                                                                               // line

      Log.details("Email content:\n" + emailContent);

      if (!linkText.isEmpty()) {
        url = getUrlFromEmailHtml(msg, linkText);
      }
      if (!content.trim().replaceAll("(?m)^[ \t]*\r?\n", "").replace("\r", "").equals(emailContent)
          && !content.isEmpty()) {
        Log.details("Email content doesn't match");
        return false;
      }
      return true;
    }

    public String getUrlFromEmailHtml(Message msg, String linkText)
        throws MessagingException, IOException {
      if (msg.isMimeType("text/html")) {
        String html = (String) msg.getContent();
        HtmlUtils document = new HtmlUtils(html);
        return document.getUrlFromLinkText(linkText);
      }
      return "";
    }

    /**
     * Return url from email content (First found)
     * 
     * @param: String content
     * @return: String url
     */
    public String getUrl(String content) {
      Pattern linkPattern = Pattern.compile(
          "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
          Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

      Matcher matcher = linkPattern.matcher(content);

      List<String> links = new ArrayList<String>();
      while (matcher.find()) {
        links.add(matcher.group(0));
      }
      try {
        return links.get(0);
      } catch (Exception e) {
        return "";
      }
    }

    /**
     * Wait for email with subject in a number of seconds
     * 
     * @param subject
     * @return
     * @throws InterruptedException
     * @throws MessagingException
     */
    public boolean waitForEmail(String subject, int timeout) throws InterruptedException {
      int duration = 0;
      while (true) {
        Message msg = null;
        try {
          msg = getLastEmail();
          // Case no email in mailbox
          if (msg == null) {
            if (duration >= timeout) {
              return false; // reached timeout
            }
            duration += 5;
            Thread.sleep(5000); // wait 5s then search search for email again
            continue;
          }
          // subject does not match
          if (msg.getSubject().equals(subject))
            return true;// got the mail
          // timeout
          else if (duration >= timeout)
            return false; // reached timeout
          // continue waiting
          else {
            duration += 5;
            Thread.sleep(5000); // wait 5s then search search for email again
            continue;
          }
        } catch (Exception e) {
          Log.details(e.getMessage());
        }
      }
    }

    /**
     * Get last email content
     * 
     * @return
     * @throws MessagingException
     * @throws IOException
     */
    public String getLastEmailContent() throws MessagingException, IOException {
      Message msg = getLastEmail();
      return getEmailContent(msg);
    }

    public void close() throws MessagingException {
      store.close();
    }

    public void send(String dest, String subject, String content, Path attachmentPath)
        throws AddressException, MessagingException {
      Message message = new MimeMessage(session);
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dest));
      message.setSubject(subject);
      message.setContent(content, "text/html");

      Transport.send(message);
    }

    public void archive(String destFolder) throws MessagingException, InterruptedException {
      Folder dest = store.getFolder(destFolder);

      Message[] msgList = inboxFolder.getMessages();
      inboxFolder.copyMessages(msgList, dest);

      // Set to read write
      // if (inboxFolder.isOpen()) {
      // inboxFolder.close();
      // Thread.sleep(1000);
      // inboxFolder.open(Folder.READ_WRITE);
      // }
      // // set flag to deleted
      // for (Message msg : msgList) {
      // msg.setFlag(Flag.DELETED, true);
      // }
      inboxFolder.setFlags(msgList, new Flags(Flags.Flag.DELETED), true);
      inboxFolder.expunge();
    }
  }

}
