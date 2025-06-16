import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link, useNavigate } from 'react-router-dom';
import { Layout, Menu, Button, Space } from 'antd';
import { LogoutOutlined } from '@ant-design/icons';
import ProjectList from './components/ProjectList';
import ProjectForm from './components/ProjectForm';
import ProjectDetail from './components/ProjectDetail';
import Login from './components/Login';
import PrivateRoute from './components/PrivateRoute';
import { authService } from './services/auth';

const { Header, Content, Footer } = Layout;

const AppHeader: React.FC = () => {
    const navigate = useNavigate();
    const isAuthenticated = authService.isAuthenticated();

    const handleLogout = async () => {
        await authService.logout();
        navigate('/login');
    };

    return (
        <Header style={{ display: 'flex', alignItems: 'center' }}>
            <div className="logo" style={{ color: 'white', fontSize: '1.5em', marginRight: '2em' }}>
                Project Management
            </div>
            {isAuthenticated && (
                <>
                    <Menu theme="dark" mode="horizontal" style={{ flex: 1 }}>
                        <Menu.Item key="1">
                            <Link to="/">Projects</Link>
                        </Menu.Item>
                    </Menu>
                    <Button 
                        type="text" 
                        icon={<LogoutOutlined />} 
                        onClick={handleLogout}
                        style={{ color: 'white' }}
                    >
                        Logout
                    </Button>
                </>
            )}
        </Header>
    );
};

const App: React.FC = () => {
    const navigate = useNavigate();

    const handleFormSuccess = () => {
        navigate('/');
    };

    const handleFormCancel = () => {
        navigate(-1);
    };

    return (
        <Router>
            <Layout style={{ minHeight: '100vh' }}>
                <AppHeader />
                <Content style={{ padding: '24px 50px' }}>
                    <Routes>
                        <Route path="/login" element={<Login />} />
                        <Route
                            path="/"
                            element={
                                <PrivateRoute>
                                    <ProjectList />
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/projects/new"
                            element={
                                <PrivateRoute>
                                    <ProjectForm 
                                        onSuccess={handleFormSuccess} 
                                        onCancel={handleFormCancel} 
                                    />
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/projects/:id"
                            element={
                                <PrivateRoute>
                                    <ProjectDetail 
                                        onEdit={() => navigate(`/projects/${window.location.pathname.split('/')[2]}/edit`)} 
                                    />
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/projects/:id/edit"
                            element={
                                <PrivateRoute>
                                    <ProjectForm 
                                        onSuccess={handleFormSuccess} 
                                        onCancel={handleFormCancel} 
                                    />
                                </PrivateRoute>
                            }
                        />
                    </Routes>
                </Content>
                <Footer style={{ textAlign: 'center' }}>
                    Project Management System ©{new Date().getFullYear()}
                </Footer>
            </Layout>
        </Router>
    );
};

export default App; 