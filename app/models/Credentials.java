package models;

import play.db.jpa.Model;
import play.modules.oauthclient.ICredentials;

import javax.persistence.Entity;

@Entity
public class Credentials extends Model implements ICredentials {

        private String token;

        private String secret;

        public void setToken(String token) {
                this.token = token;
        }

        public String getToken() {
                return token;
        }

        public void setSecret(String secret) {
                this.secret = secret;
        }

        public String getSecret() {
                return secret;
        }

}
