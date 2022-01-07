package com.koxx4.simpleworkout.simpleworkoutserver.services;

import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUser;
import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUserPassword;
import com.koxx4.simpleworkout.simpleworkoutserver.data.UserRole;
import com.koxx4.simpleworkout.simpleworkoutserver.data.UserWorkout;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserPasswordRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UserRepositoryService implements UserService{

    private final AppUserRepository userRepository;
    private final AppUserPasswordRepository passwordRepository;
    private final AppUserWorkoutRepository workoutRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRepositoryService(@Autowired AppUserRepository appUserRepository,
                                 @Autowired AppUserWorkoutRepository workoutRepository,
                                 @Autowired AppUserPasswordRepository appPasswordRepository,
                                 @Autowired PasswordEncoder passwordEncoder) {
        this.userRepository = appUserRepository;
        this.workoutRepository = workoutRepository;
        this.passwordRepository = appPasswordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Uses provided password encoder to encode the provided raw password
     * and binds it to the given AppUser object. Finally, user is persisted in
     * database.
     * @param user AppUser instance with prepared earlier data. Any password set before
     *             will be replaced with new encoded version of provided raw password.
     * @param password Raw password that will be encoded and bound to the user.
     * @throws SQLException If user with the same nickname or/and email already exists.
     * @return Saved user.
     */
    @Override
    public AppUser saveUser(AppUser user, CharSequence password) throws SQLException {
        identityCheck(user.getNickname(), user.getEmail());

        user.setPassword(new AppUserPassword(user, passwordEncoder.encode(password)));

        return userRepository.save(user);
    }

    /**
     * Creates user with given nickname, email and roles.
     * Uses provided password encoder to encode the provided raw password
     * and binds it to user. Finally, user is persisted in database.
     * @param nickname Nickname of user to persist.
     * @param email Email of user to persist.
     * @param password Raw password that will be encoded and bound to the user.
     * @param roles Roles that the user will be given
     * @throws SQLException If user with the same nickname or/and email already exists.
     * @return Saved user.
     */
    @Override
    public AppUser saveUser(String nickname, String email, CharSequence password, String[] roles) throws SQLException {
        identityCheck(nickname, email);

        AppUser newAppUser = new AppUser(email, nickname);
        AppUserPassword newAppUserPassword = new AppUserPassword(newAppUser, passwordEncoder.encode(password));
        newAppUser.setPassword(newAppUserPassword);

        for(var role : roles)
            newAppUser.addRole(new UserRole(role));

        return userRepository.save(newAppUser);
    }

    @Override
    public void addWorkoutEntryToUser(String nickname, UserWorkout workout) {
        var user = userRepository.findByNickname(nickname);

        user.ifPresent(appUser -> {
            appUser.addWorkout(workout);
            userRepository.save(appUser);
        });
    }

    @Override
    public void changeUserNickname(String oldNickname, String newNickname) {
        Optional<AppUser> fetchedUser = userRepository.findByNickname(oldNickname);

        fetchedUser.ifPresent( user -> {
            user.setNickname(newNickname);
            userRepository.save(user);
        });
    }

    @Override
    public void changeUserEmail(String userNickname, String newEmail) {

    }

    @Override
    public void changeUserPassword(String userNickname, CharSequence newPassword) {
        userRepository.findByNickname(userNickname).ifPresent(appUser -> {
                    AppUserPassword newEncodedPassword =
                            new AppUserPassword(appUser, passwordEncoder.encode(newPassword));
                    passwordRepository.delete(appUser.getPassword());
                    appUser.setPassword(newEncodedPassword);
                    userRepository.save(appUser);
                });
    }

    @Override
    public void deleteUserWorkoutEntry(String userNickname, long workoutId) {

    }

    @Override
    public void deleteAllUserWorkoutEntries(String userNickname) {

    }

    @Override
    public void deleteUser(String nickname) {

    }

    @Override
    public void deleteUser(long id) {

    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<List<UserWorkout>> getAllUserWorkouts(String nickname) {
        var user = userRepository.findByNickname(nickname);
        return user.map(AppUser::getWorkouts);
    }

    @Override
    public Optional<UserWorkout> getUserWorkout(String nickname, long workoutId) {
        return workoutRepository.findByIdAndAppUserNickname(workoutId, nickname);
    }

    private void identityCheck(String nickname, String email) throws SQLException {
        if(existsByNickname(nickname))
            throw new SQLException("User with this nickname already exists.");

        if(existsByEmail(email))
            throw new SQLException("User with this email already exists.");
    }

}
