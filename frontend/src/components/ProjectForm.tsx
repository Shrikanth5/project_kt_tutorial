import React from 'react';
import { Form, Input, Select, Button, message } from 'antd';
import { Project, ProjectStage } from '../types/Project';
import { createProject, updateProject } from '../services/api';

const { TextArea } = Input;
const { Option } = Select;

interface ProjectFormProps {
    project?: Project;
    onSuccess: () => void;
    onCancel: () => void;
}

const ProjectForm: React.FC<ProjectFormProps> = ({ project, onSuccess, onCancel }) => {
    const [form] = Form.useForm();
    const [loading, setLoading] = React.useState(false);

    React.useEffect(() => {
        if (project) {
            form.setFieldsValue(project);
        }
    }, [project, form]);

    const handleSubmit = async (values: any) => {
        try {
            setLoading(true);
            if (project) {
                await updateProject(project.id, values);
                message.success('Project updated successfully');
            } else {
                await createProject(values);
                message.success('Project created successfully');
            }
            onSuccess();
        } catch (error) {
            message.error('An error occurred while saving the project');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Form
            form={form}
            layout="vertical"
            onFinish={handleSubmit}
            initialValues={{ stage: ProjectStage.PLANNING }}
        >
            <Form.Item
                name="name"
                label="Project Name"
                rules={[{ required: true, message: 'Please enter project name' }]}
            >
                <Input />
            </Form.Item>

            <Form.Item
                name="overview"
                label="Overview"
                rules={[{ required: true, message: 'Please enter project overview' }]}
            >
                <TextArea rows={4} />
            </Form.Item>

            <Form.Item
                name="architectureDiagram"
                label="Architecture Diagram URL"
                rules={[{ required: true, message: 'Please enter architecture diagram URL' }]}
            >
                <Input />
            </Form.Item>

            <Form.Item
                name="businessRequirements"
                label="Business Requirements"
                rules={[{ required: true, message: 'Please enter business requirements' }]}
            >
                <TextArea rows={4} />
            </Form.Item>

            <Form.Item
                name="technicalDocumentation"
                label="Technical Documentation"
                rules={[{ required: true, message: 'Please enter technical documentation' }]}
            >
                <TextArea rows={4} />
            </Form.Item>

            <Form.Item
                name="apiDocumentation"
                label="API Documentation"
                rules={[{ required: true, message: 'Please enter API documentation' }]}
            >
                <TextArea rows={4} />
            </Form.Item>

            <Form.Item
                name="setupInstructions"
                label="Setup Instructions"
                rules={[{ required: true, message: 'Please enter setup instructions' }]}
            >
                <TextArea rows={4} />
            </Form.Item>

            <Form.Item
                name="stage"
                label="Project Stage"
                rules={[{ required: true, message: 'Please select project stage' }]}
            >
                <Select>
                    {Object.values(ProjectStage).map((stage) => (
                        <Option key={stage} value={stage}>
                            {stage}
                        </Option>
                    ))}
                </Select>
            </Form.Item>

            <Form.Item>
                <Button type="primary" htmlType="submit" loading={loading}>
                    {project ? 'Update Project' : 'Create Project'}
                </Button>
                <Button style={{ marginLeft: 8 }} onClick={onCancel}>
                    Cancel
                </Button>
            </Form.Item>
        </Form>
    );
};

export default ProjectForm; 