package org.example.authservice.utils;

import org.example.authservice.model.UserInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * Re-usable stateless validator for user sign-up / update flows.
 */
public final class ValidationUtil {

    private static final Logger log = LoggerFactory.getLogger(ValidationUtil.class);

    /** Very relaxed RFC-5322 pattern – tweak if you need stricter rules. */
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    /**
     * ≥8 chars, at least one upper-case, one lower-case, one digit, one special.
     * Adjust to your security policy.
     */
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])"         // at least 1 digit
                    + "(?=.*[a-z])"          // at least 1 lower
                    + "(?=.*[A-Z])"          // at least 1 upper
                    + "(?=.*[@#$%^&+=!])"    // at least 1 special
                    + ".{8,}$");             // length

    private ValidationUtil() {}   // utility – prevent instantiation

    /**
     * Throws {@link IllegalArgumentException} when validation fails.
     */
    public static void validateUserAttributes(UserInfoDto dto) {

        if (dto == null) {
            throw new IllegalArgumentException("User details can't be null");
        }

        /* ---------- e-mail ---------- */
        String email = dto.getEmail();
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            log.warn("Rejected sign-up – invalid email '{}'", email);
            throw new IllegalArgumentException("Invalid e-mail address");
        }

        /* ---------- password ---------- */
        String rawPassword = dto.getPassword();   // not yet encoded
        if (rawPassword == null || !PASSWORD_PATTERN.matcher(rawPassword).matches()) {
            log.warn("Rejected sign-up for '{}' – weak password", email);
            throw new IllegalArgumentException(
                    "Password must be ≥8 chars and contain upper, lower, digit, special char");
        }

        log.debug("User attributes validated for '{}'", email);
    }
}
