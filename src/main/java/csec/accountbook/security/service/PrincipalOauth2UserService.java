package csec.accountbook.security.service;

import csec.accountbook.domain.Member;
import csec.accountbook.repository.MemberRepository;
import csec.accountbook.security.oauth.CustomOauth2UserDetails;
import csec.accountbook.security.oauth.GoogleUserInfo;
import csec.accountbook.security.oauth.OAuth2UserInfo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttribute : {}", oAuth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;

        String provider = userRequest.getClientRegistration().getRegistrationId();

        if(provider.equals("google")){
            log.info("login in google");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }

        String providerId = oAuth2UserInfo.getProviderId();
        String loginId = provider + "_" + providerId;
        String username = oAuth2UserInfo.getName();
        String email = oAuth2UserInfo.getEmail();

        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        Member member = null;

        if(optionalMember.isEmpty()){
            member = Member.builder()
                    .loginId(loginId)
                    .username(username)
                    .email(email)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            memberRepository.save(member);
        }else{
            member = optionalMember.get();
        }



        return new CustomOauth2UserDetails(member, oAuth2User.getAttributes());
    }
}
