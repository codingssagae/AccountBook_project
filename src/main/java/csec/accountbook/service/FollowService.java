package csec.accountbook.service;

import csec.accountbook.domain.Follow;
import csec.accountbook.domain.Member;
import csec.accountbook.repository.FollowRepository;
import csec.accountbook.repository.MemberRepository;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;

    @Transactional
    public void followMember(Long fromUser, Long toUser){
        Follow.PK pk = new Follow.PK(toUser, fromUser);
        if(fromUser.equals(toUser)){
            throw new IllegalStateException("본인은 팔로우 몬한다..");
        }
        if (followRepository.existsById(pk)){
            throw new IllegalStateException("이미 팔로우한 사용자임ㅋ");
        }
        Follow follow = new Follow(toUser, fromUser);
        followRepository.save(follow);

        Optional<Member> follower = memberRepository.findById(fromUser);
        Optional<Member> following = memberRepository.findById(toUser);
        String message = follower.get().getUsername()+"님이 당신을 팔로우했습니다.";
        System.out.println("message = " + message);
        notificationService.sendNotification(following.get().getId(), message);
    }

    @Transactional
    public void unfollowMember(Long fromUser, Long toUser) {
        Follow.PK pk = new Follow.PK(toUser, fromUser);
        if (!followRepository.existsById(pk)) {
            throw new IllegalStateException("팔로우하지 않은 사용자다ㅋ");
        }
        followRepository.deleteById(pk);
    }

    public List<Member> getFollowingList(Long userId){
        List<Follow> follows = followRepository.findAllByFromUser(userId);
        return follows.stream()
                .map(follow -> memberRepository.findById(follow.getToUser()).orElse(null))
                .collect(Collectors.toList());
    }

    public List<Member> getFollowerList(Long userId){
        List<Follow> follows = followRepository.findAllByToUser(userId);
        return follows.stream()
                .map(follow -> memberRepository.findById(follow.getFromUser()).orElse(null))
                .collect(Collectors.toList());
    }

}
