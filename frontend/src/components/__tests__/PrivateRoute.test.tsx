import React from 'react';
import { render, screen } from '@testing-library/react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import PrivateRoute from '../PrivateRoute';
import { authService } from '../../services/auth';

// Mock the auth service
jest.mock('../../services/auth', () => ({
    authService: {
        isAuthenticated: jest.fn(),
    },
}));

const TestComponent = () => <div>Protected Content</div>;

describe('PrivateRoute Component', () => {
    beforeEach(() => {
        jest.clearAllMocks();
    });

    it('renders children when authenticated', () => {
        (authService.isAuthenticated as jest.Mock).mockReturnValue(true);

        render(
            <BrowserRouter>
                <Routes>
                    <Route
                        path="/"
                        element={
                            <PrivateRoute>
                                <TestComponent />
                            </PrivateRoute>
                        }
                    />
                </Routes>
            </BrowserRouter>
        );

        expect(screen.getByText('Protected Content')).toBeInTheDocument();
    });

    it('redirects to login when not authenticated', () => {
        (authService.isAuthenticated as jest.Mock).mockReturnValue(false);

        render(
            <BrowserRouter>
                <Routes>
                    <Route
                        path="/"
                        element={
                            <PrivateRoute>
                                <TestComponent />
                            </PrivateRoute>
                        }
                    />
                    <Route path="/login" element={<div>Login Page</div>} />
                </Routes>
            </BrowserRouter>
        );

        expect(screen.getByText('Login Page')).toBeInTheDocument();
    });
}); 