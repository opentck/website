package controllers;

import play.cache.Cache;
import play.libs.WS;
import play.mvc.Controller;
import play.mvc.With;

/**
 * Controller that caches Data from Github
 */
public class Github extends OpenTCKBaseController {

    private static final String GITHUB_REPOS_CACHE_NAME = "githubRepos";

    public static void repositories()
    {
        String githubData = (String) Cache.get(GITHUB_REPOS_CACHE_NAME);
        if(githubData == null)
        {
            WS.HttpResponse res = WS.url("http://github.com/api/v2/json/organizations/opentck/public_repositories").get();
            githubData = res.getString();
            Cache.add(GITHUB_REPOS_CACHE_NAME, githubData, "10mn");
        }
        renderJSON(githubData);
    }


    public static void clear()
    {
        if( Secure.Security.connected() == null)
            return;

        if( Secure.Security.check("admin") || Secure.Security.check("automatic_api"))
        {
            Cache.delete(GITHUB_REPOS_CACHE_NAME);
        }
    }
}
