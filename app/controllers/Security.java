package controllers;

import models.Member;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
public class Security extends Secure.Security {

    public static void login() throws Throwable {
        redirect("/auth_twitter");
    }

    static boolean authenticate(String username, String password)
    {
        return false;
    }

    static void onDisconnected() {
        Application.index();
    }

    static void onAuthenticated() {
        Admin.index();
    }

    static boolean check(String profile) {
        if(connected() == null)
            return false;
        if("admin".equals(profile)) {
            Member u = Member.find("username", connected()).first();
            if(u == null)
                return false;
            return u.isAdmin;
        }
    return false;
}

}
