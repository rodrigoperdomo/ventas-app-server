package ar.com.rodrigoperdomo.server.services.interfaces;

public interface IEmailSenderService {
  void enviarCorreo(String asunto, String para, String cuerpo);
}
