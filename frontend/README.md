# Project Management System Frontend

This is the frontend application for the Project Management System, built with React, TypeScript, and Ant Design.

## Features

- View list of projects
- Create new projects
- View project details
- Edit existing projects
- Responsive design
- Modern UI with Ant Design components

## Prerequisites

- Node.js (v14 or higher)
- npm (v6 or higher)

## Getting Started

1. Install dependencies:
```bash
npm install
```

2. Create a `.env` file in the root directory with the following content:
```
REACT_APP_API_BASE_URL=http://localhost:8080/api
```

3. Start the development server:
```bash
npm start
```

The application will be available at http://localhost:3000.

## Available Scripts

- `npm start` - Runs the app in development mode
- `npm test` - Launches the test runner
- `npm run build` - Builds the app for production
- `npm run eject` - Ejects from Create React App

## Project Structure

```
src/
  ├── components/         # React components
  ├── services/          # API services
  ├── types/             # TypeScript type definitions
  ├── App.tsx           # Main application component
  └── index.tsx         # Application entry point
```

## Dependencies

- React 18
- TypeScript
- Ant Design
- React Router
- Axios

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 