package nl.novi.baccampsite.utils;

import nl.novi.baccampsite.dtos.CharacterResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {
    public static boolean hasRole(UserDetails userDetails, String role) {
        return userDetails.getAuthorities().stream().anyMatch(auth ->
                auth.getAuthority().equals(role));
    }

    public static boolean isSelfOrAdmin(UserDetails userDetails, String username) {
        return userDetails.getUsername().equals(username) || hasRole(userDetails, "ROLE_ADMIN");
    }

    public static boolean isOwner(UserDetails userDetails, CharacterResponseDto dto) {
        return dto.user != null && dto.user.equals(userDetails.getUsername());
    }

    public static boolean isDungeonMaster(UserDetails userDetails, CharacterResponseDto dto) {
        return dto.campaign != null
                && dto.campaign.dungeonMaster != null
                && dto.campaign.dungeonMaster.equals(userDetails.getUsername());
    }

    public static boolean canAccessCharacter(UserDetails userDetails, CharacterResponseDto dto) {
        return dto.user == null
                || isSelfOrAdmin(userDetails, dto.user)
                || isDungeonMaster(userDetails, dto);
    }

    public static boolean canEditCharacter(UserDetails userDetails, CharacterResponseDto dto) {
        return dto.user == null
                || isOwner(userDetails, dto)
                || isDungeonMaster(userDetails, dto);
    }

    public static boolean canSpecCharacter(UserDetails userDetails, CharacterResponseDto dto) {
        return (dto.user == null && isDungeonMaster(userDetails, dto))
                || isOwner(userDetails, dto);
    }

    public static boolean canDeleteCharacter(UserDetails userDetails, CharacterResponseDto dto) {
        return hasRole(userDetails, "ROLE_ADMIN")
                || isOwner(userDetails, dto)
                || (dto.user == null && isDungeonMaster(userDetails, dto));
    }
}
