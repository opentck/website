package controllers;

import play.cache.Cache;
import play.libs.WS;
import play.mvc.Controller;

/**
 * Controller that caches Data from Github
 */
public class Github extends Controller {

    public static void repositories()
    {
        String githubData = (String) Cache.get("githubRepos");
        if(githubData == null)
        {
            WS.HttpResponse res = WS.url("http://github.com/api/v2/json/organizations/opentck/public_repositories").get();
            githubData = res.getString();
            Cache.add("githubRepos", githubData, "10mn");
        }
        renderJSON(githubData);
    }
}
