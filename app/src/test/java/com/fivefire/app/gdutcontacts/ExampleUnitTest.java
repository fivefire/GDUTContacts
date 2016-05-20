package com.fivefire.app.gdutcontacts;

import com.fivefire.app.gdutcontacts.widget.dialpad.query.IUser;
import com.fivefire.app.gdutcontacts.widget.dialpad.query.NineKeyQuery;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testQuery() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User("谢伟鹏", "18819475888"));
        users.add(new User("张树悦", "18819475110"));
        users.add(new User("周杰伦", "18819475444"));
        users.add(new User("张嘉伟", "18819577145"));
        users.add(new User("涨你妹", "18813465813"));
        users.add(new User("龙应台", "18137321351"));
        NineKeyQuery query = new NineKeyQuery();
        List<User> result = (List<User>) query.filter(users, "336494983");
        for (User user : result) {
            System.out.println(user.getName() + user.getPhone());
        }
    }

    private static class User implements IUser {
        public User(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }

        private String name;
        private String phone;

        @Override
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        @Override
        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}