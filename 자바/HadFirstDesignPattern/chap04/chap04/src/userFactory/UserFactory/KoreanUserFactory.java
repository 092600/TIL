package userFactory.UserFactory;

import country.Korea;
import user.KoreanUserJob.KoreanDeadBeat;
import user.KoreanUserJob.KoreanOfficer;
import user.KoreanUserJob.KoreanPolice;
import user.KoreanUserJob.KoreanSoccerPlayer;
import user.User.User;

public class KoreanUserFactory implements UserFactory, Korea {
    private String country = Korea.COUNTRY;
    public User createUser(String job){
        User user = null;
        
        if (job.equals("police")){
            user = new KoreanPolice();
        } else if (job.equals("officer")){
            user = new KoreanOfficer();
        } else if(job.equals("soccer player")) {
            user = new KoreanSoccerPlayer();
        } else {
            user = new KoreanDeadBeat();
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
