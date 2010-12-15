package controllers;

import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

/**
 * Admin controller for the OpenTCK website.
 */
@With(Secure.class)
public class Admin extends OpenTCKBaseController {


    @Check("admin")
    public static void index()
    {
        render();
    }

}
