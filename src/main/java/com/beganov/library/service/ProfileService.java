package com.beganov.library.service;

import com.beganov.library.model.Profile;

import java.util.List;

public interface ProfileService {

    Long save(Profile profile);

    Profile getById(Long id);

    List<Profile> getAllProfiles();

    Profile updateEmail(Long id, String newEmail);

    Profile updateBio(Long id, String newBio);

    String delete(Long id);
}
