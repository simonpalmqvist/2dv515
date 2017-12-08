package project.users;

import com.fasterxml.jackson.annotation.JsonIgnore;

class User {

    private final String name;

    User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
