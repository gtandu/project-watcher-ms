package fr.gtandu.service;

public interface UserService {
    /**
     * Checks if a user exists by their ID.
     *
     * @param id The ID of the user to check.
     * @return true if the user exists, false otherwise.
     */
    boolean existsById(String id);
}
