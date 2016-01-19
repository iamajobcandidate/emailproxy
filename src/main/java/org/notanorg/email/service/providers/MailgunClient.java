package org.notanorg.email.service.providers;

import javax.ws.rs.core.MediaType;

import org.notanorg.email.service.EmailException;
import org.notanorg.email.service.EmailSendRequest;
import org.notanorg.email.service.EmailServiceProvider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class MailgunClient implements EmailServiceProvider {

  // please use an external config. please
  private static final String AUTH_KEY = "key-ed00257f2e5464362e5b52ac0f0d628c";
  private static final String API_ENDPOINT = "https://api.mailgun.net/v3/sandboxe0fe52484f824070b4a974986b43ed75.mailgun.org/messages";
  private static final String SENDER_EMAIL = "Mailgun Sandbox <postmaster@sandboxe0fe52484f824070b4a974986b43ed75.mailgun.org>";

  private Client client;

  public MailgunClient() {
    client = Client.create();
    client.addFilter(new HTTPBasicAuthFilter("api", AUTH_KEY));
  }

  public void send(EmailSendRequest request) throws EmailException {
    WebResource webResource = client.resource(API_ENDPOINT);
    MultivaluedMapImpl formData = new MultivaluedMapImpl();
    formData.add("from", SENDER_EMAIL);
    formData.add("to", request.getTo());
    formData.add("subject", request.getSubject());
    formData.add("text", request.getMessage());

    try {
      ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class,
          formData);
      if (response.getStatus() >= 400) {
        throw new EmailException("Mailgun API failed with HTTP status " + response.getStatus());
      }
    } catch (EmailException e) {
      throw e;
    } catch (Exception e) {
      throw new EmailException(e);
    }
  }
}
