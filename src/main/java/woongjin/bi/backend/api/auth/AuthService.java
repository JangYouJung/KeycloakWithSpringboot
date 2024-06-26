package woongjin.bi.backend.api.auth;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woongjin.bi.backend.domain.entity.User;
import woongjin.bi.backend.domain.repository.UserRepository;
import woongjin.bi.backend.exception.ResourceNotFoundException;
import woongjin.bi.backend.util.ResponseCode;
import woongjin.bi.backend.util.SecurityUtil;

@AllArgsConstructor
@Service
public class AuthService {

    private UserRepository userRepository;

    // 로그인 유저 정보 반환 to @CurrentUser
    @Transactional(readOnly = true)
    public User getUserInfo(){
        return userRepository.findByUsernameAndDeletedAtIsNull(SecurityUtil.getCurrentUserName())
                .orElseThrow(() -> new ResourceNotFoundException(ResponseCode.ACCOUNT_NOT_FOUND));
    }


}
