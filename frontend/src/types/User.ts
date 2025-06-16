export interface User {
    id: number;
    username: string;
    email: string;
    role: UserRole;
    createdAt: string;
    updatedAt: string;
}

export enum UserRole {
    ADMIN = 'ADMIN',
    PROJECT_MANAGER = 'PROJECT_MANAGER',
    EMPLOYEE = 'EMPLOYEE'
}

export interface LoginRequest {
    username: string;
    password: string;
}

export interface LoginResponse {
    token: string;
    user: User;
} 