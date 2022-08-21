package com.koxx4.simpleworkout.simpleworkoutserver.services;

import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUser;
import com.koxx4.simpleworkout.simpleworkoutserver.data.AppUserPassword;
import com.koxx4.simpleworkout.simpleworkoutserver.data.UserRole;
import com.koxx4.simpleworkout.simpleworkoutserver.data.UserWorkout;
import com.koxx4.simpleworkout.simpleworkoutserver.exceptions.NoSuchAppUserException;
import com.koxx4.simpleworkout.simpleworkoutserver.exceptions.NoSuchWorkoutException;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserPasswordRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserRepository;
import com.koxx4.simpleworkout.simpleworkoutserver.repositories.AppUserWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserRepositoryService implements UserService{

    private final AppUserRepository userRepository;
    private final AppUserPasswordRepository passwordRepository;
    private final AppUserWorkoutRepository workoutRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserRepositoryService(AppUserRepository appUserRepository,
                                 AppUserWorkoutRepository workoutRepository,
                                 AppUserPasswordRepository appPasswordRepository,
                                 PasswordEncoder passwordEncoder) {

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
    public UserWorkout addWorkoutEntryToUser(String nickname, UserWorkout workout) throws NoSuchAppUserException {
        AppUser foundUser = userRepository.findByNickname(nickname).orElseThrow(NoSuchAppUserException::new);
        foundUser.addWorkout(workout);
        return workoutRepository.save(workout);
    }

    @Override
    public void changeUserNickname(String oldNickname, String newNickname) throws NoSuchAppUserException {
        AppUser fetchedUser = userRepository.findByNickname(oldNickname).orElseThrow(NoSuchAppUserException::new);
        fetchedUser.setNickname(newNickname);
        userRepository.save(fetchedUser);
    }

    @Override
    public void changeUserEmail(String userNickname, String newEmail) throws NoSuchAppUserException  {
        AppUser fetchedUser = userRepository.findByNickname(userNickname).orElseThrow(NoSuchAppUserException::new);
        fetchedUser.setEmail(newEmail);
        userRepository.save(fetchedUser);
    }

    @Override
    public void changeUserPassword(String userNickname, CharSequence newPassword) throws NoSuchAppUserException{
        AppUser fetchedUser = userRepository.findByNickname(userNickname).orElseThrow(NoSuchAppUserException::new);
        AppUserPassword newEncodedPassword = new AppUserPassword(fetchedUser, passwordEncoder.encode(newPassword));

        passwordRepository.delete(fetchedUser.getPassword());
        fetchedUser.setPassword(newEncodedPassword);
        userRepository.save(fetchedUser);
    }

    @Override
    public void deleteUserWorkoutEntry(String userNickname, long workoutId) throws NoSuchAppUserException, NoSuchWorkoutException {
        if (!workoutRepository.existsById(workoutId))
            throw new NoSuchWorkoutException(String.format("No workout with ID{%d} found", workoutId));
        if(!userRepository.existsByNickname(userNickname))
            throw new NoSuchAppUserException();

        this.workoutRepository.deleteByAppUserNicknameAndId(userNickname, workoutId);
    }

    @Override
    public void deleteUserWorkoutEntry(String userNickname, UserWorkout userWorkout) throws NoSuchAppUserException, NoSuchWorkoutException{
        this.deleteUserWorkoutEntry(userNickname, userWorkout.getId());
    }

    @Override
    public void deleteAllUserWorkoutEntries(String userNickname) throws NoSuchAppUserException {
        if (!userRepository.existsByNickname(userNickname))
            throw new NoSuchAppUserException();
        this.workoutRepository.deleteAllByAppUserNickname(userNickname);
    }

    @Override
    public void deleteUser(String userNickname) throws NoSuchAppUserException {
        if (!userRepository.existsByNickname(userNickname))
            throw new NoSuchAppUserException();

        this.userRepository.deleteByNickname(userNickname);
    }

    @Override
    public void deleteUser(long id) throws NoSuchAppUserException{
        if (!userRepository.existsById(id))
            throw new NoSuchAppUserException();


        this.userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<UserWorkout>> getAllUserWorkouts(String nickname) {
        var user = userRepository.findByNickname(nickname);
        return user.map(AppUser::getWorkouts);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserWorkout> getUserWorkout(String nickname, long workoutId) {
        return workoutRepository.findByIdAndAppUserNickname(workoutId, nickname);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppUser> getUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppUser> getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    private void identityCheck(String nickname, String email) throws SQLException {
        if(existsByNickname(nickname))
            throw new SQLException("User with this nickname already exists.");

        if(existsByEmail(email))
            throw new SQLException("User with this email already exists.");
    }

}
