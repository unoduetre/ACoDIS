package pl.lodz.p.pracowniaproblemowa.acodis.profile;

public class Profile {

    private String name;
    private String surname;
    private String duty;
    private int age;
    private int id;
    private int accessLevel;

    public Profile(String name, String surname, String duty, int age, int id, int accessLevel) {
        this.name = name;
        this.surname = surname;
        this.duty = duty;
        this.age = age;
        this.id = id;
        this.accessLevel = accessLevel;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDuty() {
        return duty;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public String getAccessLabel() {
        switch (accessLevel) {
            case 0:
                return "no";
            case 1:
                return "read";
            case 2:
                return "write";
            case 3:
                return "special";
            default:
                return "unknown";
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void setAccessLabel(String access) {
        if (access.equals("read")) {
            this.accessLevel = 1;
        } else if (access.equals("write")) {
            this.accessLevel = 2;
        } else if (access.equals("special")) {
            this.accessLevel = 3;
        } else {
            this.accessLevel = 0;
        }
    }

    public String getLabel() {
        return name + " " + surname;
    }
}
