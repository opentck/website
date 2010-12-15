package controllers;

import models.Member;
import play.libs.WS;
import play.mvc.With;

import java.net.URLEncoder;

@With(Secure.class)
public class Twitter extends OpenTCKBaseController {

    public static void index() throws Exception {
        String url = "http://twitter.com/statuses/mentions.xml";

        String mentions = play.libs.WS.url(TwitterAuth.getConnector().sign(getUser().credentials, url)).get().getString();
		render(mentions);
    }

    public static void setStatus(String status) throws Exception {
		String url = "http://twitter.com/statuses/update.json?status=" + URLEncoder.encode(status, "utf-8");
 		String response = TwitterAuth.getConnector().sign(getUser().credentials, WS.url(url), "POST").post().getString();
		request.current().contentType = "application/json";
		renderText(response);
	}

    static Member getUser() {
        return Member.find("username", Security.connected()).first();
    }

}
