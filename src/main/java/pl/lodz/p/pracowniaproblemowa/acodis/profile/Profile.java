package pl.lodz.p.pracowniaproblemowa.acodis.profile;

public class Profile {

    private String name;
    private String surname;
    private String duty;
    private int age;
    private int id;
    private int accessLevel;

    public Profile() {
        this.name = "Jan";
        this.surname = "Kowalski";
        this.duty = "programista";
        this.age = 28;
        this.id = 1122334455;
        this.accessLevel = 2;
    }

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

}
