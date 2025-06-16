import React from 'react';
import { Card, Descriptions, Tag, Button, Space, Typography } from 'antd';
import { Project, ProjectStage } from '../types/Project';
import { getProject } from '../services/api';

const { Title, Paragraph } = Typography;

interface ProjectDetailProps {
    projectId: number;
    onEdit: () => void;
}

const ProjectDetail: React.FC<ProjectDetailProps> = ({ projectId, onEdit }) => {
    const [project, setProject] = React.useState<Project | null>(null);
    const [loading, setLoading] = React.useState(true);

    React.useEffect(() => {
        const fetchProject = async () => {
            try {
                const data = await getProject(projectId);
                setProject(data);
            } catch (error) {
                console.error('Error fetching project:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchProject();
    }, [projectId]);

    const getStageColor = (stage: ProjectStage) => {
        const colors: Record<ProjectStage, string> = {
            [ProjectStage.PLANNING]: 'blue',
            [ProjectStage.DEVELOPMENT]: 'orange',
            [ProjectStage.TESTING]: 'purple',
            [ProjectStage.DEPLOYMENT]: 'cyan',
            [ProjectStage.MAINTENANCE]: 'green',
            [ProjectStage.COMPLETED]: 'success',
        };
        return colors[stage];
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (!project) {
        return <div>Project not found</div>;
    }

    return (
        <div>
            <Space direction="vertical" size="large" style={{ width: '100%' }}>
                <Card>
                    <Space direction="vertical" size="middle" style={{ width: '100%' }}>
                        <Space justify="space-between" style={{ width: '100%' }}>
                            <Title level={2}>{project.name}</Title>
                            <Button type="primary" onClick={onEdit}>
                                Edit Project
                            </Button>
                        </Space>
                        <Tag color={getStageColor(project.stage)}>{project.stage}</Tag>
                    </Space>
                </Card>

                <Card title="Project Overview">
                    <Paragraph>{project.overview}</Paragraph>
                </Card>

                <Card title="Architecture Diagram">
                    <img
                        src={project.architectureDiagram}
                        alt="Architecture Diagram"
                        style={{ maxWidth: '100%', height: 'auto' }}
                    />
                </Card>

                <Card title="Business Requirements">
                    <Paragraph>{project.businessRequirements}</Paragraph>
                </Card>

                <Card title="Technical Documentation">
                    <Paragraph>{project.technicalDocumentation}</Paragraph>
                </Card>

                <Card title="API Documentation">
                    <Paragraph>{project.apiDocumentation}</Paragraph>
                </Card>

                <Card title="Setup Instructions">
                    <Paragraph>{project.setupInstructions}</Paragraph>
                </Card>

                <Card title="Project Details">
                    <Descriptions bordered>
                        <Descriptions.Item label="Created At">
                            {new Date(project.createdAt).toLocaleDateString()}
                        </Descriptions.Item>
                        <Descriptions.Item label="Last Updated">
                            {new Date(project.updatedAt).toLocaleDateString()}
                        </Descriptions.Item>
                        <Descriptions.Item label="Project Manager ID">
                            {project.projectManagerId}
                        </Descriptions.Item>
                    </Descriptions>
                </Card>
            </Space>
        </div>
    );
};

export default ProjectDetail; 