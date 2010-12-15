package controllers;

import models.Member;
import play.mvc.With;

import java.util.List;

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

    @Check("admin")
    public static void listMembers()
    {
        List<Member> members = Member.find("order by username asc").fetch();

        render(members);
    }

}
