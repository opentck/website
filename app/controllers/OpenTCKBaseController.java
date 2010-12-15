package controllers;

import models.User;
import play.mvc.Before;
import play.mvc.Controller;

/**
 * Base Controller for the OpenTCK Website, allowing to
 * get the User object of the logged in user on any page.
 */
public class OpenTCKBaseController extends Controller {
    @Before
    static void setConnectedUser(){
        if(Security.isConnected()){
            User user = User.find("username", Security.connected()).first();
            if(user != null){
                renderArgs.put("user", user.username);
            }
        }
    }

}
