package controllers;

import groovy.util.slurpersupport.GPathResult;
import groovy.util.slurpersupport.Node;

import models.OAuthCredentials;
import models.User;
import play.Logger;
import play.cache.Cache;
import play.libs.Codec;
import play.libs.WS;
import play.modules.oauthclient.OAuthClient;
import play.mvc.Controller;
import play.mvc.Router;
import play.mvc.Scope;
import play.templates.JavaExtensions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * OAuth 1 authentication against Twitter.
 */
public class TwitterAuth extends Controller {


        public static void authenticate(String callback) throws Exception {
        // 1: get the request token
        Map<String, Object> args = new HashMap<String, Object>();

        String uuid = Codec.UUID();
        OAuthCredentials cred = new OAuthCredentials();

        Logger.trace("in authenticate, storing uuid " + uuid);
        args.put("callback", callback);
        args.put("uuid", uuid);
        String callbackURL = Router.getFullUrl(request.controller + ".oauthCallback", args);
        String redirectUrl = getConnector().retrieveRequestToken(cred, callbackURL);
        Cache.add(uuid, cred, "3mn");
        redirect(redirectUrl);
    }

    public static void oauthCallback(String callback, String oauth_token, String oauth_verifier, String uuid) throws Exception {
        if(uuid == null)
            throw new IllegalArgumentException("UUID must be given");
        play.Logger.trace("in oauthCallback, uuid is = " + uuid);

        play.Logger.trace("calling the callback");
        play.Logger.trace("callback = " + callback);
        play.Logger.trace("oauth_token = " + oauth_token);
        play.Logger.trace("oauth_verifier = " + oauth_verifier);

        // 2: get the access token
        OAuthCredentials cred;

        play.Logger.trace("getting credentials from cache");
        cred = (OAuthCredentials) Cache.get(uuid);
        if(cred == null)
        {
            throw new IllegalStateException("CREDENTIALS ARE NULL!!!!");
        }
        play.Logger.trace("credentials before retrieveAccessToken");
        play.Logger.trace("cred.getSecret() = " + cred.getSecret());
        play.Logger.trace("cred.getToken() = " + cred.getToken());
        getConnector().retrieveAccessToken(cred, oauth_verifier);


        play.Logger.trace("credentials after retrieveAccessToken");
        play.Logger.trace("cred.getSecret() = " + cred.getSecret());
        play.Logger.trace("cred.getToken() = " + cred.getToken());

//        System.out.println("redirecting");

        play.Logger.trace("getting user data");

        String userDataUrl = getConnector().sign(cred, "http://api.twitter.com/1/account/verify_credentials.xml");
        String userData = WS.url(userDataUrl).get().getString();
        String username = getUsername(userData);
        if(username == null)
            throw new IllegalStateException("Username may not be null");

        User u = getUser(username);
        play.Logger.trace("got user " + u);
        u.credentials = cred;
        u.save();
        play.Logger.trace("saved");

        Scope.Session.current().put("username", username);

        if(callback == null)
            callback = "/loggedInTwitter";
        redirect(callback);
    }


    public static String getUsername(String xmlData){
        GPathResult res = JavaExtensions.asXml(xmlData);
        Iterator iter = res.childNodes();
        while(iter.hasNext())
        {
            Node o = (Node) iter.next();
            if(o.name().equals("screen_name"))
            {
                return o.text();
            }
        }
        return null;
    }

    private static OAuthClient connector = null;

    static OAuthClient getConnector() {
        if (connector == null) {
            connector = new OAuthClient(
                    "http://twitter.com/oauth/request_token",
                    "http://twitter.com/oauth/access_token",
//                    "http://twitter.com/oauth/authorize",
                    "http://twitter.com/oauth/authenticate",
                    "AHvqDoTKrORK40sxRmrEA",
                    "3UGPUmMVHbG6JwdzgzK5R7HHPQ4mWtXuF0CpnBVcxI");
        }
        return connector;
    }

    static User getUser(String username) {
        if(username==null)
            username = Security.connected();
        if(username == null)
            throw new IllegalStateException("Username may not be null, you're not logged in.");
        return User.findOrCreate(username);
    }

}
