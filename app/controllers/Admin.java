package controllers;

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
