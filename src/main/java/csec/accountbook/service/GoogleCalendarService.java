package csec.accountbook.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;

@Service
public class GoogleCalendarService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final OAuth2AuthorizedClientService authorizedClientService; // OAuth2 클라이언트의 인증 정보 관리
    private final ClientRegistrationRepository clientRegistrationRepository; // 클라이언트 등록 정보 관리

    public GoogleCalendarService(OAuth2AuthorizedClientService authorizedClientService, ClientRegistrationRepository clientRegistrationRepository) {
        this.authorizedClientService = authorizedClientService;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    public String addEvent(OAuth2AuthorizedClient client, String summary, String location, String description, LocalDateTime startDateTime, LocalDateTime endDateTime) throws GeneralSecurityException, IOException {
        // OAuth2AuthorizedClient 객체에서 OAuth2 엑세스 토큰 추출
        // 액세스 토큰 객체 생성 -> api 사용 준비 완료
        String accessTokenValue = client.getAccessToken().getTokenValue();
        AccessToken accessToken = new AccessToken(accessTokenValue, null);
        // 구글 캘린더 api 클라이언트 초기화
        Calendar service = new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, new HttpCredentialsAdapter(
                GoogleCredentials.create(accessToken).createScoped(Collections.singleton("https://www.googleapis.com/auth/calendar.events"))))
                .setApplicationName("AccountBookProj")
                .build();
        // 이벤트 객체 설정하고 생성
        Event event = new Event()
                .setSummary(summary)
                .setLocation(location)
                .setDescription(description);

        EventDateTime start = new EventDateTime()
                .setDateTime(new com.google.api.client.util.DateTime(java.util.Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant())))
                .setTimeZone("Asia/Seoul");
        event.setStart(start);

        EventDateTime end = new EventDateTime()
                .setDateTime(new com.google.api.client.util.DateTime(java.util.Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant())))
                .setTimeZone("Asia/Seoul");
        event.setEnd(end);

        String calendarId = "primary";
        event = service.events().insert(calendarId, event).execute();
        return event.getId();
    }

    public void deleteEvent(OAuth2AuthorizedClient client, String eventId) throws GeneralSecurityException, IOException {
        String accessToken = client.getAccessToken().getTokenValue();

        GoogleCredentials credentials = GoogleCredentials.create(new AccessToken(accessToken, null))
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/calendar.events"));

        Calendar service = new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, new HttpCredentialsAdapter(credentials))
                .setApplicationName("AccountBookProj")
                .build();

        String calendarId = "primary";
        service.events().delete(calendarId, eventId).execute();
    }

    //인증된 클라이언트를 가져옴(사용자 인증 상태 확인, 사용자별 맞춤 서비스 제공, 리프레시 토큰을 이용해 액세스 토큰 갱신)
    public OAuth2AuthorizedClient getAuthorizedClient(String clientRegistrationId, String principalName) {
        return authorizedClientService.loadAuthorizedClient(clientRegistrationId, principalName);
    }
}