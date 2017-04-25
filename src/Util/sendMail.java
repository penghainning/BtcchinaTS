package Util;

import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class sendMail {
    public static String code="mrwmzhokmhjubfej";
    public static String user="791089735@qq.com";
	  
	  public static synchronized void sendMsg(List<String> to,String title,String content) throws Exception{
		  Properties props = new Properties();
          // 开启debug调试
          props.setProperty("mail.debug", "false");
          // 发送服务器需要身份验证
          props.setProperty("mail.smtp.auth", "true");
          // 设置邮件服务器主机名
          props.setProperty("mail.host", "smtp.qq.com");
          // 发送邮件协议名称
          props.setProperty("mail.transport.protocol", "smtp");
          MailSSLSocketFactory sf = new MailSSLSocketFactory();
          sf.setTrustAllHosts(true);
          props.put("mail.smtp.ssl.enable", "true");
          props.put("mail.smtp.ssl.socketFactory", sf);
          Session session = Session.getInstance(props);

          Message msg = new MimeMessage(session);
          msg.setSubject(title);
          StringBuilder builder = new StringBuilder();
          builder.append(content);
          msg.setText(builder.toString());
          msg.setFrom(new InternetAddress(user));
          Transport transport = session.getTransport();
          transport.connect("smtp.qq.com", user, code);
          Address[] addresss=new Address[to.size()];
          for(int i =0;i<addresss.length;i++){
        	  addresss[i]=new InternetAddress(to.get(i));
          }
          transport.sendMessage(msg, addresss);
          transport.close();
		  
	  }
	  
	  public  static synchronized void sendMsg(String to,String title,String content) throws Exception{
		  Properties props = new Properties();
          // 开启debug调试
          props.setProperty("mail.debug", "false");
          // 发送服务器需要身份验证
          props.setProperty("mail.smtp.auth", "true");
          // 设置邮件服务器主机名
          props.setProperty("mail.host", "smtp.qq.com");
          // 发送邮件协议名称
          props.setProperty("mail.transport.protocol", "smtp");
          MailSSLSocketFactory sf = new MailSSLSocketFactory();
          sf.setTrustAllHosts(true);
          props.put("mail.smtp.ssl.enable", "true");
          props.put("mail.smtp.ssl.socketFactory", sf);
          Session session = Session.getInstance(props);

          Message msg = new MimeMessage(session);
          msg.setSubject(title);
          StringBuilder builder = new StringBuilder();
          builder.append(content);
          msg.setText(builder.toString());
          msg.setFrom(new InternetAddress(user));
          Transport transport = session.getTransport();
          transport.connect("smtp.qq.com", user, code);
          transport.sendMessage(msg, new Address[] { new InternetAddress(to) });
          transport.close();
		  
	  }
	  
	  
	  
	  
}
