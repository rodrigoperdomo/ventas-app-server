package ar.com.rodrigoperdomo.server.services;

import static ar.com.rodrigoperdomo.server.utils.Constantes.EMAIL_ENV;

import ar.com.rodrigoperdomo.server.services.interfaces.IEmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class IEmailSenderServiceImpl implements IEmailSenderService {

  @Autowired JavaMailSender mailSender;

  /**
   * Método para enviar mail
   *
   * @param asunto
   * @param para
   * @param cuerpo
   */
  @Override
  public void enviarCorreo(String asunto, String para, String cuerpo) {
    SimpleMailMessage mensaje = new SimpleMailMessage();
    mensaje.setTo(para);
    mensaje.setSubject(asunto);
    mensaje.setText(cuerpo);
    mensaje.setFrom(System.getenv(EMAIL_ENV));
    mailSender.send(mensaje);
  }
}
