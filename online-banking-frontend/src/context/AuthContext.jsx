import React, { createContext, useContext, useEffect, useState } from 'react'

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {

    const [user, setUser] = useState(null);

    const getUserFromCookies = () => {
        const cookie = document.cookie
            .split("; ")
            .find(row => row.startsWith("user_data="));
        if (cookie) {
            const userData = JSON.parse(decodeURIComponent(cookie.split("=")[1])); // Decode and parse user data
            return userData;
        }
        return null;
    };

    const setUserInCookies = (userData) => {
        document.cookie = `user_data=${encodeURIComponent(JSON.stringify(userData))}; path=/`; // Store user data in cookie
    };

    const login = (userData) => {
        setUser(userData);
        setUserInCookies(userData);
    }

    const logout = () => {
        setUser(null);
        document.cookie = "user_data=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT";
    }

    useEffect(() => {
        const userData = getUserFromCookies();
        if (userData) {
            setUser(userData)
        }
    }, [])

    return (
        <AuthContext.Provider value={{ user, login, logout }}>{children}</AuthContext.Provider>
    )

}

