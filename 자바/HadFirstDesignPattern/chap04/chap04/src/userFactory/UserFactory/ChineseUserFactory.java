package userFactory.UserFactory;

import country.China;
import user.ChineseUserJob.ChineseDeadBeat;
import user.ChineseUserJob.ChineseOfficer;
import user.ChineseUserJob.ChinesePolice;
import user.ChineseUserJob.ChineseSoccerPlayer;
import user.User.User;

public class ChineseUserFactory implements UserFactory, China{
    private String country = China.COUNTRY;
    public User createUser(String job){
        User user = null;
        
        if (job.equals("police")){
            user = new ChinesePolice();
        } else if (job.equals("officer")){
            user = new ChineseOfficer();
        } else if(job.equals("soccer player")) {
            user = new ChineseSoccerPlayer();
        } else {
            user = new ChineseDeadBeat();
        }

        user.setJob(job);
        user.setCountry(this.country);

        user.takeShower();
        user.wearingClothes();

        System.out.println();
        System.out.println("유저가 준비를 마치고 생성되었습니다.");
        System.out.println();

        return user;
    }

}
