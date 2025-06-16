import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import Login from '../Login';
import { authService } from '../../services/auth';

// Mock the auth service
jest.mock('../../services/auth', () => ({
    authService: {
        login: jest.fn(),
    },
}));

// Mock useNavigate
const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockNavigate,
}));

describe('Login Component', () => {
    beforeEach(() => {
        jest.clearAllMocks();
    });

    it('renders login form', () => {
        render(
            <BrowserRouter>
                <Login />
            </BrowserRouter>
        );

        expect(screen.getByPlaceholderText('Username')).toBeInTheDocument();
        expect(screen.getByPlaceholderText('Password')).toBeInTheDocument();
        expect(screen.getByRole('button', { name: /log in/i })).toBeInTheDocument();
    });

    it('handles successful login', async () => {
        (authService.login as jest.Mock).mockResolvedValueOnce({ token: 'fake-token', user: {} });

        render(
            <BrowserRouter>
                <Login />
            </BrowserRouter>
        );

        fireEvent.change(screen.getByPlaceholderText('Username'), {
            target: { value: 'testuser' },
        });
        fireEvent.change(screen.getByPlaceholderText('Password'), {
            target: { value: 'password123' },
        });
        fireEvent.click(screen.getByRole('button', { name: /log in/i }));

        await waitFor(() => {
            expect(authService.login).toHaveBeenCalledWith({
                username: 'testuser',
                password: 'password123',
            });
            expect(mockNavigate).toHaveBeenCalledWith('/');
        });
    });

    it('handles login error', async () => {
        (authService.login as jest.Mock).mockRejectedValueOnce(new Error('Invalid credentials'));

        render(
            <BrowserRouter>
                <Login />
            </BrowserRouter>
        );

        fireEvent.change(screen.getByPlaceholderText('Username'), {
            target: { value: 'testuser' },
        });
        fireEvent.change(screen.getByPlaceholderText('Password'), {
            target: { value: 'wrongpassword' },
        });
        fireEvent.click(screen.getByRole('button', { name: /log in/i }));

        await waitFor(() => {
            expect(authService.login).toHaveBeenCalledWith({
                username: 'testuser',
                password: 'wrongpassword',
            });
            expect(mockNavigate).not.toHaveBeenCalled();
        });
    });
}); 