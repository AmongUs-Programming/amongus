package User;

public class UserInfo {
    private static String name;
    public UserInfo(String name){
        this.name = name;
    }
    public static String getName(){
        return name;
    }
}
