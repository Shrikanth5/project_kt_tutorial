export interface LoginRequest {
    email: string;
    password: string;
}

export interface LoginResponse {
    token: string;
    email: string;
    name: string;
    roles: string[];
}

export interface User {
    id: number;
    email: string;
    name: string;
    roles: string[];
    enabled: boolean;
    createdAt: string;
    updatedAt: string;
} 