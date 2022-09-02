package userFactory.UserFactory;

import country.Japan;
import user.JapaneseUserJob.JapaneseDeadBeat;
import user.JapaneseUserJob.JapaneseOfficer;
import user.JapaneseUserJob.JapanesePolice;
import user.JapaneseUserJob.JapaneseSoccerPlayer;
import user.User.User;

public class JapaneseUserFactory implements UserFactory, Japan {    
    private String country = Japan.COUNTRY;

    public User createUser(String job){
        User user = null;
        
        if (job.equals("police")){
            user = new JapanesePolice();
        
        } else if (job.equals("officer")){
            user = new JapaneseOfficer();

        } else if(job.equals("soccer player")) {
            user = new JapaneseSoccerPlayer();

        } else {
            user = new JapaneseDeadBeat();

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
