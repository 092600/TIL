import java.util.*;

public class Q1316 {
    public static void main(String[] args){
        Scanner stdIn = new Scanner(System.in);
        int N = stdIn.nextInt(); // 단어 입력 개수
        int cnt = 0; // 그룹단어 개수

        for(int i=0; i<N; i++) {
            String in = stdIn.next();
            char [] alpha = new char [in.length()];
            int last = 0;
            // 입력받은 문자열을 문자로 쪼개서 배열에 입력
            for(int j=0; j<in.length(); j++) {
                alpha[j] = in.charAt(j);
            }

            // 단어의 길이가 2 이하라면 무조건 그룹 단어
            if(in.length()<=2) {
                cnt++;
                continue;
            }

            // 그룹 단어인지 아닌지 판단
            loop: // for문 이름 지정
            for(int k=0; k<in.length()-1; k++) {
                if(alpha[k] == alpha[k+1]) // k번과 k+1이 같으면 넘어가
                    continue;

                if(alpha[k] != alpha[k+1]) { // 만약 다르다면
                    for(int h=0; h<k; h++) { // k+1 전에 일치하는 단어가 있으면 그룹단어가 아님
                        if(alpha[h] == alpha[k+1]) {
                            last++;
                            break loop; // 이름 loop인 for문 나가기
                        }
                    }
                    // 일치하는 단어가 없으면 다음 k번 진행
                }
            }

            if(last == 0)
                cnt++;


        }
        System.out.println(cnt);
    }
}