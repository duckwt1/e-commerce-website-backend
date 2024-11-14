package com.dacs2_be.security;

public class Endpoints {

    public static final String front_end_host = "http://localhost:3000";

    public static final String[] PUBLIC_GET = {
            "/api/product",
            "/api/product/**",
            "/api/category/**",
    };

    public static final String[] PUBLIC_POST = {
            "/auth/login",
            "/auth/register",
            "auth/activate",
            "auth/forgot-password",
            "auth/reset-password",
            "auth/change-password",

    };

    public static final String[] PUBLIC_PUT = {

    };

    public static final String[] PUBLIC_DELETE = {

    };

    public static final String[] ADMIN_ENDPOINT = {
            "/api/user",
            "/api/user/**",
            "/api/category",
            "/api/category/**",
            "/api/product",
            "/api/product/**",
            "/api/order",
            "/api/order/**",
            "/api/order-detail",
            "/api/order-detail/**",
            "/api/feedback",
            "/api/feedback/**",
            "/api/review",
            "/api/review/**",
            "/api/favorite-book",
            "/api/favorite-book/**",
            "/api/cart-item",
            "/api/cart-item/**",
            "/api/cart",
            "/api/cart/**",
            "/api/vnpay",
            "/api/vnpay/**",
    };
}
