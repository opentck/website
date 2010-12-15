package models;

import play.db.jpa.*;

import javax.persistence.*;



@Entity
public class User extends Model {

        public String username;
        public boolean isAdmin = false;


        public Credentials credentials;

        public User(String username) {
                this.username = username;
                this.credentials = new Credentials();
                this.credentials.save();
        }

        public static User findOrCreate(String username) {
                User user = User.find("username", username).first();
                if (user == null) {
                        user = new User(username);
                }
                return user;
        }

}
