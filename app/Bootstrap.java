import models.User;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

/**
 * Dominik Dorn
 * 0626165
 * dominik.dorn@tuwien.ac.at
 */
@OnApplicationStart
public class Bootstrap extends Job<Void> {

    @Override
    public void doJob() throws Exception {

        if(User.count() == 0)
        {
            Fixtures.load("initial-data.yml");
        }
    }
}
