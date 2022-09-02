package SIMZ;

import java.util.Scanner;

import user.User.User;
import userFactory.UserFactory.*;

public class Simz {
    Scanner stdIn = new Scanner(System.in);
    
    public UserFactory selectUserFactory(String country){
        UserFactory userFactory = null;
        
        if ((country.equals("korea"))){
            userFactory = new KoreanUserFactory();
        } else if ((country.equals("japan"))){
            userFactory = new JapaneseUserFactory();
        } else if ((country.equals("china"))) {
            userFactory = new ChineseUserFactory();
        }

        return userFactory;
    }

    public User createUser(){    
        System.out.println("나라를 입력하세요.");
        String country = stdIn.nextLine();
        
        System.out.println("직업을 입력하세요.");
        String job = stdIn.nextLine();

        UserFactory userFactory = selectUserFactory(country);
        
        return userFactory.createUser(job);
    }
    
    public int simzPlay(){
        User user; int result;
        
        System.out.println("1. 유저 생성하기, 0. 종료하기");
        result = stdIn.nextInt();
        
        if ((result != 0) && (result != 1)){
            while (true){
                System.out.println("1. 유저 생성하기, 0. 종료하기");
                result = stdIn.nextInt();
                if ((result == 1) || (result == 0)){
                    break;
                }
            }
        }
        
        if (result == 1){
            System.out.println("유저가 생성되었습니다.");
            user = this.createUser();
            user.introducingUserself();   
            return 1;
        } else {
            System.out.println("종료합니다.");
            return 0;
        }
    
    }
}
