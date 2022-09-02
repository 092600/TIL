package user.User;


public abstract class User {
    private String country;
    private String job;

    public void takeShower(){
        System.out.println("꺠끗하게 씻는 중 입니다.");
    }
    public void wearingClothes(){
        System.out.println("깔끔하게 옷을 입는 중 입니다.");
    }

    public void introducingUserself(){
        System.out.println("Hello");
        System.out.println("i came from "+this.country);
        System.out.println("i'm a "+this.job);
    }

    public void setCountry(String country){
        this.country = country;
    }

    public void setJob(String job){
        this.job = job;
    }

    public String getJob(){
        return this.job;
    }
    
    public String getCountry(){
        return this.country;
    }

}
