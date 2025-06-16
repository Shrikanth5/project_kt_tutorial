import axios from 'axios';
import { LoginRequest, LoginResponse, User } from '../types/auth';
import { Project } from '../types/Project';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080/api';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Add request interceptor for authentication
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export const authService = {
    login: async (data: LoginRequest): Promise<LoginResponse> => {
        const response = await api.post<LoginResponse>('/auth/login', data);
        return response.data;
    },

    register: async (data: User): Promise<User> => {
        const response = await api.post<User>('/auth/register', data);
        return response.data;
    },

    logout: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
    },
};

export const getProjects = async (): Promise<Project[]> => {
    const response = await api.get('/projects');
    return response.data;
};

export const getProject = async (id: number): Promise<Project> => {
    const response = await api.get(`/projects/${id}`);
    return response.data;
};

export const createProject = async (project: Omit<Project, 'id' | 'createdAt' | 'updatedAt'>): Promise<Project> => {
    const response = await api.post('/projects', project);
    return response.data;
};

export const updateProject = async (id: number, project: Partial<Project>): Promise<Project> => {
    const response = await api.put(`/projects/${id}`, project);
    return response.data;
};

export const deleteProject = async (id: number): Promise<void> => {
    await api.delete(`/projects/${id}`);
};

export default api; 