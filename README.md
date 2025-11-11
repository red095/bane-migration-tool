


# Bane Migration Tool

A full-stack application for migrating clients from LegacyCRM to NewConnect system. This tool provides a simple interface to manage client migration between legacy and new systems.

## 🚀 Features

- **Backend**: Spring Boot REST API with in-memory storage
- **Frontend**: Vue.js 3 + Vuetify responsive UI
- **Client Management**: View legacy and migrated clients
- **One-Click Migration**: Migrate clients with a single click
- **Real-time Updates**: Automatic UI refresh after migration
- **Error Handling**: Comprehensive error handling and user feedback
- **CORS Enabled**: Cross-origin resource sharing for development

## 🛠️ Tech Stack

### Backend
- **Framework**: Spring Boot 3.2.x
- **Language**: Java 17
- **Build Tool**: Maven
- **Storage**: In-memory (ConcurrentHashMap)
- **API**: RESTful endpoints

### Frontend
- **Framework**: Vue.js 3
- **UI Library**: Vuetify 3
- **HTTP Client**: Axios
- **Build Tool**: Vite
- **Language**: JavaScript

## 📁 Project Structure

```
bane-migration-tool/
├── bane-backend/                 # Spring Boot Application
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/inpart/bane/
│   │               ├── BaneApplication.java
│   │               ├── config/
│   │               │   └── CorsConfig.java
│   │               ├── controller/
│   │               │   └── MigrationController.java
│   │               ├── service/
│   │               │   ├── ClientService.java
│   │               │   └── MigrationService.java
│   │               ├── model/
│   │               │   └── Client.java
│   │               └── dto/
│   │                   └── ApiResponse.java
│   └── pom.xml
├── bane-frontend/                # Vue.js Application
│   ├── src/
│   │   ├── components/
│   │   │   ├── MigrationTool.vue
│   │   │   ├── LegacyClients.vue
│   │   │   └── MigratedClients.vue
│   │   ├── services/
│   │   │   └── api.js
│   │   ├── App.vue
│   │   └── main.js
│   ├── package.json
│   └── vite.config.js
└── README.md
```

## 🚀 Quick Start

### Prerequisites
- **Java**: 17 or later
- **Node.js**: 16 or later
- **Maven**: 3.6 or later

### 1. Clone the Repository
```bash
git clone https://github.com/YOUR_USERNAME/bane-migration-tool.git
cd bane-migration-tool
```

### 2. Backend Setup (Spring Boot)
```bash
# Navigate to backend directory
cd bane-backend

# Run the Spring Boot application
./mvnw spring-boot:run

# Or on Windows
mvnw.cmd spring-boot:run
```

The backend will start on **http://localhost:8080**

### 3. Frontend Setup (Vue.js)
Open a new terminal and run:
```bash
# Navigate to frontend directory
cd bane-frontend

# Install dependencies
npm install

# Start development server
npm run dev
```

The frontend will start on **http://localhost:5173**

### 4. Access the Application
- **Frontend**: http://localhost:5173
- **Backend API**: http://localhost:8080/api

## 📚 API Documentation

### Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/legacy/clients` | Get all legacy clients |
| `GET` | `/api/new/clients` | Get all migrated clients |
| `POST` | `/api/migrate/{id}` | Migrate a client by ID |

### Sample Requests

**Get Legacy Clients:**
```bash
curl http://localhost:8080/api/legacy/clients
```

**Migrate a Client:**
```bash
curl -X POST http://localhost:8080/api/migrate/1
```

### Response Format
```json
{
  "success": true,
  "message": "Operation successful",
  "data": [...]
}
```

## 🧪 Testing the Application

1. **Start both applications** (backend and frontend)
2. **Open the frontend** at http://localhost:5173
3. **View Legacy Clients** in the first tab
4. **Click "Migrate"** on any client to move them to the new system
5. **View Migrated Clients** in the second tab to see the results

## 🎯 Usage Example

1. **Legacy Clients Tab**: Displays all clients waiting for migration
    - Each client has a "Migrate" button
    - Clicking the button moves the client to the new system

2. **Migrated Clients Tab**: Shows all successfully migrated clients
    - Displays migration status
    - Real-time updates when clients are migrated

3. **Notifications**: Success/error messages for all operations

## 🔧 Configuration

### Backend Configuration
- Port: 8080
- CORS: Enabled for localhost:5173 and localhost:3000
- Sample data: 5 clients pre-loaded on startup

### Frontend Configuration
- Port: 5173
- API Base URL: http://localhost:8080/api
- UI Framework: Vuetify 3

## 🐛 Troubleshooting

### Common Issues

**Backend won't start:**
- Check Java version (requires 17+)
- Verify port 8080 is available

**Frontend can't connect to backend:**
- Ensure backend is running on port 8080
- Check CORS configuration
- Verify no firewall blocking the connection

**Dependencies issues:**
- Delete `node_modules` and run `npm install` again
- For Maven, delete `target` folder and run `mvn clean install`

### Logs
- Backend logs: Check console output
- Frontend logs: Open browser DevTools (F12) → Console

## 👨‍💻 Development

### Adding New Features
1. Backend: Add new endpoints in `MigrationController`
2. Frontend: Create new components in `src/components/`
3. API: Update `src/services/api.js` for new endpoints

### Code Structure
- **Backend**: Follows Spring Boot MVC pattern
- **Frontend**: Component-based architecture with Vue 3
- **Communication**: REST API with JSON payloads

## 📄 License

This project is developed as a technical assessment for a Full-Stack Developer position.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📞 Support

For issues and questions:
1. Check the troubleshooting section
2. Review browser console for errors
3. Verify both applications are running

---

**Note**: This is a demonstration project with in-memory storage. Data will reset when the application restarts.


