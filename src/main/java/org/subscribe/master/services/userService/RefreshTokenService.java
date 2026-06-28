package org.subscribe.master.services.userService;

import org.subscribe.master.entities.AuthUser;
import org.subscribe.master.entities.RefreshToken;

public interface RefreshTokenService {

    RefreshToken generateRefreshToken(AuthUser user);

    RefreshToken validateRefreshToken(String token);

    void revokeRefreshToken(String token);

}
