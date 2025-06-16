import api from './api';
import { LoginRequest, LoginResponse, User } from '../types/User';

export const authService = {
    async login(credentials: LoginRequest): Promise<LoginResponse> {
        const response = await api.post<LoginResponse>('/auth/login', credentials);
        const { token, user } = response.data;
        localStorage.setItem('token', token);
        localStorage.setItem('user', JSON.stringify(user));
        return response.data;
    },

    async logout(): Promise<void> {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
    },

    async getCurrentUser(): Promise<User | null> {
        const userStr = localStorage.getItem('user');
        if (!userStr) return null;
        return JSON.parse(userStr);
    },

    isAuthenticated(): boolean {
        return !!localStorage.getItem('token');
    },

    getToken(): string | null {
        return localStorage.getItem('token');
    }
}; 