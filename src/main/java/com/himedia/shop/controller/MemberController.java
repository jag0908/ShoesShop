package com.himedia.shop.controller;



import com.google.gson.Gson;
import com.himedia.shop.dto.KakaoProfile;
import com.himedia.shop.dto.MemberVO;
import com.himedia.shop.dto.OAuthToken;
import com.himedia.shop.service.MailService;
import com.himedia.shop.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.lang.reflect.Member;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

@Controller
public class MemberController {

    @Autowired
    MemberService ms;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "member/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("dto") @Valid MemberVO membervo, BindingResult result, Model model, HttpServletRequest request) {
        String url = "member/loginForm";
        if (result.hasFieldErrors("userid"))
            model.addAttribute("message", "아이디를 입력하세요");
        else if (result.hasFieldErrors("pwd"))
            model.addAttribute("message", "패스워드를 입력하세요");
        else {
            MemberVO mvo = ms.getMember(membervo.getUserid());
            if (mvo == null)
                model.addAttribute("message", "아이디와 비번을 확인하세요");
            else if (!mvo.getPwd().equals(membervo.getPwd()))
                model.addAttribute("message", "아이디와 비번을 확이하세요");
            else {
                url = "redirect:/";
                HttpSession session = request.getSession();
                session.setAttribute("loginUser", mvo);
            }
        }
        return url;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return "redirect:/";
    }

    @GetMapping("/kakaostart")
    public @ResponseBody String kakaostart() {
        String exeCode = "<script type='text/javascript'>"
                + "location.href='https://kauth.kakao.com/oauth/authorize?"
                + "client_id=ef831019e9e8dd4b0b460612f92b49d1&"
                + "redirect_uri=http://54.180.138.250:8070/kakaoLogin"
                + "&response_type=code'"
                + "</script>";

        return exeCode;
    }

    @GetMapping("/kakaoLogin")
    public String kakaoLogin(@RequestParam("code") String code, HttpSession session) throws IOException {
        System.out.println("1차 토큰 : " + code);  // 1차 토큰 수신

        // 1차 토큰으로 2차 토큰 요청
        String endpoint = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(endpoint);

        String bodyData = "grant_type=authorization_code";
        bodyData += "&client_id=ef831019e9e8dd4b0b460612f92b49d1";
        bodyData += "&redirect_uri=http://54.180.138.250 :8070/kakaoLogin";
        bodyData += "&code=" + code;
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        conn.setDoOutput(true);
        BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(conn.getOutputStream(), "UTF-8")
        );
        bw.write(bodyData);
        bw.flush();
        // 2차 토큰 수신-----------------------------------------------------------------------------------
        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "UTF-8")
        );
        String input = "";
        StringBuilder sb = new StringBuilder();  // 조각난 String 을 조립하기위한 객체
        while ((input = br.readLine()) != null) {  // 응답 수신
            sb.append(input);      // 수신 내용의 누적
        }

        // 수신된 2차 토큰이 들어있는 내용을 객체에 복사해서 해당 토큰(accessToken, refreshToken 등)을 추출
        // 수신된 내용의 제목들을 변수로 하는 클래스 생성 -> 복사
        Gson gson = new Gson();
        OAuthToken oAuthToken = gson.fromJson(sb.toString(), OAuthToken.class);  // sb -> oAuthToken

        // accessToken 을 이요해서 실제 개인 정보를 요청
        endpoint="https://kapi.kakao.com/v2/user/me";
        url =new URL(endpoint);
        conn=(HttpsURLConnection)url.openConnection();
        conn.setRequestProperty("Authorization", "Bearer "+oAuthToken.getAccess_token());
        conn.setDoOutput(true);
        br=new BufferedReader(
                new InputStreamReader(conn.getInputStream(),"UTF-8")
        );

        // 개인정보 수신
        input="";
        sb=new StringBuilder();
        while((input=br.readLine())!=null) {
            sb.append(input);
        }

        // 수신 내용을 KakaoProfile 클래스 형태로 변환 복사
        KakaoProfile kakaoProfile = gson.fromJson(sb.toString(), KakaoProfile.class);

        String id = kakaoProfile.getId();
        KakaoProfile.KakaoAccount ac = kakaoProfile.getKakao_account();
        KakaoProfile.KakaoAccount.Profile pf =  ac.getProfile();
        String nickname = pf.getNickname();

        MemberVO mvo = ms.getMember(id);
        if(mvo==null){
            mvo = new MemberVO();
            mvo.setUserid(id);
            mvo.setName(nickname);
//            mvo.setEmail(nickname);
            mvo.setProvider("Kakao");
            ms.insert(mvo);
        }
        session.setAttribute("loginUser", mvo);
        return "redirect:/";
    }


    @GetMapping("/contract")
    public String contract() {
        return "member/contract";
    }

    @PostMapping("/joinForm")
    public String joinForm() {
        return "member/joinForm";
    }

    @PostMapping("/idcheck")
    @ResponseBody
    public HashMap<String, Object> idcheck(@RequestParam("userid") String userid) {
        MemberVO mvo = ms.getMember(userid);
        HashMap<String, Object> result = new HashMap<>();
        if(mvo==null){
            result.put("idcheckmsg", 1);
        }else{
            result.put("idcheckmsg", -1);
        }
        result.put("userid", userid);
        return result;
    }

    @PostMapping("/join")
    public String join(@ModelAttribute("dto") @Valid MemberVO membervo, BindingResult result,
                       @RequestParam(value = "reid", required = false, defaultValue = "") String reid,
                       @RequestParam(value = "pwdCheck", required = false, defaultValue = "")String pwdCheck , Model model) {
        String url = "member/joinForm";
        model.addAttribute("reid", reid);
        if(result.hasFieldErrors("userid"))
            model.addAttribute("validmsg", "아이디를 입력하세요");
        else if(!reid.equals(membervo.getUserid()))
            model.addAttribute("validmsg", "아이디 중복 검사를 실시하세요");
        else if(result.hasFieldErrors("pwd"))
            model.addAttribute("validmsg","비밀번호를 입력하세요");
        else if(!pwdCheck.equals(membervo.getPwd()))
            model.addAttribute("validmsg","비밀번호가 일치하지 않습니다");
        else if(result.hasFieldErrors("name"))
            model.addAttribute("validmsg","이름을 입력하세요");
        else if(result.hasFieldErrors("email"))
            model.addAttribute("validmsg","이메일을 입력하세요");
        else if(result.hasFieldErrors("phone"))
            model.addAttribute("validmsg","전화번호를 입력하세요");
        else {
            url = "member/loginForm";
            model.addAttribute("message", "회원가입이 완료되었습니다. 로그인하세요");
            ms.insert(membervo);
        }
        return url;
    }

    @GetMapping("/findacc")
    public String findacc() {
        return "member/findacc";
    }

    @GetMapping("/findId")
    public String findId() {
        return "member/findId";
    }

    @Autowired
    MailService mailService;

    private int number;

    @PostMapping("/sendMail")
    @ResponseBody
    public HashMap<String, Object> sendMail(
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email
    ) {
        System.out.println(name+" "+phone+" "+email);
        HashMap<String, Object> result = new HashMap<>();
        MemberVO mvo = ms.getMemberByNamePhone(name, phone);
        if(mvo==null){
            result.put("findidmsg", "해당 전화번호와 이름으로 회원이 존재하지 않습니다");
        }else{
            number = mailService.sendMail(email);
            result.put("findidmsg", "이메일을 발송했습니다. 메일을 확인하세요");
            result.put("state", "ok");
        }
        return  result;
    }

    @PostMapping("/sendMailForPwd")
    @ResponseBody
    public HashMap<String, Object> sendMailForPwd(
            @RequestParam("userid") String userid,
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email
    ){
        HashMap<String, Object> result = new HashMap<>();
        MemberVO mvo = ms.getMember(userid);
        if(mvo==null) result.put("findpwdmsg", "해당 아이디의 회원이 존재하지 않습니다");
        else if(!mvo.getName().equals(name)) result.put("findpwdmsg", "이름이 일치하지 않습니다");
        else if(!mvo.getPhone().equals(phone)) result.put("findpwdmsg", "전화번호가 일치하지 않습니다");
        else {
            number = mailService.sendMail(email);
            result.put("findpwdmsg", "이메일을 발송했습니다. 메일을 확인하세요");
            result.put("state", "ok");
        }
        return result;
    }

    @PostMapping("/confirmCode")
    public String confirmCode(
            @ModelAttribute("name") @RequestParam("name")  String name,
            @ModelAttribute("phone") @RequestParam("phone")  String phone,
            @ModelAttribute("email") @RequestParam("email")  String email,
            @ModelAttribute("sendok") @RequestParam("sendok") String sendok,
            @RequestParam("code") String code, Model model) {
        String url = "member/findid";
        if(!sendok.equals("ok"))
            model.addAttribute("msg", "이메일 인증을 실행하세요");
        else{
            if(code.equals(String.valueOf(number))){
                // 회원의 아이디를 갖고 conpletFindId.jsp로
                MemberVO mvo = ms.getMemberByNamePhone(name, phone);
                model.addAttribute("userid", mvo.getUserid());
                url = "member/completFindId";
            }else{
                model.addAttribute("msg", "인증번호가 일치하지 않습니다");
            }
        }
        return url;
    }

    @PostMapping("/confirmCodeForPwd")
    public String confirmCodeForPwd(
            @ModelAttribute("userid") @RequestParam("userid")  String userid,
            @ModelAttribute("name") @RequestParam("name")  String name,
            @ModelAttribute("phone") @RequestParam("phone")  String phone,
            @ModelAttribute("email") @RequestParam("email")  String email,
            @ModelAttribute("sendok") @RequestParam("sendok") String sendok,
            @RequestParam("code") String code, Model model) {
        String url = "member/findpwd";
        if(!sendok.equals("ok"))
            model.addAttribute("msg", "이메일 인증을 실행하세요");
        else{
            if(code.equals(String.valueOf(number))){
                MemberVO mvo = ms.getMemberByNamePhone(name, phone);
                model.addAttribute("userid", mvo.getUserid());
                url = "member/resetPwd";
            }else{
                model.addAttribute("msg", "인증번호가 일치하지 않습니다");
            }
        }
        return url;
    }


    @GetMapping("/findpwd")
    public String findpwd(@RequestParam(value = "userid", required = false, defaultValue = "") String userid, Model model) {
        model.addAttribute("userid", userid);
        return "member/findpwd";
    }

    @PostMapping("/resetPwd")
    public String resetPwd(
            @RequestParam(value = "pwd", required = false, defaultValue = "") String pwd,
            @RequestParam(value = "pwdCheck", required = false, defaultValue = "") String pwdCheck,
            @ModelAttribute("userid") @RequestParam("userid") String userid, Model model
    ){
        String url = "member/resetPwd";
        if(pwd.equals(""))
            model.addAttribute("msg", "비밀번호를 입력하세요");
        else if(!pwd.equals(pwdCheck))
            model.addAttribute("msg", "비밀번호 확인이 일치하지 않습니다");
        else{
            ms.updatePwd(userid, pwd);
            url = "member/resetComplete";
        }
        return url;
    }
}