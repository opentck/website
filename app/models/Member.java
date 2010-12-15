package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;


@Entity
public class Member extends Model {

        public String username;
        public boolean isAdmin = false;


    @OneToOne(fetch = FetchType.EAGER)
    public OAuthCredentials credentials = new OAuthCredentials();

        public Member(String username) {
                this.username = username;
                this.credentials = new OAuthCredentials();
//                this.credentials.save();
        }

        public static Member findOrCreate(String username) {
                Member user = Member.find("username", username).first();
                if (user == null) {
                        user = new Member(username);
                }
                return user;
        }

}
