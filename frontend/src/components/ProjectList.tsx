import React, { useState, useEffect } from 'react';
import { Table, Button, Space, message, Modal, Typography } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { projectService } from '../services/api';
import { Project } from '../types';

const { Title } = Typography;
const { confirm } = Modal;

const ProjectList: React.FC = () => {
    const [projects, setProjects] = useState<Project[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [pagination, setPagination] = useState({
        current: 1,
        pageSize: 10,
        total: 0
    });
    const navigate = useNavigate();

    const fetchProjects = async (page = 1, pageSize = 10) => {
        try {
            setLoading(true);
            setError(null);
            const response = await projectService.getAllProjects(page - 1, pageSize);
            setProjects(response.content);
            setPagination({
                current: page,
                pageSize,
                total: response.totalElements
            });
        } catch (err) {
            setError('Failed to fetch projects. Please try again later.');
            message.error('Failed to fetch projects');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchProjects();
    }, []);

    const handleTableChange = (pagination: any) => {
        fetchProjects(pagination.current, pagination.pageSize);
    };

    const handleDelete = (id: number) => {
        confirm({
            title: 'Are you sure you want to delete this project?',
            content: 'This action cannot be undone.',
            okText: 'Yes',
            okType: 'danger',
            cancelText: 'No',
            onOk: async () => {
                try {
                    await projectService.deleteProject(id);
                    message.success('Project deleted successfully');
                    fetchProjects(pagination.current, pagination.pageSize);
                } catch (err) {
                    message.error('Failed to delete project');
                }
            }
        });
    };

    const columns = [
        {
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
            render: (text: string, record: Project) => (
                <a onClick={() => navigate(`/projects/${record.id}`)}>{text}</a>
            )
        },
        {
            title: 'Description',
            dataIndex: 'description',
            key: 'description'
        },
        {
            title: 'Start Date',
            dataIndex: 'startDate',
            key: 'startDate',
            render: (date: string) => new Date(date).toLocaleDateString()
        },
        {
            title: 'End Date',
            dataIndex: 'endDate',
            key: 'endDate',
            render: (date: string) => date ? new Date(date).toLocaleDateString() : '-'
        },
        {
            title: 'Status',
            dataIndex: 'status',
            key: 'status'
        },
        {
            title: 'Actions',
            key: 'actions',
            render: (_: any, record: Project) => (
                <Space>
                    <Button
                        type="primary"
                        icon={<EditOutlined />}
                        onClick={() => navigate(`/projects/${record.id}/edit`)}
                    >
                        Edit
                    </Button>
                    <Button
                        danger
                        icon={<DeleteOutlined />}
                        onClick={() => handleDelete(record.id)}
                    >
                        Delete
                    </Button>
                </Space>
            )
        }
    ];

    return (
        <div>
            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: 16 }}>
                <Title level={2}>Projects</Title>
                <Button
                    type="primary"
                    icon={<PlusOutlined />}
                    onClick={() => navigate('/projects/new')}
                >
                    New Project
                </Button>
            </div>
            {error && (
                <div style={{ marginBottom: 16, color: 'red' }}>
                    {error}
                </div>
            )}
            <Table
                columns={columns}
                dataSource={projects}
                rowKey="id"
                loading={loading}
                pagination={pagination}
                onChange={handleTableChange}
            />
        </div>
    );
};

export default ProjectList; 