package com.teju.greenwoods.application.service;

public interface TokenProvider {

    String generateToken(String email, String role);
}