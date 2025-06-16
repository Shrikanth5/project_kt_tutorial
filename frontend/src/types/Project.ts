export interface Project {
    id: number;
    name: string;
    overview: string;
    architectureDiagram: string;
    businessRequirements: string;
    technicalDocumentation: string;
    apiDocumentation: string;
    setupInstructions: string;
    stage: ProjectStage;
    assignedEmployeeIds: number[];
    projectManagerId: number;
    createdAt: string;
    updatedAt: string;
}

export enum ProjectStage {
    PLANNING = 'PLANNING',
    DEVELOPMENT = 'DEVELOPMENT',
    TESTING = 'TESTING',
    DEPLOYMENT = 'DEPLOYMENT',
    MAINTENANCE = 'MAINTENANCE',
    COMPLETED = 'COMPLETED'
} 