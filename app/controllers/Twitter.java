package controllers;

import models.User;
import play.libs.WS;
import play.mvc.Controller;
import play.mvc.With;

import java.net.URLEncoder;

@With(Secure.class)
public class Twitter extends OpenTCKBaseController {

    public static void index() throws Exception {
        String url = "http://twitter.com/statuses/mentions.xml";
        User user = getUser();

        String mentions = play.libs.WS.url(TwitterAuth.getConnector().sign(user.credentials, url)).get().getString();
		render(mentions, user);
    }

    public static void setStatus(String status) throws Exception {
		String url = "http://twitter.com/statuses/update.json?status=" + URLEncoder.encode(status, "utf-8");
 		String response = TwitterAuth.getConnector().sign(getUser().credentials, WS.url(url), "POST").post().getString();
		request.current().contentType = "application/json";
		renderText(response);
	}

    static User getUser() {
        return User.find("byUsername", Security.connected()).first();
    }

}
