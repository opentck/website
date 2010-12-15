package controllers;

import play.mvc.Controller;
import play.mvc.With;

/**
 * Admin controller for the OpenTCK website.
 */
@With(Secure.class)
public class Admin extends Controller {

    public static void index()
    {


        render();
    }

}
