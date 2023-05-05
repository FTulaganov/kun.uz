package com.example.service;

import com.example.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public void run(String... args) throws Exception {
        
    }
}
