package models;

import play.db.jpa.Model;
import play.modules.oauthclient.ICredentials;

import javax.persistence.Entity;
import javax.persistence.ExcludeSuperclassListeners;
import javax.persistence.Inheritance;
import java.io.Serializable;

/**
 * OAuth based credentials
 */
@Entity
public class OAuthCredentials extends Model implements ICredentials, Serializable {
    public String token;
    public String secret;

    public void setToken(String token) {
        this.token=token;
    }


    public void setSecret(String secret) {
        this.secret=secret;
    }


    public String getToken() {
        return this.token;
    }


    public String getSecret() {
        return this.secret;
    }
}
